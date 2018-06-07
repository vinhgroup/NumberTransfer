package com.vinhgroup.numbertransfer.Presenter.Transfer;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Context;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.widget.Toast;

import com.vinhgroup.numbertransfer.Adapter.TestResuiltAdapter;
import com.vinhgroup.numbertransfer.Model.TestResuilt.TestResuilt;
import com.vinhgroup.numbertransfer.View.Transfer.TransferBase;
import com.vinhgroup.numbertransfer.View.Transfer.TransferView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Vinh on 6/6/2018.
 */

public class TransferLogic extends TransferBase implements TransferImp {
    Context context;
    TransferView mTransferView;

    public TransferLogic(Context context, TransferView mTransferView) {
        this.context = context;
        this.mTransferView = mTransferView;
    }

    @Override
    public void getNumberPhones() {
        mTransferView.showProgress();
        new ReadContacts().execute();
    }

    public void startTransfer() {
        new TransferContact().execute();
    }


    class TransferContact extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            TranferHere();
            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }


    public void TranferHere() {
        for (int i = 0; i< arrTestResuilt.size(); i++){
            if (Integer.parseInt(arrTestResuilt.get(i).getId()) > 0){
                if (arrTestResuilt.get(i).getEmail()==null){
                    arrTestResuilt.get(i).setEmail("");
                }
                updateContact(arrTestResuilt.get(i).getName(), arrTestResuilt.get(i).getNumberPhoneAfterChange(), arrTestResuilt.get(i).getEmail(), arrTestResuilt.get(i).getId());
            }else {
                Toast.makeText(context, "cant not doing with this contact", Toast.LENGTH_SHORT).show();
            }
        }

    }

//    private void TranferHere() {
//        for (int i = 0; i< arrTestResuilt.size(); i++){
//            int phoneID = Integer.parseInt(arrTestResuilt.get(i).getId());
//            if ( phoneID > 0){
//                Intent intent = new Intent(Intent.ACTION_EDIT);
//                intent.setData(ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, phoneID));
//                context.startActivity(intent);
//            }else {
//                Toast.makeText(context, "contact not in list",
//                        Toast.LENGTH_SHORT).show();
//            }
//        }
//
//    }

    class ReadContacts extends AsyncTask<String, Void, List<TestResuilt>> {

        @Override
        protected List<TestResuilt> doInBackground(String... strings) {
            arrTestResuilt = getNumberPhones(context, 10);
            return arrTestResuilt;
        }

        @Override
        protected void onPostExecute(List<TestResuilt> testResuilts) {
            TestResuiltAdapter adapter = new TestResuiltAdapter(context, arrTestResuilt, true);
            mTransferView.setListAdapter(adapter);
            mTransferView.closeProgress();
            super.onPostExecute(testResuilts);
        }
    }


    public boolean updateContact(String name, String number, String email, String ContactId) {
        boolean success = true;
        String phnumexp = "^[0-9]*$";

        try {
            name = name.trim();
            email = email.trim();
            number = number.trim();

            if (name.equals("") && number.equals("") && email.equals("")) {
                success = false;
            } else if ((!number.equals("")) && (!match(number, phnumexp))) {
                success = false;
            } else if ((!email.equals("")) && (!isEmailValid(email))) {
                success = false;
            } else {
                ContentResolver contentResolver = context.getContentResolver();

                String where = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";

                String[] emailParams = new String[]{ContactId, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE};
                String[] nameParams = new String[]{ContactId, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE};
                String[] numberParams = new String[]{ContactId, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE};

                ArrayList<ContentProviderOperation> ops = new ArrayList<android.content.ContentProviderOperation>();

                if (!email.equals("")) {
                    ops.add(android.content.ContentProviderOperation.newUpdate(android.provider.ContactsContract.Data.CONTENT_URI)
                            .withSelection(where, emailParams)
                            .withValue(ContactsContract.CommonDataKinds.Email.DATA, email)
                            .build());
                }

                if (!name.equals("")) {
                    ops.add(android.content.ContentProviderOperation.newUpdate(android.provider.ContactsContract.Data.CONTENT_URI)
                            .withSelection(where, nameParams)
                            .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, name)
                            .build());
                }

                if (!number.equals("")) {

                    ops.add(android.content.ContentProviderOperation.newUpdate(android.provider.ContactsContract.Data.CONTENT_URI)
                            .withSelection(where, numberParams)
                            .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, number)
                            .build());
                }
                contentResolver.applyBatch(ContactsContract.AUTHORITY, ops);
            }
        } catch (Exception e) {
            e.printStackTrace();
            success = false;
        }
        return success;
    }


    private boolean isEmailValid(String email) {
        String emailAddress = email.toString().trim();
        if (emailAddress == null)
            return false;
        else if (emailAddress.equals(""))
            return false;
        else if (emailAddress.length() <= 6)
            return false;
        else {
            String expression = "^[a-z][a-z|0-9|]*([_][a-z|0-9]+)*([.][a-z|0-9]+([_][a-z|0-9]+)*)?@[a-z][a-z|0-9|]*\\.([a-z][a-z|0-9]*(\\.[a-z][a-z|0-9]*)?)$";
            CharSequence inputStr = emailAddress;
            Pattern pattern = Pattern.compile(expression,
                    Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(inputStr);
            if (matcher.matches())
                return true;
            else
                return false;
        }
    }

    private boolean match(String stringToCompare, String regularExpression) {
        boolean success = false;
        Pattern pattern = Pattern.compile(regularExpression);
        Matcher matcher = pattern.matcher(stringToCompare);
        if (matcher.matches())
            success = true;
        return success;
    }
}
