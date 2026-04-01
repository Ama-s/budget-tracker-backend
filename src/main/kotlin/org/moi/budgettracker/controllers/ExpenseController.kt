package org.moi.budgettracker.controllers

import org.moi.budgettracker.dto.ExpenseRequest
import org.moi.budgettracker.dto.ExpenseResponse
import org.moi.budgettracker.entities.User
import org.moi.budgettracker.service.ExpenseService
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/expenses")
class ExpenseController(
    private val expenseService: ExpenseService
) {
    private val defaultUser = User(id = 1, username = "public")

    @PostMapping
    fun createExpense(@RequestBody request: ExpenseRequest): ResponseEntity<ExpenseResponse> {
        val response = expenseService.createExpense(defaultUser, request)
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    @GetMapping
    fun getExpenses(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "50") size: Int
    ): ResponseEntity<Page<ExpenseResponse>> {
        val response = expenseService.getExpenses(defaultUser, page, size)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/search") // hasn't been implemented yet
    fun searchExpenses(
        @RequestParam keyword: String,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "50") size: Int
    ): ResponseEntity<Page<ExpenseResponse>> {
        val response = expenseService.searchExpenses(defaultUser, keyword, page, size)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/{id}")  // might not be useful in the frontend
    fun getExpense(@PathVariable id: Long): ResponseEntity<ExpenseResponse> {
        val response = expenseService.getExpenseById(defaultUser, id)
        return ResponseEntity.ok(response)
    }

    @PutMapping("/{id}") // has not been implemented yet
    fun updateExpense(
        @PathVariable id: Long,
        @RequestBody request: ExpenseRequest
    ): ResponseEntity<ExpenseResponse> {
        val response = expenseService.updateExpense(defaultUser, id, request)
        return ResponseEntity.ok(response)
    }

    @DeleteMapping("/{id}") // hasn't been implemented yet
    fun deleteExpense(@PathVariable id: Long): ResponseEntity<Void> {
        expenseService.deleteExpense(defaultUser, id)
        return ResponseEntity.noContent().build()
    }
}