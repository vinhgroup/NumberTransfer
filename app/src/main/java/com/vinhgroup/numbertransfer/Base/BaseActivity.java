package com.vinhgroup.numbertransfer.Base;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.vinhgroup.numbertransfer.Model.TestResuilt.TestResuilt;
import com.vinhgroup.numbertransfer.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Vinh on 6/4/2018.
 */

public class BaseActivity extends SumBase {


    @BindView(R.id.adView)
    AdView mAdView;

    public List<TestResuilt> getNumberPhones(Context context, int numberUneed, boolean isTen) {
        List<TestResuilt> arrTestResuiltLocal = new ArrayList<>();
        try {
            boolean isStop = false;
            ContentResolver cr = context.getContentResolver();
            Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
                    null, null, null);
            if (cur.getCount() > 0) {
                while (cur.moveToNext()) {
                    String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                    String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    Integer hasPhone = cur.getInt(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                    Log.i("Names", name);
                    String email = null;
                    Cursor ce = cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?", new String[]{id}, null);
                    if (ce != null && ce.moveToFirst()) {
                        email = ce.getString(ce.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                        ce.close();
                    }
                    if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                        // Query phone here. Covered next
                        Cursor phones = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id, null, null);
                        while (phones.moveToNext()) {
                            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)).replace(" ", "");
//                        Log.i("Number", phoneNumber +  email);
                            if (isTen) {
                                if (isTenBeforeChange(phoneNumber)) {
                                    String LastPart = phoneNumber.substring(3, phoneNumber.length());
                                    arrTestResuiltLocal.add(new TestResuilt(id,
                                            name,
                                            phoneNumber,
                                            getFirstPartTransferEleven(phoneNumber) + LastPart,
                                            email,
                                            false));
                                }
                            } else {
                                if (isElevenBeforeChange(phoneNumber)) {
                                    String LastPart = phoneNumber.substring(4, phoneNumber.length());
                                    arrTestResuiltLocal.add(new TestResuilt(id, name, phoneNumber, getFirstPartTransferTen(phoneNumber) + LastPart, email, false));
                                }
                            }
                            if (numberUneed != 0 && arrTestResuiltLocal.size() == numberUneed) {
                                isStop = true;
                                break;
                            }
                        }
                        phones.close();
                    }
                    if (isStop) {
                        break;
                    }
                }
            }
        } catch (Exception e) {
            Log.d("VinhCNLog", "Catch");
            arrTestResuiltLocal = null;
        } finally {
            return arrTestResuiltLocal;
        }
    }


    public void showDialogInform(String msg, final Context context, final boolean isShowConfirm) {
       final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
//        alertDialog.setTitle("Thông báo");
        alertDialog.setMessage(msg);
        alertDialog.setButton(android.support.v7.app.AlertDialog.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (isShowConfirm){
                            onBackPressed();
                            // Toast.makeText(context, "Vui lòng  trở về màn hình trước", Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }


    public void beginWorking() {

    }

    Context contextRequest;
    public void checkRunTimePermission(Context context) {
        contextRequest = context;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) context, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_CONTACTS},
                        11111);
                return;
            } else {
                beginWorking();
            }
        } else {
            beginWorking();
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 11111) {
            int size = permissions.length;
            int size2 = grantResults.length;
            Log.d("VinhCNLog: ",  size + size2 + "");
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) contextRequest, Manifest.permission.READ_CONTACTS)){
                showDialogInform(contextRequest.getString(R.string.please_allow_me_to_change), contextRequest, true);
            }else{
                beginWorking();
            }

        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        initActionbar();
        super.onCreate(savedInstanceState);
    }


    public void initAdsBottomBanner(Context context) {
        MobileAds.initialize(context, getString(R.string.ads_mod_id));
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when when the user is about to return
                // to the app after tapping on an ad.
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean isElevenBeforeChange(String phoneNumber) {
        if (phoneNumber.length() == 11 && (
//                0162, 0163, 0164,0165, 0166, 0167, 0168,0169
                phoneNumber.substring(0, 4).equals("0162") ||
                        phoneNumber.substring(0, 4).equals("0163") ||
                        phoneNumber.substring(0, 4).equals("0164") ||
                        phoneNumber.substring(0, 4).equals("0165") ||
                        phoneNumber.substring(0, 4).equals("0166") ||
                        phoneNumber.substring(0, 4).equals("0167") ||
                        phoneNumber.substring(0, 4).equals("0168") ||
                        phoneNumber.substring(0, 4).equals("0169") ||
//                        0120, 0121, 0122, 0126, 0128
                        phoneNumber.substring(0, 4).equals("0120") ||
                        phoneNumber.substring(0, 4).equals("0121") ||
                        phoneNumber.substring(0, 4).equals("0122") ||
                        phoneNumber.substring(0, 4).equals("0126") ||
                        phoneNumber.substring(0, 4).equals("0128") ||
//                        0123, 0124, 0125, 0127, 0129
                        phoneNumber.substring(0, 4).equals("0123") ||
                        phoneNumber.substring(0, 4).equals("0124") ||
                        phoneNumber.substring(0, 4).equals("0125") ||
                        phoneNumber.substring(0, 4).equals("0127") ||
                        phoneNumber.substring(0, 4).equals("0129") ||
                        //        0188, 0186,  0199
                        phoneNumber.substring(0, 4).equals("0188") ||
                        phoneNumber.substring(0, 4).equals("0186") ||
                        phoneNumber.substring(0, 4).equals("0199")
        )) {
            return true;
        }
        return false;
    }

    private String getFirstPartTransferEleven(String phoneNumber) {
        String FirstPart = phoneNumber.substring(0, 3);
        switch (FirstPart) {
            //                            0162, 0163, 0164,0165, 0166, 0167, 0168,0169
//                                032, 033, 034, 035, 036, 037, 038, 039
            case "032":
                FirstPart = "0162";
                break;
            case "033":
                FirstPart = "0163";
                break;
            case "034":
                FirstPart = "0164";
                break;
            case "035":
                FirstPart = "0165";
                break;
            case "036":
                FirstPart = "0166";
                break;
            case "037":
                FirstPart = "0167";
                break;
            case "038":
                FirstPart = "0168";
                break;
            case "039":
                FirstPart = "0169";
                break;
            //                            0120, 0121, 0122, 0126, 0128
//                                070, 079, 077, 076, 078.
            case "070":
                FirstPart = "0120";
                break;
            case "079":
                FirstPart = "0121";
                break;
            case "077":
                FirstPart = "0122";
                break;
            case "076":
                FirstPart = "0126";
                break;
            case "078":
                FirstPart = "0128";
                break;
            //                            0123, 0124, 0125, 0127, 0129
            //                                083, 084, 085, 081, 082
            case "083":
                FirstPart = "0123";
                break;
            case "084":
                FirstPart = "0124";
                break;
            case "085":
                FirstPart = "0125";
                break;
            case "081":
                FirstPart = "0127";
                break;
            case "082":
                FirstPart = "0129";
                break;
//                                0188, 0186
            case "056":
                FirstPart = "0188";
                break;
            case "058":
                FirstPart = "0186";
                break;
//                                0199
            case "059":
                FirstPart = "0199";
                break;
            default:
                break;
        }
        return FirstPart;
    }


    public String getEmail(String contactId, Context context) {
        String emailStr = "";
        Cursor emailCur = context.getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?", new String[]{contactId}, null);
        while (emailCur.moveToNext()) {
            String email = emailCur.getString(emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
            Log.e("Email", emailStr + " " + email);
        }
        emailCur.close();
        return emailStr;
    }


    boolean isTenBeforeChange(String phoneNumber) {
        if (phoneNumber.length() == 10 && (
                //                032, 033, 034, 035, 036, 037, 038, 039
                phoneNumber.substring(0, 3).equals("032") ||
                        phoneNumber.substring(0, 3).equals("033") ||
                        phoneNumber.substring(0, 3).equals("034") ||
                        phoneNumber.substring(0, 3).equals("035") ||
                        phoneNumber.substring(0, 3).equals("036") ||
                        phoneNumber.substring(0, 3).equals("037") ||
                        phoneNumber.substring(0, 3).equals("038") ||
                        phoneNumber.substring(0, 3).equals("039") ||
                        //        070, 079, 077, 076, 078.
                        phoneNumber.substring(0, 3).equals("070") ||
                        phoneNumber.substring(0, 3).equals("079") ||
                        phoneNumber.substring(0, 3).equals("077") ||
                        phoneNumber.substring(0, 3).equals("076") ||
                        phoneNumber.substring(0, 3).equals("078") ||
                        //                083, 084, 085, 081, 082
                        phoneNumber.substring(0, 3).equals("083") ||
                        phoneNumber.substring(0, 3).equals("084") ||
                        phoneNumber.substring(0, 3).equals("085") ||
                        phoneNumber.substring(0, 3).equals("081") ||
                        phoneNumber.substring(0, 3).equals("082") ||
                        //        056, 058 059
                        phoneNumber.substring(0, 3).equals("056") ||
                        phoneNumber.substring(0, 3).equals("058") ||
                        phoneNumber.substring(0, 3).equals("059")
        )) {
            return true;
        }
        return false;
    }

    public String getFirstPartTransferTen(String phoneNumber) {
        String FirstPart = phoneNumber.substring(0, 4);
        switch (FirstPart) {
            //                            0162, 0163, 0164,0165, 0166, 0167, 0168,0169
            //                                032, 033, 034, 035, 036, 037, 038, 039
            case "0162":
                FirstPart = "032";
                break;
            case "0163":
                FirstPart = "033";
                break;
            case "0164":
                FirstPart = "034";
                break;
            case "0165":
                FirstPart = "035";
                break;
            case "0166":
                FirstPart = "036";
                break;
            case "0167":
                FirstPart = "037";
                break;
            case "0168":
                FirstPart = "038";
                break;
            case "0169":
                FirstPart = "039";
                break;
            //                            0120, 0121, 0122, 0126, 0128
//                                070, 079, 077, 076, 078.
            case "0120":
                FirstPart = "070";
                break;
            case "0121":
                FirstPart = "079";
                break;
            case "0122":
                FirstPart = "077";
                break;
            case "0126":
                FirstPart = "076";
                break;
            case "0128":
                FirstPart = "078";
                break;
            //                            0123, 0124, 0125, 0127, 0129
//                                083, 084, 085, 081, 082
            case "0123":
                FirstPart = "083";
                break;
            case "0124":
                FirstPart = "084";
                break;
            case "0125":
                FirstPart = "085";
                break;
            case "0127":
                FirstPart = "081";
                break;
            case "0129":
                FirstPart = "082";
                break;
//                                0188, 0186
            case "0188":
                FirstPart = "056";
                break;
            case "0186":
                FirstPart = "058";
                break;
//                                0199
            case "0199":
                FirstPart = "059";
                break;
            default:
                break;
        }
        return FirstPart;
    }


    /*
     Progress dialog for all activity
*/
    public ProgressDialog mProgressDialog;

    public void showProgressDialog(String msg, Context context) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(context);
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setTitle(null);
            mProgressDialog.setMessage(msg);
            mProgressDialog.show();
        }

    }

    /*
    close dialog for all activity
*/

    public void closeProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    public void showProgessUpdate(String s, Context context, int size) {
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setMessage(s);
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setMax(size);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
        }
    }

    public void initActionbar() {
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

}
