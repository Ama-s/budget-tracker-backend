package org.moi.budgettracker.repository

import org.moi.budgettracker.dto.ExpenseResponse
import org.moi.budgettracker.entities.Expense
import org.moi.budgettracker.entities.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

@Repository
interface ExpenseRepository : JpaRepository<Expense, Long> {
    fun findByUserOrderByExpenseDateDesc(user: User, pageable: Pageable): Page<Expense>

    fun findExpenseById(user: User, id: Long): Optional<Expense>

    fun findByUserAndExpenseDateBetween(
        user: User,
        startDate: LocalDateTime,
        endDate: LocalDateTime,
        pageable: Pageable
    ): Page<Expense>

    @Query("SELECT e FROM Expense e WHERE e.user = :user AND " +
            "(LOWER(e.description) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(e.note) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    fun searchByKeyword(
        @Param("user") user: User,
        @Param("keyword") keyword: String,
        pageable: Pageable
    ): Page<Expense>

    @Query("SELECT SUM(e.amount) FROM Expense e WHERE e.user = :user AND " +
            "YEAR(e.expenseDate) = :year AND MONTH(e.expenseDate) = :month")
    fun getTotalSpentInMonth(
        @Param("user") user: User,
        @Param("year") year: Int,
        @Param("month") month: Int
    ): BigDecimal?

    @Query("SELECT e FROM Expense e WHERE e.user = :user AND " +
            "YEAR(e.expenseDate) = :year AND MONTH(e.expenseDate) = :month " +
            "ORDER BY e.expenseDate DESC")
    fun findByUserAndMonth(
        @Param("user") user: User,
        @Param("year") year: Int,
        @Param("month") month: Int
    ): List<Expense>
}