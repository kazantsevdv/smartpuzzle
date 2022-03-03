package com.kazantsev.data.preference

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.kazantsev.domain.repository.DataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


private val Context.dataStore by preferencesDataStore("settings")

@Singleton
class DataStoreManagerImpl @Inject constructor(@ApplicationContext appContext: Context) :
    DataStore {

    private val settingsDataStore = appContext.dataStore

    override suspend fun setDifficult(difficult: Int) {
        settingsDataStore.edit { settings ->
            settings[PreferenceScheme.difficult] = difficult
        }
    }

    override val difficultMode: Flow<Int> = settingsDataStore.data.map { preferences ->
        preferences[PreferenceScheme.difficult] ?: 0
    }
}

object PreferenceScheme {
    val difficult = intPreferencesKey("difficult")
}