package dev.sudnik.data.user

import dev.sudnik.basecleanandroid.data.BaseDataSource
import dev.sudnik.basecleanandroid.data.RetrofitHelper
import dev.sudnik.basecleanandroid.domain.RepositoryResponse
import dev.sudnik.domain.entity.UserEntity

class UserDataSource(response: RepositoryResponse<UserEntity>) :
    BaseDataSource<UserEntity, UsersDTO, UserApi>(response, RetrofitHelper(URI).retrofit) {

    suspend fun getUsers(groupId: String) = loadData { api.users(groupId) }

    override val successDataResponse: (UsersDTO) -> Unit = {
        println(it.toString())
    }

    override fun successDomainResponse(dto: UsersDTO) =
        UserEntity(UserMapper().transformUserList(dto.userDTOList))

    override val apiClazz: Class<UserApi> = UserApi::class.java

    companion object {
        private const val URI = "uri.site"
    }
}
