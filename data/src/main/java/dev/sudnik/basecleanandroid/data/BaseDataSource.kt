package dev.sudnik.basecleanandroid.data

import dev.sudnik.basecleanandroid.domain.ErrorResponse
import dev.sudnik.basecleanandroid.domain.RepositoryResponse
import retrofit2.Response
import retrofit2.Retrofit

abstract class BaseDataSource<EntityType, DTOType, ApiType>(
    private val domainResponse: RepositoryResponse<EntityType>,
    private val retrofit: Retrofit
) {

    protected val api: ApiType by lazy {
        retrofit.create(apiClazz)
    }

    protected abstract val apiClazz: Class<ApiType>
    protected abstract val successDataResponse: (DTOType) -> Unit
    protected abstract fun successDomainResponse(dto: DTOType): EntityType

    protected open fun errorDataResponse(e: Exception): ErrorResponse =
        ErrorResponse(e.message ?: e.toString(), 500)

    protected open fun errorDomainResponse(errorResponse: Response<DTOType>): ErrorResponse =
        ErrorResponse(errorResponse.errorBody().toString(), errorResponse.code())

    protected suspend fun loadData(request: suspend () -> Response<DTOType>) =
        try {
            request().apply {
                body()?.let {
                    successDataResponse(it)
                    domainResponse.onSuccess(successDomainResponse(it))
                } ?: domainResponse.onError(errorDomainResponse(this))
            }
        } catch (e: Exception) {
            domainResponse.onError(errorDataResponse(e))
        }
}
