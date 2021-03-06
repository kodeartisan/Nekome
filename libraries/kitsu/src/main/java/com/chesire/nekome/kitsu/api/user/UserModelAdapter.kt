package com.chesire.nekome.kitsu.api.user

import com.chesire.nekome.core.flags.Service
import com.chesire.nekome.core.models.ImageModel
import com.chesire.nekome.core.models.UserModel
import com.squareup.moshi.FromJson

/**
 * Class for converting a [GetUserDetailsResponse] into a [UserModel].
 */
class UserModelAdapter {
    /**
     * Converts [response] into a [UserModel].
     */
    @FromJson
    fun userModelFromUserDetails(response: GetUserDetailsResponse): UserModel {
        val details = response.data.first()
        return UserModel(
            details.id,
            details.attributes.name,
            details.attributes.avatar ?: ImageModel.empty,
            details.attributes.coverImage ?: ImageModel.empty,
            Service.Kitsu
        )
    }
}
