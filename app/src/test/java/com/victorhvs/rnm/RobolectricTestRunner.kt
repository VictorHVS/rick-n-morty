package com.victorhvs.rnm

import android.os.Build
import androidx.fragment.app.FragmentActivity
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode
import org.robolectric.annotation.LooperMode.Mode.PAUSED

@RunWith(RobolectricTestRunner::class)
@Config(
    sdk = [Build.VERSION_CODES.O_MR1],
    application = RobolectricTestApplication::class,
    manifest = Config.NONE
)
@LooperMode(PAUSED)
abstract class RobolectricTestRunner {

    val activity: FragmentActivity
        get() = Robolectric.buildActivity(FragmentActivity::class.java).create().get()
}
