package org.moi.budgettracker.controllers

import org.moi.budgettracker.dto.CategoryResponse
import org.moi.budgettracker.repository.CategoryRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/categories")
class CategoryController(
    private val categoryRepository: CategoryRepository
) {
    @GetMapping
    fun getCategories(): ResponseEntity<List<CategoryResponse>> {
        val categories = categoryRepository.findByIsDefaultTrue()
        val response = categories.map {
            CategoryResponse(
                id = it.id!!,
                name = it.name,
                color = it.color,
                icon = it.icon
            )
        }
        return ResponseEntity.ok(response)
    }
}