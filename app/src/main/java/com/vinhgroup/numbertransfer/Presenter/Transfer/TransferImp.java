package com.vinhgroup.numbertransfer.Presenter.Transfer;

/**
 * Created by Vinh on 6/6/2018.
 */

public interface TransferImp {
    void getNumberPhones();

    void setCheckAll(boolean checked);

    void selectOneNumber(int position, boolean isCheck);
}
