package com.android.framework.jc;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/7/16 18:21
 * @describe 动态权限申请
 * @update
 */
public class FkPermission {
    public static Builder with(@NonNull Activity activity) {
        return new Builder(activity);
    }

    public static class Builder {
        private final Activity mActivity;
        private IPermissionListener mListener;
        private List<String> mPermissions;

        Builder(@NonNull Activity activity) {
            mActivity = activity;
            this.mPermissions = new ArrayList<>();
        }


        /**
         * 添加需要申请的权限，多个权限分别添加
         *
         * @param permission
         *         需要申请的权限
         *
         * @return builder
         */
        public Builder permission(String permission) {
            mPermissions.add(permission);
            return this;
        }

        /**
         * 添加监听
         *
         * @param listener 回调监听
         *
         * @return
         */
        public Builder listener(IPermissionListener listener) {
            this.mListener = listener;
            return this;
        }

        /**
         * Request.
         */
        public void request() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Looper.getMainLooper().getThread() != Thread.currentThread()) {
                    this.mActivity.runOnUiThread(() -> {
                        PermissionFragment permissionFragment = getPermissionFragment(Builder.this.mActivity);
                        permissionFragment.requestPermissions(Builder.this.mPermissions, Builder.this.mListener);
                    });
                } else {
                    PermissionFragment permissionFragment = getPermissionFragment(this.mActivity);
                    permissionFragment.requestPermissions(mPermissions, this.mListener);
                }


            } else {
                if (mListener != null) {
                    mListener.onGrant();
                }
            }

        }

        private PermissionFragment getPermissionFragment(Activity activity) {
            FragmentManager fragmentManager = activity.getFragmentManager();
            PermissionFragment permissionFragment = (PermissionFragment) fragmentManager.findFragmentByTag("FkPermission");
            if (permissionFragment == null) {
                permissionFragment = new PermissionFragment();
                fragmentManager.beginTransaction().add(permissionFragment, "FkPermission").commit();
                fragmentManager.executePendingTransactions();
            }

            return permissionFragment;
        }

    }

    public static class PermissionFragment extends Fragment {
        private Context mContext;
        private IPermissionListener mListener;
        private final static int REQUEST_PERMISSION = 1828;

        @Override
        public void onAttach(Context context) {
            super.onAttach(context);
            mContext = context;
        }

        /**
         * Request permissions.
         *
         * @param permissions
         *         the permissions
         * @param listener
         *         the listener
         */
        @RequiresApi(api = Build.VERSION_CODES.M)
        protected void requestPermissions(List<String> permissions, IPermissionListener listener) {
            List<String> requestPermissions = new ArrayList<>();
            for (String s : permissions) {
                if (!isGranted(s, mContext)) {
                    requestPermissions.add(s);
                }

            }
            this.mListener = listener;
            if (requestPermissions.isEmpty()) {
                if (mListener != null) {
                    this.mListener.onGrant();
                }
            } else {
                this.requestPermissions(requestPermissions.toArray(new String[requestPermissions.size()]), REQUEST_PERMISSION);
            }


        }

        private boolean isGranted(String permission, Context context) {
            boolean result = true;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (context.getApplicationInfo().targetSdkVersion >= Build.VERSION_CODES.M) {
                    result = ContextCompat.checkSelfPermission(context, permission) == 0;
                } else {
                    result = PermissionChecker.checkSelfPermission(context, permission) == 0;
                }
            }
            return result;
        }

        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            if (requestCode == REQUEST_PERMISSION && mListener != null) {
                if (grantResults.length > 0) {
                    this.onRequestPermissionsResult(permissions, grantResults);
                } else {
                    mListener.onDenied(permissions);
                }

            }

        }

        @TargetApi(Build.VERSION_CODES.M)
        private void onRequestPermissionsResult(String[] permissions, @NonNull int[] grantResults) {
            List<String> deniedPermissions = new ArrayList<>();
            List<String> rationalePermission = new ArrayList<>();

            for (int i = 0; i < permissions.length; ++i) {
                if (grantResults[i] != 0) {
                    deniedPermissions.add(permissions[i]);
                }
            }
            if (deniedPermissions.isEmpty()) {
                this.mListener.onGrant();
            } else {
                for (String d : deniedPermissions) {
                    if (!shouldShowRequestPermissionRationale(d)) {
                        rationalePermission.add(d);
                    }
                }
                if (!rationalePermission.isEmpty()) {
                    this.mListener.onRationale(rationalePermission.toArray(new String[rationalePermission.size()]));
                }

                this.mListener.onDenied(deniedPermissions.toArray(new String[deniedPermissions.size()]));
            }

        }
    }


    /**
     * 权限申请回调
     */
    public interface IPermissionListener {

        void onGrant();

        void onRationale(String[] permissions);

        void onDenied(String[] permissions);
    }
}
