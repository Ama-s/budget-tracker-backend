package org.moi.budgettracker.mappers

import org.moi.budgettracker.dto.BudgetResponse
import org.moi.budgettracker.entities.Budget

fun Budget.toResponse() = BudgetResponse(
    id = id!!,
    monthYear = monthYear,
    income = income,
    fixedExpenses = fixedExpenses,
    savingsAllocation = savingsAllocation,
    miscellaneousSpend = miscellaneousSpend,
    notes = notes
)