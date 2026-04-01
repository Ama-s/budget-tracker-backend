package org.moi.budgettracker.controllers

import org.moi.budgettracker.dto.RecurringExpenseRequest
import org.moi.budgettracker.dto.RecurringExpenseResponse
import org.moi.budgettracker.entities.User
import org.moi.budgettracker.service.RecurringExpenseService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
// haven't implemented anything here
@RestController
@RequestMapping("/recurring")
class RecurringExpenseController(
    private val recurringExpenseService: RecurringExpenseService
) {
    private val defaultUser = User(id = 1, username = "public")

    @PostMapping
    fun createRecurringExpense(@RequestBody request: RecurringExpenseRequest): ResponseEntity<RecurringExpenseResponse> {
        val response = recurringExpenseService.createRecurringExpense(defaultUser, request)
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    @GetMapping
    fun getRecurringExpenses(): ResponseEntity<List<RecurringExpenseResponse>> {
        val expenses = recurringExpenseService.getUserRecurringExpenses(defaultUser)
        return ResponseEntity.ok(expenses)
    }

    @PutMapping("/{id}")
    fun updateRecurringExpense(
        @PathVariable id: Long,
        @RequestBody request: RecurringExpenseRequest
    ): ResponseEntity<RecurringExpenseResponse> {
        val response = recurringExpenseService.updateRecurringExpense(defaultUser, id, request)
        return ResponseEntity.ok(response)
    }

    @DeleteMapping("/{id}")
    fun deleteRecurringExpense(@PathVariable id: Long): ResponseEntity<Void> {
        recurringExpenseService.deleteRecurringExpense(defaultUser, id)
        return ResponseEntity.noContent().build()
    }

    @PatchMapping("/{id}/toggle")
    fun toggleRecurringExpense(@PathVariable id: Long): ResponseEntity<RecurringExpenseResponse> {
        val response = recurringExpenseService.toggleRecurringExpense(defaultUser, id)
        return ResponseEntity.ok(response)
    }
}