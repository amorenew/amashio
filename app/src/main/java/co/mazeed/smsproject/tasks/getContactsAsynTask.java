package co.mazeed.smsproject.tasks;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import co.mazeed.smsproject.Adapters.ContactsAdapter;
import co.mazeed.smsproject.controller.communication.AsyncTaskListener;
import co.mazeed.smsproject.controller.communication.AsyncTaskLocalListener;
import co.mazeed.smsproject.controller.communication.Task;
import co.mazeed.smsproject.storage.ServiceStorage;
import co.mazeed.smsprojects.model.ContactData;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
/**
 * Created by neolptp on 7/10/2015.
 */
public class getContactsAsynTask extends AsyncTask<Void, Integer, Void>
{
    private Context mContext;
    private AsyncTaskLocalListener taskListener;

    private ProgressDialog pb;
    String[] aNameFromContacts;
    String[] aNumbersFromContacts;
    Map<String,String> namesNumbers=new HashMap<String,String>();
    ArrayList<ContactData> conactsList=new ArrayList<>();



    public getContactsAsynTask(Context context,AsyncTaskLocalListener taskListener)
    {
        this.mContext = context;
        this.taskListener = taskListener;

    }

    @Override
    protected void onPreExecute()
    {
        if (taskListener != null)
            taskListener.onStart();

        pb = new ProgressDialog(mContext);
        pb.setTitle("Please wait ...");
        pb.setIndeterminate(false);
        pb.setMessage("Getting Your contacts...");
        pb.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pb.setProgress(0);
        pb.setCancelable(false);
        pb.show();

        super.onPreExecute();
    }
    @Override
    protected Void doInBackground(Void... params) {
        // **Code**
//        getContactList();
        readContacts();
//        int i = 0;
//        Cursor phones = mContext.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
//        int count=phones.getCount();
//                aNameFromContacts = new String[phones.getCount()];
//        aNumbersFromContacts=new String[phones.getCount()];
//        pb.setMax(count);
//
//        int progress;
//        while (phones.moveToNext())
//        {
//            String name=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
//            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//            String id = phones.getString(phones.getColumnIndex(ContactsContract.Contacts._ID));
//
//            Cursor emailCur = mContext.getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?", new String[]{id}, null);
//
//            while (emailCur.moveToNext()) {
//
//                String email = emailCur.getString(emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
//
//                if (email != null) {
//                    Log.d("Email", email);
//                }
//            }
//            emailCur.close();
////            aNameFromContacts[i] = name;
////            aNumbersFromContacts[i] = phoneNumber;
//                namesNumbers.put(phoneNumber, name);
//
////            i++;
//            progress=(int)((namesNumbers.size()/(float)count)*100);
//            publishProgress(progress);
//            //            publishProgress((int) ((i / (float) count) * 100));
//
//
//        }
//        phones.close();

//        final ContentResolver cr = mContext.getContentResolver();
//
//
//        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
//                null, null, null);
//        aNameFromContacts = new String[cur.getCount()];
//        aNumbersFromContacts=new String[cur.getCount()];
//        int count=cur.getCount();
//        pb.setMax(count);

//        while (cur.moveToNext()) {
//            String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
//            String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
//            Log.i("Names", name);
//
//            aNameFromContacts[i] = name;
//            if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
////                // Query phone here. Covered next
//                Cursor phones = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id, null, null);
//                while (phones.moveToNext()) {
//                    String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//                    Log.d("Number", phoneNumber);
//                    int type = phones.getInt(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
//                    switch (type) {
//                        case ContactsContract.CommonDataKinds.Phone.TYPE_HOME:
//                            // do something with the Home number here...
//                            break;
//                        case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
//                            // do something with the Mobile number here...
//                            break;
//                        case ContactsContract.CommonDataKinds.Phone.TYPE_WORK:
//                            // do something with the Work number here...
//                            break;
//                    }
//                    aNumbersFromContacts[i] = phoneNumber;
//
//
//                }
//
//                phones.close();
//
//            }
//
//            i++;
//            publishProgress((int) ((i / (float) count) * 100));
//
//        }
//
//
//        cur.close();
//if(namesNumbers!=null&&namesNumbers.size()>0) {
////    aNumbersFromContacts =  namesNumbers.keySet().toArray(new String[0]);
////    aNumbersFromContacts= new String[namesNumbers.size()];
////    aNameFromContacts = new String[namesNumbers.size()];
//    int index = 0;
//        for (Map.Entry<String, String> mapEntry : namesNumbers.entrySet()) {
////            aNumbersFromContacts[index]
//                    String number= mapEntry.getKey();
////           aNameFromContacts[index]
//                   String name= mapEntry.getValue();
//            ContactData  data=new ContactData(name,number,index,"");
//            conactsList.add(data);
//        index++;
//    }
//}
        return null;
    }
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        pb.incrementProgressBy(values[0]);
        values[0]++;
    }
    @Override
    protected void onPostExecute(Void result) {
//            super.onPostExecute(result);
        ServiceStorage.contactsList=conactsList;
//        ServiceStorage.contactsNumbersList=aNumbersFromContacts;
        pb.cancel();
        if (taskListener != null)
            taskListener.onFinish();
    }
    public void getContactList() {
        final ContentResolver cr = mContext.getContentResolver();
        int i = 0;
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
                null, null, null);
        aNameFromContacts = new String[cur.getCount()];
        while (cur.moveToNext()) {
            String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
            String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            Log.i("Names", name);
            aNameFromContacts[i] = name;
            if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
//                // Query phone here. Covered next
                Cursor phones = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id, null, null);
                while (phones.moveToNext()) {
                    String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    Log.d("Number", phoneNumber);
                    int type = phones.getInt(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
                    switch (type) {
                        case ContactsContract.CommonDataKinds.Phone.TYPE_HOME:
                            // do something with the Home number here...
                            break;
                        case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
                            // do something with the Mobile number here...
                            break;
                        case ContactsContract.CommonDataKinds.Phone.TYPE_WORK:
                            // do something with the Work number here...
                            break;
                    }
                }
                phones.close();
            }

            i++;
        }


        cur.close();



    }


    public void readContacts() {
        ContentResolver cr = mContext.getContentResolver();
        try {
            String[] PROJECTION = new String[]{ContactsContract.Contacts._ID,
                    ContactsContract.Contacts.DISPLAY_NAME,
                    ContactsContract.CommonDataKinds.Phone.NUMBER
            };

            Cursor c = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    PROJECTION, null, null, null);
                    aNameFromContacts = new String[c.getCount()];
        aNumbersFromContacts=new String[c.getCount()];
        int count=c.getCount();
        pb.setMax(count);
            int i=0;
            if (c.moveToFirst()) {
                String ClsPhonename = null;
                String ClsphoneNo = null;

                do {
                    ClsPhonename = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    ClsphoneNo = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));


                    ClsphoneNo.replaceAll("\\D", "");
                    ClsPhonename = ClsPhonename.replaceAll("&", "");
                    ClsPhonename.replace("|", "");
                    String ClsPhoneName = ClsPhonename.replace("|", "");
                    System.out.println("name  : " + ClsPhonename);
                    namesNumbers.put(ClsPhonename, ClsPhonename);
                    i++;
                    int progress=(int)((namesNumbers.size()/(float)count)*100);

                    publishProgress(progress);



                } while (c.moveToNext());
            }
            c.close();
            if(namesNumbers!=null&&namesNumbers.size()>0) {

                int index = 0;
                for (Map.Entry<String, String> mapEntry : namesNumbers.entrySet()) {
//            aNumbersFromContacts[index]
                    String number = mapEntry.getKey();
//           aNameFromContacts[index]
                    String name = mapEntry.getValue();
                    ContactData data = new ContactData(name, number, index, "");
                    conactsList.add(data);
                    index++;
                }
            }
        } catch (Exception e) {

        }
    }
}