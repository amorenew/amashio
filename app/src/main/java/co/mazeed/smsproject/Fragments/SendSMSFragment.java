package co.mazeed.smsproject.Fragments;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources.NotFoundException;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;

import co.mazeed.smsproject.Activities.ChooseContactActivity;
import co.mazeed.smsproject.Activities.HomeActivity;
import co.mazeed.smsproject.Activities.TemplatesActivity;
import co.mazeed.smsproject.R;
import co.mazeed.smsproject.controller.communication.DataRequestor;
import co.mazeed.smsproject.controller.communication.Task;
import co.mazeed.smsproject.storage.ServiceStorage;
import co.mazeed.smsprojects.model.ContactData;
import co.mazeed.smsprojects.model.DraftData;
import co.mazeed.smsprojects.model.GroupInfo;
import co.mazeed.smsprojects.model.UserInfo;

public class SendSMSFragment extends  android.support.v4.app.Fragment  implements View.OnClickListener, DataRequestor, AllContactsFragment.OnFragmentInteractionListener, ContactsGroupFragment.OnFragmentInteractionListener , DatePickerDialog.OnDateSetListener,
        View.OnTouchListener {
    private static final int PICK_CONTACT_REQUEST = 10;
    public static final int PICK_Template_REQUEST = 11;
    ;
    private Toolbar toolbar;


    EditText etContacts;
    TextView tvLettersCount, tvYourPoints;
    Button btnSendNow, btnSchedule,btnDraft;
    TextView tvTemplates;
    EditText etMessage;
    private Activity currentActivity;
    Button numconatcts_selcted;
    private HashMap<Integer, ContactData> selectedContacts;
    private View.OnClickListener clickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            openContactScreen();
        }
    };
    private SharedPreferences appSettin;    private ArrayList<String> drafts;
    private ArrayAdapter adapter;
    GregorianCalendar date = new GregorianCalendar();
    FragmentActivity macHomeActivity;
    private String myMsg="";
    private String num_selected="";
    private String fullConatcts="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        macHomeActivity= getActivity();
        View view = inflater.inflate(R.layout.activity_send_sms, container, false);
        Bundle bundle = this.getArguments();
        if(bundle!=null) {
            myMsg = bundle.getString("result", "");
            num_selected= bundle.getString("numselcted", "");
            fullConatcts=bundle.getString("SelectedNumbers","");

        }appSettin = getActivity().getSharedPreferences("SMS", Context.MODE_PRIVATE);

        initUI(view);


        return view;

    }


    public void initUI(View view) {
        loadArrayDarfts();
        tvTemplates = (TextView) view.findViewById(R.id.tvTemplates);
        tvTemplates.setOnClickListener(this);
        numconatcts_selcted=(Button)view.findViewById(R.id.numconatcts_selcted);
        numconatcts_selcted.setOnClickListener(clickListener);
        etMessage = (EditText) view.findViewById(R.id.etMessage);
        if(myMsg.length()>0)
            etMessage.setText(myMsg);
        etContacts = (EditText) view.findViewById(R.id.etContacts);
        etContacts.setOnClickListener(this);
        tvLettersCount = (TextView) view.findViewById(R.id.tvLettersCount);
        tvYourPoints = (TextView) view.findViewById(R.id.tvYourPoints);
        btnSendNow = (Button) view.findViewById(R.id.btnSendNow);
        btnSchedule = (Button) view.findViewById(R.id.btnSchedule);
        btnDraft=(Button)view.findViewById(R.id.btnDraft);
        btnSchedule.setOnClickListener(this);
        btnSendNow.setOnClickListener(this);
        btnDraft.setOnClickListener(this);
        if (ServiceStorage.currentUser != null) {
            fillUserInfo(ServiceStorage.currentUser);
        }

        updateSelectedContacts();
        etMessage.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                int mod = cs.length() % 160;
                float div = cs.length() / 160;
                int dev = cs.length() / 160;
                if (mod == 0) {
                    if (dev - 1 > 1)
                        tvLettersCount.setText(cs.length() + "/" + (dev - 1) + "sms");
                    else
                        tvLettersCount.setText(cs.length() + "/sms");

                } else {
                    if (dev + 1 > 1)

                        tvLettersCount.setText(cs.length() + "/" + (dev + 1) + "sms");
                    else
                        tvLettersCount.setText(cs.length() + "/sms");
                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });
    }

    public void  updateSelectedContacts()
    {
        numconatcts_selcted.setText(""+num_selected);
        String fullContactsNumbers=etContacts.getText().toString();

        if(fullConatcts.length()>0&&fullContactsNumbers.length()>0)
            etContacts.setText(fullContactsNumbers+","+fullConatcts);
        else if (fullConatcts.length()>0&&fullContactsNumbers.length()==0)
            etContacts.setText(fullConatcts);



    }
    private void fillUserInfo(UserInfo currentUser) {
        tvYourPoints.setText(currentUser.getBalance() + " Your points");

    }

    //    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getActivity().getMenuInflater().inflate(R.menu.menu_send_sms, menu);
