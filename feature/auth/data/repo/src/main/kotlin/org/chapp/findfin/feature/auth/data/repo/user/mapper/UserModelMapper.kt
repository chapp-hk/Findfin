package org.chapp.findfin.feature.auth.data.repo.user.mapper

import org.chapp.findfin.feature.auth.data.repo.user.model.UserModel
import org.chapp.findfin.feature.auth.data.repo.user.remote.response.UserResponse
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper
internal interface UserModelMapper {
    @Mapping(source = "emailVerified", target = "isEmailVerified")
    fun clone(userResponse: UserResponse): UserModel
}
