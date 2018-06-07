package com.vinhgroup.numbertransfer.View.Transfer;

import com.vinhgroup.numbertransfer.Adapter.TestResuiltAdapter;

/**
 * Created by Vinh on 6/6/2018.
 */

public interface TransferView {
    void setListAdapter(TestResuiltAdapter adapter);

    void showProgress();

    void closeProgress();
}
