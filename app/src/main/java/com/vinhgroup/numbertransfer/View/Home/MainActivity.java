package com.vinhgroup.numbertransfer.View.Home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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

    @Optional
    @OnClick({R.id.button_guide, R.id.button_transfer, R.id.button_return, R.id.button_information, R.id.exit})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_guide:
                startActivity(new Intent(this, GuideActivity.class));
                break;
            case R.id.button_transfer:
                startActivity(new Intent(this, TransferActivity.class));
                break;
            case R.id.button_return:
                startActivity(new Intent(this, Reverse.class));
                break;
            case R.id.button_information:
                break;
            case R.id.exit:
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }
}
