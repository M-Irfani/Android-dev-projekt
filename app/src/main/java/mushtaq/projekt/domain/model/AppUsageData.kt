package mushtaq.projekt.domain.model

import android.graphics.drawable.Drawable

data class AppUsageData(
    val packageName: String,
    val appName: String,
    val usageDuration: Long,
    val icon: Drawable? = null
)
