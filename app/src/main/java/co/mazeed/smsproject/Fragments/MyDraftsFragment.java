package co.mazeed.smsproject.Fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import co.mazeed.smsproject.Activities.HomeActivity;
import co.mazeed.smsproject.Adapters.TemplateAdapter;
import co.mazeed.smsproject.R;
import co.mazeed.smsproject.controller.communication.AsyncTaskInvoker;
import co.mazeed.smsproject.controller.communication.DataRequestor;
import co.mazeed.smsproject.controller.communication.Task;
import co.mazeed.smsproject.storage.ServiceStorage;
import co.mazeed.smsproject.tasks.GetSMSTemplateCategory;
import co.mazeed.smsproject.tasks.GetTemplates;
import co.mazeed.smsprojects.model.TemplateData;
import info.hoang8f.android.segmented.SegmentedGroup;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * Created by neolptp on 9/30/2015.
 */
public class MyDraftsFragment extends android.support.v4.app.Fragment implements DataRequestor,  RadioGroup.OnCheckedChangeListener {
    HomeActivity mActivity;
    private StickyListHeadersListView lvTemplates;
    EditText etFilter;
    private TemplateAdapter templateAdapter;
    private ProgressDialog mSpinnerProgress;
    private HashMap<Integer, Map<Integer, TemplateData>> templatesMap;
    ListView lvDrafts;
    SegmentedGroup segmented2;
    private ArrayList<String> drafts;
    private ArrayAdapter adapter;
    private Toolbar toolbar;
    private SharedPreferences appSettin;
    private FragmentTransaction fragmentTransaction;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_templates, container, false);
        mActivity = (HomeActivity) this.getActivity();

        toolbar = (Toolbar) view.findViewById(R.id.appBar);
        toolbar.setVisibility(View.GONE);
        etFilter = (EditText) view.findViewById(R.id.etFilter);
        lvTemplates = (StickyListHeadersListView) view.findViewById(R.id.lvTemplates);
        drafts=loadArrayDarfts();
        lvDrafts = (ListView) view.findViewById(R.id.lvDrafts);
        if (drafts != null && drafts.size() > 0) {
            adapter = new ArrayAdapter(mActivity, android.R.layout.simple_list_item_1, drafts);
            lvDrafts.setAdapter(adapter);
        }
        segmented2 = (SegmentedGroup) view.findViewById(R.id.segmented2);
        segmented2.setOnCheckedChangeListener(this);
        segmented2.check(R.id.bttemplates);
        etFilter.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                templateAdapter.getFilter().filter(cs);
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
        if (ServiceStorage.templatesCategoryList == null) {
            getTemplatesCategories();
        }
        if (ServiceStorage.templatesMap != null && ServiceStorage.templatesMap.size() > 0) {
            templatesMap = ServiceStorage.templatesMap;
            fillList();
        } else {
            getTemplates();
        }
        fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();

        lvTemplates.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TemplateData data = (TemplateData) templateAdapter.getItem(position);

                sendData(data.getSmsTemplateContent());
            }
        });

        lvDrafts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String data = (String) adapter.getItem(position);
                sendData(data);
            }
        });

        return view;

    }
    public void sendData(String data)
    {
        Log.d("OnItemclick", data);
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result", data);
//                mActivity.setResult(Activity.RESULT_OK, returnIntent);

        toolbar.setTitle(getString(R.string.send_sms));
        SendSMSFragment sendSMSActivity = new SendSMSFragment ();
        Bundle args = new Bundle();
        args.putString("result", data);
        sendSMSActivity.setArguments(args);
        fragmentTransaction.replace(R.id.content, sendSMSActivity, "SendSMS");

        if( mActivity.mState == mActivity.HIDE_MENU) {
            mActivity.mState =! mActivity.HIDE_MENU;
            mActivity.invalidateOptionsMenu();
        }
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    public ArrayList<String> loadArrayDarfts()
    {
        appSettin = getActivity().getSharedPreferences("SMS", Context.MODE_PRIVATE);


        drafts = new ArrayList();
        int size = appSettin.getInt("Drafts_size", 0);

        for(int i=0;i<size;i++)
        {
            drafts.add(appSettin.getString("Draft_" + i, null));
        }

return drafts;
    }



    public void getTemplatesCategories() {

        HashMap<String, String> input = new HashMap<String, String>();
        input.put("tokenkey", ServiceStorage.currentUser.getTokenKey());
        input.put("lang", "eng");

        Task task = new GetSMSTemplateCategory(this, mActivity.getApplicationContext(), input);
        AsyncTaskInvoker.RunTaskInvoker(task);
    }

    public void fillList() {
        templateAdapter = new TemplateAdapter(mActivity.getApplicationContext(), templatesMap);
        lvTemplates.setAdapter(templateAdapter);

    }

    public void getTemplates() {
        if (ServiceStorage.currentUser != null) {
            mSpinnerProgress = new ProgressDialog(mActivity);
            mSpinnerProgress.setIndeterminate(true);
            mSpinnerProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mSpinnerProgress.setMessage("Loading ....");
            mSpinnerProgress.show();
            HashMap<String, String> input = new HashMap<String, String>();
            input.put("tokenkey", ServiceStorage.currentUser.getTokenKey());
            input.put("lang", "eng");

            Task task = new GetTemplates(this, mActivity.getApplicationContext(), input);
            AsyncTaskInvoker.RunTaskInvoker(task);
        }
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_my_contacts, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.bttemplates:
                lvTemplates.setVisibility(View.VISIBLE);
                lvDrafts.setVisibility(View.GONE);
                return;
            case R.id.btdrafts:
                lvTemplates.setVisibility(View.GONE);
                lvDrafts.setVisibility(View.VISIBLE);

                return;
            default:
        }
    }

    @Override
    public void onStart(Task task) {

    }

        @Override
        public void onFinish(Task task) {
            if (task.getId() == Task.TaskID.GetTemplatesTask) {
                mSpinnerProgress.cancel();

                if (ServiceStorage.templatesMap != null && ServiceStorage.templatesMap.size() > 0)
                    templatesMap = ServiceStorage.templatesMap;
                fillList();

            }
            if (task.getId() == Task.TaskID.GetTemplatesCategoryTask) {

            }
        }



    @Override
    public void handleClick() {

    }
}

