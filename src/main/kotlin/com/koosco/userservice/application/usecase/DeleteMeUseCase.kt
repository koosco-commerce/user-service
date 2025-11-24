package com.koosco.userservice.application.usecase

import com.koosco.common.core.annotation.UseCase
import com.koosco.userservice.application.service.UserService

@UseCase
class DeleteMeUseCase(val userService: UserService) {

    fun deleteMe(userId: Long) {
        userService.deleteById(userId)
    }
}
