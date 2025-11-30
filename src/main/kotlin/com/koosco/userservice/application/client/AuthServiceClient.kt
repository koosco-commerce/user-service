package com.koosco.userservice.application.client

import com.koosco.userservice.domain.vo.AuthProvider
import com.koosco.userservice.domain.vo.UserRole

interface AuthServiceClient {

    fun notifyUserCreated(userId: Long, password: String, email: String, provider: AuthProvider?, role: UserRole)
}
