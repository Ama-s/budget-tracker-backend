package org.moi.budgettracker.controllers

import org.moi.budgettracker.dto.RecurringExpenseRequest
import org.moi.budgettracker.dto.RecurringExpenseResponse
import org.moi.budgettracker.repository.UserRepository
import org.moi.budgettracker.security.CustomUserDetails
import org.moi.budgettracker.service.RecurringExpenseService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

// haven't implemented anything here yet
@RestController
@RequestMapping("/recurring")
class RecurringExpenseController(
    private val recurringExpenseService: RecurringExpenseService,
    private val userRepository: UserRepository
) {

    @PostMapping
    fun createRecurringExpense(
        @AuthenticationPrincipal userDetails: CustomUserDetails,
        @RequestBody request: RecurringExpenseRequest
    ): ResponseEntity<RecurringExpenseResponse> {
        val user = userDetails.user
        val response = recurringExpenseService.createRecurringExpense(user, request)
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    @GetMapping
    fun getRecurringExpenses(
        @AuthenticationPrincipal userDetails: CustomUserDetails
    ): ResponseEntity<List<RecurringExpenseResponse>> {
        val user = userDetails.user
        val expenses = recurringExpenseService.getUserRecurringExpenses(user)
        return ResponseEntity.ok(expenses)
    }

    @PutMapping("/{id}")
    fun updateRecurringExpense(
        @AuthenticationPrincipal userDetails: CustomUserDetails,
        @PathVariable id: Long,
        @RequestBody request: RecurringExpenseRequest
    ): ResponseEntity<RecurringExpenseResponse> {
        val user = userDetails.user
        val response = recurringExpenseService.updateRecurringExpense(user, id, request)
        return ResponseEntity.ok(response)
    }

    @DeleteMapping("/{id}")
    fun deleteRecurringExpense(
        @AuthenticationPrincipal userDetails: CustomUserDetails,
        @PathVariable id: Long
    ): ResponseEntity<Void> {
        val user = userDetails.user
        recurringExpenseService.deleteRecurringExpense(user, id)
        return ResponseEntity.noContent().build()
    }

    @PatchMapping("/{id}/toggle")
    fun toggleRecurringExpense(
        @AuthenticationPrincipal userDetails: CustomUserDetails,
        @PathVariable id: Long
    ): ResponseEntity<RecurringExpenseResponse> {
        val user = userDetails.user
        val response = recurringExpenseService.toggleRecurringExpense(user, id)
        return ResponseEntity.ok(response)
    }
}