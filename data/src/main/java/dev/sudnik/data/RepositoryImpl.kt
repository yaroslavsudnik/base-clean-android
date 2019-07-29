package dev.sudnik.data

import dev.sudnik.basecleanandroid.domain.ErrorResponse
import dev.sudnik.basecleanandroid.domain.OnCallback
import dev.sudnik.data.RemoteDataSource.OnPostsRemoteReadyCallback
import dev.sudnik.data.RemoteDataSource.OnUsersRemoteReadyCallback
import dev.sudnik.data.post.PostDTO
import dev.sudnik.data.post.PostMapper
import dev.sudnik.data.user.UserDTO
import dev.sudnik.data.user.UserMapper
import dev.sudnik.domain.entity.PostEntity
import dev.sudnik.domain.entity.UserEntity
import dev.sudnik.domain.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RepositoryImpl : Repository {

    private val remoteDataSource = RemoteDataSource()
    private val userMapper = UserMapper()
    private val postMapper = PostMapper()

    override fun getUserList(callback: OnCallback<UserEntity>) {
        remoteDataSource.getUsers(object : OnUsersRemoteReadyCallback {
            override fun onUsersReady(users: ArrayList<UserDTO>) {
                val userList = userMapper.transformUserList(users)
                callback.onSuccess(UserEntity(userList))
            }

            override fun onError(error: ErrorResponse) {
                callback.onError(error)
            }
        })
    }

    override fun getPostList(callback: OnCallback<PostEntity>) {
        remoteDataSource.getPosts(object : OnPostsRemoteReadyCallback {
            override fun onPostReady(postDTO: PostDTO) {
                GlobalScope.launch {
                    val post = postMapper.transformToPost(postDTO)
                    withContext(Dispatchers.Main) {
                        callback.onSuccess(PostEntity(post))
                    }
                }
            }
        })
    }
}