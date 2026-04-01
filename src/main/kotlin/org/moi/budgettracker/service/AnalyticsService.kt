package org.moi.budgettracker.service

import org.moi.budgettracker.dto.AnalyticsSummaryResponse
import org.moi.budgettracker.entities.User
import org.moi.budgettracker.repository.BudgetRepository
import org.moi.budgettracker.repository.ExpenseRepository
import org.moi.budgettracker.mappers.toResponse  // Import the extension function
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

@Service
class AnalyticsService(
    private val expenseRepository: ExpenseRepository,
    private val budgetRepository: BudgetRepository
) {
    fun getMonthlySummary(user: User): AnalyticsSummaryResponse {
        val currentMonth = YearMonth.now()
        val monthYear = currentMonth.format(DateTimeFormatter.ofPattern("yyyy-MM"))

        val budget = budgetRepository.findByUserAndMonthYear(user, monthYear)
            .orElse(null)

        val expenses = expenseRepository.findByUserAndMonth(
            user,
            currentMonth.year,
            currentMonth.monthValue
        )

        val totalSpent = expenses.sumOf { it.amount }
        val daysElapsed = LocalDate.now().dayOfMonth
        val daysInMonth = currentMonth.lengthOfMonth()

        val averageDailySpend = if (daysElapsed > 0) {
            totalSpent.divide(BigDecimal(daysElapsed), 2, RoundingMode.HALF_UP)
        } else {
            BigDecimal.ZERO
        }

        val daysRemaining = daysInMonth - daysElapsed
        val projected = averageDailySpend * BigDecimal(daysRemaining)

        val miscellaneousRemaining = if (budget != null) {
            budget.miscellaneousSpend - totalSpent
        } else {
            BigDecimal.ZERO
        }

        return AnalyticsSummaryResponse(
            monthYear = monthYear,
            totalIncome = budget?.income ?: BigDecimal.ZERO,
            totalSpent = totalSpent,
            savingsAchieved = budget?.savingsAllocation ?: BigDecimal.ZERO,
            miscellaneousRemaining = miscellaneousRemaining,
            averageDailySpend = averageDailySpend,
            projectedMonthEndBalance = miscellaneousRemaining - projected,
            largestExpense = expenses.maxByOrNull { it.amount }?.toResponse()
        )
    }
}