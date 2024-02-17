package com.glacierpower.pexelsapp.domain.setting

import com.glacierpower.pexelsapp.data.sharedpreferences.UiMode
import kotlinx.coroutines.flow.Flow

interface SettingRepository {
    suspend fun setDarkMode(uiMode: UiMode)

    fun uIModeFlow(): Flow<UiMode>


}