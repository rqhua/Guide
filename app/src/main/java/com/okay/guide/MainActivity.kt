package com.okay.guide

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.rqhua.noviceguide.guide.display.GuideByDecorView
import com.rqhua.noviceguide.guide.display.PopGuideDisplay
import com.rqhua.noviceguide.guide.guider
import com.rqhua.noviceguide.guide.guidview.AbsGuideView
import com.rqhua.noviceguide.guide.guidview.AbsPopGuideView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.guide_sample_layout.view.*

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //全屏引导
        fullScreen?.setOnClickListener {
            guider(GuideByDecorView<AbsGuideView>(window)) {
                addGuideView(GuidView(1))
                addGuideView(GuidView(2))
                addGuideView(GuidView(3))
                addGuideView(GuidView(4))
                startGuid()
            }
        }

        popGuid?.setOnClickListener {
            //弹出式引导
            guider(PopGuideDisplay<AbsPopGuideView>()) {
                // LEFT/START
                addGuideView(PopGuideView("Left_Top", Gravity.LEFT).apply { anchor = popGuid })
                addGuideView(PopGuideView("Left_Center", Gravity.START or Gravity.CENTER).apply { anchor = popGuid })
                addGuideView(PopGuideView("Left_Bottom", Gravity.LEFT or Gravity.BOTTOM).apply { anchor = popGuid })

                // TOP
                addGuideView(PopGuideView("Top_Right", Gravity.TOP).apply { anchor = popGuid })
                addGuideView(PopGuideView("Top_Center", Gravity.TOP or Gravity.CENTER).apply { anchor = popGuid })
                addGuideView(PopGuideView("Top_Left", Gravity.TOP or Gravity.LEFT).apply { anchor = popGuid })

                // RIGHT/END
                addGuideView(PopGuideView("Right_Bottom", Gravity.END).apply { anchor = popGuid })
                addGuideView(PopGuideView("Right_Center", Gravity.RIGHT or Gravity.CENTER).apply { anchor = popGuid })
                addGuideView(PopGuideView("Right_Top", Gravity.RIGHT or Gravity.TOP).apply { anchor = popGuid })

                // BOTTOM
                addGuideView(PopGuideView("Bottom_Left", Gravity.BOTTOM).apply { anchor = popGuid })
                addGuideView(PopGuideView("Bottom_Center", Gravity.BOTTOM or Gravity.CENTER).apply { anchor = popGuid })
                addGuideView(PopGuideView("Bottom_Right", Gravity.BOTTOM or Gravity.RIGHT).apply { anchor = popGuid })
                startGuid()
            }
        }


    }

    class GuidView(val index: Int) : AbsGuideView() {
        private val TAG = "GuidView"
        override fun onCreateGuidView(layoutInflater: LayoutInflater): View? {
            return layoutInflater.inflate(R.layout.guide_sample_fullscreen_layout, null)?.apply {
                findViewById<TextView>(R.id.tips)?.apply {
                    text = "$index"
                }
                findViewById<TextView>(R.id.next)?.apply {
                    setOnClickListener {
                        notifyFinish()
                    }
                }

                findViewById<TextView>(R.id.pre)?.apply {
                    setOnClickListener {
                        notifyGuideBack()
                    }
                }
            }
        }

        override fun onAttached() {
            Log.i(TAG, "onAttached $index")
        }

        override fun onDetached() {
            Log.i(TAG, "onDetached $index")
        }

    }

    class PopGuideView(val testText: String, val gravity: Int = Gravity.LEFT) : AbsPopGuideView(gravity) {
        private val TAG = "PopGuideView"
        override fun onCreateGuidView(layoutInflater: LayoutInflater): View? {
            return layoutInflater.inflate(R.layout.guide_sample_layout, null)?.apply {
                next?.apply {
                    text = "$testText 点击下一步"
                    setOnClickListener {
                        notifyFinish()
                    }
                }
                pre?.apply {
                    text = "$testText 点击上一步"
                    setOnClickListener {
                        notifyGuideBack()
                    }
                }
            }
        }

        override fun onAttached() {
            Log.d(TAG, "onAttached $testText}")
        }

        override fun onDetached() {
            Log.d(TAG, "onDetached $testText")
        }

        /*override fun setAnimation(): Animation? {
            return RotateAnimation(0f, 360f).apply {
                duration = 2000
            }
        }*/
    }
}
