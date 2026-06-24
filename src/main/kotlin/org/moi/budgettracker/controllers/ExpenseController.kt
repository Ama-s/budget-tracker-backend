package org.moi.budgettracker.controllers

import org.moi.budgettracker.dto.ExpenseRequest
import org.moi.budgettracker.dto.ExpenseResponse
import org.moi.budgettracker.repository.UserRepository
import org.moi.budgettracker.security.CustomUserDetails
import org.moi.budgettracker.service.ExpenseService
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/expenses")
class ExpenseController(
    private val expenseService: ExpenseService,
    private val userRepository: UserRepository
) {

    @PostMapping
    fun createExpense(
        @AuthenticationPrincipal userDetails: CustomUserDetails,
        @RequestBody request: ExpenseRequest
    ): ResponseEntity<ExpenseResponse> {
        val user = userDetails.user
        val response = expenseService.createExpense(user, request)
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    @GetMapping
    fun getExpenses(
        @AuthenticationPrincipal userDetails: CustomUserDetails,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "50") size: Int
    ): ResponseEntity<Page<ExpenseResponse>> {
        val user = userDetails.user
        val response = expenseService.getExpenses(user, page, size)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/search") // hasn't been implemented yet
    fun searchExpenses(
        @AuthenticationPrincipal userDetails: CustomUserDetails,
        @RequestParam keyword: String,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "50") size: Int
    ): ResponseEntity<Page<ExpenseResponse>> {
        val user = userDetails.user
        val response = expenseService.searchExpenses(user, keyword, page, size)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/{id}")  // might not be useful in the frontend
    fun getExpense(
        @AuthenticationPrincipal userDetails: CustomUserDetails,
        @PathVariable id: Long
    ): ResponseEntity<ExpenseResponse> {
        val user = userDetails.user
        val response = expenseService.getExpenseById(user, id)
        return ResponseEntity.ok(response)
    }

    @PutMapping("/{id}") // has not been implemented yet
    fun updateExpense(
        @AuthenticationPrincipal userDetails: CustomUserDetails,
        @PathVariable id: Long,
        @RequestBody request: ExpenseRequest
    ): ResponseEntity<ExpenseResponse> {
        val user = userDetails.user
        val response = expenseService.updateExpense(user, id, request)
        return ResponseEntity.ok(response)
    }

    @DeleteMapping("/{id}") // hasn't been implemented yet
    fun deleteExpense(
        @AuthenticationPrincipal userDetails: CustomUserDetails,
        @PathVariable id: Long
    ): ResponseEntity<Void> {
        val user = userDetails.user
        expenseService.deleteExpense(user, id)
        return ResponseEntity.noContent().build()
    }
}