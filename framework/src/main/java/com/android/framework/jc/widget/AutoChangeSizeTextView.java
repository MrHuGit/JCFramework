package com.android.framework.jc.widget;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.TypedValue;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2019/4/26 15:49
 * @describe 自动修改字体大小（默认最新字体大小5px,默认最大字体大小30px）
 * @update
 */
public class AutoChangeSizeTextView extends AppCompatTextView {
    private Paint testPaint;
    private float minTextSize;
    private float maxTextSize;
    private static float MinTextSize = -1;

    public AutoChangeSizeTextView(Context context) {
        this(context, null, 0);

    }

    public AutoChangeSizeTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoChangeSizeTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialise();
    }

    private void initialise() {
        testPaint = new Paint();
        testPaint.set(this.getPaint());
        testPaint.setColor(getCurrentTextColor());
        // max size defaults to the intially specified text size unless it is
        // too small
        maxTextSize = this.getTextSize();
        float defaultMineTextSize = 5;
        float defaultMaxTextSize = 30;
        if (maxTextSize <= defaultMineTextSize) {
            maxTextSize = defaultMaxTextSize;
        }
        if (MinTextSize > defaultMaxTextSize && maxTextSize > MinTextSize) {
            maxTextSize = MinTextSize;
        }
        minTextSize = defaultMineTextSize;
    }

    /**
     * 通过不断的进行对字体缩小来找到合适的大小，这个有个说明需要给定控件一个最小宽度否则会出现极小字体
     */
    private void refitText(String text, int textWidth) {
        if (textWidth > 0) {
            int availableWidth = textWidth - this.getPaddingLeft() -
                    this.getPaddingRight();
            float trySize = maxTextSize;
            testPaint.setTextSize(trySize);
            while ((trySize > minTextSize) &&
                    (testPaint.measureText(text) > availableWidth)) {
                trySize -= 1;
                if (trySize <= minTextSize) {
                    trySize = minTextSize;
                    break;
                }
                testPaint.setTextSize(trySize);
            }
            MinTextSize = trySize;
            this.setTextSize(TypedValue.COMPLEX_UNIT_PX, trySize);
        }
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int before,
                                 int after) {
        super.onTextChanged(text, start, before, after);
        refitText(text.toString(), this.getWidth());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (w != oldw) {
            refitText(this.getText().toString(), w);
        }
    }

    @Override
    public void setBackgroundResource(@DrawableRes int resId) {
        super.setBackgroundResource(resId);
    }

    @Override
    public void setTextAppearance(int resId) {
        setTextAppearance(getContext(), resId);
    }

    @Override
    public void setTextAppearance(Context context, int resId) {
        super.setTextAppearance(context, resId);
    }

    @Override
    public void setCompoundDrawablesRelativeWithIntrinsicBounds(
            @DrawableRes int start, @DrawableRes int top, @DrawableRes int end, @DrawableRes int bottom) {
        super.setCompoundDrawablesRelativeWithIntrinsicBounds(start, top, end, bottom);
    }

    @Override
    public void setCompoundDrawablesWithIntrinsicBounds(
            @DrawableRes int left, @DrawableRes int top, @DrawableRes int right, @DrawableRes int bottom) {
        super.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
    }

}