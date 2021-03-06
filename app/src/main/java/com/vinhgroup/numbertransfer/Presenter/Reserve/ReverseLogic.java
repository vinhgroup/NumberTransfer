package com.vinhgroup.numbertransfer.Presenter.Reserve;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Context;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import com.vinhgroup.numbertransfer.Adapter.TestResuiltAdapter;
import com.vinhgroup.numbertransfer.Base.BaseActivity;
import com.vinhgroup.numbertransfer.Model.TestResuilt.TestResuilt;
import com.vinhgroup.numbertransfer.R;
import com.vinhgroup.numbertransfer.View.Reverse.ReverseView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReverseLogic extends BaseActivity implements ReverseImp {
    Context context;
    ReverseView mReverseView;
    TestResuiltAdapter adapter = new TestResuiltAdapter(context, arrTestResuilt, true);

    public ReverseLogic(Context context, ReverseView mReverseView) {
        this.context = context;
        this.mReverseView = mReverseView;

    }

    @Override
    public void getNumber10() {
        mReverseView.showProgress();
        new ReadContact10().execute();
    }

    public void setCheckAll(boolean checked) {
        for (int i = 0; i < arrTestResuilt.size(); i++) {
            arrTestResuilt.get(i).setSelect(checked);
        }
        adapter.notifyDataSetChanged();
    }


    public void selectOneNumber(int position, boolean isCheck) {
        boolean isEnableCheckAll = true;
        arrTestResuilt.get(position).setSelect(isCheck);
        for (int i = 0; i < arrTestResuilt.size(); i++) {
            if (!arrTestResuilt.get(i).isSelect()) {
                isEnableCheckAll = false;
                break;
            }
        }
        mReverseView.enableAllCheck(isEnableCheckAll);
    }

    class ReadContact10 extends AsyncTask<String, Void, List<TestResuilt>> {

        @Override
        protected List<TestResuilt> doInBackground(String... strings) {
            arrTestResuilt = getNumberPhones(context, numberUneed, true);
            return arrTestResuilt;
        }

        @Override
        protected void onPostExecute(List<TestResuilt> testResuilts) {
            if (arrTestResuilt != null) {
                adapter = new TestResuiltAdapter(context, arrTestResuilt, true);
                mReverseView.setListAdapter(adapter);
                if (arrTestResuilt.size() == 0) {
                    showDialogInform(context.getString(R.string.cant_see_any_number), context, false);
                }
            }
            mReverseView.closeProgress();
            super.onPostExecute(testResuilts);
        }
    }

    public void startReverse() {
        showProgessUpdate(context.getString(R.string.please_do_not_turn_off_application), context, arrTestResuilt.size());
        new ReverseContact().execute();
    }

    class ReverseContact extends AsyncTask<Void, Integer, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
//            ReverseHere();
            for (int i = 0; i < arrTestResuilt.size(); i++) {
                if (Integer.parseInt(arrTestResuilt.get(i).getId()) > 0) {
                    if (arrTestResuilt.get(i).getEmail() == null) {
                        arrTestResuilt.get(i).setEmail("");
                    }
                    updateContact(arrTestResuilt.get(i).getName(), arrTestResuilt.get(i).getNumberPhoneAfterChange(), arrTestResuilt.get(i).getEmail(), arrTestResuilt.get(i).getId());
                    publishProgress(i + 1);
                } else {
                    Toast.makeText(context, "cant not doing with this contact", Toast.LENGTH_SHORT).show();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(context, context.getString(R.string.completed), Toast.LENGTH_SHORT).show();
            mProgressDialog.dismiss();
            super.onPostExecute(aVoid);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            mProgressDialog.setProgress(values[0]);
            super.onProgressUpdate(values);
        }
    }

//    public void ReverseHere() {
//        for (int i = 0; i < arrTestResuilt.size(); i++) {
//            if (Integer.parseInt(arrTestResuilt.get(i).getId()) > 0) {
//                if (arrTestResuilt.get(i).getEmail() == null) {
//                    arrTestResuilt.get(i).setEmail("");
//                }
//                updateContact(arrTestResuilt.get(i).getName(), arrTestResuilt.get(i).getNumberPhoneAfterChange(), arrTestResuilt.get(i).getEmail(), arrTestResuilt.get(i).getId());
//            } else {
//                Toast.makeText(context, "cant not doing with this contact", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }


    public boolean updateContact(String name, String number, String email, String ContactId) {
        if (name.equals("Tra Sua")){
            Log.d("VinhCNLog: ", "Here");

        }
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
