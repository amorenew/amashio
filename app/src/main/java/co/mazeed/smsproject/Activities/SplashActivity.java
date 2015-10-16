package co.mazeed.smsproject.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.Window;
import android.view.WindowManager;

import co.mazeed.smsproject.R;
import co.mazeed.smsproject.controller.communication.AsyncTaskInvoker;
import co.mazeed.smsproject.controller.communication.DataRequestor;
import co.mazeed.smsproject.controller.communication.Task;
import co.mazeed.smsproject.tasks.UpdateTokenKey;

import java.util.ArrayList;
import java.util.HashMap;


public class SplashActivity extends Activity implements DataRequestor {
    private SharedPreferences appSettin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // remove title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        appSettin = getSharedPreferences("SMS", Context.MODE_PRIVATE);

        String tokenKey = appSettin.getString("TokenKey", "");
        checkToken(tokenKey);

        if (tokenKey.length() > 0) {
            HashMap<String,String>input=new HashMap<>();
            input.put("tokenkey", tokenKey);
            UpdateTokenKey task = new UpdateTokenKey(this,this,input);
            AsyncTaskInvoker.RunTaskInvoker(task);

        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    Intent i = new Intent(SplashActivity.this,
                            LoginActivity.class);
                    startActivity(i);
                    finish();
                }
            }, 3000);
        }


//        openGuideActivity();
    }

    private void checkToken(String token) {
       String expiredData= appSettin.getString("TokenExpire","");

    }

    private void openGuideActivity() {
//        Intent intent1 = new Intent(this, GuideActivity.class);
//        startActivity(intent1);
        Intent intent2 = new Intent(this, LoginActivity.class);
        startActivity(intent2);
//        Intent intent3 = new Intent(this, SignUpActivity.class);
//        startActivity(intent3);
//        Intent intent4 = new Intent(this, ProfileActivity.class);
//        startActivity(intent4);
//        Intent intent5 = new Intent(this, SendSMSFragment.class);
//        startActivity(intent5);
//        Intent intent6 = new Intent(this, ContactDetailActivity.class);
//        startActivity(intent6);
//        Intent intent7 = new Intent(this, MessageSentActivity.class);
//        startActivity(intent7);
//        Intent intent8 = new Intent(this, YourPointsActivity.class);
//        startActivity(intent8);
//        Intent intent9 = new Intent(this, HistoryActivity.class);
//        startActivity(intent9);
//        Intent intent10 = new Intent(this, MessageDetailsActivity.class);
//        startActivity(intent10);
//        Intent intent11 = new Intent(this, TemplatesActivity.class);
//        startActivity(intent11);
//        Intent intent12 = new Intent(this, MyContactsFragment.class);
//        startActivity(intent12);
    }

    @Override
    public void onStart(Task task) {

    }

    @Override
    public void onFinish(Task task) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent i = new Intent(SplashActivity.this,
                        HomeActivity.class);
                startActivity(i);
                finish();
            }
        }, 3000);

    }

    @Override
    public void handleClick() {

    }


}
