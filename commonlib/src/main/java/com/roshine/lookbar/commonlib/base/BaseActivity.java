package com.roshine.lookbar.commonlib.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;

import com.roshine.lookbar.commonlib.utils.ActivityUtil;
import com.roshine.lookbar.commonlib.utils.DisplayUtil;
import com.roshine.lookbar.commonlib.utils.ToastUtil;
import com.roshine.lookbar.commonlib.wight.NormalProgressDialog;
import com.roshine.lookbar.commonlib.R;

import org.greenrobot.eventbus.EventBus;


/**
 * @author Roshine
 * @date 2017/7/21 21:59
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc 所有activity通用基类
 */
public abstract class BaseActivity extends AppCompatActivity implements BaseView{
    protected Activity activity;
//    private Unbinder unbinder;
    protected int screenWidth;
    protected int screenHeight;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if(Build.VERSION.SDK_INT >= 21){
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        }
        super.onCreate(savedInstanceState);
        screenHeight = DisplayUtil.getScreenHeight(this);
        screenWidth = DisplayUtil.getScreenWidth(this);
        ActivityUtil.getInstance().addActivity(this);//添加activity栈
        activity = this;
        if(getLayoutId() != 0){
            setContentView(getLayoutId());
//            unbinder = ButterKnife.bind(this);
            if(needEventBus()){
                EventBus.getDefault().register(this);
            }
        }
        initViewData(savedInstanceState);
    }

    /**
     * 封装的findViewByID方法
     */
    @SuppressWarnings("unchecked")
    protected <T extends View> T $(@IdRes int id) {
        return (T) super.findViewById(id);
    }

    protected abstract int getLayoutId();

    protected abstract void initViewData(Bundle savedInstanceState);

    @Override
    public void showProgress() {
        showProgress(this.getString(R.string.common_loading_text),false);
    }

    @Override
    public void showProgress(String message) {
        showProgress(message,false);
    }

    @Override
    public void showProgress(String message, boolean cancelable) {
        NormalProgressDialog.showLoading(this,message,cancelable);
    }

    @Override
    public void hideProgress() {
        NormalProgressDialog.stopLoading();
    }

    @Override
    public void toast(String message) {
        ToastUtil.showSingleToast(message);
    }

    @Override
    public void toastLong(String message) {
        ToastUtil.showLong(message);
    }

    @Override
    public void toastWithTime(int time, String message) {
        ToastUtil.showWithTime(message,time);
    }

    @Override
    public void startActivity(Class<?> clz) {
        startActivity(new Intent(BaseActivity.this, clz));
    }

    @Override
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    @Override
    public void startActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void startActivityWithTransname(Class<?> cls, Bundle bundle, ActivityOptionsCompat compat) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        ActivityCompat.startActivity(this,intent, compat.toBundle());
    }

    @Override
    public void startActivityForResultWithTransname(Class<?> cls, Bundle bundle, int requestCode, ActivityOptionsCompat compat) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        ActivityCompat.startActivityForResult(this,intent,requestCode, compat.toBundle());
    }

    @Override
    public void finishActivity() {
        supportFinishAfterTransition();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityUtil.getInstance().removeActivity(this);//移除activity栈
//        if (unbinder != null && unbinder != Unbinder.EMPTY) unbinder.unbind();
//        this.unbinder = null;
        NormalProgressDialog.stopLoading();
        if (needEventBus()) {
            EventBus.getDefault().unregister(this);
        }
    }

    protected boolean needEventBus(){
        return false;
    }
}
