package com.vinhgroup.numbertransfer.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vinhgroup.numbertransfer.Model.TestResuilt.TestResuilt;
import com.vinhgroup.numbertransfer.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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
        // comment something
        if (convertView != null){
            mViewHolder = (ViewHolder) convertView.getTag();
        }else {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_test_resuilt, null);
            mViewHolder = new ViewHolder(convertView);
            convertView.setTag(mViewHolder);
        }
        mViewHolder.tvName.setText(object.get(position).getName());
        mViewHolder.tvPhoneNumber.setText(object.get(position).getNumberPhone());
        mViewHolder.tvNewPhoneNumber.setText(object.get(position).getNumberPhoneAfterChange());
        mViewHolder.tvNewPhoneNumber.setVisibility(isTransfer ? View.VISIBLE : View.GONE);
        return convertView;
    }


    class ViewHolder {
        @BindView(R.id.name)
        TextView tvName;
        @BindView(R.id.phone_number)
        TextView tvPhoneNumber;
        @BindView(R.id.new_phone_number)
        TextView tvNewPhoneNumber;
        public ViewHolder(View view) {
            ButterKnife.bind(this, view);

        }
    }
}
