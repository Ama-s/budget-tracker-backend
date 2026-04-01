package org.moi.budgettracker.entities

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "categories")
data class Category(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User? = null,

    @Column(nullable = false, length = 100)
    val name: String,

    @Column(length = 7)
    val color: String = "#FF8A65",

    @Column(length = 50)
    val icon: String? = null,

    @Column(name = "is_default")
    val isDefault: Boolean = false,

    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now()
)