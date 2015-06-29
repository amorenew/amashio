package co.mazeed.smsproject.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import co.mazeed.smsproject.Adapters.TemplateAdapter;
import co.mazeed.smsproject.R;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class TemplatesActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;

    private StickyListHeadersListView lvTemplates;
    private TemplateAdapter templateAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_templates);
        toolbar = (Toolbar) findViewById(R.id.appBar);

        toolbar.setTitle(getString(R.string.templates));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        lvTemplates = (StickyListHeadersListView) findViewById(R.id.lvTemplates);
        templateAdapter = new TemplateAdapter(this);
        lvTemplates.setAdapter(templateAdapter);
        lvTemplates.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == lvTemplates.getId())
            Toast.makeText(this, "Item: " + v.getTag(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_templates, menu);
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
