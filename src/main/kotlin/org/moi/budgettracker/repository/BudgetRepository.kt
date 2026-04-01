package org.moi.budgettracker.repository

import org.moi.budgettracker.entities.Budget
import org.moi.budgettracker.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface BudgetRepository : JpaRepository<Budget, Long> {
    fun findByUserAndMonthYear(user: User, monthYear: String): Optional<Budget>
    fun findByUserOrderByMonthYearDesc(user: User): List<Budget>
    fun existsByUserAndMonthYear(user: User, monthYear: String): Boolean
}