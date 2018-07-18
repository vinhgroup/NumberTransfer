package com.vinhgroup.numbertransfer.Base;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.vinhgroup.numbertransfer.Model.TestResuilt.TestResuilt;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vinh on 6/4/2018.
 */

public class BaseActivity extends AppCompatActivity {


    public List<TestResuilt> arrTestResuilt;

    public List<TestResuilt> getNumberPhones(Context context, int numberUneed) {
        List<TestResuilt> arrTestResuilt = new ArrayList<>();
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
                        String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)).replace(" ","");
//                        Log.i("Number", phoneNumber +  email);
                        if (phoneNumber.length() == 11) {
                            String LastPart = phoneNumber.substring(4, phoneNumber.length());
                            arrTestResuilt.add(new TestResuilt(id, name, phoneNumber, getFirstPartTransfer(phoneNumber) + LastPart, email));
                        }
                        if (numberUneed != 0 && arrTestResuilt.size() == numberUneed) {
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
        return arrTestResuilt;
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

    public String getFirstPartTransfer(String phoneNumber) {
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
    private ProgressDialog mProgressDialog;

    public void showProgressDialog(String msg, Context context) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(context);
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.setTitle(msg);
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

}
