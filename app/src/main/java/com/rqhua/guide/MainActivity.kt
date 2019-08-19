package com.rqhua.guide

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import com.rqhua.collection.R
import com.rqhua.guide.dummy.DummyContent
import com.rqhua.noviceguide.guide.guidview.AbsGuideView
import com.rqhua.noviceguide.guide.guidview.AbsPopGuideView
import kotlinx.android.synthetic.main.guide_sample_fullscreen_layout.view.*
import kotlinx.android.synthetic.main.guide_sample_layout.view.*

class MainActivity : AppCompatActivity(), OnFragmentInteractionListener {
    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.beginTransaction().replace(android.R.id.content, ItemFragment.newInstance())
            .commitAllowingStateLoss()
    }

    override fun onFragmentInteraction(item: DummyContent.DummyItem?) {
        val frag = item?.clazz?.getConstructor()?.newInstance() ?: return
        supportFragmentManager.beginTransaction().replace(android.R.id.content, frag).addToBackStack(item.title)
            .commitAllowingStateLoss()
    }


}
