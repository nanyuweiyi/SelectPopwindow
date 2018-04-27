package com.jaygoo.selector.single;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jaygoo.selector.R;

import java.util.ArrayList;

/**
 * ================================================
 * 描    述: 单选弹出框
 * 作    者：tnn
 * 版    本：1.1.0
 * 创建日期：2017/2/22
 * ================================================
 */
public class SingleSelectPopWindow {

    private PopupWindow mPopupWindow;
    private SingleSelectListAdapter adapter;
    private TextView cancelBtn;
    private TextView confirmBtn;
    private TextView titleTV;
    private OnConfirmClickListenerSingle mOnConfirmListener;
    private int mIndex;
    private String mString;
    private Builder mBuilder;

    static public class Builder{
        private Activity mActivity;
        private ArrayList<String> choiceNameList = new ArrayList<>();
        private String title;
        private String confirmText;
        private String cancelText;
        private boolean isOutsideTouchable;
        private View.OnClickListener mOnCancelListener;
        private OnConfirmClickListenerSingle mOnConfirmListener;
        private int mConfirmTextColor;
        private int mCancelTextColor;
        private int mTitleTextColor;

        public Builder(Activity mActivity){
            this.mActivity = mActivity;
        }
        public Builder setNameArray(ArrayList<String> list){
            this.choiceNameList = list;
            return this;
        }

        /**
         * set title
         * @param title
         * @return
         */
        public Builder setTitle(String title){
            this.title = title;
            return this;
        }

        /**
         * set confirm button text
         * @param str
         * @return
         */
        public Builder setConfirm(String str){
            this.confirmText = str;
            return this;
        }

        /**
         * set cacel button text
         * @param str
         * @return
         */
        public Builder setCancel(String str){
            this.cancelText = str;
            return this;
        }

        /**
         * set title's text color
         * @param color
         * @return
         */
        public Builder setTitleTextColor(int color){
            this.mTitleTextColor = color;
            return this;
        }

        /**
         * set confirm button's text color
         * @param color
         * @return
         */
        public Builder setConfirmTextColor(int color){
            this.mConfirmTextColor = color;
            return this;
        }

        /**
         * set cancel button's text color
         * @param color
         * @return
         */
        public Builder setCancelTextColor(int color){
            this.mCancelTextColor = color;
            return this;
        }

        /**
         * set if can touchable if your finger touch outside
         * @param isOutsideTouchable
         * @return
         */
        public Builder setOutsideTouchable(boolean isOutsideTouchable){
            this.isOutsideTouchable = isOutsideTouchable;
            return this;
        }

        public SingleSelectPopWindow build(){
            return new SingleSelectPopWindow(this);
        }

        public Builder setConfirmListener(OnConfirmClickListenerSingle listener){
            this.mOnConfirmListener = listener;
            return this;
        }

        public Builder setCancelListener(View.OnClickListener listener){
            this.mOnCancelListener = listener;
            return this;
        }

    }

    private SingleSelectPopWindow(final Builder builder){
        mBuilder = builder;

        //init PopWindow
        View popview = View.inflate(builder.mActivity, R.layout.single_select_list_popwindow, null);
        mPopupWindow = new PopupWindow(popview, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setAnimationStyle(R.style.popwindow_anim_style);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        mPopupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        mPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(builder.isOutsideTouchable);

        initViews(mPopupWindow.getContentView());

        initListener();

        RecyclerView recyclerView = (RecyclerView) mPopupWindow.getContentView().findViewById(R.id.mRecycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(mBuilder.mActivity.getApplication()));
        adapter = new SingleSelectListAdapter(mBuilder.choiceNameList, mOnConfirmListener);
        recyclerView.setAdapter(adapter);

        adapter.setOnSelectChangeListener(new SingleSelectListAdapter.OnSelectChangeListener() {
            @Override
            public void onChanged(int index) {
                mIndex = index;
            }
        });
    }

    /**
     * init listener
     */
    private void initListener() {
        this.mOnConfirmListener = mBuilder.mOnConfirmListener;

        // change the background's color
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBuilder.mOnConfirmListener != null){
                    mString = mBuilder.choiceNameList.get(mIndex);
                    mOnConfirmListener.onClick(mIndex,mString);
                }
                dismiss();
            }
        });

        if (mBuilder.mOnCancelListener != null){
            cancelBtn.setOnClickListener(mBuilder.mOnCancelListener);
        }

        cancelBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                dismiss();
                return false;
            }
        });

    }

    private void initViews(View root) {
        titleTV = (TextView) root.findViewById(R.id.title);
        cancelBtn = (TextView) root.findViewById(R.id.cancelBtn);
        confirmBtn = (TextView) root.findViewById(R.id.confirmBtn);

        setText(titleTV,mBuilder.title);
        setText(cancelBtn,mBuilder.cancelText);
        setText(confirmBtn,mBuilder.confirmText);

        setTextColor(titleTV,mBuilder.mTitleTextColor);
        setTextColor(cancelBtn,mBuilder.mCancelTextColor);
        setTextColor(confirmBtn,mBuilder.mConfirmTextColor);
    }

    private void setText(TextView tv, String str){
        if (tv != null && str != null){
            tv.setText(str);
        }
    }

    private void setTextColor(TextView tv, int color){
        if (tv != null && color != 0){
            tv.setTextColor(color);
        }
    }

    public void dismiss() {
        if (mPopupWindow != null){
            mPopupWindow.dismiss();
        }
    }

    /**
     * parent is the popwindow show location
     */
    public void show(){
        if (mPopupWindow != null){
            backgroundAlpha(0.8f);
            mPopupWindow.showAtLocation(titleTV, Gravity.BOTTOM, 0, 0);
        }
    }

    /**
     * set background alpha
     * @param alpha
     */
    public void backgroundAlpha(float alpha) {
        try {
            WindowManager.LayoutParams lp = mBuilder.mActivity.getWindow().getAttributes();
            lp.alpha = alpha; //0.0-1.0
            mBuilder.mActivity.getWindow().setAttributes(lp);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
