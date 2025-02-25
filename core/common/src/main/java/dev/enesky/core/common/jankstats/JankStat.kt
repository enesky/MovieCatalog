package dev.enesky.core.common.jankstats

import androidx.metrics.performance.JankStats
import com.google.firebase.crashlytics.FirebaseCrashlytics

/**
 * Created by Enes Kamil YILMAZ on 25/02/2025
 */
object JankStat {

    private const val JANK_KEY = "jank_frame_time"
    private const val JANK_EXCEPTION_MESSAGE = "Jank detected"
    val jankFrameListener = JankStats.OnFrameListener { frameData ->
        FirebaseCrashlytics.getInstance().apply {
            setCustomKey(JANK_KEY, frameData.toString())
            recordException(Exception(JANK_EXCEPTION_MESSAGE))
        }
    }
}
