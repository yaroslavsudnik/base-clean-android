package dev.sudnik.basecleanandroid.domain

abstract class BaseInteractor<DataType, Entity> {

    lateinit var dataCallback: (DataState<DataType>) -> Unit

    abstract fun processSuccessState(clazz: DataType, data: Entity): DataType
    abstract fun processErrorState(error: ErrorResponse): DataType

    fun call(responseDataType: DataType, request: (OnCallback<Entity>) -> Unit) =
            request(getCallback(responseDataType))

    private fun instanceSuccessState(clazz: DataType, data: Entity) =
            DataState.OnSuccess(processSuccessState(clazz, data))

    private fun instanceErrorState(error: ErrorResponse): DataState.OnError<DataType> =
            DataState.OnError(processErrorState(error))

    private fun processServerError(error: ErrorResponse): DataState.ServerError<DataType> =
            when (error.errorCode) {
                401 -> DataState.ServerError.AuthError()
                404 -> DataState.ServerError.NotFound()
                406 -> DataState.ServerError.StrangeError()
                else -> DataState.ServerError.UnknownError()
            }

    private fun getCallback(clazz: DataType): OnCallback<Entity> = object : OnCallback<Entity> {
        override fun onSuccess(entity: Entity) = dataCallback(instanceSuccessState(clazz, entity))
        override fun onError(error: ErrorResponse) = when (error.errorCode) {
            401, 404, 500 -> dataCallback(processServerError(error))
            else -> dataCallback(instanceErrorState(error))
        }
    }
}
