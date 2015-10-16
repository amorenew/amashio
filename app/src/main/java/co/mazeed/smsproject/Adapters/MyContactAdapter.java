
package co.mazeed.smsproject.Adapters;

        import android.content.Context;
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
        import java.util.List;

        import co.mazeed.smsproject.R;
        import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

public class MyContactAdapter extends BaseAdapter implements
        StickyListHeadersAdapter, SectionIndexer,Filterable {

    private final Context mContext;
    private String[] mContacts;
    private String[] mOrginalContacts;
    private int[] mSectionIndices;
    private Character[] mSectionLetters;
    private LayoutInflater mInflater;
    final int[] list;

    public MyContactAdapter(Context context,String[] mContacts) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        Arrays.sort(mContacts);
        this.mOrginalContacts =mContacts;
        this.mContacts=mContacts;
        this.list=new int[mContacts.length];
        mSectionIndices = getSectionIndices();
        mSectionLetters = getSectionLetters();
    }

    private int[] getSectionIndices() {
        ArrayList<Integer> sectionIndices = new ArrayList<Integer>();
        char lastFirstChar = mContacts[0].charAt(0);
        sectionIndices.add(0);
        for (int i = 1; i < mContacts.length; i++) {
            if (mContacts[i].charAt(0) != lastFirstChar) {
                lastFirstChar = mContacts[i].charAt(0);
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
            letters[i] = mContacts[mSectionIndices[i]].charAt(0);
        }
        return letters;
    }

    @Override
    public int getCount() {
        return mContacts.length;
    }

    @Override
    public Object getItem(int position) {
        return mContacts[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.contact_row, parent, false);
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.cbContact);
            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int getPosition = (Integer) buttonView.getTag();  // Here we get the position that we have set for the checkbox using setTag.
                    if(buttonView.isChecked())
                        list[getPosition]=1;
                    else
                        list[getPosition]=0;

                }
            });
            convertView.setTag(holder);
            convertView.setTag(R.id.cbContact, holder.checkBox);


        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.checkBox.setTag(position); // This line is important.

        holder.checkBox.setText(mContacts[position]);
        if(list.length>0) {
            int posit = list[position];
            if (posit != 0) {
                holder.checkBox.setChecked(true);
            } else
                holder.checkBox.setChecked(false);
        }



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
        CharSequence headerChar = mContacts[position].subSequence(0, 1);
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
        return mContacts[position].subSequence(0, 1).charAt(0);
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
        mContacts = new String[0];
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
        return null;
    }

    class HeaderViewHolder {
        TextView text;
    }

    class ViewHolder {
        CheckBox checkBox;
    }

}
