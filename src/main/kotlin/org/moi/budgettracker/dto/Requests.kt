package org.moi.budgettracker.dto

import java.math.BigDecimal
import java.time.LocalDateTime
//
//data class RegisterRequest(
//    val username: String,
//    val password: String,
//    val currency: String = "NGN"
//)
//
//data class LoginRequest(
//    val username: String,
//    val password: String
//)

data class BudgetRequest(
    val monthYear: String,
    val income: BigDecimal,
    val fixedExpenses: BigDecimal = BigDecimal.ZERO,
    val savingsAllocation: BigDecimal = BigDecimal.ZERO,
    val notes: String? = null
)

data class ExpenseRequest(
    val categoryId: Long,
    val amount: BigDecimal,
    val description: String,
    val note: String? = null,
    val paymentMethod: String,
    val expenseDate: LocalDateTime? = null
)

data class CategoryRequest(
    val name: String,
    val color: String = "#FF8A65",
    val icon: String? = null
)

data class RecurringExpenseRequest(
    val categoryId: Long,
    val amount: BigDecimal,
    val description: String,
    val paymentMethod: String,
    val frequency: String,
    val startDate: String
)