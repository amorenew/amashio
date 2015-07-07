package co.mazeed.smsproject.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import co.mazeed.smsproject.R;
import co.mazeed.smsproject.controller.communication.DataRequestor;
import co.mazeed.smsproject.controller.communication.Task;
import co.mazeed.smsproject.storage.ServiceStorage;
import co.mazeed.smsprojects.model.UserInfo;

public class SendSMSActivity extends AppCompatActivity implements View.OnClickListener, DataRequestor {
    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mPlanetTitles;
    DrawerLayout drawerLayout;
    ListView content;
    EditText myEditText, etContacts;
    TextView tvLettersCount, tvYourPoints, tvUserName;
    Button btnSendNow, btnSchedule;
    ImageView ivProfileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_sms);
        toolbar = (Toolbar) findViewById(R.id.appBar);
        toolbar.setTitle(getString(R.string.send_sms));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
//            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        initUI();

    }

    public void initUI() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        content = (ListView) findViewById(R.id.navigation_drawer_list);
        content.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(SendSMSActivity.this, position + " pressed", Toast.LENGTH_LONG).show();
                drawerLayout.closeDrawers();
                android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

                switch (position) {
                    case 0:
                        break;
                    case 1://My Drafts
                        break;
                    case 2://Templates
                        toolbar.setTitle(getString(R.string.templates));

                        TemplatesActivity fragment = new TemplatesActivity();
                        fragmentTransaction.replace(R.id.content, fragment);
                        fragmentTransaction.commit();

                        // For rest of the options we just show a toast on click
                        break;
                    case 3://My Contacts
                        toolbar.setTitle(getString(R.string.contacts));

                        Intent intent12 = new Intent(SendSMSActivity.this, MyContactsActivity.class);
                        startActivity(intent12);
                        break;
                    case 4://History
                        toolbar.setTitle(getString(R.string.history));
                        HistoryActivity historyFragment = new HistoryActivity();
                        fragmentTransaction.replace(R.id.content, historyFragment);
                        fragmentTransaction.commit();

                        break;
                    case 5://Profile

                        Intent intent4 = new Intent(SendSMSActivity.this, ProfileActivity.class);
                        startActivity(intent4);
                        break;
                    case 6://Switch to Arabic
                        break;
                    case 7://Sign out
                        break;
                    case 8://About
                        break;

                }
            }
        });


        myEditText = (EditText) findViewById(R.id.etMessage);
// Check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }


        etContacts = (EditText) findViewById(R.id.etContacts);
        tvLettersCount = (TextView) findViewById(R.id.tvLettersCount);
        tvYourPoints = (TextView) findViewById(R.id.tvYourPoints);
        btnSendNow = (Button) findViewById(R.id.btnSendNow);
        btnSchedule = (Button) findViewById(R.id.btnSchedule);
        btnSchedule.setOnClickListener(this);
        btnSendNow.setOnClickListener(this);
        tvUserName = (TextView) findViewById(R.id.tvUserName);
        if (ServiceStorage.currentUser != null) {
            fillUserInfo(ServiceStorage.currentUser);
        }
    }

    private void fillUserInfo(UserInfo currentUser) {
        tvUserName.setText(currentUser.getUserName());
        tvYourPoints.setText(currentUser.getBalance() + "");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_send_sms, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        toolbar.getTitle();
        if (id == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);

            return true;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_send) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
            //additional code
        } else {
            getSupportFragmentManager().popBackStack();
        }

    }


    @Override
    public void onStart(Task task) {

    }

    @Override
    public void onFinish(Task task) {

    }

    @Override
    public void handleClick() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSchedule:
                break;
            case R.id.btnSendNow:
                break;
        }

    }
}
