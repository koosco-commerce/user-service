package com.koosco.userservice.api.controller

import com.koosco.common.core.response.ApiResponse
import com.koosco.commonsecurity.resolver.AuthId
import com.koosco.userservice.api.RegisterRequest
import com.koosco.userservice.api.UpdateRequest
import com.koosco.userservice.application.command.GetUserDetailCommand
import com.koosco.userservice.application.usecase.DeleteMeUseCase
import com.koosco.userservice.application.usecase.GetUserDetailUseCase
import com.koosco.userservice.application.usecase.RegisterUseCase
import com.koosco.userservice.application.usecase.UpdateMeUseCase
import io.swagger.v3.oas.annotations.Operation
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController(
    private val registerUseCase: RegisterUseCase,
    private val getUserDetailUseCase: GetUserDetailUseCase,
    private val deleteMeUseCase: DeleteMeUseCase,
    private val updateMeUseCase: UpdateMeUseCase,
) {

    @Operation(
        summary = "로컬 회원가입",
        description = "로컬 회원가입을 진행합니다.",
    )
    @PostMapping
    fun registerUser(@Valid @RequestBody request: RegisterRequest): ApiResponse<Any> {
        registerUseCase.execute(request.toCommand())

        return ApiResponse.success()
    }

    @Operation(
        summary = "사용자 조회",
        description = "사용자 정보를 조회합니다.",
    )
    @GetMapping("/{userId}")
    fun getUser(@PathVariable userId: Long): ApiResponse<Any> {
        val response = getUserDetailUseCase.execute(GetUserDetailCommand(userId))

        return ApiResponse.success(response)
    }

    @Operation(
        summary = "본인 삭제",
        description = "본인 계정을 삭제합니다.",
    )
    @DeleteMapping("/me")
    fun deleteMe(@AuthId userId: Long): ApiResponse<Any> {
        deleteMeUseCase.execute(userId)

        return ApiResponse.success()
    }

    @Operation(
        summary = "사용자 수정",
        description = "사용자 정보를 수정합니다.",
    )
    @PatchMapping("/me")
    fun updateMe(@AuthId userId: Long, @RequestBody request: UpdateRequest): ApiResponse<Any> {
        updateMeUseCase.execute(request.toCommand(userId))

        return ApiResponse.success()
    }
}
