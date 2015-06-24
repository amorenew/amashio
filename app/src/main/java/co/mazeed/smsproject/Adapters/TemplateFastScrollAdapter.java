package co.mazeed.smsproject.Adapters;

import android.content.Context;
import android.widget.SectionIndexer;

/**
 * Created by amorenew on 6/24/2015.
 */
public class TemplateFastScrollAdapter extends TemplateAdapter implements SectionIndexer {

    private Item[] sections;

    public TemplateFastScrollAdapter(Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    @Override
    protected void prepareSections(int sectionsNumber) {
        sections = new Item[sectionsNumber];
    }

    @Override
    protected void onSectionAdded(Item section, int sectionPosition) {
        sections[sectionPosition] = section;
    }

    @Override
    public Item[] getSections() {
        return sections;
    }

    @Override
    public int getPositionForSection(int section) {
        if (section >= sections.length) {
            section = sections.length - 1;
        }
        return sections[section].listPosition;
    }

    @Override
    public int getSectionForPosition(int position) {
        if (position >= getCount()) {
            position = getCount() - 1;
        }
        return getItem(position).sectionPosition;
    }

}