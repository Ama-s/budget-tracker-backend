package org.moi.budgettracker.repository

import org.moi.budgettracker.entities.Category
import org.moi.budgettracker.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository : JpaRepository<Category, Long> {
    fun findByUserOrUserIsNull(user: User): List<Category>

    @Query("SELECT c FROM Category c WHERE c.user.id = :userId OR c.isDefault = true")
    fun findAvailableCategories(userId: Long): List<Category>

    fun findByIsDefaultTrue(): List<Category>
}