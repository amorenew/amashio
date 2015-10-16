package co.mazeed.smsproject.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import co.mazeed.smsproject.Fragments.AllContactsFragment;
import co.mazeed.smsproject.Fragments.ContactsGroupFragment;
import co.mazeed.smsproject.Fragments.MyContactsFragment;
import co.mazeed.smsproject.Fragments.MyDraftsFragment;
import co.mazeed.smsproject.Fragments.SendSMSFragment;
import co.mazeed.smsproject.R;
import co.mazeed.smsproject.controller.communication.DataRequestor;
import co.mazeed.smsproject.controller.communication.Task;
import co.mazeed.smsproject.storage.ServiceStorage;
import co.mazeed.smsprojects.model.ContactData;
import co.mazeed.smsprojects.model.GroupInfo;
import co.mazeed.smsprojects.model.UserInfo;

import static android.view.View.*;

public class HomeActivity extends AppCompatActivity implements OnClickListener, DataRequestor, AllContactsFragment.OnFragmentInteractionListener, ContactsGroupFragment.OnFragmentInteractionListener {
    Toolbar toolbar;


    private ProgressDialog mSpinnerProgress;

    DrawerLayout drawerLayout;
    private RelativeLayout navigation_drawer_header_include;
    private String[] myList;
    private TextView tvUserNameMenu;
    private ListView content;
    private SharedPreferences appSettin;
    public  boolean HIDE_MENU = false;
    boolean HIDE_Send = false;
  public boolean mState = true; // setting state
//    boolean m

    public HashMap<Integer, ContactData> selectedContacts = new HashMap<>();
    private FragmentTransaction fragmentTransaction;
    private ArrayList <String> drafts;
    public String sendSmsMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        appSettin = getSharedPreferences("SMS", Context.MODE_PRIVATE);
        fragmentTransaction = getSupportFragmentManager().beginTransaction();

