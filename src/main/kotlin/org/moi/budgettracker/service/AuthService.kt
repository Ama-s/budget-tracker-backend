package org.moi.budgettracker.service

import org.moi.budgettracker.dto.AuthResponse
import org.moi.budgettracker.dto.RegisterRequest
import org.moi.budgettracker.entities.User
import org.moi.budgettracker.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {
    fun register(request: RegisterRequest): AuthResponse {
        if (userRepository.existsByUsername(request.username)) {
            throw IllegalArgumentException("Username already taken")
        }
        val securePassword = passwordEncoder.encode(request.password)
        val newUser = User(username = request.username, passwordHash = securePassword!!, email = request.email, fullName = request.fullName)
        userRepository.save(newUser)

        return AuthResponse(
            username = request.username,
            email = request.email,
            fullName = newUser.fullName
        )
    }
}