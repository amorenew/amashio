package co.mazeed.smsproject.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hb.views.PinnedSectionListView;

import java.util.Locale;

import co.mazeed.smsproject.R;

/**
 * Created by amorenew on 6/24/2015.
 */
public class TemplateAdapter extends ArrayAdapter<Item> implements PinnedSectionListView.PinnedSectionListAdapter {

    private static final int[] COLORS = new int[]{
            R.color.gray3};

    public TemplateAdapter(Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
        generateDataset('A', 'Z', false);
    }

    public void generateDataset(char from, char to, boolean clear) {

        if (clear) clear();

        final int sectionsNumber = to - from + 1;
        prepareSections(sectionsNumber);

        int sectionPosition = 0, listPosition = 0;
        for (char i = 0; i < sectionsNumber; i++) {
            Item section = new Item(Item.SECTION, String.valueOf((char) ('A' + i)));
            section.sectionPosition = sectionPosition;
            section.listPosition = listPosition++;
            onSectionAdded(section, sectionPosition);
            add(section);

            final int itemsNumber = (int) Math.abs((Math.cos(2f * Math.PI / 3f * sectionsNumber / (i + 1f)) * 25f));
            for (int j = 0; j < itemsNumber; j++) {
                Item item = new Item(Item.ITEM, section.text.toUpperCase(Locale.ENGLISH) + " - " + j);
                item.sectionPosition = sectionPosition;
                item.listPosition = listPosition++;
                add(item);
            }

            sectionPosition++;
        }
    }

    protected void prepareSections(int sectionsNumber) {
    }

    protected void onSectionAdded(Item section, int sectionPosition) {
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView view = (TextView) super.getView(position, convertView, parent);
        view.setTextColor(Color.DKGRAY);
        view.setTag("" + position);
        Item item = getItem(position);
        if (item.type == Item.SECTION) {
            //view.setOnClickListener(PinnedSectionListActivity.this);
            view.setBackgroundColor(parent.getResources().getColor(COLORS[item.sectionPosition % COLORS.length]));
        }
        return view;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).type;
    }

    @Override
    public boolean isItemViewTypePinned(int viewType) {
        return viewType == Item.SECTION;
    }

}
