package com.koosco.userservice.application.service

import com.koosco.common.core.exception.NotFoundException
import com.koosco.userservice.application.dto.UserDto
import com.koosco.userservice.application.repository.UserRepository
import com.koosco.userservice.common.UserErrorCode
import org.springframework.stereotype.Service

@Service
class UserQueryService(private val userRepository: UserRepository) {

    fun findById(userId: Long): UserDto {
        val user = userRepository.findActiveUserById(userId)
            ?: throw NotFoundException(UserErrorCode.USER_NOT_FOUND)

        return UserDto(
            id = user.id!!,
            email = user.email.value,
            name = user.name,
            phone = user.phone.value,
        )
    }
}
