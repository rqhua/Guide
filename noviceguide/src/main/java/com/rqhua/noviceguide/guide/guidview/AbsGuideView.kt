package com.rqhua.noviceguide.guide.guidview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import com.rqhua.noviceguide.guide.IGuidHost
import com.rqhua.noviceguide.guide.removeChildById

/**
 * 定义显示与隐藏方法及回调,新手引导View要实现此接口
 * @author Create by rqhua
 * @date 2019-08-12 下午3:23
 */
abstract class AbsGuideView : View.OnAttachStateChangeListener {
    var guidView: View? = null
    var host: IGuidHost? = null

    open fun initialize(layoutInflater: LayoutInflater) {
        guidView = onCreateGuidView(layoutInflater)?.apply {
            addOnAttachStateChangeListener(this@AbsGuideView)
            animation = setAnimation()
        }
    }

    open fun addToParent(parent: ViewGroup) {
        guideViewOption {
            parent.addView(this)
        }
    }

    /**
     * 创建引导View
     */
    abstract fun onCreateGuidView(layoutInflater: LayoutInflater): View?

    /**
     * 引导View已经加载到Window中时回调
     */
    abstract fun onAttached()

    /**
     * 引导View 从Window中移除时回调
     */
    abstract fun onDetached()

    /**
     * 设置动画到引导View
     */
    open fun setAnimation(): Animation? {
        return null
    }

    /**
     * 调用此方法关闭引导,多引导时显示下一个
     */
    fun notifyFinish() {
        host?.guidNext()
    }

    /**
     * 多引导时,显示上一步引导
     */
    fun notifyGuideBack() {
        host?.guidPre()
    }

    override fun onViewDetachedFromWindow(v: View?) {
        guidView?.removeOnAttachStateChangeListener(this)
        onDetached()
    }

    override fun onViewAttachedToWindow(v: View?) {
        onAttached()
    }
}

/**
 * 操作引导View 省去重复?.
 */
fun AbsGuideView.guideViewOption(guidViewOption: View.() -> Unit) {
    guidView?.guidViewOption()
}

/**
 * 统一添加引导View到父容器方法:添加回调,增加Id
 */
fun AbsGuideView.initAndAddToParent(parent: ViewGroup) {
    initialize(parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
    guideViewOption {
        if (id == View.NO_ID) {
            id = View.generateViewId()
        }
        addToParent(parent)
    }
}

/**
 * 移除
 */
fun AbsGuideView.removeFromeParent(parent: ViewGroup) {
    guideViewOption {
        parent.removeChildById(this.id)
    }
}