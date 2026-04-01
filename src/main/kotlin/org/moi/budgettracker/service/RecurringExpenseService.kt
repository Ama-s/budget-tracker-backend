package org.moi.budgettracker.service

import org.moi.budgettracker.dto.RecurringExpenseRequest
import org.moi.budgettracker.dto.RecurringExpenseResponse
import org.moi.budgettracker.entities.RecurringExpense
import org.moi.budgettracker.entities.User
import org.moi.budgettracker.exception.NotFoundException
import org.moi.budgettracker.mappers.toResponse
import org.moi.budgettracker.repository.CategoryRepository
import org.moi.budgettracker.repository.RecurringExpenseRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Service
class RecurringExpenseService(
    private val recurringExpenseRepository: RecurringExpenseRepository,
    private val categoryRepository: CategoryRepository
) {

    @Transactional
    fun createRecurringExpense(user: User, request: RecurringExpenseRequest): RecurringExpenseResponse {
        val category = categoryRepository.findById(request.categoryId)
            .orElseThrow { NotFoundException("Category not found") }

        val startDate = LocalDate.parse(request.startDate, DateTimeFormatter.ISO_DATE)

        val recurringExpense = RecurringExpense(
            user = user,
            category = category,
            amount = request.amount,
            description = request.description,
            paymentMethod = request.paymentMethod,
            frequency = request.frequency,
            startDate = startDate,
            nextExecutionDate = startDate,
            isActive = true
        )

        return recurringExpenseRepository.save(recurringExpense).toResponse()
    }

    fun getUserRecurringExpenses(user: User): List<RecurringExpenseResponse> {
        return recurringExpenseRepository.findByUserAndIsActiveTrue(user)
            .map { it.toResponse() }
    }

    @Transactional
    fun updateRecurringExpense(
        user: User,
        id: Long,
        request: RecurringExpenseRequest
    ): RecurringExpenseResponse {
        val recurringExpense = recurringExpenseRepository.findById(id)
            .orElseThrow { NotFoundException("Recurring expense not found") }

        // Verify ownership
        if (recurringExpense.user.id != user.id) {
            throw NotFoundException("Recurring expense not found")
        }

        val category = categoryRepository.findById(request.categoryId)
            .orElseThrow { NotFoundException("Category not found") }

        val startDate = LocalDate.parse(request.startDate, DateTimeFormatter.ISO_DATE)

        val updated = recurringExpense.copy(
            category = category,
            amount = request.amount,
            description = request.description,
            paymentMethod = request.paymentMethod,
            frequency = request.frequency,
            startDate = startDate
        )

        return recurringExpenseRepository.save(updated).toResponse()
    }

    @Transactional
    fun deleteRecurringExpense(user: User, id: Long) {
        val recurringExpense = recurringExpenseRepository.findById(id)
            .orElseThrow { NotFoundException("Recurring expense not found") }

        // Verify ownership
        if (recurringExpense.user.id != user.id) {
            throw NotFoundException("Recurring expense not found")
        }

        // Soft delete by marking as inactive
        val deactivated = recurringExpense.copy(isActive = false)
        recurringExpenseRepository.save(deactivated)
    }

    @Transactional
    fun toggleRecurringExpense(user: User, id: Long): RecurringExpenseResponse {
        val recurringExpense = recurringExpenseRepository.findById(id)
            .orElseThrow { NotFoundException("Recurring expense not found") }

        // Verify ownership
        if (recurringExpense.user.id != user.id) {
            throw NotFoundException("Recurring expense not found")
        }

        val toggled = recurringExpense.copy(isActive = !recurringExpense.isActive)
        return recurringExpenseRepository.save(toggled).toResponse()
    }
}