package com.koosco.userservice.infra.client

import com.koosco.userservice.infra.client.dto.CreateUserRequest
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping

@FeignClient(name = "auth-service", path = "/api/auth")
interface AuthClient {

    @PostMapping
    fun createUser(request: CreateUserRequest)
}