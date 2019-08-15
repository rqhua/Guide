package com.rqhua.noviceguide.guide.display

import android.view.ViewGroup
import com.rqhua.noviceguide.guide.guidview.AbsGuideView
import com.rqhua.noviceguide.guide.guidview.initAndAddToParent
import com.rqhua.noviceguide.guide.guidview.removeFromeParent

/**
 * 定义加载显示引导View的方法
 * @author Create by rqhua
 * @date 2019-08-12 下午4:05
 */
interface IDisplayGuide<T : AbsGuideView> {

    /**
     * 显示下一个引导
     */
    fun navigate(guideView: T) {
        val container = getContainer(guideView) ?: return
        guideView.initAndAddToParent(container)
    }

    /**
     * 隐藏正在显示的引导
     */
    fun finishGuide(guideView: T) {
        val container = getContainer(guideView) ?: return
        guideView.removeFromeParent(container)
    }

    fun getContainer(guideView: T): ViewGroup?
}

