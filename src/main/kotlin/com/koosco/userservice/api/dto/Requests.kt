package com.koosco.userservice.api.dto

import com.koosco.userservice.application.dto.CreateUserDto
import com.koosco.userservice.application.dto.UpdateUserDto
import com.koosco.userservice.domain.vo.AuthProvider
import jakarta.validation.constraints.NotBlank

data class RegisterRequest(
    @NotBlank(message = "Email must not be blank")
    val email: String,

    @NotBlank(message = "Password must not be blank")
    val password: String,

    @NotBlank(message = "Name must not be blank")
    val name: String,

    val phone: String? = null,

    val provider: AuthProvider = AuthProvider.LOCAL,
)

fun RegisterRequest.toDto(): CreateUserDto = CreateUserDto(
    email = this.email,
    password = this.password,
    name = this.name,
    phone = this.phone,
    provider = this.provider,
)

data class UpdateRequest(
    @NotBlank(message = "Name must not be blank")
    val name: String,

    @NotBlank(message = "Phone must not be blank")
    val phone: String,
)

fun UpdateRequest.toDto(): UpdateUserDto = UpdateUserDto(
    name = this.name,
    phone = this.phone,
)
