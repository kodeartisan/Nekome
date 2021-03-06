package com.chesire.nekome.core.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.chesire.nekome.core.flags.Service
import com.squareup.moshi.JsonClass

/**
 * Data for a singular user entity.
 */
@Entity
@JsonClass(generateAdapter = true)
data class UserModel(
    val userId: Int,
    val name: String,
    val avatar: ImageModel,
    val coverImage: ImageModel,
    @PrimaryKey
    val service: Service
)
