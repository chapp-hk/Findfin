package ch.app.hk.bank.locator.feature.auth.data.repo.user.mapper

import ch.app.hk.bank.locator.feature.auth.data.remote.user.response.UserResponse
import ch.app.hk.bank.locator.feature.auth.data.repo.user.model.UserModel
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper
interface UserModelMapper {
    @Mapping(source = "emailVerified", target = "isEmailVerified")
    fun clone(userResponse: UserResponse): UserModel
}
