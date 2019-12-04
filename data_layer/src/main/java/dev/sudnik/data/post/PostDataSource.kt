package dev.sudnik.data.post

import dev.sudnik.basecleanandroid.data.BaseDataSource
import dev.sudnik.basecleanandroid.data.RetrofitHelper
import dev.sudnik.basecleanandroid.domain.RepositoryResponse
import dev.sudnik.domain.entity.PostEntity

class PostDataSource(response: RepositoryResponse<PostEntity>) :
    BaseDataSource<PostEntity, PostDTO, PostApi>(
        response,
        RetrofitHelper(URI).retrofit
    ) {

    suspend fun getPost(postId: String) = loadData { api.post(postId) }

    override val successDataResponse: (PostDTO) -> Unit = {
        println(it.toString())
    }

    override fun successDomainResponse(dto: PostDTO) =
        PostEntity(PostMapper().transformToPost(dto))

    override val apiClazz: Class<PostApi> = PostApi::class.java

    companion object {
        private const val URI = "site.com/api"
    }
}