//        return true;
//    }
    private void openContactScreen()
    {
//        HomeActivity parentActivity= (HomeActivity) getActivity();
//        FragmentTransaction fragmentTransaction = parentActivity.getSupportFragmentManager().beginTransaction();
////        toolbar.setTitle(getString(R.string.send_sms));
//        MyContactsFragment myContactsFragment = new MyContactsFragment();
//        parentActivity.sendSmsMsg=etMessage.getText().toString();
//        fragmentTransaction.replace(R.id.content, myContactsFragment);
//        parentActivity.mState =parentActivity.HIDE_MENU;
//        parentActivity.invalidateOptionsMenu();
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();

        Intent intent = new Intent(getActivity(), ChooseContactActivity.class);
        startActivityForResult(intent, PICK_CONTACT_REQUEST);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        toolbar.getTitle();
        if (id == android.R.id.home) {

            return true;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_send) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public void onBackPressed() {
//
//        int count = getSupportFragmentManager().getBackStackEntryCount();
//
//        if (count == 0) {
//            super.onBackPressed();
//            //additional code
//        } else {
//            getSupportFragmentManager().popBackStack();
//        }
//
//    }


    @Override
    public void onStart(Task task) {

    }

    @Override
    public void onFinish(Task task) {

    }

    @Override
    public void handleClick() {

    }

    public void onDateClick(View v) {

        try {
//            Locale locale = new Locale("ar");
//            Locale.setDefault(locale);
//            Configuration config = new Configuration();
//            config.locale = locale;
//            getActivity().getApplicationContext().getResources().updateConfiguration(config,
//                    null);
            final DatePickerDialog localDatePickerDialog = new DatePickerDialog(macHomeActivity, SendSMSFragment.this, date.get(1), date.get(2), date.get(5));
            localDatePickerDialog.setTitle(R.string.schedulesms);
            changeDialogTheme(localDatePickerDialog);
            localDatePickerDialog.setButton(-1, getString(R.string.save),
                    new DialogInterface.OnClickListener() {
                        public void onClick(
                                DialogInterface paramAnonymousDialogInterface,
                                int paramAnonymousInt) {
                        }
                    });
            localDatePickerDialog.setButton(-2, getString(R.string.exit),
                    new DialogInterface.OnClickListener() {
                        public void onClick(
                                DialogInterface paramAnonymousDialogInterface,
                                int paramAnonymousInt) {
                        }
                    });
            localDatePickerDialog.show();
            localDatePickerDialog.getButton(-1).setOnClickListener(
                    new View.OnClickListener() {
                        public void onClick(View paramAnonymousView) {
                            if (SendSMSFragment.this
                                    .onDateSet(localDatePickerDialog))
                                localDatePickerDialog.dismiss();
                        }
                    });
            return;
        } catch (Exception localException) {
            while (true)
                localException.printStackTrace();
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear,
                          int dayOfMonth) {
    }

    public boolean onDateSet(DatePickerDialog paramDatePickerDialog) {
        boolean i = true;
        DatePicker localDatePicker = paramDatePickerDialog.getDatePicker();
        GregorianCalendar localGregorianCalendar = new GregorianCalendar(
                localDatePicker.getYear(), localDatePicker.getMonth(),
                localDatePicker.getDayOfMonth());
        if (localGregorianCalendar.getTimeInMillis() > System
                .currentTimeMillis()) {
            Toast.makeText(getActivity(),
                    getResources().getString(R.string.dateValiadtion),
                    Toast.LENGTH_LONG).show();
            i = false;
        }
        while (true) {
            this.date = localGregorianCalendar;
//            refreshDate();
            return i;

        }
    }
    public void changeDialogTheme(DatePickerDialog dialog) {
        LinearLayout llFirst = (LinearLayout) dialog.getDatePicker()
                .getChildAt(0);
        LinearLayout llSecond = (LinearLayout) llFirst.getChildAt(0);
        for (int i = 0; i < llSecond.getChildCount(); i++) {
            NumberPicker picker = (NumberPicker) llSecond.getChildAt(i); // Numberpickers
            // in
            // llSecond
            // reflection - picker.setDividerDrawable(divider); <<
            // didn't seem to work.
            Field[] pickerFields = NumberPicker.class.getDeclaredFields();
            for (Field pf : pickerFields) {
                if (pf.getName().equals("mSelectionDivider")) {
                    pf.setAccessible(true);
                    try {
                        pf.set(picker,
                                this.getResources().getDrawable(
                                        R.drawable.divider_horizontal));
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (NotFoundException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }
        // New top:
        int titleHeight = 90;
        // Container:
        LinearLayout llTitleBar = new LinearLayout(getActivity());
        llTitleBar.setOrientation(LinearLayout.VERTICAL);
        llTitleBar.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                titleHeight));

        // TextView Title:
        TextView tvTitle = new TextView(getActivity());
        tvTitle.setText(this.getString(R.string.schedulesms));
        tvTitle.setGravity(Gravity.CENTER);
        tvTitle.setPadding(10, 10, 10, 10);
        // tvTitle.setTextSize(12);
//        Typeface font = Typeface.createFromAsset(getAssets(),
//                "DroidKufi-Bold.ttf");
//        tvTitle.setTypeface(font);

        tvTitle.setTextColor(getActivity().getResources().getColor(R.color.pruple));
        tvTitle.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                titleHeight - 2));
        llTitleBar.addView(tvTitle);

        // View line:
        View vTitleDivider = new View(getActivity());
        vTitleDivider.setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT, 2));
        vTitleDivider.setBackgroundColor(getActivity().getResources().getColor(
                R.color.pruple));
        llTitleBar.addView(vTitleDivider);

        dialog.getDatePicker().addView(llTitleBar);
        FrameLayout.LayoutParams lp = (android.widget.FrameLayout.LayoutParams) llFirst
                .getLayoutParams();
        lp.setMargins(0, titleHeight, 0, 0);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSchedule:
                onDateClick(v);
                break;
            case R.id.btnSendNow:
                break;
            case R.id.btnDraft:
                drafts.add(etMessage.getText().toString());
                saveDraftsArray();

                break;
            case R.id.etContacts:
//                Intent intent = new Intent(getActivity(), ChooseContactActivity.class);
//                startActivityForResult(intent, PICK_CONTACT_REQUEST);
                break;
            case R.id.tvTemplates:
                Log.d("tv templates", "tvtemplates clicked");
                Intent intent12 = new Intent(getActivity(), TemplatesActivity.class);
                Bundle bundle=new Bundle();
                bundle.putStringArrayList("DraftsList",drafts);
                intent12.putExtras(bundle);
                startActivityForResult(intent12, PICK_Template_REQUEST);
                break;
        }

    }
    public  boolean saveDraftsArray()
    {

        SharedPreferences.Editor mEdit1 = appSettin.edit();
        mEdit1.putInt("Drafts_size", drafts.size()); /* sKey is an array */

        for(int i=0;i<drafts.size();i++)
        {
            mEdit1.remove("Draft_" + i);
            mEdit1.putString("Draft_" + i, drafts.get(i));
        }
        boolean done =mEdit1.commit();
        Toast.makeText(this.getActivity(),"Msg  saved as draft",Toast.LENGTH_SHORT).show();
        return done;

    }
    public void loadArrayDarfts()
    {

        drafts = new ArrayList();
        int size = appSettin.getInt("Drafts_size", 0);

        for(int i=0;i<size;i++)
        {
            drafts.add(appSettin.getString("Draft_" + i, null));
        }


    }


