package com.okay.guide.guide.display

import android.view.LayoutInflater
import com.okay.guide.guide.guidview.AbsGuideView

/**
 * 定义加载显示引导View的方法
 * @author Create by rqhua
 * @date 2019-08-12 下午4:05
 */
interface IDisplayGuide<T : AbsGuideView> {
    var inflate: LayoutInflater

    /**
     * 显示下一个引导
     */
    fun navigate(guideView: T)

    /**
     * 隐藏正在显示的引导
     */
    fun finishGuide(guideView: T)
}

