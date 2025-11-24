package com.koosco.userservice.application.client

interface AuthServiceClient {

    fun notifyUserCreated(userId: Long, password: String)
}
