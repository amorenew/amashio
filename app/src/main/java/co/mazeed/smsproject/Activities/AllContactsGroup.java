package co.mazeed.smsproject.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import co.mazeed.smsproject.Adapters.ContactsAdapter;
import co.mazeed.smsproject.Adapters.TemplateAdapter;
import co.mazeed.smsproject.R;
import co.mazeed.smsproject.controller.communication.AsyncTaskInvoker;
import co.mazeed.smsproject.controller.communication.DataRequestor;
import co.mazeed.smsproject.controller.communication.Task;
import co.mazeed.smsproject.storage.ServiceStorage;
import co.mazeed.smsproject.tasks.GetSMSTemplateCategory;
import co.mazeed.smsproject.tasks.GetTemplates;
import co.mazeed.smsprojects.model.ContactData;
import co.mazeed.smsprojects.model.TemplateData;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * Created by neolptp on 7/14/2015.
 */
public class AllContactsGroup  extends AppCompatActivity implements  ContactsAdapter.onContactSelectorUnSelect {
    private Toolbar toolbar;

    private StickyListHeadersListView lvContacts;
    EditText etFilter;
    private ContactsAdapter contactsAdapter;
    private ProgressDialog mSpinnerProgress;
    private HashMap<Integer, Map<Integer, TemplateData>> templatesMap;
    private TextView tvNoContacts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conatcts_group);
        toolbar = (Toolbar) findViewById(R.id.appBar);

//        toolbar.setTitle(getString(R.string.conatcts));
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
        lvContacts = (StickyListHeadersListView) findViewById(R.id.lvContacts);
        tvNoContacts=(TextView)findViewById(R.id.tvNoContacts);
        Bundle b=this.getIntent().getExtras();
        ArrayList < ContactData> lstObj = (ArrayList<ContactData>) b.getSerializable("Contacts");
//        String[] aNameFromContacts=b.getStringArray("Contacts");
//        String[]aNumberFromContacts=b.getStringArray("Numbers");
        if(lstObj!=null&&lstObj.size()>0) {
//            contactsAdapter = new ContactsAdapter(getac, lstObj,this);
//            lvContacts.setAdapter(contactsAdapter);
        }
        else{
            tvNoContacts.setVisibility(View.VISIBLE);
            etFilter.setVisibility(View.GONE);

        }

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


        lvContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Log.d("OnItemclick", "Cnatct");

            }
        });


    }

    @Override
    public void onSelectContact(ContactData uri) {

    }

    @Override
    public void onUnselectContact(ContactData uri) {

    }
}
