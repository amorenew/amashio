package co.mazeed.smsproject.Activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import co.mazeed.smsproject.R;
import co.mazeed.smsproject.controller.communication.AsyncTaskInvoker;
import co.mazeed.smsproject.controller.communication.DataRequestor;
import co.mazeed.smsproject.controller.communication.Task;
import co.mazeed.smsproject.storage.ServiceStorage;
import co.mazeed.smsproject.tasks.Signup;
import co.mazeed.smsprojects.model.Country;

public class SignUpActivity extends AppCompatActivity  implements DataRequestor,
        View.OnClickListener {
    Toolbar toolbar;
    EditText etUserName, etEmail, etMobileNumber, etPassword,
            etPasswordConfirmation;
    Button btnSignUp, btnCancel;
    Spinner spinnerMobileKey;
    private ProgressDialog mSpinnerProgress;
  ArrayList<String>  countriesCodes=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        toolbar = (Toolbar) findViewById(R.id.appBar);
        toolbar.setTitle(getString(R.string.sign_up));
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
        initUI();

    }

    public void initUI() {
        etUserName = (EditText) findViewById(R.id.etUserName);
        etEmail = (EditText) findViewById(R.id.etEmail);

        etMobileNumber = (EditText) findViewById(R.id.etMobileNumber);

        etPassword = (EditText) findViewById(R.id.etPassword);
        etPasswordConfirmation = (EditText) findViewById(R.id.etPasswordConfirmation);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        spinnerMobileKey = (Spinner) findViewById(R.id.spinnerMobileKey);
        btnCancel.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);


        readCountriesCode("countries.json");
    }

    void readCountriesCode(String params) {
        try {

            InputStream is = getAssets().open(params);
//            FileInputStream is = new FileInputStream(f);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String mResponse = new String(buffer);
            Log.d("Countries File", mResponse);
            JSONObject ob = new JSONObject(mResponse);
            Iterator<?> keys = ob.keys();
            ArrayList<Country> countriesList = new ArrayList<>();
            while (keys.hasNext()) {
                String key = (String) keys.next();
                if (ob.get(key) instanceof JSONArray) {
                    JSONArray array = ob.getJSONArray(key);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject countryOb = (JSONObject) array.get(i);
                        Country country = Country.FromJson(countryOb.toString());
                        countriesList.add(country);
                        countriesCodes.add(country.getCode());


                    }


                }
            }
            ServiceStorage.countries = countriesList;
            ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(SignUpActivity.this, android.R.layout.simple_spinner_item,countriesCodes);
            dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerMobileKey.setAdapter(dataAdapter2);
            spinnerMobileKey.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                public void onItemSelected(AdapterView<?> arg0, View arg1,
                                           int arg2, long arg3) {
                    // TODO Auto-generated method stub

                    Toast.makeText(getBaseContext(), spinnerMobileKey.getSelectedItem().toString(),
                            Toast.LENGTH_SHORT).show();
                    String cl=spinnerMobileKey.getSelectedItem().toString();
                    Log.d("classname",cl);
                }

                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub

                }
            });
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
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
            case R.id.btnSignUp:
                singup();
                break;
            case R.id.btnCancel:
                onBackPressed();
                break;

            default:
                break;
        }
    }

    private void singup() {
        String mail = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        if (mail != null && mail.length() > 0 && password != null
                && password.length() > 0) {

            mSpinnerProgress = new ProgressDialog(SignUpActivity.this);
            mSpinnerProgress.setIndeterminate(true);
            mSpinnerProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mSpinnerProgress.setMessage("Loading ....");
            mSpinnerProgress.show();
            HashMap<String, String> input = new HashMap<String, String>();
            input.put("Name", etUserName.getText().toString());
            input.put("Password", etPassword.getText().toString());
            input.put("Email", etEmail.getText().toString());

            input.put("mobileNumber", etMobileNumber.getText().toString());

            Task task = new Signup(this, this.getApplicationContext(), input,etMobileNumber.getText().toString());
            AsyncTaskInvoker.RunTaskInvoker(task);
        }
    }

    @Override
    public void onStart(Task task) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onFinish(Task task) {

        if(task.getId()== Task.TaskID.SignupTask)
        {
            mSpinnerProgress.cancel();
            String result=	(String)task.getResult();
            Toast.makeText(this.getApplicationContext(), result, Toast.LENGTH_SHORT).show();

            if (ServiceStorage.userID!=null)
            {
                finish();
            }

        }
    }

    @Override
    public void handleClick() {
        // TODO Auto-generated method stub

    }

}

