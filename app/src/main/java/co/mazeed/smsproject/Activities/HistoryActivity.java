package co.mazeed.smsproject.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import co.mazeed.smsproject.Adapters.HistoryAdapter;
import co.mazeed.smsproject.R;

public class HistoryActivity extends android.support.v4.app.Fragment {

    private ListView lvHistory;
    private Toolbar toolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_history, container, false);
        lvHistory = (ListView) view.findViewById(R.id.lvPoints);
        List<String> history = new ArrayList<>();
        history.add("1");
        history.add("1");
        history.add("1");
        history.add("1");
        history.add("1");
        history.add("1");

        HistoryAdapter historyAdapter = new HistoryAdapter(this.getActivity(), history);
        lvHistory.setAdapter(historyAdapter);
//        toolbar = (Toolbar) findViewById(R.id.appBar);
//        toolbar.setTitle(getString(R.string.history));
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);
return  view;
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_history, menu);
//        return true;
//    }

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
