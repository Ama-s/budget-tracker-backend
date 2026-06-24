package org.moi.budgettracker.controllers

import org.moi.budgettracker.dto.AuthResponse
import org.moi.budgettracker.dto.RegisterRequest
import org.moi.budgettracker.service.AuthService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthController(private val authService: AuthService) {

    @PostMapping("/register")
    fun register(@RequestBody request: RegisterRequest): ResponseEntity<AuthResponse> {
        val response = authService.register(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }
}

@GetMapping("/me")
fun me(authentication: Authentication?): ResponseEntity<Any> {
    return if (authentication != null && authentication.isAuthenticated
        && authentication.principal != "anonymousUser") {
        ResponseEntity.ok(mapOf("username" to authentication.name))
    } else {
        ResponseEntity.status(401).build()
    }
}