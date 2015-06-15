package co.mazeed.smsproject.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import co.mazeed.smsproject.Adapters.PointsAdapter;
import co.mazeed.smsproject.R;

public class YourPointsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ListView lvPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_points);
        lvPoint = (ListView) findViewById(R.id.lvPoints);
        List<String> points = new ArrayList<>();
        points.add("1");
        points.add("1");
        points.add("1");
        points.add("1");
        points.add("1");
        points.add("1");

        PointsAdapter pointsAdapter = new PointsAdapter(this, points);
        lvPoint.setAdapter(pointsAdapter);
        toolbar = (Toolbar) findViewById(R.id.appBar);
        toolbar.setTitle(getString(R.string.your_points));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_your_points, menu);
        return true;
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
}
