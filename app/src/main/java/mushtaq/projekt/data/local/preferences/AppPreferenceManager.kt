package mushtaq.projekt.data.local.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "setting")

class AppPreferenceManager(private val context: Context) {

    companion object {
        val IS_BLOCKER_ENABLED = booleanPreferencesKey("is_blocker_enabled")
    }

    val isBlockerEnables: Flow<Boolean> = context.dataStore.data
        .catch { exception ->
            if(exception is IOException){
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[IS_BLOCKER_ENABLED] ?: false
        }

    suspend fun setBlockerEnabled(enabled: Boolean){
        context.dataStore.edit{ preferences ->
            preferences[IS_BLOCKER_ENABLED] = enabled
        }
    }
}