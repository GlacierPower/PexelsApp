package com.glacierpower.pexelsapp.data.repositoryImpl

import com.glacierpower.pexelsapp.data.sharedpreferences.SettingDataStore
import com.glacierpower.pexelsapp.data.sharedpreferences.UiMode
import com.glacierpower.pexelsapp.domain.setting.SettingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SettingRepositoryImpl @Inject constructor(
    private val settingDataStore: SettingDataStore
) : SettingRepository {
    override suspend fun setDarkMode(uiMode: UiMode) {
        withContext(Dispatchers.IO) {
            settingDataStore.setDarkMode(uiMode)
        }
    }

    override fun uIModeFlow(): Flow<UiMode> {
        return settingDataStore.uiModeFlow
    }


}