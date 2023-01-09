package app.vahid.datasource.cache.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "setting")
data class CachedSetting(
    @PrimaryKey var key: String,
    val value: String,
)