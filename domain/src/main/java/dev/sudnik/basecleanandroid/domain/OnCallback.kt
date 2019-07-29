package dev.sudnik.basecleanandroid.domain

interface OnCallback<Entity> {
    fun onSuccess(entity: Entity)
    fun onError(error: ErrorResponse)
}