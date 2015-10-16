package co.mazeed.smsproject.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import co.mazeed.smsproject.R;
import co.mazeed.smsproject.storage.ServiceStorage;
import co.mazeed.smsprojects.model.TemplateData;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

public class TemplateAdapter extends BaseAdapter implements
        StickyListHeadersAdapter, SectionIndexer, Filterable {

    private final Context mContext;
    Activity mActivity;
    private HashMap<Integer, Map<Integer, TemplateData>> mTemplatesMap;
    private Integer[] mSectionIndices;
    private Integer[] mSectionLetters;
    private LayoutInflater mInflater;
    ArrayList<TemplateData> mTemplates;
    HashMap<Integer, Map<Integer, TemplateData>> mOrinaglmTemplates;



    public TemplateAdapter(Context context, HashMap<Integer, Map<Integer, TemplateData>> mTemplatesmap) {
       this. mContext = context;
        mInflater = LayoutInflater.from(context);
        this.mTemplatesMap = mTemplatesmap;
        Set<Integer> myset = mTemplatesmap.keySet();
        mSectionIndices = myset.toArray(new Integer[myset.size()]);
        mTemplates = getAllData(mSectionIndices,mTemplatesmap);
        mOrinaglmTemplates=mTemplatesmap;
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
    private ArrayList<TemplateData> getAllData(Integer[] sectionIndices, HashMap<Integer, Map<Integer, TemplateData>>templatesMap) {
        ArrayList<TemplateData> templates = new ArrayList<TemplateData>();
        for (int i = 0; i < sectionIndices.length; i++) {
            Map<Integer, TemplateData> mapdata = templatesMap.get(sectionIndices[i]);
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
        return mTemplates.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.template_row, parent, false);
            holder.text = (TextView) convertView.findViewById(R.id.tvTemplate);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
//        convertView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Toast.makeText(TemplatesActivity.this.getApplicationContext(), "Item " + position + " clicked!", Toast.LENGTH_SHORT).show();
//
//                TemplateData data = (TemplateData)getItem(position);
//                Log.d("OnItemclick", data.getSmsTemplateContent());
//                Intent returnIntent = new Intent();
//                returnIntent.putExtra("result", data.getSmsTemplateContent());
//                mActivity.setResult(Activity.RESULT_OK, returnIntent);
//                mActivity. finish();
//            }
//        });

        holder.text.setText(mTemplates.get(position).getSmsTemplateContent());

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
        if (ServiceStorage.templatesCategoryList != null && ServiceStorage.templatesCategoryList.size() > 0) {
            if (ServiceStorage.templatesCategoryList.containsKey(mTemplates.get(position).getCategoryID())) {
                String categoryy = ServiceStorage.templatesCategoryList.get(mTemplates.get(position).getCategoryID()).getCategoryName();
                holder.text.setText(categoryy);
            } else {
                CharSequence headerChar = mTemplates.get(position).getCategoryID() + "";
                holder.text.setText(headerChar);
            }

        } else {
            CharSequence headerChar = mTemplates.get(position).getCategoryID() + "";
            holder.text.setText(headerChar);
        }
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
        mTemplates = getAllData(mSectionIndices,mTemplatesMap);
//                new ArrayList<TemplateData>(mTemplatesMap.values());
        Set<Integer> myset = mTemplatesMap.keySet();
        mSectionIndices = myset.toArray(new Integer[myset.size()]);
        mSectionLetters = mSectionIndices;
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                Log.d("publishResults", "**** PUBLISHING RESULTS for: " + constraint);

                HashMap<Integer, Map<Integer, TemplateData>> templatesmap = (HashMap<Integer, Map<Integer, TemplateData>>) results.values;
                Set<Integer> myset = templatesmap.keySet();
                Integer[] sectionIndices = myset.toArray(new Integer[myset.size()]);
                mTemplates = getAllData(sectionIndices,templatesmap);
//                mOrinaglmTemplates=templatesmap;
                mSectionLetters = mSectionIndices;
                TemplateAdapter.this.notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                Log.d("performFiltering", "**** PERFORM FILTERING for: " + constraint);
                final FilterResults oReturn = new FilterResults();

                final HashMap<Integer, Map<Integer, TemplateData>> results = new HashMap<Integer, Map<Integer, TemplateData>> ();
                if (mOrinaglmTemplates == null)
                    mOrinaglmTemplates = mTemplatesMap;
                if (constraint != null) {
                    if (mOrinaglmTemplates != null && mOrinaglmTemplates.size() > 0) {
                        for (Integer i: mOrinaglmTemplates.keySet()) {
                            for(TemplateData g:mOrinaglmTemplates.get(i).values()) {
                                if (g.getSmsTemplateContent().toLowerCase()
                                        .contains(constraint.toString()))
                                    if(results.containsKey(g.getCategoryID()))
                                    {
                                        Map<Integer, TemplateData> dataMap=results.get(g.getCategoryID());
                                        dataMap.put(g.getSmsTemplateID(),g);
                                        results.put(g.getCategoryID(), dataMap);
                                    }
                                    else
                                    {
                                        Map<Integer, TemplateData> dataMap=new HashMap<Integer, TemplateData>();
                                        dataMap.put(g.getSmsTemplateID(),g);
                                        results.put(g.getCategoryID(), dataMap);

                                    }
                                
                            }
                        }
                    }
                    oReturn.values = results;
                }
                return oReturn;

            }
        };
    }

    class HeaderViewHolder {
        TextView text;
    }

    class ViewHolder {
        TextView text;
    }


}
