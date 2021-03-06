package com.onescream.settings;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.onescream.HomeActivity;
import com.onescream.Utils.Utility;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.Date;

import com.onescream.R;
import com.uc.prjcmn.ActivityTask;
import com.uc.prjcmn.GlobalValues;
import com.uc.prjcmn.PRJCONST;
import com.uc.prjcmn.PRJFUNC;
import com.uc.prjcmn.SharedPreferencesMgr;

/**
 * Activity class for Your Details Screen
 * <p>
 * Created by Anwar Almojarkesh
 */

public class YourDetailsActivity extends Activity implements View.OnClickListener {

    private final String TAG = "YourDetailsActivity";

    private Context mContext;

    private EditText m_etName;
    private EditText m_etPhone;
    private EditText m_etEmail;

    private TextView m_tvUpgradePlan;
    private TextView m_tvRemainedDays;
    private TextView tv_pi, tv_name, tv_phone, tv_email, tv_ypp, tv_your_address;
    Typeface facethin, facebold, faceRegular, EstiloRegular, faceMedium;
    private Utility utility;

    // ////////////////////////////////////////////////////////////
    // //////////////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        utility = new Utility(this);
        utility.RegisterScreen(this, getResources().getString(R.string.personal_info));
        setContentView(R.layout.activity_new_your_details);
        ActivityTask.INSTANCE.add(this);

        mContext = (Context) this;

        updateLCD();

