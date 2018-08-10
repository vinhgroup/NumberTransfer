package com.vinhgroup.numbertransfer.View.Transfer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;

import com.vinhgroup.numbertransfer.Adapter.TestResuiltAdapter;
import com.vinhgroup.numbertransfer.Presenter.Transfer.TransferLogic;
import com.vinhgroup.numbertransfer.R;
import com.vinhgroup.numbertransfer.View.Home.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

public class TransferActivity extends TransferBase implements TransferView {

    @BindView(R.id.button_transfer)
    Button btnTrans;
    @BindView(R.id.button_back_to_home)
    Button btnBackToHome;
    @BindView(R.id.lv)
    ListView lv;
    @BindView(R.id.check_box_all)
    CheckBox cbAll;

    @Optional
    @OnClick({R.id.button_transfer, R.id.button_back_to_home, R.id.check_box_all})
    void OnClick(View view) {
        switch (view.getId()) {
            case R.id.button_transfer:
                mTransferLogic.startTransfer();
                break;
            case R.id.button_back_to_home:
                onBackPressed();
                break;
            case R.id.check_box_all:
                mTransferLogic.setCheckAll(cbAll.isChecked());
                break;
        }
    }

//    @Override
//    public void onBackPressed() {
//        startActivity(new Intent(this, MainActivity.class));
//        finish();
//        //super.onBackPressed();
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);
        ButterKnife.bind(this);
        getSupportActionBar().setTitle("Chuyển 11 sang 10");
        initAdsBottomBanner(this);
        mTransferLogic = new TransferLogic(this, this);
        checkRunTimePermission(this);
    }

    public void beginWorking() {
        mTransferLogic.getNumberPhones();
    }

    @Override
    public void setListAdapter(TestResuiltAdapter adapter) {
        lv.setAdapter(adapter);
    }

    @Override
    public void showProgress() {
        showProgressDialog(getString(R.string.loading_please_wait), TransferActivity.this);
    }



    @Override
    public void closeProgress() {
        closeProgressDialog();
    }

    @Override
    public void enableAllCheck(boolean isEnableCheckAll) {
        cbAll.setChecked(isEnableCheckAll);
    }

    public void selectOne(int position, boolean isCheck) {
        mTransferLogic.selectOneNumber(position, isCheck);
    }
}
