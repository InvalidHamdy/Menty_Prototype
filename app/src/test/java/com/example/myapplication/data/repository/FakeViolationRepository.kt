package com.example.myapplication.data.repository

import com.example.myapplication.data.Violation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FakeViolationRepository : ViolationRepository {
    private val _violations = MutableStateFlow<List<Violation>>(emptyList())

    override fun getViolations(): Flow<List<Violation>> = _violations.asStateFlow()

    override suspend fun getViolationById(id: String): Violation? {
        return _violations.value.find { it.id == id }
    }

    fun seed(violations: List<Violation>) {
        _violations.value = violations
    }
}
