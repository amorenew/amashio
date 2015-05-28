package co.mazeed.smsproject.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;

import co.mazeed.smsproject.R;


public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        openGuideActivity();
    }

    private void openGuideActivity() {
        Intent intent = new Intent(this, GuideActivity.class);
        startActivity(intent);
    }

}
