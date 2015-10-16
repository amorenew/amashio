package co.mazeed.smsproject.Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import co.mazeed.smsproject.Activities.HomeActivity;
import co.mazeed.smsproject.Adapters.ContactsFragmentPagerAdapter;
import co.mazeed.smsproject.R;
import co.mazeed.smsprojects.model.ContactData;

public class MyContactsFragment extends android.support.v4.app.Fragment implements AllContactsFragment.OnFragmentInteractionListener {
    private Toolbar toolbar;
    HomeActivity mActivity;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_my_contacts, container, false);
        mActivity = (HomeActivity) this.getActivity();
//        toolbar = (Toolbar) findViewById(R.id.appBar);
//
//        toolbar.setTitle(getString(R.string.contacts));
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);

        viewPager = (ViewPager) view.findViewById(R.id.viewpager);

        tabLayout = (TabLayout) view.findViewById(R.id.sliding_tabs);
        tabLayout.setTabTextColors(getResources().getColorStateList(R.color.tab_selector));
        // Give the TabLayout the ViewPager

        return view;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ContactsFragmentPagerAdapter contactsFragmentPagerAdapter = new ContactsFragmentPagerAdapter(getChildFragmentManager(), mActivity.getApplicationContext(),MyContactsFragment.this);
        viewPager.setAdapter(contactsFragmentPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);


    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_my_contacts, menu);
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

    @Override
    public void onFragmentInteraction(Uri uri) {

    }



    @Override
    public void onContactSelected(ContactData uri, boolean b) {

    }
}
