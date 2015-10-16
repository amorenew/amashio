package co.mazeed.smsprojects.model;

import android.annotation.SuppressLint;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by neolptp on 7/14/2015.
 */
@SuppressLint("ParcelCreator")
public class ContactInfo implements Serializable {
    public  String name;
    public String number;

    @Override
    public String toString() {
        return name+ " ("+number+")";
    }

    public String getName() {
        return name;
    }
    public String getNumber() {
        return number;
    }
    public static Comparator<GroupInfo> ContactNameComparator = new Comparator<GroupInfo>() {

        public int compare(GroupInfo s1, GroupInfo s2) {
            String contactName1 = s1.title.toUpperCase();
            String contactName2 = s2.title.toUpperCase();

            //ascending order
            return contactName1.compareTo(contactName2);


        }};


}
