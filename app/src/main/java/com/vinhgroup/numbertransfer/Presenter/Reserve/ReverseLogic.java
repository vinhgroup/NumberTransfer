package com.vinhgroup.numbertransfer.Presenter.Reserve;

import android.content.Context;
import android.os.AsyncTask;

import com.vinhgroup.numbertransfer.Adapter.TestResuiltAdapter;
import com.vinhgroup.numbertransfer.Base.BaseActivity;
import com.vinhgroup.numbertransfer.Model.TestResuilt.TestResuilt;
import com.vinhgroup.numbertransfer.View.Reverse.ReverseView;

import java.util.List;

public class ReverseLogic extends BaseActivity implements ReverseImp {
    Context context;
    ReverseView mReverseView;

    public ReverseLogic(Context context, ReverseView mReverseView) {
        this.context = context;
        this.mReverseView = mReverseView;
    }

    @Override
    public void getNumber10() {
        mReverseView.showProgress();
        new ReadContact10().execute();
    }


    class ReadContact10 extends AsyncTask<String, Void, List<TestResuilt>>{

        @Override
        protected List<TestResuilt> doInBackground(String... strings) {
            arrTestResuilt = getNumberPhones(context, 5);
            return arrTestResuilt;
        }

        @Override
        protected void onPostExecute(List<TestResuilt> testResuilts) {
            TestResuiltAdapter adapter = new TestResuiltAdapter(context, arrTestResuilt, true);
            mReverseView.setListAdapter(adapter);
            mReverseView.closeProgress();
            super.onPostExecute(testResuilts);
        }
    }
}
