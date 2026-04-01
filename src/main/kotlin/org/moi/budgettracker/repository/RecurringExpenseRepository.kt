package org.moi.budgettracker.repository

import org.moi.budgettracker.entities.RecurringExpense
import org.moi.budgettracker.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface RecurringExpenseRepository : JpaRepository<RecurringExpense, Long> {
    fun findByUserAndIsActiveTrue(user: User): List<RecurringExpense>
    fun findByIsActiveTrueAndNextExecutionDateLessThanEqual(date: LocalDate): List<RecurringExpense>
}