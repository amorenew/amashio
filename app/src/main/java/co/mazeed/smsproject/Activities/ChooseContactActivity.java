package co.mazeed.smsproject.Activities;

import co.mazeed.smsproject.Adapters.ContactsFragmentPagerAdapter;
import co.mazeed.smsproject.Fragments.AllContactsFragment.OnFragmentInteractionListener;
import co.mazeed.smsproject.Fragments.ContactsGroupFragment;
import co.mazeed.smsproject.R;
import co.mazeed.smsprojects.model.ContactData;
import co.mazeed.smsprojects.model.GroupInfo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.HashMap;


/**
 * Created by neolptp on 7/19/2015.
 */
public class ChooseContactActivity  extends AppCompatActivity implements OnFragmentInteractionListener, ContactsGroupFragment.OnFragmentInteractionListener {

    private android.support.v7.widget.Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    public HashMap<Integer, ContactData> selectedContacts = new HashMap<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_contacts);
        toolbar = (Toolbar) findViewById(R.id.appBar);
         toolbar.setVisibility(View.VISIBLE);
        toolbar.setTitle(getString(R.string.contacts));
        FragmentManager manager=this.getSupportFragmentManager();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        viewPager = (ViewPager) findViewById(R.id.viewpager);

        tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setTabTextColors(getResources().getColorStateList(R.color.tab_selector));

        ContactsFragmentPagerAdapter contactsFragmentPagerAdapter = new ContactsFragmentPagerAdapter(manager,this.getApplicationContext());
        viewPager.setAdapter(contactsFragmentPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);



        // Give the TabLayout the ViewPager
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_contacts, menu);

        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            MenuItem temp = menu.findItem(R.id.action_send_contacts);
            if (item != temp) {
                item.setVisible(false);
            }
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (item.getItemId()) {
            case R.id.home:
                super.onBackPressed();
            case R.id.action_send_contacts:

                String fullContacts="";//etContacts.getText().toString();
                int i=0;
                for (Integer  key :selectedContacts.keySet()) {
                    ContactData contact = selectedContacts.get(key);
                    if (contact.getContactName() != "") {
                        if (fullContacts.length() > 0) {
                            fullContacts = fullContacts + "," + contact.getContactMobileNumber();
                        } else fullContacts = contact.getContactMobileNumber();
                   i++;
                    }
                }
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result", fullContacts);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
                return true;
            default:

                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onGroupselected(String groupselected, HashMap<String, GroupInfo> groupInfoHashMap) {
//        Intent returnIntent = new Intent();
//        returnIntent.putExtra("result",groupselected);
//        setResult(RESULT_OK, returnIntent);
//        finish();

    }

    @Override
    public void onContactSelected(ContactData uri, boolean b) {

        if (selectedContacts.containsKey(uri.getContactID()) && b==false) {
            Log.d("ChooseContacts Activity", "unselection");
//
            selectedContacts.remove(uri.getContactID());

        } else if (b==true) {
            Log.d("ChooseContacts Activity ", "Selection");
            selectedContacts.put(uri.getContactID(), uri);
        }
        Log.d("Shaimaa",""+selectedContacts.keySet().size());
    }
}
