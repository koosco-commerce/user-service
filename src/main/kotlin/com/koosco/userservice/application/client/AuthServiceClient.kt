package com.koosco.userservice.application.client

import com.koosco.userservice.domain.vo.AuthProvider

interface AuthServiceClient {

    fun notifyUserCreated(userId: Long, password: String, email: String, provider: AuthProvider?)
}
