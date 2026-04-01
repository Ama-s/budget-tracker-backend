package org.moi.budgettracker.dto

import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime

//data class UserResponse(
//    val id: Long,
//    val username: String,
//    val currency: String
//)

data class ExpenseResponse(
    val id: Long,
    val amount: BigDecimal,
    val description: String,
    val note: String?,
    val paymentMethod: String,
    val expenseDate: LocalDateTime,
    val category: CategoryResponse,
    val isRecurring: Boolean,
    val createdAt: LocalDateTime
)

data class CategoryResponse(
    val id: Long,
    val name: String,
    val color: String,
    val icon: String?
)

data class BudgetResponse(
    val id: Long,
    val monthYear: String,
    val income: BigDecimal,
    val fixedExpenses: BigDecimal,
    val savingsAllocation: BigDecimal,
    val miscellaneousSpend: BigDecimal,
    val notes: String?
)

data class AnalyticsSummaryResponse(
    val monthYear: String,
    val totalIncome: BigDecimal,
    val totalSpent: BigDecimal,
    val savingsAchieved: BigDecimal,
    val miscellaneousRemaining: BigDecimal,
    val averageDailySpend: BigDecimal,
    val projectedMonthEndBalance: BigDecimal,
    val largestExpense: ExpenseResponse?
)

data class RecurringExpenseResponse(
    val id: Long,
    val amount: BigDecimal,
    val description: String,
    val paymentMethod: String,
    val frequency: String,
    val startDate: LocalDate,
    val endDate: LocalDate?,
    val nextExecutionDate: LocalDate,
    val isActive: Boolean,
    val category: CategoryResponse
)