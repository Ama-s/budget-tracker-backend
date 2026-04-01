package org.moi.budgettracker.entities

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(unique = true, nullable = false)
    var username: String? = null,

//    @Column(name = "password_hash", nullable = false)
//    var passwordHash: String? = null,
//
//    @Column(name = "full_name", nullable = false)
//    var fullName: String? = null,
//
//    @Column(length = 10)
//    var currency: String = "NGN",
//
//    @Column(name = "created_at")
//    val createdAt: LocalDateTime = LocalDateTime.now(),
//
//    @Column(name = "updated_at")
//    var updatedAt: LocalDateTime = LocalDateTime.now()
)