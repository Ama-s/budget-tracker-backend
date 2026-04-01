package org.moi.budgettracker.controllers

import org.moi.budgettracker.dto.BudgetRequest
import org.moi.budgettracker.dto.BudgetResponse
import org.moi.budgettracker.entities.User
import org.moi.budgettracker.service.BudgetService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/budgets")
class BudgetController(
    private val budgetService: BudgetService
) {
    private val defaultUser = User(id = 1, username = "public")

    @PostMapping
    fun createBudget(@RequestBody request: BudgetRequest): ResponseEntity<BudgetResponse> {

        val response = budgetService.createOrUpdateBudget(defaultUser, request)
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    @GetMapping("/current")
    fun getCurrentBudget(): ResponseEntity<BudgetResponse> {
        val response = budgetService.getCurrentMonthBudget(defaultUser)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/remaining")
    fun getRemainingBalance(): ResponseEntity<Map<String, Any>> {
        val response = budgetService.getRemainingBalance(defaultUser)
        return ResponseEntity.ok(response)
    }

}