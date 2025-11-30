package com.koosco.userservice.application.service

import com.koosco.common.core.exception.ConflictException
import com.koosco.common.core.exception.NotFoundException
import com.koosco.userservice.application.dto.CreateUserDto
import com.koosco.userservice.application.dto.UpdateUserDto
import com.koosco.userservice.application.repository.UserRepository
import com.koosco.userservice.common.UserErrorCode
import com.koosco.userservice.domain.entity.User
import com.koosco.userservice.domain.vo.Email
import com.koosco.userservice.domain.vo.Phone
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(private val userRepository: UserRepository) {

    @Transactional
    fun registerUser(dto: CreateUserDto): User {
        try {
            val user = User.createUser(
                email = Email.of(dto.email),
                name = dto.name,
                phone = Phone.of(dto.phone),
                provider = dto.provider,
            )

            return userRepository.save(user)
        } catch (ex: DataIntegrityViolationException) {
            throw ConflictException(
                UserErrorCode.EMAIL_ALREADY_EXISTS,
                "이미 존재하는 이메일입니다.",
                ex,
            )
        }
    }

    @Transactional
    fun deleteById(userId: Long) {
        val user = userRepository.findActiveUserById(userId) ?: throw NotFoundException(
            UserErrorCode.USER_NOT_FOUND,
            "User with id $userId not found",
        )

        user.quit()
    }

    @Transactional
    fun update(userId: Long, dto: UpdateUserDto) {
        val user = userRepository.findActiveUserById(userId) ?: throw NotFoundException(
            UserErrorCode.USER_NOT_FOUND,
            "User with id $userId not found",
        )

        user.update(
            name = dto.name,
            phone = Phone.of(dto.phone),
        )
    }
}
