package com.vinhgroup.numbertransfer.Presenter.TestResuilt;

import android.content.Context;

import com.vinhgroup.numbertransfer.Adapter.TestResuiltAdapter;
import com.vinhgroup.numbertransfer.Base.TestResuiltBase;
import com.vinhgroup.numbertransfer.R;
import com.vinhgroup.numbertransfer.View.Test.TestResuiltView;

import java.util.ArrayList;

/**
 * Created by Vinh on 6/5/2018.
 */

public class TestResuiltLogic extends TestResuiltBase implements TestResuiltImp {

    Context context;
    TestResuiltView mTestResuiltView;

    public TestResuiltLogic(Context context, TestResuiltView mTestResuiltView) {
        this.context = context;
        this.mTestResuiltView = mTestResuiltView;
    }

    @Override
    public void getPhoneContacts(boolean isTransfer) {
        arrTestResuilt = getNumberPhones(context, 5, false);
        if (arrTestResuilt!=null){
            testResuiltAdapter = new TestResuiltAdapter(context, arrTestResuilt, isTransfer);
            mTestResuiltView.setListAdapter(testResuiltAdapter);
            if (arrTestResuilt.size()==0){
                showDialogInform(context.getString(R.string.cant_see_any_number), context, false);
            }
        }else {
            showDialogInform(context.getString(R.string.please_allow_me_to_change), context, true);
        }

    }


}
