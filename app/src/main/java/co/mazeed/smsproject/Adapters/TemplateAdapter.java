package co.mazeed.smsproject.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import co.mazeed.smsproject.R;
import co.mazeed.smsprojects.model.TemplateData;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

public class TemplateAdapter extends BaseAdapter implements
        StickyListHeadersAdapter, SectionIndexer {

    private final Context mContext;
    private  HashMap<Integer, Map<Integer, TemplateData>> mTemplatesMap;
    private Integer[] mSectionIndices;
    private Integer[] mSectionLetters;
    private LayoutInflater mInflater;
    ArrayList<TemplateData> mTemplates;

    public TemplateAdapter(Context context,HashMap<Integer, Map<Integer, TemplateData>> mTemplatesmap) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        this.mTemplatesMap = mTemplatesmap;
        Set<Integer> myset=mTemplatesmap.keySet();
        mSectionIndices =myset.toArray(new Integer[myset.size()]);
        mTemplates = getAllData();
//                new ArrayList<TemplateData>(mTemplatesmap.get(mSectionIndices[0]).values());

        mSectionLetters = mSectionIndices;
    }

//    private int[] getSectionIndices() {
//        ArrayList<Integer> sectionIndices = new ArrayList<Integer>();
//        char lastFirstChar = mCountries[0].charAt(0);
//        sectionIndices.add(0);
//        for (int i = 1; i < mCountries.length; i++) {
//            if (mCountries[i].charAt(0) != lastFirstChar) {
//                lastFirstChar = mCountries[i].charAt(0);
//                sectionIndices.add(i);
//            }
//        }
//        int[] sections = new int[sectionIndices.size()];
//        for (int i = 0; i < sectionIndices.size(); i++) {
//            sections[i] = sectionIndices.get(i);
//        }
//        return sections;
//    }
//
//    private Character[] getSectionLetters() {
//        Character[] letters = new Character[mSectionIndices.length];
//        for (int i = 0; i < mSectionIndices.length; i++) {
//            letters[i] = mCountries[mSectionIndices[i]].charAt(0);
//        }
//        return letters;
//    }
    private ArrayList<TemplateData> getAllData() {
        ArrayList<TemplateData> templates = new ArrayList<TemplateData>();
        for (int i = 0; i < mSectionIndices.length; i++) {
          Map<Integer,TemplateData> mapdata= mTemplatesMap.get(mSectionIndices[i]);
            templates.addAll(mapdata.values());
        }
        return templates;
    }
    @Override
    public int getCount() {
        return mTemplates.size();
    }

    @Override
    public Object getItem(int position) {
      return mTemplates.get(position) ;   }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.template_row, parent, false);
            holder.text = (TextView) convertView.findViewById(R.id.tvTemplate);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.text.setText( mTemplates.get(position).getSmsTemplateContent());

        return convertView;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;

        if (convertView == null) {
            holder = new HeaderViewHolder();
            convertView = mInflater.inflate(R.layout.list_section, parent, false);
            holder.text = (TextView) convertView.findViewById(R.id.tvSection);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }

        // set list_section text as first char in name
        CharSequence headerChar = mTemplates.get(position).getCategoryID()+"";
        holder.text.setText(headerChar);

        return convertView;
    }

    /**
     * Remember that these have to be static, postion=1 should always return
     * the same Id that is.
     */
    @Override
    public long getHeaderId(int position) {
        // return the first character of the country as ID because this is what
        // headers are based upon
        return mTemplates.get(position).getCategoryID();
    }

    @Override
    public int getPositionForSection(int section) {
        if (mSectionIndices.length == 0) {
            return 0;
        }

        if (section >= mSectionIndices.length) {
            section = mSectionIndices.length - 1;
        } else if (section < 0) {
            section = 0;
        }
        return mSectionIndices[section];
    }

    @Override
    public int getSectionForPosition(int position) {
        for (int i = 0; i < mSectionIndices.length; i++) {
            if (position < mSectionIndices[i]) {
                return i - 1;
            }
        }
        return mSectionIndices.length - 1;
    }

    @Override
    public Object[] getSections() {
        return mSectionLetters;
    }

    public void clear() {
        mTemplates = new ArrayList<>();
        mSectionIndices = new Integer[0];
        mSectionLetters = new Integer[0];
        notifyDataSetChanged();
    }

    public void restore() {
        mTemplates = getAllData();
//                new ArrayList<TemplateData>(mTemplatesMap.values());
        Set<Integer> myset=mTemplatesMap.keySet();
        mSectionIndices =myset.toArray(new Integer[myset.size()]);
        mSectionLetters = mSectionIndices;
        notifyDataSetChanged();
    }

    class HeaderViewHolder {
        TextView text;
    }

    class ViewHolder {
        TextView text;
    }

}
