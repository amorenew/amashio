package co.mazeed.smsproject.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import co.mazeed.smsproject.R;


public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // remove title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        openGuideActivity();
    }

    private void openGuideActivity() {
        Intent intent1 = new Intent(this, GuideActivity.class);
        startActivity(intent1);
        Intent intent2 = new Intent(this, LoginActivity.class);
        startActivity(intent2);
        Intent intent3 = new Intent(this, SignUpActivity.class);
        startActivity(intent3);
        Intent intent4 = new Intent(this, ProfileActivity.class);
        startActivity(intent4);
//        Intent intent5 = new Intent(this, SignUpActivity.class);
//        startActivity(intent5);
//        Intent intent6 = new Intent(this, SignUpActivity.class);
//        startActivity(intent6);
//        Intent intent7= new Intent(this, SignUpActivity.class);
//        startActivity(intent7);
//        Intent intent8= new Intent(this, SignUpActivity.class);
//        startActivity(intent8);
    }

}
