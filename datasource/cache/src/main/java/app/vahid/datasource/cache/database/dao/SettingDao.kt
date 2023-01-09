package app.vahid.datasource.cache.database.dao

import androidx.room.Dao
import androidx.room.Query
import app.vahid.datasource.cache.database.entity.CachedSetting
import kotlinx.coroutines.flow.Flow

@Dao
interface SettingDao : BaseDao<CachedSetting> {
    @Query("SELECT value FROM `setting` WHERE `key`=:key LIMIT 1")
    fun getSetting(key: String): Flow<String>
}

enum class SettingKeys {
    BaseCurrency
}