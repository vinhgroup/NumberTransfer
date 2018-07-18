package com.vinhgroup.numbertransfer.View.Guide;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.vinhgroup.numbertransfer.R;
import com.vinhgroup.numbertransfer.View.Home.MainActivity;
import com.vinhgroup.numbertransfer.View.Test.TestResuiltActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

public class GuideActivity extends AppCompatActivity {

    @BindView(R.id.button_back_to_home)
    Button btnBackHome;
    @BindView(R.id.button_test)
    Button btnTest;

    @Optional
    @OnClick({R.id.button_back_to_home, R.id.button_test})
    void OnClick(View view) {
        switch (view.getId()) {
            case R.id.button_back_to_home:
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
            case R.id.button_test:
                startActivity(new Intent(this, TestResuiltActivity.class));
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);
    }
}
