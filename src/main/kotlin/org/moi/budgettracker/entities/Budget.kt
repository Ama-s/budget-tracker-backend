package org.moi.budgettracker.entities

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "budgets")
data class Budget(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @Column(name = "month_year", nullable = false, length = 7)
    val monthYear: String, // Format: 2024-02

    @Column(nullable = false, precision = 15, scale = 2)
    val income: BigDecimal,

    @Column(name = "fixed_expenses", precision = 15, scale = 2)
    val fixedExpenses: BigDecimal = BigDecimal.ZERO,

    @Column(name = "savings_allocation", precision = 15, scale = 2)
    val savingsAllocation: BigDecimal = BigDecimal.ZERO,

    @Column(columnDefinition = "TEXT")
    val notes: String? = null,

    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now()
) {
    val miscellaneousSpend: BigDecimal
        get() = income - fixedExpenses - savingsAllocation
}