package org.moi.budgettracker.service

import org.moi.budgettracker.dto.ExpenseRequest
import org.moi.budgettracker.dto.ExpenseResponse
import org.moi.budgettracker.mappers.toResponse
import org.moi.budgettracker.exception.NotFoundException
import org.moi.budgettracker.entities.Expense
import org.moi.budgettracker.entities.User
import org.moi.budgettracker.repository.CategoryRepository
import org.moi.budgettracker.repository.ExpenseRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class ExpenseService(
    private val expenseRepository: ExpenseRepository,
    private val categoryRepository: CategoryRepository
) {
    @Transactional
    fun createExpense(user: User, request: ExpenseRequest): ExpenseResponse {
        val category = categoryRepository.findById(request.categoryId)
            .orElseThrow { NotFoundException("Category not found") }

        val expense = Expense(
            user = user,
            category = category,
            amount = request.amount,
            description = request.description,
            note = request.note,
            paymentMethod = request.paymentMethod,
            expenseDate = request.expenseDate ?: LocalDateTime.now()
        )

        return expenseRepository.save(expense).toResponse()
    }

    fun getExpenses(user: User, page: Int, size: Int): Page<ExpenseResponse> {
        val pageable = PageRequest.of(page, size)
        return expenseRepository.findByUserOrderByExpenseDateDesc(user, pageable)
            .map { it.toResponse() }
    }

    fun searchExpenses(user: User, keyword: String, page: Int, size: Int): Page<ExpenseResponse> {
        val pageable = PageRequest.of(page, size)
        return expenseRepository.searchByKeyword(user, keyword, pageable)
            .map { it.toResponse() }
    }

    @Transactional
    fun deleteExpense(user: User, expenseId: Long) {
        val expense = expenseRepository.findById(expenseId)
            .orElseThrow { NotFoundException("Expense not found") }

        if (expense.user.id != user.id) {
            throw NotFoundException("Expense not found")
        }

        expenseRepository.delete(expense)
    }

    fun getExpenseById(user: User, id: Long): ExpenseResponse? {
        val budget = expenseRepository.findExpenseById(user, id)
            .orElseThrow { NotFoundException("Expense not found") }
        return budget.toResponse()
    }

    fun updateExpense(user: User, id: Long, request: ExpenseRequest): ExpenseResponse {
        val existingExpense = expenseRepository.findExpenseById(user, id)
        val category = categoryRepository.findById(request.categoryId)
            .orElseThrow { NotFoundException("Category not found") }

        val expense = try {
            existingExpense.get().copy(
                category = category,
                amount = request.amount,
                description = request.description,
                note = request.note,
                paymentMethod = request.paymentMethod,
                expenseDate = LocalDateTime.now()
            )
        } catch (ex: Exception) {
            throw NotFoundException("Expense not found")
        }
        return expenseRepository.save(expense).toResponse()
    }
}