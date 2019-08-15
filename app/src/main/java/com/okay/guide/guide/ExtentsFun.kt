package com.okay.guide.guide

import android.app.Activity
import android.app.Dialog
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.FrameLayout
import com.okay.guide.guide.display.GuideByDecorView
import com.okay.guide.guide.display.IDisplayGuide
import com.okay.guide.guide.guidview.AbsGuideView
import java.util.*

/**
 * 扩展函数
 * @author Create by rqhua
 * @date 2019-08-12 下午5:23
 */
fun Activity.guider(display: IDisplayGuide<AbsGuideView>? = null, init: GuidePresenter.() -> Unit): GuidePresenter {
    return GuidePresenter().apply {
        guideDisplay = display ?: GuideByDecorView(window)
        init()
    }
}

fun Fragment.guider(display: IDisplayGuide<AbsGuideView>? = null, init: GuidePresenter.() -> Unit): GuidePresenter {
    return GuidePresenter().apply {
        if (activity?.window != null) {
            guideDisplay = display ?: GuideByDecorView(activity!!.window)
            init()
        }
    }
}

fun AlertDialog.guider(display: IDisplayGuide<AbsGuideView>? = null, init: GuidePresenter.() -> Unit): GuidePresenter {
    return GuidePresenter().apply {
        guideDisplay = display ?: GuideByDecorView(window)
        init()
    }
}

fun Dialog.guider(display: IDisplayGuide<AbsGuideView>? = null, init: GuidePresenter.() -> Unit): GuidePresenter {
    return GuidePresenter().apply {
        guideDisplay = display ?: GuideByDecorView(window)
        init()
    }
}

fun Window.addGuideView(guideView: AbsGuideView?, attached: () -> Unit) {
    if (guideView?.guidView is View) {
        (decorView.findViewById<View>(android.R.id.content) as? FrameLayout)?.addView(guideView.guidView)
        attached.invoke()
    }
}

fun Window.removeGuideView(guideView: AbsGuideView?, removed: () -> Unit) {
    if (guideView?.guidView is View) {
        (decorView.findViewById<View>(android.R.id.content) as? FrameLayout)?.removeView(guideView.guidView)
        removed.invoke()
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