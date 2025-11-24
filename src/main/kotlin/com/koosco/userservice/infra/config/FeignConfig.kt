package com.koosco.userservice.infra.config

import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Configuration

@Configuration
@EnableFeignClients(basePackages = ["com.koosco.userservice.infra.client"])
class FeignConfig {
}
