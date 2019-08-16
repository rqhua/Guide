package com.rqhua.noviceguide.guide.guidview

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import java.lang.IllegalArgumentException
import java.util.*
import kotlin.math.max

/**
 * 根据传入的参考位置信息,确定child的布局位置
 * 参照系统AbsoluteLayout
 * @author Create by rqhua
 * @date 2019-08-14 上午10:25
 */
class AnchorLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    ViewGroup(context, attrs, defStyleAttr) {

    /**
     * 锚点在屏幕上的坐标
     */
    var anchorLocation = IntArray(2)
    /**
     * 锚点区域
     */
    var anchorRect = Rect()
    /**
     * 引导view要放置的位置
     */
    var childLocation = IntArray(2)
    /**
     * child区域
     */
    var childRect = Rect()

    /**
     * 保存View相对于锚点的对齐方式
     */
    private var viewsGrivity: HashSet<Pair<View, Int>> = hashSetOf()

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val count = childCount
        var maxHeight = 0
        var maxWidth = 0

        // Find out how big everyone wants to be
        measureChildren(widthMeasureSpec, heightMeasureSpec)

        // Find rightmost and bottom-most child
        for (i in 0 until count) {
            val child = getChildAt(i)
            if (child.visibility != View.GONE) {
                val childRight: Int
                val childBottom: Int

                val lp = child.layoutParams as LayoutParams

                childRight = lp.anchorLocation[0] + child.measuredWidth
                childBottom = lp.anchorLocation[1] + anchorRect.height() + child.measuredHeight

                maxWidth = max(maxWidth, childRight)
                maxHeight = max(maxHeight, childBottom)
            }
        }

        // Account for padding too
        maxWidth += paddingLeft + paddingRight
        maxHeight += paddingTop + paddingBottom

        // Check against minimum height and width
        maxHeight = max(maxHeight, suggestedMinimumHeight)
        maxWidth = max(maxWidth, suggestedMinimumWidth)

