package co.mazeed.smsproject.Adapters;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.SectionIndexer;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import co.mazeed.smsproject.Activities.HomeActivity;
import co.mazeed.smsproject.R;
import co.mazeed.smsprojects.model.ContactData;
import co.mazeed.smsprojects.model.TemplateData;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

public class ContactsAdapter extends BaseAdapter implements
        StickyListHeadersAdapter, SectionIndexer,Filterable {

    private Activity mContext;
    private ArrayList<ContactData> mContacts;
    private ArrayList<ContactData> mOrginalContacts;
    private int[] mSectionIndices;
    private Character[] mSectionLetters;
    private LayoutInflater mInflater;
    int[] list;
    private ContactData contact;

    public interface onContactSelectorUnSelect {
        public void onSelectContact(ContactData uri);
        public void onUnselectContact(ContactData uri);
    }
    onContactSelectorUnSelect mSelectionListener;
    public ContactsAdapter(Activity context,ArrayList<ContactData> mNumbers,onContactSelectorUnSelect listener) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        Collections.sort(mNumbers, ContactData.conatctNameComparator);
        this.mOrginalContacts =mNumbers;
        this.mContacts=mNumbers;
        mSelectionListener=listener;
        this.list=new int[mContacts.size()];
        mSectionIndices = getSectionIndices();
        mSectionLetters = getSectionLetters();
    }

//    public ContactsAdapter(Context context,String[] mContacts, String[] aNumberFromContacts) {
//    }

    private int[] getSectionIndices() {
        ArrayList<Integer> sectionIndices = new ArrayList<Integer>();
        char lastFirstChar = mContacts.get(0).getContactName().charAt(0);
        sectionIndices.add(0);
        for (int i = 1; i < mContacts.size(); i++) {
            if (mContacts.get(i).getContactName().charAt(0) != lastFirstChar) {
                lastFirstChar = mContacts.get(i).getContactName().charAt(0);
                sectionIndices.add(i);
            }
        }
        int[] sections = new int[sectionIndices.size()];
        for (int i = 0; i < sectionIndices.size(); i++) {
            sections[i] = sectionIndices.get(i);
        }
        return sections;
    }

    private Character[] getSectionLetters() {
        Character[] letters = new Character[mSectionIndices.length];
        for (int i = 0; i < mSectionIndices.length; i++) {
            letters[i] = mContacts.get(mSectionIndices[i]).getContactName()
                    .charAt(0);
        }
        return letters;
    }

    @Override
    public int getCount() {
        return mContacts.size();
    }

    @Override
    public Object getItem(int position) {
        return mContacts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView( final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            contact=mContacts.get(position);
            convertView = mInflater.inflate(R.layout.contact_row, parent, false);
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.cbContact);
            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int getPosition = (Integer) buttonView.getTag();  // Here we get the position that we have set for the checkbox using setTag.
                    if(buttonView.isChecked()) {
                        list[getPosition] = 1;
//                        mSelectionListener.onSelectContact(mContacts.get(getPosition));
                    }
                    else {
                        list[getPosition] = 0;

                    }

                }
            });


            convertView.setTag(holder);
            convertView.setTag(R.id.cbContact, holder.checkBox);


        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //if  its  selected  before
//        if(mContext.selectedContacts.containsKey(mContacts.get(position).getContactID()))
//        {
////            int pos= (int) holder.checkBox.getTag();
//            list[position] = 1;
//
//        }
        holder.checkBox.setTag(position); // This line is important.
        holder.checkBox.setText(mContacts.get(position).getContactName());

        if(list.length>0) {
            int posit = list[position];
            if (posit != 0) {
                holder.checkBox.setChecked(true);
            } else
                holder.checkBox.setChecked(false);
        }
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int getPosition = (Integer) buttonView.getTag();  // Here we get the position that we have set for the checkbox using setTag.
                if(buttonView.isChecked()) {
                    list[getPosition] = 1;
                    mSelectionListener.onSelectContact(mContacts.get(getPosition));
                }
                else {
                    list[getPosition] = 0;
                    mSelectionListener.onUnselectContact(mContacts.get(getPosition));


                }
            }
        });



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
        CharSequence headerChar = mContacts.get(position).getContactName().subSequence(0, 1);
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
        return mContacts.get(position).getContactName().subSequence(0, 1).charAt(0);
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
        mContacts = new ArrayList<ContactData>();
        mSectionIndices = new int[0];
        mSectionLetters = new Character[0];
        notifyDataSetChanged();
    }

    public void restore() {
        mContacts = this.mOrginalContacts;
        mSectionIndices = getSectionIndices();
        mSectionLetters = getSectionLetters();
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                Log.d("publishResults", "**** PUBLISHING RESULTS for: " + constraint);

                ArrayList<ContactData> resultList =(ArrayList<ContactData>) results.values;
                mContacts = resultList;
                ContactsAdapter.this.notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                Log.d("performFiltering", "**** PERFORM FILTERING for: " + constraint);
                final FilterResults oReturn = new FilterResults();

                ArrayList<ContactData> results =new ArrayList<ContactData>();

                if (mOrginalContacts == null)
                    mOrginalContacts = mContacts;
                if (constraint != null) {
                    if (mOrginalContacts != null && mOrginalContacts.size() > 0) {
                        for (ContactData i: mOrginalContacts) {
                            if (i.getContactName().toLowerCase()
                                    .startsWith(constraint.toString()))
                            {
                                results.add(i);
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
        CheckBox checkBox;
    }

}
