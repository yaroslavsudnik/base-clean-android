package dev.sudnik.data.post

import dev.sudnik.basecleanandroid.data.BaseDataSource
import dev.sudnik.basecleanandroid.data.RetrofitHelper
import dev.sudnik.basecleanandroid.domain.RepositoryResponse
import dev.sudnik.domain.entity.PostEntity

class PostDataSource(response: RepositoryResponse<PostEntity>) :
    BaseDataSource<PostEntity, PostDTO, PostApi>(
        response,
        RetrofitHelper("site.com/api").retrofit
    ) {
    override val apiClazz: Class<PostApi> = PostApi::class.java

    override val successDataResponse: (PostDTO) -> Unit = {
        println(it.toString())
    }

    override fun successDomainResponse(dto: PostDTO) =
        PostEntity(PostMapper().transformToPost(dto))

    fun getPost(postId: String) = loadData { api.post(postId) }
}
