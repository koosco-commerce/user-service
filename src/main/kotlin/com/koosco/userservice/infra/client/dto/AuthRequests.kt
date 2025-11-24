package com.koosco.userservice.infra.client.dto

data class CreateUserRequest(
    val userId: Long,
    val password: String
)

data class DeleteUserRequest(
    val userId: Long
)
