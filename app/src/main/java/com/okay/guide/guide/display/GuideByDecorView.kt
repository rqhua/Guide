package com.okay.guide.guide.display

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import com.okay.guide.guide.guidview.AbsGuideView
import com.okay.guide.guide.guidview.initAndAddToParent
import com.okay.guide.guide.guidview.removeFromeParent

/**
 * @author Create by rqhua
 * @date 2019-08-13 下午7:45
 */
open class GuideByDecorView(var window: Window) : IDisplayGuide<AbsGuideView> {
    override var inflate: LayoutInflater = window.layoutInflater

    override fun navigate(guideView: AbsGuideView) {
        val container = getContainer() ?: return
        guideView.initAndAddToParent(container)
    }

    override fun finishGuide(guideView: AbsGuideView) {
        val container = getContainer() ?: return
        guideView.removeFromeParent(container)
    }

    private var container: ViewGroup? = null
    open fun getContainer(): ViewGroup? {
        if (container == null) {
            container = window.decorView as ViewGroup
        }
        return container
    }
}