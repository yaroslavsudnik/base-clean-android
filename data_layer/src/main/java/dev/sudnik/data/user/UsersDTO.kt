package dev.sudnik.data.user

data class UsersDTO(val userDTOList: ArrayList<UserDTO>)

data class UserDTO(val first_name: String, val second_name: String, val user_age: Int)
