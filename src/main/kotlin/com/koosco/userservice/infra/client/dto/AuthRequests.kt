package com.koosco.userservice.infra.client.dto

import com.koosco.userservice.domain.vo.AuthProvider

data class CreateUserRequest(val userId: Long, val email: String, val password: String, val provider: AuthProvider?)

data class DeleteUserRequest(val userId: Long)
