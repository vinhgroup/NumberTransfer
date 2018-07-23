package com.vinhgroup.numbertransfer.View.Transfer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

    @Optional
    @OnClick({R.id.button_transfer, R.id.button_back_to_home})
    void OnClick(View view){
            switch (view.getId()){
                case R.id.button_transfer:
                    mTransferLogic.startTransfer();
                    break;
                case R.id.button_back_to_home:
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                    break;
            }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
        //super.onBackPressed();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);
        ButterKnife.bind(this);
        getSupportActionBar().setTitle("Chuyển 11 sang 10");
        mTransferLogic = new TransferLogic(this, this);
        mTransferLogic.getNumberPhones();

    }

    @Override
    public void setListAdapter(TestResuiltAdapter adapter) {
        lv.setAdapter(adapter);
    }

    @Override
    public void showProgress() {
        showProgressDialog(getString(R.string.please_wait), TransferActivity.this);
    }

    @Override
    public void closeProgress() {
        closeProgressDialog();
    }
}
