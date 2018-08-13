package com.vinhgroup.numbertransfer.View.Home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.vinhgroup.numbertransfer.Base.BaseActivity;
import com.vinhgroup.numbertransfer.R;
import com.vinhgroup.numbertransfer.View.Guide.GuideActivity;
import com.vinhgroup.numbertransfer.View.Reverse.Reverse;
import com.vinhgroup.numbertransfer.View.Transfer.TransferActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

public class MainActivity extends BaseActivity {

    @BindView(R.id.button_guide)
    Button btnGuide;
    @BindView(R.id.button_transfer)
    Button btnTransfer;
    @BindView(R.id.button_return)
    Button btnReturn;
    @BindView(R.id.button_information)
    Button btnInfor;
    @BindView(R.id.exit)
    Button btnExit;


    @SuppressLint("NewApi")
    @Optional
    @OnClick({R.id.button_guide, R.id.button_transfer, R.id.button_return, R.id.button_information, R.id.exit})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_guide:
                startActivity(new Intent(this, GuideActivity.class));
//                finish();
                break;
            case R.id.button_transfer:
                startActivity(new Intent(this, TransferActivity.class));
//                finish();
                break;
            case R.id.button_return:
                startActivity(new Intent(this, Reverse.class));
//                finish();
                break;
            case R.id.button_information:
                break;
            case R.id.exit:
                finishAndRemoveTask();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        Log.d("VinhCN: ", "onResume");
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.baseline_cached_24);
        getSupportActionBar().setTitle(" Chuyển đổi đầu số");
        initAdsBottomBanner(this);



    }
}
