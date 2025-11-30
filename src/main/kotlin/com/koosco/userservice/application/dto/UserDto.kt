package com.koosco.userservice.application.dto

import com.koosco.userservice.domain.enums.AuthProvider

data class CreateUserDto(
    val email: String,
    val password: String,
    val name: String,
    val phone: String?,
    val provider: AuthProvider,
)

data class UpdateUserDto(val name: String, val phone: String)

data class UserDto(val id: Long, val email: String, val name: String, val phone: String?)
