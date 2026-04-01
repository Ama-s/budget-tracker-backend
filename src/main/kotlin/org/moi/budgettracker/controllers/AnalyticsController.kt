package org.moi.budgettracker.controllers

import org.moi.budgettracker.dto.AnalyticsSummaryResponse
import org.moi.budgettracker.entities.User
import org.moi.budgettracker.service.AnalyticsService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/analytics")
class AnalyticsController(
    private val analyticsService: AnalyticsService
) {
    private val defaultUser = User(id = 1, username = "public")

    @GetMapping("/summary") // hasn't been implemented yet
    fun getMonthlySummary(
    ): ResponseEntity<AnalyticsSummaryResponse> {
        val response = analyticsService.getMonthlySummary(defaultUser)
        return ResponseEntity.ok(response)
    }
}