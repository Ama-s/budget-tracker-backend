package org.moi.budgettracker.mappers

import org.moi.budgettracker.dto.CategoryResponse
import org.moi.budgettracker.dto.ExpenseResponse
import org.moi.budgettracker.entities.Expense

fun Expense.toResponse(): ExpenseResponse {
    return ExpenseResponse(
        id = this.id!!,
        amount = this.amount,
        description = this.description,
        note = this.note,
        paymentMethod = this.paymentMethod,
        expenseDate = this.expenseDate,
        category = CategoryResponse(
            id = this.category.id!!,
            name = this.category.name,
            color = this.category.color,
            icon = this.category.icon
        ),
        isRecurring = this.isRecurring,
        createdAt = this.createdAt
    )
}