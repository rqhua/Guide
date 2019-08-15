package com.okay.guide.guide

import android.util.Log
import com.okay.guide.guide.display.IDisplayGuide
import com.okay.guide.guide.guidview.AbsGuideView
import java.util.*

/**
 * 控制调度引导的显示
 * @author Create by rqhua
 * @date 2019-08-12 下午3:59
 */
class GuidePresenter : IGuidHost {
    private val TAG = "GuidePresenter"
    /**
     * 正向引导数据
     */
    val guideStack = LinkedList<AbsGuideView>()
    /**
     * 回退引导数据
     */
    val guideBackStack = LinkedList<AbsGuideView>()
    /**
     * 用于显示引导
     */
    var guideDisplay: IDisplayGuide<AbsGuideView>? = null
    /**
     * 从第一个开始的引导
     */
    private var isFirstNavigate = true

    /**
     * 开始引导
     */
    fun startGuid() {
        isFirstNavigate = true
        guidNext()
    }

    /**
     * 显示下一个引导
     */
    override fun guidNext() {
        if (guideDisplay == null) {
            return
        }

        //回退栈为空的时候表示要显示第一个
        if (!isFirstNavigate) {
            // 将正在显示的添加到回退栈
            guideStack.operateFirst {
                guideDisplay?.finishGuide(it)
                it.host = null
                guideBackStack.notNullToFirst(guideStack.pollFirst())
            }
        }
        //显示当前第一个
        guideStack.operateFirst {
            it.host = this
            guideDisplay?.navigate(it)
            isFirstNavigate = false
        }

        if (guideBackStack.size == 0) {
            Log.d(TAG, "当前是第一个")
        }

        if (guideStack.size == 1) {
            Log.d(TAG, "当前是最后一个")
        }
        if (guideStack.size == 0) {
            Log.d(TAG, "全部隐藏")
        }
    }

    /**
     * 显示上一个引导
     */
    override fun guidPre() {
        if (guideDisplay == null) {
            return
        }

        // 只有当回退栈能取出第一个的时候才做回退操作
        guideBackStack.operateFirst { backTop ->
            guideStack.operateFirst { curTop ->
                //隐藏当前显示的
                guideDisplay?.finishGuide(curTop)
                // 从回退栈取出第一个显示
                backTop.host = this
                guideDisplay?.navigate(backTop)
                guideStack.notNullToFirst(guideBackStack.pollFirst())
            }
        }

        isFirst()
    }

    private fun isFirst() {
        if (guideBackStack.size == 0) {
            Log.d(TAG, "当前是第一个")
        }
    }

    fun addGuideView(guideView: AbsGuideView?) {
        guideStack.notNullToLast(guideView)
    }

    fun removeGuideView(guideView: AbsGuideView?) {
        guideStack.notNullToRemove(guideView)
    }
}

inline fun <T> LinkedList<T>.operateLast(operate: (T) -> Unit) {
    if (isNotEmpty()) {
        operate(last)
    }
}

inline fun <T> LinkedList<T>.operateFirst(operate: (T) -> Unit) {
    if (isNotEmpty()) {
        operate(first)
    }
}
