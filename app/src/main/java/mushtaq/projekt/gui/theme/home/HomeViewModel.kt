package mushtaq.projekt.gui.theme.home

import android.app.Application
import android.content.Intent
import android.provider.Settings
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import mushtaq.projekt.data.repository.UsageRepository
import mushtaq.projekt.domain.model.AppUsageData



class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = UsageRepository(application)

    //Holds list of apps to display
    private val _usageData = MutableStateFlow<List<AppUsageData>>(emptyList())
    val usageData: StateFlow<List<AppUsageData>> = _usageData.asStateFlow()


    private val _hasPermission = MutableStateFlow(false)
    val hasPermission: StateFlow<Boolean>  = _hasPermission.asStateFlow()

    init {
        checkPermission()
    }

    //Checks if permission is granted if yes fetch data
    fun checkPermission() {
        _hasPermission.value = repository.hasUsagePermission()
        if(_hasPermission.value){
            fetchUsageData()
        }
    }

    private fun fetchUsageData() {
        viewModelScope.launch(Dispatchers.IO) {
            val stats = repository.getInstalledAppUsage()
            _usageData.value = stats
        }
    }

    fun openUsageSettings() {
        val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        getApplication<Application>().startActivity(intent)
    }


}