        toolbar = (Toolbar) findViewById(R.id.appBar);
        toolbar.setTitle(getString(R.string.my_account));
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
//            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        tvUserNameMenu = (TextView) findViewById(R.id.tvUserNameMenu);
        navigation_drawer_header_include = (RelativeLayout) findViewById(R.id.navigation_drawer_header_include);
        navigation_drawer_header_include.setOnClickListener(this);
        content = (ListView) findViewById(R.id.navigation_drawer_list);
        myList = getResources().getStringArray(R.array.menu_items);
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_list_item_1,
                        myList) {

                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {

                        View view = super.getView(position, convertView, parent);
                        TextView text = (TextView) view.findViewById(android.R.id.text1);

                        text.setTextColor(Color.WHITE);


                        return view;
                    }
                };
        content.setAdapter(adapter);
        content.setOnItemClickListener(clickLisener);
        ProfileFragment home = new ProfileFragment();
        fragmentTransaction.replace(R.id.content, home).commit();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (ServiceStorage.currentUser != null) {
            fillUserInfo(ServiceStorage.currentUser);
        } else {
            mReadJsonData("CurrentUser");
        }

    }

    private void fillUserInfo(UserInfo currentUser) {
        tvUserNameMenu.setText(currentUser.getUserName());
    }

    private SendSMSFragment sendSMSActivity;
    private MyDraftsFragment myDraftsFragment;
    AdapterView.OnItemClickListener clickLisener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(SendSMSFragment.this, position + " pressed", Toast.LENGTH_LONG).show();
            drawerLayout.closeDrawers();

            switch (position) {
                case 0:
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    toolbar.setTitle(getString(R.string.send_sms));
                    sendSMSActivity = new SendSMSFragment();
                    fragmentTransaction.replace(R.id.content, sendSMSActivity, "SendSMS");
                   if( mState == HIDE_MENU) {
                       mState =! HIDE_MENU;
                       invalidateOptionsMenu();
                   }
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    break;
                case 1://My Drafts
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    toolbar.setTitle(getString(R.string.my_drafts));
                    myDraftsFragment = new MyDraftsFragment();

                    fragmentTransaction.replace(R.id.content, myDraftsFragment, "MyDraftsFragment");
                    if( mState == HIDE_MENU) {
                        mState =! HIDE_MENU;
                        invalidateOptionsMenu();
                    }
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    break;

                case 2://My Contacts
                    toolbar.setTitle(getString(R.string.contacts));
                    if(selectedContacts!=null&&selectedContacts.size()>0)
                        selectedContacts.clear();
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    MyContactsFragment myContactsFragment = new MyContactsFragment();
                    fragmentTransaction.replace(R.id.content, myContactsFragment);
                    mState = HIDE_MENU;
                    invalidateOptionsMenu();
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    break;
                case 3://History
                    toolbar.setTitle(getString(R.string.history));
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    HistoryActivity historyFragment = new HistoryActivity();
                    fragmentTransaction.replace(R.id.content, historyFragment);
                    if( mState == HIDE_MENU) {
                        mState =! HIDE_MENU;
                        invalidateOptionsMenu();
                    }
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    break;
//                case 4://Profile
//                    toolbar.setTitle(getString(R.string.my_account));
//                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                    ProfileFragment home = new ProfileFragment();
//                    fragmentTransaction.replace(R.id.content, home);
//                    if( mState == HIDE_MENU) {
//                        mState =! HIDE_MENU;
//                        invalidateOptionsMenu();
//                    }
//                    fragmentTransaction.addToBackStack(null);
//                    fragmentTransaction.commit();
//
//                    break;
                case 5://Switch to Arabic
                    break;
                case 6://Sign out
                    break;
                case 7://About
                    break;

            }
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);

        if (mState == HIDE_MENU) {
            for (int i = 0; i < menu.size(); i++) {
                MenuItem item = menu.getItem(i);
                MenuItem temp = menu.findItem(R.id.action_send_contacts);
                if (item != temp) {
                    item.setVisible(false);
                }
            }
        } else {
            for (int i = 0; i < menu.size(); i++)
                menu.getItem(i).setVisible(false);

        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case R.id.action_send_contacts:
                openSendSMS(selectedContacts);
                return true;
//                openSearch();
//            case android.R.id.action_settings:
//                openSettings();
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
            default:

                return super.onOptionsItemSelected(item);
        }
    }

    private void openSendSMS(HashMap<Integer, ContactData> selectedContacts) {

//        if(selectedContacts!=null&& selectedContacts.size()>0)
//        {
//            numconatcts_selcted.setText(""+selectedContacts.size());
            String fullContacts="";//etContacts.getText().toString();
            for (Integer  key :selectedContacts.keySet())
            {
                ContactData contact=selectedContacts.get(key);
                if(fullContacts.length()>0)
                {
                    fullContacts=fullContacts+","+contact.getContactMobileNumber();
                }
                else fullContacts=contact.getContactMobileNumber();
            }
        Log.d("Number selected",selectedContacts.size()+"");
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        toolbar.setTitle(getString(R.string.send_sms));
        sendSMSActivity = new SendSMSFragment();
        Bundle args = new Bundle();
        args.putString("numselcted", ""+selectedContacts.size());
        args.putString("SelectedNumbers",fullContacts);
        args.putString("result",sendSmsMsg);
        sendSMSActivity.setArguments(args);
        fragmentTransaction.replace(R.id.content, sendSMSActivity, "SendSMS");
        invalidateOptionsMenu();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
//        sendSMSActivity.updateSelectedContacts();
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.btnSignOut:
                logout();
                break;
            case R.id.btnGetPoints:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.yoursms.net/eng/"));
                startActivity(browserIntent);
                break;
            case R.id.navigation_drawer_header_include:
                drawerLayout.closeDrawers();

                android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                toolbar.setTitle(getString(R.string.my_account));
                ProfileFragment home = new ProfileFragment();
                fragmentTransaction.replace(R.id.content, home);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();


                break;
            default:
                break;
        }
    }

    private void logout() {

//        if (ServiceStorage.currentUser != null) {
//            mSpinnerProgress = new ProgressDialog(HomeActivity.this);
//            mSpinnerProgress.setIndeterminate(true);
//            mSpinnerProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            mSpinnerProgress.setMessage("Loading ....");
//            mSpinnerProgress.show();
//            HashMap<String, String> input = new HashMap<String, String>();
//            input.put("tokenKey", ServiceStorage.currentUser.getTokenKey());
//
//            Task task = new Logout(this, this.getApplicationContext(), input);
//            AsyncTaskInvoker.RunTaskInvoker(task);
//        }
        appSettin.edit().clear().commit();
        ServiceStorage.clearSetting();

        Intent i = new Intent(HomeActivity.this, LoginActivity.class);
        startActivity(i);
        finish();
    }


    public void mReadJsonData(String params) {
        try {
            File f = new File("/data/data/" + getPackageName() + "/" + params);
            FileInputStream is = new FileInputStream(f);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String mResponse = new String(buffer);
            Log.d("User File", mResponse);
            UserInfo userData = UserInfo.fromJson(mResponse.toString());
            ServiceStorage.currentUser = userData;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void onStart(Task task) {

    }

    @Override
    public void onFinish(Task task) {
        switch (task.getId()) {
            case LogoutTask:
                String result = (String) task.getResult();
                if (result != null) {
                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                    mSpinnerProgress.cancel();
                }
                break;

            default:
                break;
        }
    }

    @Override
    public void handleClick() {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onGroupselected(String Groupselected, HashMap<String, GroupInfo> groupInfoHashMap) {

    }

    @Override
    public void onContactSelected(ContactData uri, boolean b) {
        Log.d("home ", ""+b);

        if (selectedContacts.containsKey(uri.getContactID()) && b==false) {
            Log.d("Home Activity ", "unselection");
//
            selectedContacts.remove(uri.getContactID());

        } else if (b==true) {
            Log.d("Home Activity ", "Selection");
            selectedContacts.put(uri.getContactID(), uri);
        }
        Log.d("Shaimaa",""+selectedContacts.keySet().size());



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("SendSMS");

        if (fragment != null) {

            fragment.onActivityResult(SendSMSFragment.PICK_Template_REQUEST, resultCode, data);
        }
        else if (requestCode == SendSMSFragment.PICK_Template_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra("result");
//                etMessage.setText(result);
            }
        }
    }

    @Override
    public void onBackPressed() {

//        int count = getSupportFragmentManager().getBackStackEntryCount();
//
//        if (count == 0) {
//            super.onBackPressed();
//            //additional code
//        } else {
//            getSupportFragmentManager().popBackStack();
//        }

    }




}