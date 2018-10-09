package com.roshine.lookbar;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.roshine.lookbar.commonlib.base.BaseApplication;
import com.roshine.lookbar.commonlib.utils.Utils;

/**
 * @author Roshine
 * @date 2018/10/7 17:01
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc
 */
public class LookBarApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        if (Utils.isAppDebug()) {
            //开启InstantRun之后，一定要在ARouter.init之前调用openDebug
            ARouter.openDebug();
            ARouter.openLog();
        }
        ARouter.init(this);
        //崩溃日志记录初始化
//        ACRA.init(this);
//        ACRA.getErrorReporter().removeAllReportSenders();
//        ACRA.getErrorReporter().setReportSender(new CrashReportSender());
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // dex突破65535的限制
        MultiDex.install(this);
    }
}
