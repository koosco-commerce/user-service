package com.koosco.userservice.application.usecase

import com.koosco.common.core.annotation.UseCase
import com.koosco.common.core.error.CommonErrorCode
import com.koosco.common.core.exception.ExternalServiceException
import com.koosco.userservice.application.client.AuthServiceClient
import com.koosco.userservice.application.dto.CreateUserDto
import com.koosco.userservice.application.service.UserService
import org.slf4j.LoggerFactory

@UseCase
class RegisterUseCase(private val userService: UserService, private val authServiceClient: AuthServiceClient) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    fun register(dto: CreateUserDto) {
        val user = userService.registerUser(dto)

        try {
            authServiceClient.notifyUserCreated(user.id!!, dto.password)
        } catch (ex: Exception) {
            runCatching { userService.rollback(user) }
                .onFailure {
                    // TODO : 실패 처리
                    logger.error("rollback error", it)
                }
            throw ExternalServiceException(
                CommonErrorCode.EXTERNAL_SERVICE_ERROR,
                "Auth service 호출 실패로 회원가입 취소",
                ex,
            )
        }
    }
}
