package org.moi.budgettracker.service

import org.moi.budgettracker.dto.BudgetRequest
import org.moi.budgettracker.dto.BudgetResponse
import org.moi.budgettracker.mappers.toResponse
import org.moi.budgettracker.exception.NotFoundException
import org.moi.budgettracker.entities.Budget
import org.moi.budgettracker.entities.User
import org.moi.budgettracker.repository.BudgetRepository
import org.moi.budgettracker.repository.ExpenseRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.time.YearMonth
import java.time.format.DateTimeFormatter

@Service
class BudgetService(
    private val budgetRepository: BudgetRepository,
    private val expenseRepository: ExpenseRepository
) {
    @Transactional
    fun createOrUpdateBudget(user: User, request: BudgetRequest): BudgetResponse {
        val existingBudget = budgetRepository.findByUserAndMonthYear(user, request.monthYear)

        val budget = if (existingBudget.isPresent) {
            existingBudget.get().copy(
                income = request.income,
                fixedExpenses = request.fixedExpenses,
                savingsAllocation = request.savingsAllocation,
                notes = request.notes
            )
        } else {
            Budget(
                user = user,
                monthYear = request.monthYear,
                income = request.income,
                fixedExpenses = request.fixedExpenses,
                savingsAllocation = request.savingsAllocation,
                notes = request.notes
            )
        }

        return budgetRepository.save(budget).toResponse()
    }

    fun getCurrentMonthBudget(user: User): BudgetResponse {
        val currentMonth = YearMonth.now().format(DateTimeFormatter.ofPattern("yyyy-MM"))
        return budgetRepository.findByUserAndMonthYear(user, currentMonth)
            .map { it.toResponse() }
            .orElseThrow { NotFoundException("No budget set for current month") }
    }

    fun getRemainingBalance(user: User): Map<String, Any> {
        val currentMonth = YearMonth.now()
        val monthYear = currentMonth.format(DateTimeFormatter.ofPattern("yyyy-MM"))

        val budget = budgetRepository.findByUserAndMonthYear(user, monthYear)
            .orElseThrow { NotFoundException("No budget set for current month") }

        val totalSpent = expenseRepository.getTotalSpentInMonth(
            user,
            currentMonth.year,
            currentMonth.monthValue
        ) ?: BigDecimal.ZERO

        val remaining = budget.miscellaneousSpend - totalSpent
        val percentage = if (budget.miscellaneousSpend > BigDecimal.ZERO) {
            (remaining.divide(budget.miscellaneousSpend, 2, BigDecimal.ROUND_HALF_UP) * BigDecimal(100))
        } else {
            BigDecimal.ZERO
        }

        val status = when {
            percentage >= BigDecimal(50) -> "safe"
            percentage >= BigDecimal(25) -> "caution"
            else -> "danger"
        }

        return mapOf(
            "miscellaneousSpend" to budget.miscellaneousSpend,
            "totalSpent" to totalSpent,
            "remaining" to remaining,
            "percentage" to percentage,
            "status" to status
        )
    }
}