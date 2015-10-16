package co.mazeed.smsproject.Adapters;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import co.mazeed.smsproject.R;
import co.mazeed.smsprojects.model.GroupData;
import co.mazeed.smsprojects.model.GroupInfo;

public class ContactsGroupAdapter extends ArrayAdapter {

    private final Context mContext;
    private LayoutInflater mInflater;
    //    private ArrayList<GroupInfo> mContacts;
//    private ArrayList<GroupInfo> mOrginalContacts;
    private ArrayList<GroupData> mContacts;
    private ArrayList<GroupData> mOrginalContacts;

//    public ContactsGroupAdapter(Context context, ArrayList<GroupInfo> mContacts) {
//        super(context, 0);
//
//        mContext = context;
//        mInflater = LayoutInflater.from(context);
//        Collections.sort(mContacts, GroupInfo.GroupNameComparator);
//
//        this.mOrginalContacts = mContacts;
//        this.mContacts = mContacts;
//        mInflater = LayoutInflater.from(context);
//    }

    public ContactsGroupAdapter(Context context, ArrayList<GroupData> contactsGroupList) {
        super(context, 0);

        mContext = context;
        mInflater = LayoutInflater.from(context);
        Collections.sort(contactsGroupList, GroupData.GroupNameComparator);
        this.mOrginalContacts = contactsGroupList;
        this.mContacts = contactsGroupList;
        mInflater = LayoutInflater.from(context);
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
        return (mContacts.get(position).getGroupID());
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

        holder.checkBox.setText(mContacts.get(position).getGroupName());

        return convertView;
    }

    class ViewHolder {
        CheckBox checkBox;
    }

}
