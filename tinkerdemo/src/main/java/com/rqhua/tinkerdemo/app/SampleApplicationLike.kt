package com.rqhua.tinkerdemo.app

import android.app.Application
import android.content.Context
import android.content.Intent
import android.support.multidex.MultiDex
import com.tencent.bugly.Bugly
import com.tencent.bugly.beta.Beta
import com.tencent.tinker.entry.DefaultApplicationLike

/**
 * @author Create by rqhua
 * @date 2019-08-19 下午7:38
 */
class SampleApplicationLike : DefaultApplicationLike {
    constructor(
        application: Application?,
        tinkerFlags: Int,
        tinkerLoadVerifyFlag: Boolean,
        applicationStartElapsedTime: Long,
        applicationStartMillisTime: Long,
        tinkerResultIntent: Intent?
    ) : super(
        application,
        tinkerFlags,
        tinkerLoadVerifyFlag,
        applicationStartElapsedTime,
        applicationStartMillisTime,
        tinkerResultIntent
    )

    override fun onCreate() {
        super.onCreate()
        // 这里实现SDK初始化，appId替换成你的在Bugly平台申请的appId
        // 调试时，将第三个参数改为true
        Bugly.init(getApplication(), "900029763", true)
    }

    override fun onBaseContextAttached(base: Context?) {
        super.onBaseContextAttached(base)
        // you must install multiDex whatever tinker is installed!
        MultiDex.install(base)

        // 安装tinker
        // TinkerManager.installTinker(this); 替换成下面Bugly提供的方法
        Beta.installTinker(this)
    }

    fun registerActivityLifecycleCallback(callbacks: Application.ActivityLifecycleCallbacks) {
        application.registerActivityLifecycleCallbacks(callbacks)
    }
}