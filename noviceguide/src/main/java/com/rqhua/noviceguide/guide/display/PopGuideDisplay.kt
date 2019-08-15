package com.rqhua.noviceguide.guide.display

import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.rqhua.noviceguide.guide.guidview.AbsPopGuideView
import com.rqhua.noviceguide.guide.guidview.AnchorLayout

/**
 * @author Create by rqhua
 * @date 2019-08-14 下午3:44
 */
class PopGuideDisplay<T : AbsPopGuideView> : IDisplayGuide<T> {
    private var anchorLayout: AnchorLayout? = null

    override fun getContainer(guideView: T): ViewGroup? {
        guideView.anchor?.parent?.takeIf { it is ViewGroup }?.apply {
            var parent = this
            while (parent.parent != null && parent.parent is ViewGroup) {
                parent = parent.parent
            }
            if (anchorLayout == null) {
                val root = parent as ViewGroup
                anchorLayout = AnchorLayout(root.context).apply {
                    layoutParams = FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT
                    )
                    id = View.generateViewId()
                }
                root.addView(anchorLayout)
            }
        }
        return anchorLayout
    }
}