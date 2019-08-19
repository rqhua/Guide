package com.rqhua.tinkerdemo.app

import com.tencent.tinker.loader.app.TinkerApplication
import com.tencent.tinker.loader.shareutil.ShareConstants

/**
 * @author Create by rqhua
 * @date 2019-08-19 下午7:36
 */
class SampleApplication : TinkerApplication {
    constructor() : super(
        ShareConstants.TINKER_ENABLE_ALL, "com.rqhua.tinkerdemo.app.SampleApplicationLike",
        "com.tencent.tinker.loader.TinkerLoader", false
    )
}