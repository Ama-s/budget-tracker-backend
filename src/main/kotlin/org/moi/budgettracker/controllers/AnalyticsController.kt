package org.moi.budgettracker.controllers

import org.moi.budgettracker.dto.AnalyticsSummaryResponse
import org.moi.budgettracker.repository.UserRepository
import org.moi.budgettracker.security.CustomUserDetails
import org.moi.budgettracker.service.AnalyticsService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/analytics")
class AnalyticsController(
    private val analyticsService: AnalyticsService,
    private val userRepository: UserRepository
) {

    @GetMapping("/summary") // hasn't been implemented yet
    fun getMonthlySummary(
        @AuthenticationPrincipal userDetails: CustomUserDetails,
    ): ResponseEntity<AnalyticsSummaryResponse> {
        val user = userDetails.user
        val response = analyticsService.getMonthlySummary(user)
        return ResponseEntity.ok(response)
    }
}