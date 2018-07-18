package com.android.framework.jc;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.LayoutRes;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/7/16 18:24
 * @describe
 * @update
 */
public class FkPopupWindow {
    private PopupWindow mPopupWindow;
    private View mView;
    private Context mContext;
    private PopupWindow.OnDismissListener mOnDismissListener;

    private FkPopupWindow(Builder builder) {
        mContext = builder.mContext;
        mView = LayoutInflater.from(builder.mContext).inflate(builder.mLayoutId, null);
        mPopupWindow = new PopupWindow(mView, builder.mWidth, builder.mHeight, builder.mFocusable);
        mPopupWindow.setOutsideTouchable(builder.mOutsideTouchable);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mPopupWindow.setAnimationStyle(builder.animStyle);
        mPopupWindow.setOnDismissListener(() -> {
            if (mOnDismissListener != null) {
                mOnDismissListener.onDismiss();
            }
        });
    }

    public void dismiss() {
        if (mPopupWindow != null) {
            mPopupWindow.dismiss();
        }
    }

    public FkPopupWindow setOnDismissListener(PopupWindow.OnDismissListener onDismissListener) {
        this.mOnDismissListener = onDismissListener;
        return this;
    }

    /**
     * 根据id获取view
     *
     * @param viewId
     *
     * @return
     */
    public <T extends View> T getItemView(int viewId) {
        if (mPopupWindow != null) {
            return this.mView.findViewById(viewId);
        }
        return null;
    }

    /**
     * 根据父布局，显示位置
     *
     * @param parentViewId
     * @param gravity
     * @param x
     * @param y
     *
     * @return
     */
    public FkPopupWindow showAtLocation(int parentViewId, int gravity, int x, int y) {
        if (mPopupWindow != null) {
            View parentView = LayoutInflater.from(mContext).inflate(parentViewId, null);
            mPopupWindow.showAtLocation(parentView, gravity, x, y);
        }
        return this;
    }

    /**
     * 根据父布局，显示位置
     *
     * @param parentView
     * @param gravity
     * @param x
     * @param y
     *
     * @return
     */
    public FkPopupWindow showAtLocation(View parentView, int gravity, int x, int y) {
        if (parentView != null) {
            mPopupWindow.showAtLocation(parentView, gravity, x, y);
        }
        return this;
    }

    /**
     * 根据id获取view ，并显示在该view的位置
     *
     * @param targetviewId
     * @param gravity
     * @param offx
     * @param offy
     *
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public FkPopupWindow showAsDropDown(int targetviewId, int gravity, int offx, int offy) {
        if (mPopupWindow != null) {
            View targetview = LayoutInflater.from(mContext).inflate(targetviewId, null);
            mPopupWindow.showAsDropDown(targetview, gravity, offx, offy);
        }
        return this;
    }

    public void setWindowAlpha(float alpha) {
        if (mContext instanceof Activity) {
            Activity activity = (Activity) mContext;
            WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
            lp.alpha = alpha;
            if (alpha == 1f) {
                activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            } else {
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            }
            activity.getWindow().setAttributes(lp);
        }
    }

    /**
     * @param parentView
     *
     * @return
     */
    public void showAsDropDown(View parentView) {
        if (mPopupWindow != null) {
            mPopupWindow.showAsDropDown(parentView);
        }
    }

    /**
     * 显示在 targetView 的不同位置
     *
     * @param anchor
     * @param xoff
     * @param yoff
     * @param gravity
     *
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public FkPopupWindow showAsDropDown(View anchor, int xoff, int yoff, int gravity) {
        if (mPopupWindow != null) {
            mPopupWindow.showAsDropDown(anchor, xoff, yoff, gravity);
        }
        return this;
    }

    /**
     * 根据id设置焦点监听
     *
     * @param itemViewId
     * @param listener
     */
    public void setOnItemClickListener(int itemViewId, View.OnClickListener listener) {
        View view = getItemView(itemViewId);
        view.setOnClickListener(listener);
    }

    /**
     * builder 类
     */
    public static class Builder {
        private @LayoutRes
        int mLayoutId;
        private Context mContext;
        private int mWidth = ViewGroup.LayoutParams.WRAP_CONTENT, mHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
        private boolean mFocusable = true;
        private boolean mOutsideTouchable = true;
        private int animStyle;

        public Builder(Context context) {
            this.mContext = context;
        }

        public Builder setContentView(int layoutId) {
            this.mLayoutId = layoutId;
            return this;
        }

        public Builder setWidth(int width) {
            this.mWidth = width;
            return this;
        }

        public Builder setHeight(int height) {
            this.mHeight = height;
            return this;
        }

        public Builder setFocusable(boolean focusable) {
            this.mFocusable = focusable;
            return this;
        }

        public Builder setOutsideTouchable(boolean outSideCancel) {
            this.mOutsideTouchable = outSideCancel;
            return this;
        }

        public Builder setAnimationStyle(int animStyle) {
            this.animStyle = animStyle;
            return this;
        }

        public FkPopupWindow builder() {
            return new FkPopupWindow(this);
        }
    }
}
