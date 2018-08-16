package com.vinhgroup.numbertransfer.Base;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.vinhgroup.numbertransfer.Model.TestResuilt.TestResuilt;

import java.util.List;

public class SumBase extends AppCompatActivity {
    public static int numberUneed = 0;

    public List<TestResuilt> arrTestResuilt;

    public List<TestResuilt> getArrTestResuilt() {
        return arrTestResuilt;
    }

    public void setArrTestResuilt(List<TestResuilt> arrTestResuilt) {
        this.arrTestResuilt = arrTestResuilt;
    }

    Context Context;

    public Context getmContext() {
        return Context;
    }

    public void setmContext(Context mContext) {
        this.Context = mContext;
    }

    boolean Ten;

    public boolean isTen() {
        return Ten;
    }

    public void setTen(boolean ten) {
        Ten = ten;
    }
}
