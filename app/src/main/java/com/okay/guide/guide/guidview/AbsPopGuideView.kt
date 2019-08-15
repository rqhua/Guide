package com.okay.guide.guide.guidview

import android.graphics.Rect
import android.util.DisplayMetrics
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout

/**
 * 根据位置弹出引导的引导 view
 * @author Create by rqhua
 * @date 2019-08-13 下午7:09
 */
abstract class AbsPopGuideView(private val gravity: Int) : AbsGuideView(), View.OnLayoutChangeListener {
    /**
     * 锚点,根据此view确定气泡引导弹出的位置
     */
    var anchor: View? = null
        set(value) {
            field = value
            updateLocation()
            field?.addOnLayoutChangeListener(this)
        }

    /**
     * 锚点布局边界发生变化
     */
    override fun onLayoutChange(
        v: View?,
        left: Int,
        top: Int,
        right: Int,
        bottom: Int,
        oldLeft: Int,
        oldTop: Int,
        oldRight: Int,
        oldBottom: Int
    ) {
        //更新位置信息
        updateLocation()
    }

    /**
     * 锚点在Window上的坐标
     */
    var anchorLocation = IntArray(2)
    /**
     * 锚点区域
     */
    var anchorRect = Rect()

    /**
     * 气泡引导View将被添加到此布局中,guidViewContainer将直接添加到Window.decorView.content中
     */
    private var guidViewContainer: AnchorLayout? = null
    /**
     * guidViewContainer 在Window 上的坐标
     */
    var guidViewContainerLocation = IntArray(2)


    override fun addToParent(parent: ViewGroup) {
        Log.d("AbsPopGuideView", "addToParent 锚点: X=${anchorLocation.x} Y=${anchorLocation.y}")
        if (parent is AnchorLayout) {
            guidViewContainer = parent
        }
        guideViewOption {
            guidViewContainer?.addViewWithGravity(this, gravity)
            updateLocation()
        }
    }

    /**
     * 更新锚点布局信息
     */
    private fun updateLocation() {
        // 获取气泡引导view父布局在Window上的坐标
        guidViewContainer?.getLocationInWindow(guidViewContainerLocation)
        // 获取锚点view在Window上的坐标及区域
        anchor?.getLocationInWindow(anchorLocation)
        anchor?.getGlobalVisibleRect(anchorRect)
        // 计算锚点view坐标映射到Window.decorView.content的坐标
        anchorLocation.location {
            x -= guidViewContainerLocation.x
            y -= guidViewContainerLocation.y
        }
        Log.d("AbsPopGuideView", "updateLocation 锚点: X=${anchorLocation.x} Y=${anchorLocation.y}")
        guidViewContainer?.updateAnchor(anchorLocation, anchorRect)
    }
}