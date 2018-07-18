package com.vinhgroup.numbertransfer.View.Test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.vinhgroup.numbertransfer.Adapter.TestResuiltAdapter;
import com.vinhgroup.numbertransfer.Base.TestResuiltBase;
import com.vinhgroup.numbertransfer.Presenter.TestResuilt.TestResuiltLogic;
import com.vinhgroup.numbertransfer.R;
import com.vinhgroup.numbertransfer.View.Home.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

public class TestResuiltActivity extends TestResuiltBase implements TestResuiltView {

    @BindView(R.id.button_re_choosen)
    Button btnReChoosen;
    @BindView(R.id.button_transfer_resuilt)
    Button btnTransfer;
    @BindView(R.id.button_back_to_home_resuilt)
    Button btnBackHome;
    @BindView(R.id.lv)
    ListView lv;

    @Optional
    @OnClick({R.id.button_re_choosen, R.id.button_transfer_resuilt, R.id.button_back_to_home_resuilt})
    void OnClick(View view) {
        switch (view.getId()) {
            case R.id.button_re_choosen:
                mTestResuiltLogic.getPhoneContacts(false);
                break;
            case R.id.button_transfer_resuilt:
                mTestResuiltLogic.getPhoneContacts(true);
                break;
            case R.id.button_back_to_home_resuilt:
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
            default:
                break;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_resuilt);
        ButterKnife.bind(this);
        mTestResuiltLogic = new TestResuiltLogic(this, this);
        mTestResuiltLogic.getPhoneContacts(false);
//        startActivity(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI));
    }

    @Override
    public void setListAdapter(TestResuiltAdapter adapter) {
        lv.setAdapter(adapter);
    }


//    @Override
//    public void onActivityResult(int reqCode, int resultCode, Intent data) {
//        super.onActivityResult(reqCode, resultCode, data);
//        if (resultCode == Activity.RESULT_OK) {
//            Uri contactData = data.getData();
//            Cursor c = managedQuery(contactData, null, null, null, null);
//            if (c.moveToFirst()) {
//                String name = c.getString(c.getColumnIndexOrThrow(Contacts.People.NAME));
//                Log.d("Name", name);
//            }
//        }
//    }
}
