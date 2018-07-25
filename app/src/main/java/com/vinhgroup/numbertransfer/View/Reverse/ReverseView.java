package com.vinhgroup.numbertransfer.View.Reverse;

import com.vinhgroup.numbertransfer.Adapter.TestResuiltAdapter;

public interface ReverseView {
    void closeProgress();

    void showProgress();

    void setListAdapter(TestResuiltAdapter adapter);

    void enableAllCheck(boolean isEnableCheckAll);
}
