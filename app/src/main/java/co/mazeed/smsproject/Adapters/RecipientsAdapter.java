package co.mazeed.smsproject.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import co.mazeed.smsproject.R;

/**
 * Created by amorenew on 6/15/2015.
 */

public class RecipientsAdapter extends ArrayAdapter<String> {


    public RecipientsAdapter(Context context, List<String> items) {
        super(context, R.layout.recipient_row, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.recipient_row, null);
        }

        String p = getItem(position);


        return v;
    }


}
