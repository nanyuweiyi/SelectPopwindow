package com.jaygoo.selector.single;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.jaygoo.selector.R;

import java.util.HashMap;
import java.util.List;

/**
 * ================================================
 * 描    述:单选适配器
 * 作    者：tnn
 * 版    本：1.1.0
 * 创建日期：2017/2/21
 * ================================================
 */
public class SingleSelectListAdapter extends RecyclerView.Adapter<SingleSelectListAdapter.ChoiceViewHolder> {

    private boolean onBind;
    private HashMap<Integer, Boolean> map = new HashMap<>();
    private OnConfirmClickListenerSingle mCallBack;
    private List<String> mData;
    private OnSelectChangeListener mOnSelectChangeListener;

    public SingleSelectListAdapter(List<String> mData, OnConfirmClickListenerSingle callback) {
        this.mData = mData;
        this.mCallBack = callback;
    }

    @Override
    public ChoiceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ChoiceViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.single_select_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final ChoiceViewHolder holder, final int position) {
        holder.choiceNameBtn.setText(mData.get(position));
        holder.choiceNameBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    map.clear();
                    map.put(position, true);
                } else {
                    map.remove(position);
                }
                if (mOnSelectChangeListener != null) {
                    mOnSelectChangeListener.onChanged(position);
                }
                if (!onBind) {
                    mCallBack.onClick(position, mData.get(position));
                    notifyDataSetChanged();
                }
            }
        });
        onBind = true;
        if (map != null && map.containsKey(position)) {
            holder.choiceNameBtn.setSelected(true);
        } else {
            holder.choiceNameBtn.setSelected(false);
        }
        onBind = false;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    /**
     * viewHolder
     */
    class ChoiceViewHolder extends RecyclerView.ViewHolder {

        public RadioButton choiceNameBtn;

        public ChoiceViewHolder(View itemView) {
            super(itemView);
            choiceNameBtn = (RadioButton) itemView.findViewById(R.id.choiceNameBtn);
        }
    }

    public interface OnSelectChangeListener {
        void onChanged(int index);
    }

    public void setOnSelectChangeListener(OnSelectChangeListener listener) {
        mOnSelectChangeListener = listener;
    }
}
