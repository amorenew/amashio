package co.mazeed.smsproject.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;

import java.util.HashMap;

import co.mazeed.smsproject.Activities.ChooseContactActivity;
import co.mazeed.smsproject.Activities.HomeActivity;
import co.mazeed.smsproject.Adapters.ContactsAdapter;
import co.mazeed.smsproject.R;
import co.mazeed.smsproject.controller.communication.AsyncTaskLocalListener;
import co.mazeed.smsproject.storage.ServiceStorage;
import co.mazeed.smsproject.tasks.getContactsAsynTask;
import co.mazeed.smsprojects.model.ContactData;
import co.mazeed.smsprojects.model.GroupInfo;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;
/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AllContactsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AllContactsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllContactsFragment extends android.support.v4.app.Fragment implements
        AsyncTaskLocalListener ,ContactsAdapter.onContactSelectorUnSelect{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private StickyListHeadersListView lvContacts;
    private ContactsAdapter contactsAdapter;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public  OnFragmentInteractionListener mListener;

    private EditText etFilter;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AllContactsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AllContactsFragment newInstance(String param1, String param2) {
        AllContactsFragment fragment = new AllContactsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public AllContactsFragment() {
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
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);
        lvContacts = (StickyListHeadersListView) view.findViewById(R.id.lvList);
        etFilter = (EditText) view.findViewById(R.id.etFilter);

//        lvContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Log.d("OnItemclick", "Cnatct"+((ContactData)contactsAdapter.getItem(position)).getContactName());
//                mListener.onContactSelected(((ContactData) contactsAdapter.getItem(position)));
//
//            }
//        });



        etFilter.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                contactsAdapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });
        if(ServiceStorage.contactsList!=null&&ServiceStorage.contactsList.size()>0) {
            contactsAdapter = new ContactsAdapter(getActivity(),ServiceStorage.contactsList,this);
            lvContacts.setAdapter(contactsAdapter);
//            lvContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    Log.d("OnItemclick", "Cnatct" + ((ContactData) contactsAdapter.getItem(position)).getContactName());
//                   // mListener.onContactSelected(((ContactData) contactsAdapter.getItem(position)));
//
//                }
//            });
        }
        else {
            new getContactsAsynTask(this.getActivity(),this).execute();
        }
        // Inflate the layout for this fragment
        return view;
    }

    //    // Called at the start of the visible lifetime.
//    @Override
//    public void onStart(){
//        super.onStart();
//
//    }


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

//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }

    @Override
    public void onFinish() {
        if(ServiceStorage.contactsList!=null&&ServiceStorage.contactsList.size()>0) {
//            aNameFromContacts = ServiceStorage.contactsList;
//            aNumberFromContacts=ServiceStorage.contactsNumbersList;
            contactsAdapter = new ContactsAdapter(getActivity(),ServiceStorage.contactsList,this);
            lvContacts.setAdapter(contactsAdapter);


        }
    }

    @Override
    public void onSelectContact(ContactData uri) {
        mListener.onContactSelected(uri,true);


    }

    @Override
    public void onUnselectContact(ContactData uri) {
        mListener.onContactSelected(uri,false);


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
        public void onContactSelected(ContactData uri, boolean b);

    }



}
