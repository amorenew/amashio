package co.mazeed.smsproject.Activities;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hb.views.PinnedSectionListView;

import co.mazeed.smsproject.Adapters.Item;
import co.mazeed.smsproject.Adapters.TemplateAdapter;
import co.mazeed.smsproject.Adapters.TemplateFastScrollAdapter;
import co.mazeed.smsproject.R;

public class TemplatesActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;

    private boolean hasHeaderAndFooter;
    private boolean isFastScroll;
    private boolean addPadding;
    private boolean isShadowVisible = true;
    private int mDatasetUpdateCount;
    private PinnedSectionListView lvTemplates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_templates);
        lvTemplates = (PinnedSectionListView) findViewById(R.id.lvTemplates);
        toolbar = (Toolbar) findViewById(R.id.appBar);

        toolbar.setTitle(getString(R.string.templates));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        if (savedInstanceState != null) {
            isFastScroll = savedInstanceState.getBoolean("isFastScroll");
            addPadding = savedInstanceState.getBoolean("addPadding");
            isShadowVisible = savedInstanceState.getBoolean("isShadowVisible");
            hasHeaderAndFooter = savedInstanceState.getBoolean("hasHeaderAndFooter");
        }
        initializeHeaderAndFooter();
        initializeAdapter();
        initializePadding();

        lvTemplates.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item item = (Item) lvTemplates.getAdapter().getItem(position);
                if (item != null) {
                    Toast.makeText(TemplatesActivity.this, "Item " + position + ": " + item.text, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TemplatesActivity.this, "Item " + position, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("isFastScroll", isFastScroll);
        outState.putBoolean("addPadding", addPadding);
        outState.putBoolean("isShadowVisible", isShadowVisible);
        outState.putBoolean("hasHeaderAndFooter", hasHeaderAndFooter);
    }


    private void updateDataset() {
        mDatasetUpdateCount++;
        TemplateAdapter adapter = (TemplateAdapter) lvTemplates.getAdapter();
        switch (mDatasetUpdateCount % 4) {
            case 0:
                adapter.generateDataset('A', 'B', true);
                break;
            case 1:
                adapter.generateDataset('C', 'M', true);
                break;
            case 2:
                adapter.generateDataset('P', 'Z', true);
                break;
            case 3:
                adapter.generateDataset('A', 'Z', true);
                break;
        }
        adapter.notifyDataSetChanged();
    }

    private void initializePadding() {
        float density = getResources().getDisplayMetrics().density;
        int padding = addPadding ? (int) (16 * density) : 0;
        lvTemplates.setPadding(padding, padding, padding, padding);
    }

    private void initializeHeaderAndFooter() {
        lvTemplates.setAdapter(null);
        if (hasHeaderAndFooter) {
            ListView list = lvTemplates;

            LayoutInflater inflater = LayoutInflater.from(this);
            TextView header1 = (TextView) inflater.inflate(android.R.layout.simple_list_item_1, list, false);
            header1.setText("First header");
            list.addHeaderView(header1);

            TextView header2 = (TextView) inflater.inflate(android.R.layout.simple_list_item_1, list, false);
            header2.setText("Second header");
            list.addHeaderView(header2);

            TextView footer = (TextView) inflater.inflate(android.R.layout.simple_list_item_1, list, false);
            footer.setText("Single footer");
            list.addFooterView(footer);
        }
        initializeAdapter();
    }

    @SuppressLint("NewApi")
    private void initializeAdapter() {
        lvTemplates.setFastScrollEnabled(isFastScroll);
        if (isFastScroll) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                lvTemplates.setFastScrollAlwaysVisible(true);
            }
            lvTemplates.setAdapter(new TemplateFastScrollAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1));
        } else {
            lvTemplates.setAdapter(new TemplateAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1));
        }
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
