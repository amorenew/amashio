package co.mazeed.smsproject.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import co.mazeed.smsproject.Fragments.AllContactsFragment;
import co.mazeed.smsproject.Fragments.ContactsGroupFragment;
import co.mazeed.smsproject.R;

/**
 * Created by amorenew on 6/29/2015.
 */
public class ContactsFragmentPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 2;
    private String tabTitles[];
    private Context context;
    private Fragment[] fragments = new Fragment[]{new AllContactsFragment(), new ContactsGroupFragment()};

    public ContactsFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        tabTitles = new String[]{context.getString(R.string.all_contacts), context.getString(R.string.groups)};
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}