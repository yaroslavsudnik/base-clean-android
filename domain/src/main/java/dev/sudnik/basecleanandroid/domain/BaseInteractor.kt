package dev.sudnik.basecleanandroid.domain

abstract class BaseInteractor<DataType, Entity> {

    abstract fun processSuccessState(
        clazz: DataType,
        data: Entity
    ): DataType

    abstract fun processErrorState(error: ErrorResponse): DataType

    private fun instanceSuccessState(
        clazz: DataType,
        data: Entity
    ) = DataState.OnSuccess(processSuccessState(clazz, data))

    private fun instanceErrorState(error: ErrorResponse): DataState.OnError<DataType> =
        DataState.OnError(processErrorState(error))

    private fun processServerError(error: ErrorResponse): DataState.ServerError<DataType> =
        when (error.errorCode) {
            401 -> DataState.ServerError.AuthError()
            404 -> DataState.ServerError.NotFound()
            406 -> DataState.ServerError.StrangeError()
            else -> DataState.ServerError.UnknownError()
        }

    fun getCallback(
        callback: (DataState<DataType>) -> Unit,
        clazz: DataType
    ): OnCallback<Entity> = object : OnCallback<Entity> {
        override fun onSuccess(entity: Entity) {
            callback(instanceSuccessState(clazz, entity))
        }

        override fun onError(error: ErrorResponse) {
            when (error.errorCode) {
                401, 404, 500 -> callback(processServerError(error))
                else -> callback(instanceErrorState(error))
            }
        }
    }
}
