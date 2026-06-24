package org.moi.budgettracker.controllers

import org.moi.budgettracker.dto.BudgetRequest
import org.moi.budgettracker.dto.BudgetResponse
import org.moi.budgettracker.repository.UserRepository
import org.moi.budgettracker.security.CustomUserDetails
import org.moi.budgettracker.service.BudgetService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/budgets")
class BudgetController(
    private val budgetService: BudgetService,
    private val userRepository: UserRepository
) {
    @PostMapping
    fun createBudget(
        @AuthenticationPrincipal userDetails: CustomUserDetails,
        @RequestBody request: BudgetRequest
    ): ResponseEntity<BudgetResponse> {
        val user = userDetails.user
        val response = budgetService.createOrUpdateBudget(user, request)
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    @GetMapping("/current")
    fun getCurrentBudget(
        @AuthenticationPrincipal userDetails: CustomUserDetails
    ): ResponseEntity<BudgetResponse> {
        val user = userDetails.user
        val response = budgetService.getCurrentMonthBudget(user)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/remaining")
    fun getRemainingBalance(
        @AuthenticationPrincipal userDetails: CustomUserDetails
    ): ResponseEntity<Map<String, Any>> {
        val user = userDetails.user
        val response = budgetService.getRemainingBalance(user)
        return ResponseEntity.ok(response)
    }

}