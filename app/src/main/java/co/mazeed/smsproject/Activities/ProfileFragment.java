package co.mazeed.smsproject.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import co.mazeed.smsproject.R;
import co.mazeed.smsproject.controller.communication.AsyncTaskInvoker;
import co.mazeed.smsproject.controller.communication.DataRequestor;
import co.mazeed.smsproject.controller.communication.Task;
import co.mazeed.smsproject.storage.ServiceStorage;
import co.mazeed.smsproject.tasks.Logout;
import co.mazeed.smsprojects.model.UserInfo;

public class  ProfileFragment extends android.support.v4.app.Fragment implements View.OnClickListener, DataRequestor {
    Toolbar toolbar;
    TextView tvUserName, tvEmail, tvMobileNumber, tvRegistredDate,
            tvCurrentPoints;
    Button btnGetPoints, btnSignOut;
    private SharedPreferences appSettin;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_profile, container, false);
        toolbar = (Toolbar) view.findViewById(R.id.appBar);
        toolbar.setVisibility(View.GONE);

        initUi(view);
        return view;
    }
    public void initUi(View v) {
        tvUserName = (TextView)v. findViewById(R.id.tvUserName);
        appSettin = getActivity().getSharedPreferences("SMS", Context.MODE_PRIVATE);

// Check if no view has focus:
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }


        tvEmail = (TextView) v.findViewById(R.id.tvEmail);
        tvMobileNumber = (TextView) v.findViewById(R.id.tvMobileNumber);
        tvRegistredDate = (TextView) v.findViewById(R.id.tvRegistredDate);
        tvCurrentPoints = (TextView) v.findViewById(R.id.tvCurrentPoints);
        btnGetPoints = (Button) v.findViewById(R.id.btnGetPoints);
        btnSignOut = (Button) v.findViewById(R.id.btnSignOut);
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
        DateFormat f = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            Date d =f.parse(currentUser.getCreated());

//            tvRegistredDate.setText(tvRegistredDate.getText()+" "+currentUser.getCreated());

            if(d!=null)
            {
                DateFormat date = new SimpleDateFormat("MM/dd/yyyy");
                tvRegistredDate.setText(tvRegistredDate.getText()+" "+date.format(d));

            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        tvCurrentPoints.setText(currentUser.getBalance() + "");



    }




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

        switch (v.getId())
        {
            case R.id.btnSignOut:
            logout();
                break;

            case R.id.btnGetPoints:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.yoursms.net/eng/"));
                startActivity(browserIntent);
                break;

            default:
                break;
        }
    }

    private void logout() {
        appSettin.edit().clear().commit();

        ServiceStorage.clearSetting();

        Intent i = new Intent(getActivity(),
                LoginActivity.class);
        startActivity(i);
        getActivity().finish();
        }


}
