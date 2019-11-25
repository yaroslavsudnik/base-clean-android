package dev.sudnik.data.user

import dev.sudnik.basecleanandroid.data.BaseDataSource
import dev.sudnik.basecleanandroid.data.RetrofitHelper
import dev.sudnik.basecleanandroid.domain.RepositoryResponse
import dev.sudnik.domain.entity.UserEntity

class UserDataSource(response: RepositoryResponse<UserEntity>) :
    BaseDataSource<UserEntity, UsersDTO, UserApi>(response, RetrofitHelper("uri.site").retrofit) {
    override val apiClazz: Class<UserApi> = UserApi::class.java

    override val successDataResponse: (UsersDTO) -> Unit = {
        println(it.toString())
    }

    override fun successDomainResponse(dto: UsersDTO) =
        UserEntity(UserMapper().transformUserList(dto.userDTOList))

    fun getUsers(groupId: String) = loadData { api.users(groupId) }
}
