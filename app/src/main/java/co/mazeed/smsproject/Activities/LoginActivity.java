package co.mazeed.smsproject.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import android.app.ProgressDialog;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import co.mazeed.smsproject.R;
import co.mazeed.smsproject.controller.communication.AsyncTaskInvoker;
import co.mazeed.smsproject.controller.communication.DataRequestor;
import co.mazeed.smsproject.controller.communication.Task;
import co.mazeed.smsproject.controller.communication.Task.TaskID;
import co.mazeed.smsproject.storage.ServiceStorage;
import co.mazeed.smsproject.tasks.Login;

public class LoginActivity extends AppCompatActivity implements
    OnClickListener, DataRequestor {
    private SharedPreferences appSettin;

        private Toolbar toolbar;
        EditText etEmail, etPassword;
        Button btnLogin, btnSignUp;
        private ProgressDialog mSpinnerProgress;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);
            appSettin = getSharedPreferences("SMS", Context.MODE_PRIVATE);
            etEmail = (EditText) findViewById(R.id.etEmail);
            etPassword = (EditText) findViewById(R.id.etPassword);
            btnLogin = (Button) findViewById(R.id.btnLogin);
            btnSignUp = (Button) findViewById(R.id.btnSignUp);
            btnSignUp.setOnClickListener(this);
            btnLogin.setOnClickListener(this);

            toolbar = (Toolbar) findViewById(R.id.appBar);
            toolbar.setTitle(getString(R.string.login));
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
        }

    private void login() {
        String mail = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        if (mail != null && mail.length() > 0 && password != null
                && password.length() > 0) {

            mSpinnerProgress = new ProgressDialog(LoginActivity.this);
            mSpinnerProgress.setIndeterminate(true);
            mSpinnerProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mSpinnerProgress.setMessage("Loading ....");
            mSpinnerProgress.show();
            HashMap<String, String> input = new HashMap<String, String>();
            input.put("username", etEmail.getText().toString());
            input.put("password", etPassword.getText().toString());

            Task task = new Login(this, this.getApplicationContext(), input);
            AsyncTaskInvoker.RunTaskInvoker(task);
        }
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

        switch (v.getId()) {
            case R.id.btnLogin:
                login();

                break;
            case R.id.btnSignUp:

                Intent intent3 = new Intent(this, SignUpActivity.class);
                startActivity(intent3);
                break;

            default:
                break;
        }
    }

    @Override
    public void onStart(Task task) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onFinish(Task task) {
        // TODO Auto-generated method stub
        if(task.getId()==TaskID.LoginTask)
        {
            mSpinnerProgress.cancel();
            String result=(String)task.getResult();
            if (ServiceStorage.tokenKey!=null)
            {
                SharedPreferences.Editor editor = appSettin.edit();
                editor.putString("TokenKey", ServiceStorage.tokenKey);
                editor.commit();
                mCreateAndSaveFile("CurrentUser",result);
                finish();
		        Intent intent4 = new Intent(this, HomeActivity.class);
		        startActivity(intent4);
//				  Intent intent5 = new Intent(this, SendSMSFragment.class);
//		        startActivity(intent5);
//                Intent intent6 = new Intent(this, ContactDetailActivity.class);
//                startActivity(intent6);
            }
            else
            {
                Toast.makeText(this.getApplicationContext(), result, Toast.LENGTH_SHORT).show();

            }
        }

    }

    public void mCreateAndSaveFile(String params, String mJsonResponse) {
        try {
            FileWriter file = new FileWriter("/data/data/"+ this.getPackageName() + "/" + params);
            file.write(mJsonResponse);
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handleClick() {
        // TODO Auto-generated method stub

    }

}
