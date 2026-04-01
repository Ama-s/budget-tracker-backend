package org.moi.budgettracker.entities

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "expenses")
data class Expense(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    val category: Category,

    @Column(nullable = false, precision = 15, scale = 2)
    val amount: BigDecimal,

    @Column(nullable = false, length = 500)
    val description: String,

    @Column(columnDefinition = "TEXT")
    val note: String? = null,

    // might change it to an enum in the future
    @Column(name = "payment_method", nullable = false, length = 50)
    val paymentMethod: String,

    @Column(name = "expense_date", nullable = false)
    val expenseDate: LocalDateTime = LocalDateTime.now(),

    @Column(name = "is_recurring")
    val isRecurring: Boolean = false,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recurring_id")
    val recurringExpense: RecurringExpense? = null,

    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now()
)