        setMeasuredDimension(
            View.resolveSizeAndState(maxWidth, widthMeasureSpec, 0),
            View.resolveSizeAndState(maxHeight, heightMeasureSpec, 0)
        )
    }

    override fun generateDefaultLayoutParams(): ViewGroup.LayoutParams {
        return LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, IntArray(2), Rect())
    }

    override fun onLayout(
        changed: Boolean, l: Int, t: Int,
        r: Int, b: Int
    ) {
        val count = childCount

        for (i in 0 until count) {
            val child = getChildAt(i)
            if (child.visibility != View.GONE) {
                childLocation(child)
                val childLeft = paddingLeft + childLocation[0]
                val childTop = paddingTop + childLocation[1]
                child.layout(
                    childLeft, childTop,
                    childLeft + child.measuredWidth,
                    childTop + child.measuredHeight
                )
            }
        }
    }

    /**
     * 根据锚点信息确定child的坐标
     */
    private fun childLocation(child: View) {
        childRect.set(
            0,
            0,
            child.measuredWidth,
            child.measuredHeight
        )
        for (pair in viewsGrivity) {
            if (pair.first === child) {
                when (pair.second) {
                    Gravity.CENTER -> {
                        //锚点中心
                        childLocation.location {
                            x = alignCenterX()
                            y = alignCenterY()
                        }
                    }
                    //左边
                    Gravity.START, Gravity.LEFT -> {
                        //左边靠上
                        childLocation.location {
                            x = startX()
                            y = alignTopY()
                        }
                    }
                    Gravity.START or Gravity.CENTER, Gravity.LEFT or Gravity.CENTER -> {
                        //左边居中
                        childLocation.location {
                            x = startX()
                            y = alignCenterY()
                        }
                    }
                    Gravity.START or Gravity.BOTTOM, Gravity.LEFT or Gravity.BOTTOM -> {
                        //左边靠下
                        childLocation.location {
                            x = startX()
                            y = alignBottomY()
                        }
                    }

                    //上边
                    Gravity.TOP -> {
                        // 顶部靠右
                        childLocation.location {
                            x = alignRightX()
                            y = aboveY()
                        }
                    }

                    Gravity.TOP or Gravity.CENTER -> {
                        //顶部居中
                        childLocation.location {
                            x = alignCenterX()
                            y = aboveY()
                        }
                    }

                    Gravity.TOP or Gravity.START, Gravity.TOP or Gravity.LEFT -> {
                        //顶部靠右
                        childLocation.location {
                            x = alignLeftX()
                            y = aboveY()
                        }
                    }
                    // 右边
                    Gravity.END, Gravity.RIGHT -> {
                        //右边靠下
                        childLocation.location {
                            x = endX()
                            y = alignBottomY()
                        }
                    }
                    Gravity.END or Gravity.CENTER, Gravity.RIGHT or Gravity.CENTER -> {
                        //右边居中
                        childLocation.location {
                            x = endX()
                            y = alignCenterY()
                        }

                    }
                    Gravity.END or Gravity.TOP, Gravity.RIGHT or Gravity.TOP -> {
                        //右边底部
                        childLocation.location {
                            x = endX()
                            y = alignTopY()
                        }
                    }

                    //下边
                    Gravity.BOTTOM -> {
                        //底部靠左
                        childLocation.location {
                            x = alignLeftX()
                            y = belowY()
                        }
                    }

                    Gravity.BOTTOM or Gravity.END, Gravity.BOTTOM or Gravity.RIGHT -> {
                        //底部靠右
                        childLocation.location {
                            x = alignRightX()
                            y = belowY()
                        }
                    }

                    Gravity.BOTTOM or Gravity.CENTER -> {
                        //底部居中
                        childLocation.location {
                            x = alignCenterX()
                            y = belowY()
                        }
                    }
                }
                break
            }
        }
    }

    //左对齐x值
    private fun alignLeftX(): Int {
        return anchorLocation.x
    }

    //右对齐x值
    private fun alignRightX(): Int {
        return anchorLocation.x + anchorRect.width() - childRect.width()
    }

    //居中对齐x值
    private fun alignCenterX(): Int {
        return anchorLocation.x + anchorRect.width() / 2 - childRect.width() / 2
    }

    //上对齐y值
    private fun alignTopY(): Int {
        return anchorLocation.y
    }

    //居中对齐y值
    private fun alignCenterY(): Int {
        return anchorLocation.y + anchorRect.height() / 2 - childRect.height() / 2
    }

    //底部对齐y值
    private fun alignBottomY(): Int {
        return anchorLocation.y + anchorRect.height() - childRect.height()
    }

    //在顶部的y值
    private fun aboveY(): Int {
        return anchorLocation.y - childRect.height()
    }

    //在底部的y值
    private fun belowY(): Int {
        return anchorLocation.y + anchorRect.height()
    }

    //在左边的x值
    private fun startX(): Int {
        return anchorLocation.x - childRect.width()
    }

    //在右边的X值
    private fun endX(): Int {
        return anchorLocation.x + anchorRect.width()
    }

    /**
     * @param view
     * @param gravity [Gravity] 中定义的对齐方式,如:[Gravity.BOTTOM]|[Gravity.CENTER] 指在锚点区域[anchorRect]的下方居中显示.
     * 支持的对齐类型: 底部靠右:BOTTOM|RIGHT; 底部居中:BOTTOM|CENTER, 底部靠左:BOTTOM|LEFT等(锚点的上下左右位置的三个对齐方式) 和 锚点区域正中央
     *
     */
    fun addViewWithGravity(view: View?, gravity: Int) {
        if (view == null) {
            return
        }
        viewsGrivity.add(view to gravity)
        super.addView(view)
    }

    override fun removeView(view: View?) {
        removeViewFromCacheSet(view)
        super.removeView(view)
    }

    override fun removeViewAt(index: Int) {
        removeViewFromCacheSet(getChildAt(index))
        super.removeViewAt(index)
    }

    private fun removeViewFromCacheSet(view: View?) {
        for (pair in viewsGrivity) {
            if (pair.first === view) {
                viewsGrivity.remove(pair)
                break
            }
        }
    }

    fun updateAnchor(location: IntArray, rect: Rect) {
        if (location.size != 2) {
            throw IllegalArgumentException("数组长度必需等于2")
        }

        //判断是否需要更新位置
        if (location[0] == anchorLocation[0] &&
            location[1] == anchorLocation[1] &&
            rect.left == anchorRect.left &&
            rect.top == anchorRect.top &&
            rect.right == anchorRect.right &&
            rect.bottom == anchorRect.bottom
        ) {
            return
        }
        anchorRect.set(rect)
        anchorLocation[0] = location[0]
        anchorLocation[1] = location[1]
        requestLayout()
    }

    override fun generateLayoutParams(attrs: AttributeSet): ViewGroup.LayoutParams {
        return LayoutParams(context, attrs)
    }

    override fun checkLayoutParams(p: ViewGroup.LayoutParams): Boolean {
        return p is LayoutParams
    }

    override fun generateLayoutParams(p: ViewGroup.LayoutParams): ViewGroup.LayoutParams {
        return LayoutParams(p)
    }

    override fun shouldDelayChildPressedState(): Boolean {
        return false
    }

    class LayoutParams : ViewGroup.LayoutParams {
        /**
         * 锚点在屏幕上的坐标
         */
        var anchorLocation = IntArray(2)
        /**
         * 锚点区域
         */
        var anchorRect = Rect()

        constructor(width: Int, height: Int, anchorLocation: IntArray, anchorRect: Rect) : super(width, height) {
            this.anchorLocation = anchorLocation
            this.anchorRect = anchorRect
        }

        constructor(c: Context, attrs: AttributeSet) : super(c, attrs)

        constructor(source: ViewGroup.LayoutParams) : super(source)

        companion object {
            const val MATCH_PARENT = ViewGroup.LayoutParams.MATCH_PARENT
            const val WRAP_CONTENT = ViewGroup.LayoutParams.WRAP_CONTENT
        }
    }
}

/**
 * [IntArray]扩展属性,用于设置 View 坐标场景
 */
var IntArray.x: Int
    get() = get(0)
    set(value) {
        set(0, value)
    }
/**
 * [IntArray]扩展属性,用于设置 View 坐标场景
 */
var IntArray.y: Int
    get() = get(1)
    set(value) {
        set(1, value)
    }

fun IntArray.location(init: IntArray.() -> Unit) {
    init()
}