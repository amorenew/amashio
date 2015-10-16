package co.mazeed.smsproject.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.mazeed.smsproject.Activities.AllContactsGroup;
import co.mazeed.smsproject.Adapters.ContactsAdapter;
import co.mazeed.smsproject.Adapters.ContactsGroupAdapter;
import co.mazeed.smsproject.R;
import co.mazeed.smsproject.controller.communication.AsyncTaskInvoker;
import co.mazeed.smsproject.controller.communication.DataRequestor;
import co.mazeed.smsproject.controller.communication.Task;
import co.mazeed.smsproject.storage.ServiceStorage;
import co.mazeed.smsproject.tasks.GetContactsGroupList;
import co.mazeed.smsproject.tasks.GetContactsListPerGroup;
import co.mazeed.smsprojects.model.ContactData;
import co.mazeed.smsprojects.model.GroupInfo;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ContactsGroupFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ContactsGroupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactsGroupFragment extends android.support.v4.app.Fragment implements DataRequestor{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ListView lvContacts;
    private ContactsGroupAdapter contactsAdapter;
    String[] aNameFromContacts;
    String[]aNames;

    SimpleCursorAdapter groupAdapter;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public OnFragmentInteractionListener mListener;
    private ArrayList<GroupInfo> groups=new ArrayList<GroupInfo>();
    private SharedPreferences appSettin;
    private ProgressDialog mSpinnerProgress;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AllContactsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ContactsGroupFragment newInstance(String param1, String param2) {
        ContactsGroupFragment fragment = new ContactsGroupFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ContactsGroupFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts_group, container, false);
        lvContacts = (ListView) view.findViewById(R.id.lvList);
        appSettin = this.getActivity().getSharedPreferences("SMS", Context.MODE_PRIVATE);

        if(ServiceStorage.contactsGroupList!=null&& ServiceStorage.contactsGroupList.size()>0) {
            contactsAdapter = new ContactsGroupAdapter(getActivity(), ServiceStorage.contactsGroupList);
            lvContacts.setAdapter(contactsAdapter);
        }
        else {
            getContactsGroups();
        }

//        new getContactsAsynTask(this.getActivity()).execute();
//        groupAdapter = new SimpleCursorAdapter(getActivity().getApplicationContext(),
//                android.R.layout.select_dialog_singlechoice, GroupCursor(),
//                new String[] { ContactsContract.Groups.TITLE },
//                new int[] { android.R.id.text1 });
//        lvContacts.setAdapter(groupAdapter);
//        lvContacts.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        lvContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            itemclick(id);
                getContactsinGroup(String.valueOf(id));
            }
        });

        // Inflate the layout for this fragment
        return view;
    }
