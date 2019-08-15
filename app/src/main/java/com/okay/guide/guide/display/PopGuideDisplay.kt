package com.okay.guide.guide.display

import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.FrameLayout
import com.okay.guide.guide.guidview.AnchorLayout

/**
 * @author Create by rqhua
 * @date 2019-08-14 下午3:44
 */
class PopGuideDisplay(window: Window) : GuideByDecorView(window) {
    private var anchorLayout: AnchorLayout? = null

    override fun getContainer(): ViewGroup? {
        if (anchorLayout == null) {
            anchorLayout = AnchorLayout(window.context).apply {
                layoutParams =
                    FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT
                    )
                id = View.generateViewId()
            }
            window.decorView.findViewById<ViewGroup>(android.R.id.content)?.addView(anchorLayout)
        }
        return anchorLayout
    }
}