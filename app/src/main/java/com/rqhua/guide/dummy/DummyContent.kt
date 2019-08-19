package com.rqhua.guide.dummy

import android.support.v4.app.Fragment
import java.util.*


class DummyContent {

    val ITEMS: MutableList<DummyItem> = ArrayList()

    data class DummyItem(
        var title: String = "title",
        var describe: String = "describe",
        var clazz: Class<out Fragment>? = null
    )

    companion object {
        private lateinit var instance: DummyContent

        @JvmStatic
        fun content(): DummyContent {
            if (!::instance.isInitialized) {
                instance = DummyContent()
            }
            return instance
        }
    }
}

fun Any.initItems(init: DummyContent.() -> Unit) {
    DummyContent.content().init()
}

fun DummyContent.item(init: DummyContent.DummyItem.() -> Unit) {
    ITEMS.add(DummyContent.DummyItem().apply(init))
}

fun DummyContent.clear() {
    ITEMS.clear()
}


