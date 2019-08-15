package com.rqhua.noviceguide.guide

import android.app.Activity
import android.app.Dialog
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.FrameLayout
import com.rqhua.noviceguide.guide.display.GuideByDecorView
import com.rqhua.noviceguide.guide.display.IDisplayGuide
import com.rqhua.noviceguide.guide.guidview.AbsGuideView
import java.util.*

/**
 * 扩展函数
 * @author Create by rqhua
 * @date 2019-08-12 下午5:23
 */

inline fun <T:AbsGuideView> Activity.guider(display: IDisplayGuide<T>, init: GuidePresenter<T>.() -> Unit): GuidePresenter<T> {
    return GuidePresenter<T>().apply {
        guideDisplay = display
        init()
    }
}

inline fun <T:AbsGuideView> Fragment.guider(display: IDisplayGuide<T>, init: GuidePresenter<T>.() -> Unit): GuidePresenter<T> {
    return GuidePresenter<T>().apply {
        if (activity != null) {
            guideDisplay = display
            init()
        }
    }
}

inline fun <T:AbsGuideView> AlertDialog.guider(display: IDisplayGuide<T>, init: GuidePresenter<T>.() -> Unit): GuidePresenter<T> {
    return GuidePresenter<T>().apply {
        guideDisplay = display
        init()
    }
}

inline fun <T:AbsGuideView> Dialog.guider(display: IDisplayGuide<T>, init: GuidePresenter<T>.() -> Unit): GuidePresenter<T> {
    return GuidePresenter<T>().apply {
        guideDisplay = display
        init()
    }
}

inline fun ViewGroup.removeChildByIds(ids: HashSet<Int>?) {
    ids?.forEach { id ->
        findViewById<View>(id)?.let { view ->
            removeView(view)
        }
    }
}

/**
 * 根据Id移除View
 */
inline fun ViewGroup.removeChildById(id: Int?) {
    id?.let { id ->
        findViewById<View>(id)?.let { view ->
            removeView(view)
        }
    }
}

/**
 * 非空添加
 */
fun <T> LinkedList<T>.notNullToFirst(t: T?) {
    if (t != null) {
        addFirst(t)
    }
}

/**
 * 非空添加
 */
fun <T> LinkedList<T>.notNullToLast(t: T?) {
    if (t != null) {
        addLast(t)
    }
}

/**
 * 非空移除
 */
fun <T> LinkedList<T>.notNullToRemove(t: T?) {
    if (t != null) {
        remove(t)
    }
}