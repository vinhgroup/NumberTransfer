package com.vinhgroup.numbertransfer.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.vinhgroup.numbertransfer.Model.TestResuilt.TestResuilt;
import com.vinhgroup.numbertransfer.R;
import com.vinhgroup.numbertransfer.View.Reverse.Reverse;
import com.vinhgroup.numbertransfer.View.Test.TestResuiltActivity;
import com.vinhgroup.numbertransfer.View.Transfer.TransferActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

/**
 * Created by Vinh on 6/5/2018.
 */

public class TestResuiltAdapter extends BaseAdapter {


    Context context;
    List<TestResuilt> object;
    boolean isTransfer;

    public TestResuiltAdapter(Context context, List<TestResuilt> object, boolean istransfer) {
        this.context = context;
        this.object = object;
        this.isTransfer = istransfer;
    }

    @Override
    public int getCount() {
        return object.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder;
        if (convertView != null) {
            mViewHolder = (ViewHolder) convertView.getTag();
        } else {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_test_resuilt, null);
            mViewHolder = new ViewHolder(convertView, context);
            convertView.setTag(mViewHolder);
        }
        mViewHolder.tvName.setText(object.get(position).getName());
        mViewHolder.tvPhoneNumber.setText(object.get(position).getNumberPhone());
        mViewHolder.tvNewPhoneNumber.setText(object.get(position).getNumberPhoneAfterChange());
        mViewHolder.tvNewPhoneNumber.setVisibility(isTransfer ? View.VISIBLE : View.GONE);
        mViewHolder.cbSelect.setChecked(object.get(position).isSelect());
        mViewHolder.cbSelect.setTag(position);
        Activity activity = (Activity) context;
        if (activity instanceof TestResuiltActivity)
            mViewHolder.cbSelect.setVisibility(View.GONE);
        return convertView;
    }


    class ViewHolder {
        @BindView(R.id.name)
        TextView tvName;
        @BindView(R.id.phone_number)
        TextView tvPhoneNumber;
        @BindView(R.id.new_phone_number)
        TextView tvNewPhoneNumber;
        @BindView(R.id.check_box_select)
        CheckBox cbSelect;
        Context mContext;

        public ViewHolder(View view, Context context) {
            this.mContext = context;
            ButterKnife.bind(this, view);

        }

        @Optional
        @OnClick({R.id.check_box_select})
        void OnClick(View view) {
            int position = Integer.parseInt(view.getTag().toString());
            switch (view.getId()) {
                case R.id.check_box_select:
                    Activity activity = (Activity) context;
                    if (activity instanceof TransferActivity) {
                        ((TransferActivity) mContext).selectOne(position, cbSelect.isChecked());
                    }
                    if (activity instanceof Reverse) {
                        ((Reverse) mContext).selectOne(position, cbSelect.isChecked());
                    }
                    break;
            }
        }
    }
}