        refreshUI();
    }

    @Override
    protected void onDestroy() {
        releaseValues();

        ActivityTask.INSTANCE.remove(this);

        super.onDestroy();
    }

    private void releaseValues() {

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            goBack();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(YourDetailsActivity.this, HomeActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
        finish();
    }

    // //////////////////////////////////////////////////
    private void updateLCD() {
        initTypeFace();
        // Back button in header menu
        findViewById(R.id.iv_back).setOnClickListener(this);

        findViewById(R.id.frm_board).setOnClickListener(this);
        TextView title = (TextView) findViewById(R.id.title);

        Typeface sanfacebold = Typeface.createFromAsset(this
                        .getAssets(),
                "fonts/SanFranciscoDisplay-Bold.otf");
        title.setTypeface(sanfacebold);
        ///> Your details
        m_etName = (EditText) findViewById(R.id.et_name);
        m_etPhone = (EditText) findViewById(R.id.et_phone);
        m_etEmail = (EditText) findViewById(R.id.et_email);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_pi = (TextView) findViewById(R.id.tv_pi);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_phone = (TextView) findViewById(R.id.tv_phone);
        tv_email = (TextView) findViewById(R.id.tv_email);
        tv_ypp = (TextView) findViewById(R.id.tv_ypp);
        tv_your_address = (TextView) findViewById(R.id.tv_your_address);
        m_etEmail.setEnabled(false);

        ///> Update Information
        findViewById(R.id.frm_update_info).setOnClickListener(this);


        ///> Your protection plan
        m_tvUpgradePlan = (TextView) findViewById(R.id.tv_upgrade_plan);
        m_tvRemainedDays = (TextView) findViewById(R.id.tv_remained_days);

        ///> Upgrade memeber
        findViewById(R.id.frm_subscribe).setOnClickListener(this);

        ///> WiFi addresses
        findViewById(R.id.frm_wifi_addresses).setOnClickListener(this);

        TextView managetxt = (TextView) findViewById(R.id.tv_upgrade_member);
        TextView updtaetxt = (TextView) findViewById(R.id.tv_wifi_addresses);
        managetxt.setTypeface(facebold);
        updtaetxt.setTypeface(facebold);

        if (utility.getScreenSize()) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            Log.e(TAG, " dp== " + utility.convertPixelsToDp(getResources().getInteger(R.integer.margin_top), this));
            params.topMargin = utility.convertPixelsToDp(10, this);
            tv_name.setLayoutParams(params);
            tv_pi.setLayoutParams(params);
            tv_your_address.setLayoutParams(params);
            tv_ypp.setLayoutParams(params);
            tv_email.setLayoutParams(params);
            tv_phone.setLayoutParams(params);
            m_etName.setLayoutParams(params);
            m_etEmail.setLayoutParams(params);
            m_etPhone.setLayoutParams(params);
            tv_pi.setTextSize(getResources().getInteger(R.integer.text_size));
            tv_ypp.setTextSize(getResources().getInteger(R.integer.text_size));
            tv_your_address.setTextSize(getResources().getInteger(R.integer.text_size));

            tv_name.setTextSize(getResources().getInteger(R.integer.text_size));
            tv_phone.setTextSize(getResources().getInteger(R.integer.text_size));
            tv_email.setTextSize(getResources().getInteger(R.integer.text_size));
        }


    }

    private void initTypeFace() {
        facethin = Typeface.createFromAsset(this.getAssets(),
                "fonts/Roboto-Thin.ttf");
        faceMedium = Typeface.createFromAsset(this.getAssets(),
                "fonts/Roboto-Medium.ttf");
        facebold = Typeface.createFromAsset(this
                        .getAssets(),
                "fonts/SanFranciscoDisplay-Bold.otf");

        faceRegular = Typeface.createFromAsset(this.getAssets(),
                "fonts/Roboto-Regular.ttf");
        EstiloRegular = Typeface.createFromAsset(this.getAssets(),
                "fonts/EstiloRegular.otf");
    }

    private void refreshUI() {
        ParseUser user = ParseUser.getCurrentUser();

        m_etName.setText(user.getString("first_name"));
        m_etPhone.setText(user.getString("phone"));
        m_etEmail.setText(user.getEmail());

        Date date_expiry = user.getDate("expiry_date");
        Date now = new Date();
        if (date_expiry == null)
            date_expiry = now;

        String strPayStatus = "Expired";
        long nRemainedDays = 0;
        long seconds = (date_expiry.getTime() - now.getTime()) / 1000;
        long days = (int) (seconds / (double) (24 * 3600) + 0.5);
        if (seconds >= 0) {
            if (user.getString("user_type").equals(PRJCONST.USER_TYPE_MONTH)) {
                strPayStatus = String.format("MONTH PLAN");
                nRemainedDays = days;
            } else if (user.getString("user_type").equals(PRJCONST.USER_TYPE_YEAR)) {
                strPayStatus = String.format("YEAR PLAN");
                nRemainedDays = days;
            } else {
                strPayStatus = String.format("TRIAL PLAN");
                nRemainedDays = days;
            }
        }

        m_tvUpgradePlan.setText(strPayStatus);
        m_tvRemainedDays.setText(String.format("(%d days remaining)", nRemainedDays));
    }


    // /////////////////////////////////////
    public void goBack() {
        Intent intent = new Intent(YourDetailsActivity.this, HomeActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
        finish();

    }

    public void goToUpgradeMemberActivity() {
        Intent intent = new Intent(YourDetailsActivity.this, SubscribeActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.right_in, R.anim.hold);
    }

    public void goToWiFiAddressesActivity() {
        Intent intent = new Intent(YourDetailsActivity.this, FrequentAddress.class);
        startActivity(intent);
        overridePendingTransition(R.anim.right_in, R.anim.hold);
    }

    /**
     * OnClick Event method
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                goBack();
                break;
            case R.id.frm_board:
                PRJFUNC.hideKeyboard(YourDetailsActivity.this);
                break;
            case R.id.frm_subscribe:
                //goToUpgradeMemberActivity();
                break;
            case R.id.frm_update_info:
                updateInformation();
                break;
            case R.id.frm_wifi_addresses:
                goToWiFiAddressesActivity();
                break;
            default:
                break;
        }
    }

    private String m_strName;
    private String m_strPhone;

    public void updateInformation() {
        m_strName = m_etName.getText().toString();
        m_strPhone = m_etPhone.getText().toString();

        ParseUser user = ParseUser.getCurrentUser();
        user.put("first_name", m_strName);
        user.put("last_name", "");
        user.put("phone", m_strPhone);

        PRJFUNC.showProgress(mContext, "Updating... ");

        user.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                PRJFUNC.closeProgress();
                if (e != null) {
                    PRJFUNC.showAlertDialog(YourDetailsActivity.this, e.getMessage());
                    return;
                }

                ParseUser user = ParseUser.getCurrentUser();
                GlobalValues.sharedInstance().m_strFirstName = user.getEmail();
                GlobalValues.sharedInstance().m_strFirstName = m_strName;
                GlobalValues.sharedInstance().m_strLastName = "";
                GlobalValues.sharedInstance().m_strPhoneNumber = m_strPhone;

                SharedPreferencesMgr phoneDb = new SharedPreferencesMgr(mContext);
                phoneDb.saveUserExtraInfo();

                Toast.makeText(mContext, "Updated successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
