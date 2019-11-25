package dev.sudnik.basecleanandroid.domain

abstract class BaseInteractor<DataType, EntityType> {

    lateinit var dataCallback: (DataState<DataType>) -> Unit

    protected abstract fun processSuccessState(clazz: DataType, data: EntityType): DataType
    protected abstract fun processErrorState(error: ErrorResponse): DataType

    fun call(responseDataType: DataType, request: (RepositoryResponse<EntityType>) -> Unit) =
        request(getCallback(responseDataType))

    private fun getCallback(clazz: DataType): RepositoryResponse<EntityType> =
        object : RepositoryResponse<EntityType> {
            override val onSuccess: (entity: EntityType) -> Unit =
                { dataCallback(DataState.OnSuccess(processSuccessState(clazz, it))) }

            override val onError: (error: ErrorResponse) -> Unit =
                { dataCallback(DataState.OnError(processErrorState(it))) }
        }
}
