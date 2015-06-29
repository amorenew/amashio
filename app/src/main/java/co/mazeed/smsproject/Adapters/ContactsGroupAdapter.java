package co.mazeed.smsproject.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

import co.mazeed.smsproject.R;

public class ContactsGroupAdapter extends ArrayAdapter {

    private final Context mContext;
    private String[] mCountries;
    private LayoutInflater mInflater;


    public ContactsGroupAdapter(Context context) {
        super(context, 0);
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mCountries = context.getResources().getStringArray(R.array.countries);
    }

    @Override
    public int getCount() {
        return mCountries.length;
    }

    @Override
    public Object getItem(int position) {
        return mCountries[position];
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
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.checkBox.setText(mCountries[position]);

        return convertView;
    }

    class ViewHolder {
        CheckBox checkBox;
    }

}
