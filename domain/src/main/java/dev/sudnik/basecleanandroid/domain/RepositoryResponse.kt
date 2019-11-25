package dev.sudnik.basecleanandroid.domain

interface RepositoryResponse<EntityType> {
    val onSuccess: (entity: EntityType) -> Unit
    val onError: (error: ErrorResponse) -> Unit
}
