package com.rqhua.noviceguide.guide.display

import android.view.ViewGroup
import android.view.Window
import com.rqhua.noviceguide.guide.guidview.AbsGuideView

/**
 * @author Create by rqhua
 * @date 2019-08-13 下午7:45
 */
open class GuideByDecorView<T : AbsGuideView>(var window: Window) : IDisplayGuide<T> {
    private var container: ViewGroup? = null
    override fun getContainer(guideView: T): ViewGroup? {
        if (container == null) {
            container = window.decorView as ViewGroup
        }
        return container
    }
}