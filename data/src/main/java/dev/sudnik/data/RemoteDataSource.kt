package dev.sudnik.data

import dev.sudnik.basecleanandroid.domain.ErrorResponse
import dev.sudnik.data.post.PostDTO
import dev.sudnik.data.user.UserDTO
import kotlinx.coroutines.*

class RemoteDataSource {
    private var viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun getUsers(callback: OnUsersRemoteReadyCallback) {
        val users = ArrayList<UserDTO>()
        users.add(
            UserDTO(
                "Ivan", "Popov", 5
            )
        )
        users.add(
            UserDTO(
                "Oleg", "Ivanov", 2
            )
        )
        users.add(
            UserDTO(
                "Pavel", "Pavlov", 23
            )
        )

        viewModelScope.launch {
            repeat(2) {
                delay(1000L)
            }
            callback.onUsersReady(users)
        }
    }

    fun getPosts(callback: OnPostsRemoteReadyCallback) {
        viewModelScope.launch {
            repeat(2) {
                delay(1000L)
            }
            callback.onPostReady(PostDTO("Bobby", 2, "Post title", "Body text"))
        }
    }

    interface OnUsersRemoteReadyCallback {
        fun onUsersReady(users: ArrayList<UserDTO>)
        fun onError(error: ErrorResponse)
    }

    interface OnPostsRemoteReadyCallback {
        fun onPostReady(postDTO: PostDTO)
    }
}