//    @Override
//    public boolean onKey(View v, int keyCode, KeyEvent event) {
//        // TODO Auto-generated method stub
//        if( keyCode == KeyEvent.KEYCODE_BACK ){
//            // back to previous fragment by tag
//            SendSMSFragment fragment = (SendSMSFragment) getActivity().getSupportFragmentManager().findFragmentByTag("SendSMS");
//            if(fragment != null){
//                (getActivity().getSupportFragmentManager().beginTransaction()).replace(R.id.content, fragment).commit();
//            }
//            return true;
//        }
//        return false;
//    }
//};

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onGroupselected(String Groupselected, HashMap<String, GroupInfo> groupInfoHashMap) {

    }

    @Override
    public void onContactSelected(ContactData uri, boolean b) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        super.onActivityResult(requestCode, resultCode, data);
//        getActivity();
//        if(requestCode == 1 && resultCode == Activity.RESULT_OK) {
//            //some code
//        }
        Log.d("OnactivityResult","SendSMS");
        if (requestCode == PICK_CONTACT_REQUEST&&resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra("result");
//                String number = data.getStringExtra("size");
                   int size= result.split(",").length;
                numconatcts_selcted.setText(""+size);
                etContacts.setText(result);


        } else if (requestCode == PICK_Template_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra("result");
                etMessage.setText(result);

            }

        }

    }


    public void mCreateAndSaveFile(String params, String mJsonResponse) {
        try {
            FileWriter file = new FileWriter("/data/data/"
                    + this.getActivity().getPackageName() + "/" + params);
            file.write(mJsonResponse);
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void showAlert()
    {
        currentActivity=this.getActivity();
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                currentActivity.getApplicationContext());

        // set title
        alertDialogBuilder.setTitle("Draft...");
        // set dialog message
        alertDialogBuilder.setMessage("save it as  a darfat!");
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, close
                        // current activity
                        ServiceStorage.drafts.add(new DraftData(etMessage.getText().toString(),System.currentTimeMillis()));
                        currentActivity.finish();
                    }
                }

        )
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                                currentActivity.finish();

                            }
                        }

                );

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
}
