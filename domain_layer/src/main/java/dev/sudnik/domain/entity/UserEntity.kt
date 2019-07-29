package dev.sudnik.domain.entity

import dev.sudnik.domain.model.User

class UserEntity(val users: ArrayList<User>) {
    fun getUser() = users[0]
    fun getSurname() = users[0].surname
}