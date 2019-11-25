package dev.sudnik.data

import dev.sudnik.basecleanandroid.domain.RepositoryResponse
import dev.sudnik.data.post.PostDTO
import dev.sudnik.data.post.PostMapper
import dev.sudnik.data.user.UserDTO
import dev.sudnik.data.user.UserMapper
import dev.sudnik.domain.entity.PostEntity
import dev.sudnik.domain.entity.UserEntity
import dev.sudnik.domain.repository.Repository

class RepositoryImpl : Repository {

    override fun getUserList(groupId: String, response: RepositoryResponse<UserEntity>) {
//        val dataSource = UserDataSource(response)
//        dataSource.getUsers(groupId)

        response.onSuccess(getUserEntity())
//            TODO("load token from data storage")
    }

    override fun getPost(postId: String, response: RepositoryResponse<PostEntity>) {
//        val dataSource = PostDataSource(response)
//        dataSource.getPost(postId)

        response.onSuccess(getPostEntity())
//            TODO("load token from data storage")
    }

    private fun getUserEntity(): UserEntity {
        return UserEntity(
            UserMapper().transformUserList(
                arrayListOf(
                    UserDTO(
                        "Ivan", "Popov", 5
                    ),
                    UserDTO(
                        "Oleg", "Ivanov", 2
                    ),
                    UserDTO(
                        "Pavel", "Pavlov", 23
                    )
                )
            )
        )
    }

    private fun getPostEntity(): PostEntity {
        return PostEntity(
            PostMapper().transformToPost(
                PostDTO("Bobby", 2, "Post title", "Body text")
            )
        )
    }
}
