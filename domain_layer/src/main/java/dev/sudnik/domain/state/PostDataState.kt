package dev.sudnik.domain.state

import dev.sudnik.domain.model.Post

sealed class PostDataState {
    class PostLoaded(val post: Post) : PostDataState() {
        companion object {
            fun create(): PostLoaded = PostLoaded(Post("", 0, ""))
        }
    }

    object PostError : PostDataState()
}
