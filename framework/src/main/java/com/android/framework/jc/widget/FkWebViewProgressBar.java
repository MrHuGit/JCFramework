package com.android.framework.jc.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/9/13 16:38
 * @describe
 * @update
 */
public class FkWebViewProgressBar {
    private ProgressBar progressBar;
    private boolean isStart = false;

    public FkWebViewProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    /**
     * 开始
     * 在 WebViewClient onPageStarted 调用
     */
    public void onProgressStart() {
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setAlpha(1.0f);
    }

    /**
     * 在 WebChromeClient onProgressChange 调用
     *
     * @param newProgress
     *         进度
     */
    public void onProgressChange(int newProgress) {
        int currentProgress = progressBar.getProgress();
        if (newProgress >= 100 && !isStart) {
            isStart = true;
            progressBar.setProgress(newProgress);
            startDismissAnimation(progressBar.getProgress());
        } else {
            startProgressAnimation(newProgress, currentProgress);
        }
    }


    /**
     * 下面是动画的形式
     * progressBar 进度缓慢递增，300ms/次
     */
    private void startProgressAnimation(int newProgress, int currentProgress) {
        ObjectAnimator animator = ObjectAnimator.ofInt(progressBar, "progress", currentProgress, newProgress);
        animator.setDuration(300);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.start();
    }

    private void startDismissAnimation(final int progress) {
        ObjectAnimator anim = ObjectAnimator.ofFloat(progressBar, "alpha", 1.0f, 0.0f);
        // 动画时长
        anim.setDuration(500);
        // 减速
        anim.setInterpolator(new DecelerateInterpolator());
        anim.addUpdateListener(valueAnimator -> {
            // 0.0f ~ 1.0f
            float fraction = valueAnimator.getAnimatedFraction();
            int offset = 100 - progress;
            progressBar.setProgress((int) (progress + offset * fraction));
        });

        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                progressBar.setProgress(0);
                progressBar.setVisibility(View.GONE);
                isStart = false;
            }
        });
        anim.start();
    }
}
