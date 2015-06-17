package co.mazeed.smsproject.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import co.mazeed.smsproject.Adapters.RecipientsAdapter;
import co.mazeed.smsproject.R;

public class MessageDetailsActivity extends AppCompatActivity {

    private ListView lvRecipients;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details);
        lvRecipients = (ListView) findViewById(R.id.lvRecipients);
        List<String> recipients = new ArrayList<>();
        recipients.add("1");
        recipients.add("1");
        recipients.add("1");
        recipients.add("1");
        recipients.add("1");
        recipients.add("1");

        RecipientsAdapter recipientsAdapter = new RecipientsAdapter(this, recipients);
        lvRecipients.setAdapter(recipientsAdapter);
        toolbar = (Toolbar) findViewById(R.id.appBar);
        toolbar.setTitle(getString(R.string.message_details));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_message_details, menu);
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
