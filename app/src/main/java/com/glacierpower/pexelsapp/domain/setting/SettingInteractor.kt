package com.glacierpower.pexelsapp.domain.setting

import com.glacierpower.pexelsapp.data.sharedpreferences.UiMode
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SettingInteractor @Inject constructor(private val settingRepository: SettingRepository) {
    suspend fun setDarkMode(uiMode: UiMode) {
        settingRepository.setDarkMode(uiMode)
    }

    fun uIModeFlow(): Flow<UiMode> {
        return settingRepository.uIModeFlow()
    }

}