package com.android.framework.jc.widget;

import android.os.CountDownTimer;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2019/4/26 17:31
 * @describe 倒计时计时器
 * @update
 */
public class FkCountDownTimer extends CountDownTimer {
    private final Builder mBuilder;

    public static Builder createBuilder(long millisInFuture) {
        return new FkCountDownTimer.Builder(millisInFuture);
    }

    /**
     * @param builder
     *         构造器
     */
    private FkCountDownTimer(Builder builder) {
        super(builder.millisInFuture, builder.countDownInterval);
        this.mBuilder = builder;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        if (mBuilder.timerTickListener != null) {
            mBuilder.timerTickListener.onTimerTick(millisUntilFinished);
        }
    }

    @Override
    public void onFinish() {
        if (this.mBuilder.timerFinishListener != null) {
            this.mBuilder.timerFinishListener.onTimerFinish();
        }
    }

    public final static class Builder {
        private long millisInFuture;
        private OnTimerFinishListener timerFinishListener;
        private OnTimerTickListener timerTickListener;
        private long countDownInterval = 1000L;

        private Builder(long millisInFuture) {
            this.millisInFuture = millisInFuture;
        }

        public Builder countDownInterval(long countDownInterval) {
            this.countDownInterval = countDownInterval;
            return this;
        }

        public Builder timeFinishListener(OnTimerFinishListener timerFinishListener) {
            this.timerFinishListener = timerFinishListener;
            return this;
        }

        public Builder timeTickListener(OnTimerTickListener timerTickListener) {
            this.timerTickListener = timerTickListener;
            return this;
        }


        public FkCountDownTimer build() {
            return new FkCountDownTimer(this);
        }
    }


    public interface OnTimerFinishListener {
        /**
         * 时间倒计时结束回调
         */
        void onTimerFinish();
    }

    public interface OnTimerTickListener {
        /**
         * 时间倒计时过程回调
         *
         * @param millisUntilFinished
         *         剩余倒计时
         */
        void onTimerTick(long millisUntilFinished);

    }
}
