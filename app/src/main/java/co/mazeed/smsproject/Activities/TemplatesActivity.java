package co.mazeed.smsproject.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import co.mazeed.smsproject.Adapters.TemplateAdapter;
import co.mazeed.smsproject.R;
import co.mazeed.smsproject.controller.communication.AsyncTaskInvoker;
import co.mazeed.smsproject.controller.communication.DataRequestor;
import co.mazeed.smsproject.controller.communication.Task;
import co.mazeed.smsproject.storage.ServiceStorage;
import co.mazeed.smsproject.tasks.GetSMSTemplateCategory;
import co.mazeed.smsproject.tasks.GetTemplates;
import co.mazeed.smsproject.tasks.Login;
import co.mazeed.smsprojects.model.TemplateData;
import info.hoang8f.android.segmented.SegmentedGroup;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class TemplatesActivity extends AppCompatActivity implements DataRequestor, RadioGroup.OnCheckedChangeListener {
    private Toolbar toolbar;

    private StickyListHeadersListView lvTemplates;
    EditText etFilter;
    private TemplateAdapter templateAdapter;
    private ProgressDialog mSpinnerProgress;
    private HashMap<Integer, Map<Integer, TemplateData>> templatesMap;
    ListView lvDrafts;
    SegmentedGroup segmented2;
    private ArrayList<String> drafts;
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_templates);
        Bundle b=  getIntent().getExtras();
        if  (b !=null)
        {
          drafts=  b.getStringArrayList("DraftsList");
          
        }
        toolbar = (Toolbar) findViewById(R.id.appBar);

        toolbar.setTitle(getString(R.string.templates));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        // this method handle what happen when you click at home
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        etFilter = (EditText) findViewById(R.id.etFilter);
        lvTemplates = (StickyListHeadersListView) findViewById(R.id.lvTemplates);
        lvDrafts=(ListView)findViewById(R.id.lvDrafts);
        if (drafts!=null&&drafts.size()>0) {
            adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, drafts);
            lvDrafts.setAdapter(adapter);
        }
        segmented2=(SegmentedGroup)findViewById(R.id.segmented2);
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
        lvDrafts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent returnIntent = new Intent();
                String  data= (String) adapter.getItem(position);
                returnIntent.putExtra("result", data);
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });
        lvTemplates.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TemplateData data = (TemplateData) templateAdapter.getItem(position);
                Log.d("OnItemclick", data.getSmsTemplateContent());
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result", data.getSmsTemplateContent());
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });


    }

    public void getTemplatesCategories() {

        HashMap<String, String> input = new HashMap<String, String>();
        input.put("tokenkey", ServiceStorage.currentUser.getTokenKey());
        input.put("lang", "eng");

        Task task = new GetSMSTemplateCategory(this, getApplicationContext(), input);
        AsyncTaskInvoker.RunTaskInvoker(task);
    }

    public void getTemplates() {
        if (ServiceStorage.currentUser != null) {
            mSpinnerProgress = new ProgressDialog(TemplatesActivity.this);
            mSpinnerProgress.setIndeterminate(true);
            mSpinnerProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mSpinnerProgress.setMessage("Loading ....");
            mSpinnerProgress.show();
            HashMap<String, String> input = new HashMap<String, String>();
            input.put("tokenkey", ServiceStorage.currentUser.getTokenKey());
            input.put("lang", "eng");

            Task task = new GetTemplates(this, getApplicationContext(), input);
            AsyncTaskInvoker.RunTaskInvoker(task);
        }
    }


//        @Override
//        public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        this.getActivity().getMenuInflater().inflate(R.menu.menu_templates, menu);
//        return true;
//    }

//        @Override
//        public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

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

    public void fillList() {
        templateAdapter = new TemplateAdapter(getApplicationContext(), templatesMap);
        lvTemplates.setAdapter(templateAdapter);

    }

    @Override
    public void handleClick() {

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
                // donothing
        }

    }
}
