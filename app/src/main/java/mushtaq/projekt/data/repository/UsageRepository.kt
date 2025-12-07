package mushtaq.projekt.data.repository

import android.app.AppOpsManager
import android.app.usage.UsageStatsManager
import android.content.Context
import android.os.Process
import mushtaq.projekt.domain.model.AppUsageData
import java.util.Calendar



class UsageRepository(private val context: Context) {

    //Get system service that tracks app usage
    private val usageStatsManager = context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager

    //Check if user has granted Usage Access permission
    fun hasUsagePermission(): Boolean {
        val appOps = context.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
        val mode = appOps.checkOpNoThrow(
            AppOpsManager.OPSTR_GET_USAGE_STATS,
            Process.myUid(),
            context.packageName
        )
        return mode == AppOpsManager.MODE_ALLOWED
    }


    //Fetch list of apps used in the last 24hrs
    fun getInstalledAppUsage(): List<AppUsageData> {
        val calendar = Calendar.getInstance()
        val endTime = calendar.timeInMillis
        calendar.add(Calendar.DAY_OF_YEAR, -1)
        val startTime = calendar.timeInMillis

        val usageStatsList = usageStatsManager.queryUsageStats(
            UsageStatsManager.INTERVAL_DAILY, startTime, endTime
        )

        return usageStatsList
            .filter { it.totalTimeInForeground > 0}
            .map { usageStat ->
                AppUsageData(
                    packageName = usageStat.packageName,
                    appName = usageStat.packageName,
                    usageDuration = usageStat.totalTimeInForeground
                )
            }
            .sortedByDescending { it.usageDuration } //Most used app first
    }
}