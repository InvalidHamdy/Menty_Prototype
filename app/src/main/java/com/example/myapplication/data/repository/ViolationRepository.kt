package com.example.myapplication.data.repository

import com.example.myapplication.data.local.dao.ViolationDao
import com.example.myapplication.data.local.entities.ViolationEntity
import com.example.myapplication.domain.model.Severity
import com.example.myapplication.domain.model.Violation
import com.example.myapplication.domain.repository.ViolationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RoomViolationRepositoryImpl @Inject constructor(
    private val violationDao: ViolationDao
) : ViolationRepository {

    override fun getViolations(): Flow<List<Violation>> = violationDao.getViolations().map { entities ->
        entities.map { it.toDomain() }
    }

    override suspend fun getViolationById(id: String): Violation? {
        return violationDao.getViolationById(id)?.toDomain()
    }

    override suspend fun addViolation(violation: Violation) {
        violationDao.insertViolation(violation.toEntity())
    }
}

// Mappers
fun ViolationEntity.toDomain() = Violation(
    id = id,
    title = title,
    code = code,
    time = time,
    date = date,
    description = description,
    status = status,
    isActive = isActive,
    severity = try {
        Severity.valueOf(severity.name)
    } catch(e: Exception) {
        Severity.MEDIUM
    }
)

fun Violation.toEntity() = ViolationEntity(
    id = id,
    title = title,
    code = code,
    time = time,
    date = date,
    description = description,
    status = status,
    isActive = isActive,
    severity = try {
        com.example.myapplication.data.Severity.valueOf(severity.name)
    } catch(e: Exception) {
        com.example.myapplication.data.Severity.MEDIUM
    }
)
