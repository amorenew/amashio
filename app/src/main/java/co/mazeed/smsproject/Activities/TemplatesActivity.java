package co.mazeed.smsproject.Activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import co.mazeed.smsproject.tasks.GetTemplates;
import co.mazeed.smsproject.tasks.Login;
import co.mazeed.smsprojects.model.TemplateData;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class TemplatesActivity extends android.support.v4.app.Fragment implements View.OnClickListener, DataRequestor {
    private Toolbar toolbar;

    private StickyListHeadersListView lvTemplates;
    private TemplateAdapter templateAdapter;
    private ProgressDialog mSpinnerProgress;
    private  HashMap<Integer, Map<Integer,TemplateData>> templatesMap;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_templates, container, false);
//        setContentView(R.layout.activity_templates);
//        toolbar = (Toolbar) findViewById(R.id.appBar);
//
//        toolbar.setTitle(getString(R.string.templates));
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);
//        // this method handle what happen when you click at home
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });
        lvTemplates = (StickyListHeadersListView) view.findViewById(R.id.lvTemplates);

        getTemplates();
        return view;
    }

    public void getTemplates() {
        if (ServiceStorage.currentUser != null) {
            mSpinnerProgress = new ProgressDialog(this.getActivity());
            mSpinnerProgress.setIndeterminate(true);
            mSpinnerProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mSpinnerProgress.setMessage("Loading ....");
            mSpinnerProgress.show();
            HashMap<String, String> input = new HashMap<String, String>();
            input.put("tokenkey", ServiceStorage.currentUser.getTokenKey());
            input.put("lang", "eng");

            Task task = new GetTemplates(this, this.getActivity().getApplicationContext(), input);
            AsyncTaskInvoker.RunTaskInvoker(task);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == lvTemplates.getId())
            Toast.makeText(this.getActivity(), "Item: " + v.getTag(), Toast.LENGTH_SHORT).show();
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
        mSpinnerProgress.cancel();
        if( task.getId()== Task.TaskID.GetTemplatesTask)
        {
            if(ServiceStorage.templatesMap!=null&&ServiceStorage.templatesMap.size()>0)
                templatesMap=  ServiceStorage.templatesMap;
            fillList();
        }
    }
public void fillList()
{
    templateAdapter = new TemplateAdapter(this.getActivity().getApplicationContext(),templatesMap);
    lvTemplates.setAdapter(templateAdapter);
    lvTemplates.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        }
    });
}
    @Override
    public void handleClick() {

    }
}
