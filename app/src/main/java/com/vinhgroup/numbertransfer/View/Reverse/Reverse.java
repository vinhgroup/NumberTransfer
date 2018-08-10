package com.vinhgroup.numbertransfer.View.Reverse;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;

import com.vinhgroup.numbertransfer.Adapter.TestResuiltAdapter;
import com.vinhgroup.numbertransfer.Base.BaseActivity;
import com.vinhgroup.numbertransfer.Presenter.Reserve.ReverseLogic;
import com.vinhgroup.numbertransfer.R;
import com.vinhgroup.numbertransfer.View.Home.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

public class Reverse extends ReverseBase implements ReverseView {

    @BindView(R.id.lv)
    ListView lv;
    @BindView(R.id.button_back_to_home)
    Button btnBacktoHome;
    @BindView(R.id.button_transfer)
    Button btnTransfer;
    @BindView(R.id.check_box_all_reverse)
    CheckBox btnCheckAll;


    @Optional
    @OnClick({R.id.button_back_to_home, R.id.button_transfer, R.id.check_box_all_reverse})
    void OnClick(View view) {
        switch (view.getId()) {
            case R.id.button_back_to_home:
                onBackPressed();
                break;
            case R.id.button_transfer:
                mReverseLogic.startReverse();
                break;
            case  R.id.check_box_all_reverse:
                mReverseLogic.setCheckAll(btnCheckAll.isChecked());
                break;
            default:
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
        setContentView(R.layout.activity_reverse);
        ButterKnife.bind(this);
        getSupportActionBar().setTitle("Chuyển 10 sang 11");
        initAdsBottomBanner(this);
//        getSupportActionBar().setIcon(R.drawable.baseline_cached_24);
        mReverseLogic = new ReverseLogic(this, this);
        checkRunTimePermission(this);
    }




    public void beginWorking(){
        mReverseLogic.getNumber10();
    }

    @Override
    public void closeProgress() {
        closeProgressDialog();
    }

    @Override
    public void showProgress() {
        showProgressDialog(getString(R.string.loading_please_wait), this);
    }

    @Override
    public void setListAdapter(TestResuiltAdapter adapter) {
        lv.setAdapter(adapter);
    }


    public void enableAllCheck(boolean isEnableCheckAll) {
        btnCheckAll.setChecked(isEnableCheckAll);
    }

    // e chua chinh dc cho nay :D
    public void selectOne(int position, boolean isCheck) {
        mReverseLogic.selectOneNumber(position, isCheck);
    }

}
