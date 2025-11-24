package com.koosco.userservice.application.usecase

import com.koosco.common.core.annotation.UseCase
import com.koosco.userservice.application.dto.UpdateUserDto
import com.koosco.userservice.application.service.UserService

@UseCase
class UpdateMeUseCase(val userService: UserService) {

    fun updateMe(userId: Long, dto: UpdateUserDto) {
        userService.update(userId, dto)
    }
}
