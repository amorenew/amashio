package co.mazeed.smsproject.Activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import co.mazeed.smsproject.R;
import co.mazeed.smsproject.controller.communication.AsyncTaskInvoker;
import co.mazeed.smsproject.controller.communication.DataRequestor;
import co.mazeed.smsproject.controller.communication.Task;
import co.mazeed.smsproject.storage.ServiceStorage;
import co.mazeed.smsproject.tasks.Logout;
import co.mazeed.smsprojects.model.UserInfo;

import static android.view.View.*;

public class ProfileActivity extends AppCompatActivity implements OnClickListener,DataRequestor {
    Toolbar toolbar;
    TextView tvUserName, tvEmail, tvMobileNumber, tvRegistredDate,
            tvCurrentPoints;
    Button btnGetPoints, btnSignOut;
    private ProgressDialog mSpinnerProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        toolbar = (Toolbar) findViewById(R.id.appBar);
        toolbar.setTitle(getString(R.string.my_account));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        initUi();
    }

    public void initUi() {
        tvUserName = (TextView) findViewById(R.id.tvUserName);
        tvEmail = (TextView) findViewById(R.id.tvEmail);
        tvMobileNumber = (TextView) findViewById(R.id.tvMobileNumber);
        tvRegistredDate = (TextView) findViewById(R.id.tvRegistredDate);
        tvCurrentPoints = (TextView) findViewById(R.id.tvCurrentPoints);
        btnGetPoints = (Button) findViewById(R.id.btnGetPoints);
        btnSignOut = (Button) findViewById(R.id.btnSignOut);
        btnSignOut.setOnClickListener(this);
        btnGetPoints.setOnClickListener(this);
        if (ServiceStorage.currentUser != null) {
            fillUserInfo(ServiceStorage.currentUser);
        }
    }

    private void fillUserInfo(UserInfo currentUser) {
        tvUserName.setText(currentUser.getUserName());
        tvEmail.setText(currentUser.getEmail());
        tvMobileNumber.setText(currentUser.getMobile());
        tvCurrentPoints.setText(currentUser.getBalance()+"");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            return true;
        }

        // noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.btnSignOut:
                logout();
                break;
            case R.id.btnGetPoints:
                break;
            default:
                break;
        }
    }
    private void logout() {

        if(ServiceStorage.currentUser!=null){
            mSpinnerProgress = new ProgressDialog(ProfileActivity.this);
            mSpinnerProgress.setIndeterminate(true);
            mSpinnerProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mSpinnerProgress.setMessage("Loading ....");
            mSpinnerProgress.show();
            HashMap<String, String> input = new HashMap<String, String>();
            input.put("tokenKey", ServiceStorage.currentUser.getTokenKey());

            Task task = new Logout(this, this.getApplicationContext(), input);
            AsyncTaskInvoker.RunTaskInvoker(task);
        }
    }

    @Override
    public void onStart(Task task) {

    }

    @Override
    public void onFinish(Task task) {
        switch (task.getId()) {
            case LogoutTask:
                String result=(String)task.getResult();
                if(result!=null)
                {
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
}