private  void getContactsinGroup(String id)
{
    String token=appSettin.getString("TokenKey","");
    HashMap<String,String>input=new HashMap<>();
    input.put("tokenkey",token);
    input.put("groupId",id);
    Task task = new GetContactsListPerGroup(this, this.getActivity().getApplicationContext(), input);
    AsyncTaskInvoker.RunTaskInvoker(task);

}
  private void  getContactsGroups()
  {
      String token=appSettin.getString("TokenKey", "");
      HashMap<String,String>input=new HashMap<>();
      input.put("tokenkey", token);
      Task task = new GetContactsGroupList(this, this.getActivity().getApplicationContext(), input);
      AsyncTaskInvoker.RunTaskInvoker(task);
  }
  private void itemclick(long id)
    {
        long groupId = id;
        String[] cProjection = { ContactsContract.Contacts.DISPLAY_NAME, CommonDataKinds.GroupMembership.CONTACT_ID };

        Cursor groupCursor =this.getActivity(). getContentResolver().query(
                ContactsContract.Data.CONTENT_URI,
                cProjection,
                CommonDataKinds.GroupMembership.GROUP_ROW_ID + "= ?" + " AND "
                        + CommonDataKinds.GroupMembership.MIMETYPE + "='"
                        + CommonDataKinds.GroupMembership.CONTENT_ITEM_TYPE + "'",
                new String[]{String.valueOf(groupId)}, null);
        aNames=new String[groupCursor.getCount()];
        int i=0;

        if (groupCursor != null && groupCursor.moveToFirst())
        {
            do
            {

                int nameCoumnIndex = groupCursor.getColumnIndex(Phone.DISPLAY_NAME);

                String name = groupCursor.getString(nameCoumnIndex);

                long contactId = groupCursor.getLong(groupCursor.getColumnIndex(CommonDataKinds.GroupMembership.CONTACT_ID));

                Cursor numberCursor = this.getActivity().getContentResolver().query(Phone.CONTENT_URI,
                        new String[]{Phone.NUMBER}, Phone.CONTACT_ID + "=" + contactId, null, null);

                if (numberCursor.moveToFirst())
                {
                    int numberColumnIndex = numberCursor.getColumnIndex(Phone.NUMBER);
                    do
                    {
                        String phoneNumber = numberCursor.getString(numberColumnIndex);
                        Log.d("your tag", "contact " + name + ":" + phoneNumber);
                        aNames[i]=name;
                        i++;
                    } while (numberCursor.moveToNext());
                    numberCursor.close();
                }
            } while (groupCursor.moveToNext());
            groupCursor.close();
            Bundle b=new Bundle();
            b.putStringArray("Contacts", aNames);
            Intent inent=new Intent(this.getActivity(), AllContactsGroup.class);
            inent.putExtras(b);
            startActivity(inent);
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onStart(Task task) {
        
    }

    @Override
    public void onFinish(Task task) {
        if(task.getId()== Task.TaskID.GetContactsGroupTask)
        {
            if(ServiceStorage.contactsGroupList!=null&&ServiceStorage.contactsGroupList.size()>0) {
                contactsAdapter = new ContactsGroupAdapter(getActivity(), ServiceStorage.contactsGroupList);
                lvContacts.setAdapter(contactsAdapter);
            }
        }
        else if(task.getId()== Task.TaskID.GetContactsPerGroupTask)
        {
            if(task.getResult() instanceof String) {

            }else{
                ArrayList<ContactData> result = (ArrayList<ContactData>) task.getResult();
                Bundle b = new Bundle();
                b.putSerializable("Contacts", result);
                Intent inent = new Intent(this.getActivity(), AllContactsGroup.class);
                inent.putExtras(b);
                startActivity(inent);
            }
        }

    }

    @Override
    public void handleClick() {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
        public void onGroupselected(String Groupselected ,HashMap<String,GroupInfo>groupInfoHashMap);
    }

    private Cursor GroupCursor() {
        String[] projection = { ContactsContract.Groups.TITLE,
                ContactsContract.Groups._ID,ContactsContract.Groups.SUMMARY_WITH_PHONES
        };
        Cursor gCursor = this.getActivity().getContentResolver().query(   ContactsContract.Groups.CONTENT_SUMMARY_URI,
                projection,
                ContactsContract.Groups.DELETED+"!='1' AND "+
                        ContactsContract.Groups.GROUP_VISIBLE+"!='0' "
                ,
                null,
                null);

         int IDX_ID = gCursor.getColumnIndex(ContactsContract.Groups._ID);
        final int IDX_TITLE = gCursor.getColumnIndex(ContactsContract.Groups.TITLE);
        Map<String,GroupInfo> m = new HashMap<String, GroupInfo>();

        while (gCursor.moveToNext()) {
            GroupInfo g = new GroupInfo();
            g.id = gCursor.getString(IDX_ID);
            g.title = gCursor.getString(IDX_TITLE);
            int users = gCursor.getInt(gCursor.getColumnIndex(ContactsContract.Groups.SUMMARY_WITH_PHONES));
            if (users>0) {
                // group with duplicate name?
                GroupInfo g2 = m.get(g.title);
                if (g2==null) {
                    m.put(g.title, g);
                    groups.add(g);
                } else {
                    g2.id+=","+g.id;
                }
            }
        }
//        if(idcolumn!=-1) {
//            String id = gCursor.getString(idcolumn);

//        }
        return gCursor;

    }
public void getGroupList() {

//    final ContentResolver cr = this.getActivity().getContentResolver();
//
//
    int i = 0;
//        Uri groupsUri = ContactsContract.Groups.CONTENT_SUMMARY_URI;
//    String where = "((" + ContactsContract.Groups.GROUP_VISIBLE + " = 1) AND ("
//            + ContactsContract.Groups.SUMMARY_WITH_PHONES + "!= 0))";
//    Cursor cur = cr.query(groupsUri, null, where, null, null);
    Cursor cur = GroupCursor();
    aNameFromContacts = new String[cur.getCount()];
    Map<String,GroupInfo> m = new HashMap<String, GroupInfo>();

    while (cur.moveToNext()) {
        String id = cur.getString(cur.getColumnIndex(ContactsContract.Groups._ID));
        String name = cur.getString(cur.getColumnIndex(ContactsContract.Groups.TITLE));
        GroupInfo g = new GroupInfo();
        g.id=id;
        g.title=name;
        groups.add(g);

        Log.i("Names", name);
        GroupInfo g2 = m.get(name);
        if (g2==null) {
            m.put(name, g);
            aNameFromContacts[i] = name;
            i++;
//            groups.add(g);
        } else {
            g2.id+=","+g.id;
        }
//        long contactId = cur.getLong(cur.getColumnIndex(ContactsContract.CommonDataKinds.GroupMembership.CONTACT_ID);
//        if ( > 0) {
////                // Query phone here. Covered next
//            Cursor phones = cr.query(Phone.CONTENT_URI, null, Phone.CONTACT_ID + " = " + id, null, null);
//            while (phones.moveToNext()) {
//                String phoneNumber = phones.getString(phones.getColumnIndex(Phone.NUMBER));
//                Log.d("Number", phoneNumber);
//                int type = phones.getInt(phones.getColumnIndex(Phone.TYPE));
//                switch (type) {
//                    case Phone.TYPE_HOME:
//                        // do something with the Home number here...
//                        break;
//                    case Phone.TYPE_MOBILE:
//                        // do something with the Mobile number here...
//                        break;
//                    case Phone.TYPE_WORK:
//                        // do something with the Work number here...
//                        break;
//                }
//            }
//            phones.close();
//        }


    }


    cur.close();



}
private class getContactsAsynTask extends AsyncTask<Void, Void, Void>
{
    private Context mContext;
    private ProgressDialog loadingDialog;

    getContactsAsynTask(Context context)
    {
        this.mContext = context;
    }

    @Override
    protected void onPreExecute()
    {
//        loadingDialog = new ProgressDialog(mContext);
//        loadingDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        loadingDialog.setMessage("Loading ...");
//        loadingDialog.setCancelable(false);
//        loadingDialog.show();

        super.onPreExecute();
    }
    @Override
    protected Void doInBackground(Void... params) {
        // **Code**
        getGroupList();
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
//            super.onPostExecute(result);
//        contactsAdapter = new ContactsGroupAdapter(getActivity(), groups);
//        lvContacts.setAdapter(contactsAdapter);
//        loadingDialog.cancel();
    }
}
}
