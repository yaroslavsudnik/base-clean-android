package dev.sudnik.data.user

import dev.sudnik.domain.model.User

class UserMapper {
    private fun transformUser(dto: UserDTO) = User(dto.first_name, dto.second_name, dto.user_age)

    fun transformUserList(data: ArrayList<UserDTO>): ArrayList<User> {
        val users: ArrayList<User> = ArrayList()
        for (user in data) {
            users.add(transformUser(user))
        }
        return users
    }
}
