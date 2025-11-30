package com.koosco.userservice.infra.client

import com.koosco.common.core.error.CommonErrorCode
import com.koosco.common.core.exception.ExternalServiceException
import com.koosco.userservice.application.client.AuthServiceClient
import com.koosco.userservice.domain.vo.AuthProvider
import com.koosco.userservice.domain.vo.UserRole
import com.koosco.userservice.infra.client.dto.CreateUserRequest
import feign.FeignException
import org.springframework.stereotype.Component

@Component
class AuthClientAdapter(private val authClient: AuthClient) : AuthServiceClient {

    override fun notifyUserCreated(
        userId: Long,
        password: String,
        email: String,
        provider: AuthProvider?,
        role: UserRole,
    ) {
        try {
            authClient.createUser(
                CreateUserRequest(
                    userId = userId,
                    email = email,
                    password = password,
                    provider = provider,
                    role = role,
                ),
            )
        } catch (e: FeignException) {
            throw ExternalServiceException(
                CommonErrorCode.EXTERNAL_SERVICE_ERROR,
                "Auth service 호출 실패: ${e.message}",
            )
        }
    }
}
