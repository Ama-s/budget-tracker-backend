package org.moi.budgettracker.mappers

import org.moi.budgettracker.dto.CategoryResponse
import org.moi.budgettracker.dto.RecurringExpenseResponse
import org.moi.budgettracker.entities.RecurringExpense

fun RecurringExpense.toResponse(): RecurringExpenseResponse {
    return RecurringExpenseResponse(
        id = this.id!!,
        amount = this.amount,
        description = this.description,
        paymentMethod = this.paymentMethod,
        frequency = this.frequency,
        startDate = this.startDate,
        endDate = this.endDate,
        nextExecutionDate = this.nextExecutionDate,
        isActive = this.isActive,
        category = CategoryResponse(
            id = this.category.id!!,
            name = this.category.name,
            color = this.category.color,
            icon = this.category.icon
        )
    )
}