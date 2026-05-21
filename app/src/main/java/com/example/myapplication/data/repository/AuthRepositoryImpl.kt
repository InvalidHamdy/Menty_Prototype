package com.example.myapplication.data.repository

import android.content.Context
import com.example.myapplication.domain.model.UserSession
import com.example.myapplication.domain.repository.AuthRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : AuthRepository {
    private val prefs = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    private val _session = MutableStateFlow(loadSession())

    override fun getSession(): Flow<UserSession> = _session.asStateFlow()

    override suspend fun login(username: String, pass: String): Result<UserSession> {
        if (username.isBlank() || pass.length < 4) {
            return Result.failure(Exception("Credentials must be 4+ characters"))
        }
        val session = UserSession(
            username = username,
            isLoggedIn = true,
            token = "auth_tok_${username}",
            disciplineRank = "ELITE ENFORCER"
        )
        saveSession(session)
        _session.value = session
        return Result.success(session)
    }

    override suspend fun signUp(username: String, pass: String): Result<UserSession> {
        if (username.isBlank() || pass.length < 4) {
            return Result.failure(Exception("Credentials must be 4+ characters"))
        }
        val session = UserSession(
            username = username,
            isLoggedIn = true,
            token = "auth_tok_${username}",
            disciplineRank = "RECRUIT MONITOR"
        )
        saveSession(session)
        _session.value = session
        return Result.success(session)
    }

    override suspend fun logout() {
        val session = UserSession(username = "", isLoggedIn = false, token = "")
        saveSession(session)
        _session.value = session
    }

    private fun loadSession(): UserSession {
        val isLoggedIn = prefs.getBoolean("isLoggedIn", false)
        val username = prefs.getString("username", "") ?: ""
        val token = prefs.getString("token", "") ?: ""
        val rank = prefs.getString("disciplineRank", "NOVICE MONITOR") ?: "NOVICE MONITOR"
        return UserSession(username = username, isLoggedIn = isLoggedIn, token = token, disciplineRank = rank)
    }

    private fun saveSession(session: UserSession) {
        prefs.edit().apply {
            putBoolean("isLoggedIn", session.isLoggedIn)
            putString("username", session.username)
            putString("token", session.token)
            putString("disciplineRank", session.disciplineRank)
            apply()
        }
    }
}
