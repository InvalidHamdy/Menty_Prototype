package com.example.myapplication.presentation.features.auth

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.myapplication.core.accessibility.LocalAccessibilityState
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    viewModel: AuthViewModel,
    onNavigateToOnboarding: () -> Unit,
    onNavigateToHome: () -> Unit
) {
    val session by viewModel.session.collectAsStateWithLifecycle()
    val accessState = LocalAccessibilityState.current

    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val alphaAnim by if (accessState.reduceMotion) {
        remember { mutableStateOf(1f) }
    } else {
        infiniteTransition.animateFloat(
            initialValue = 0.3f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(1200),
                repeatMode = RepeatMode.Reverse
            ),
            label = "pulseAlpha"
        )
    }

    LaunchedEffect(Unit) {
        delay(2000) // boot delay
        if (session.isLoggedIn) {
            onNavigateToHome()
        } else {
            onNavigateToOnboarding()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.clearAndSetSemantics {
                contentDescription = "MENTOR OS Initializing. Terminal loading."
            }
        ) {
            Text(
                text = "AI MENTOR",
                style = MaterialTheme.typography.displayLarge.copy(
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp
                ),
                modifier = Modifier.alpha(alphaAnim)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "SYSTEM INITIALIZING...",
                style = MaterialTheme.typography.labelMedium.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }
    }
}

@Composable
fun OnboardingScreen(
    onNavigateToSetup: () -> Unit
) {
    val accessState = LocalAccessibilityState.current
    val buttonHeight = if (accessState.touchTargetScaling) 64.dp else 56.dp

    val infiniteTransition = rememberInfiniteTransition(label = "heroPulse")
    val heroAlpha by if (accessState.reduceMotion) {
        remember { mutableStateOf(1f) }
    } else {
        infiniteTransition.animateFloat(
            initialValue = 0.85f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(1500),
                repeatMode = RepeatMode.Reverse
            ),
            label = "heroAlpha"
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(32.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(48.dp))

        Box(
            modifier = Modifier
                .size(88.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(MaterialTheme.colorScheme.primary.copy(alpha = heroAlpha * 0.15f))
                .border(2.dp, MaterialTheme.colorScheme.primary.copy(alpha = heroAlpha), RoundedCornerShape(24.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Security,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(48.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "AI MENTOR",
            style = MaterialTheme.typography.displayMedium.copy(
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp
            )
        )
        Text(
            text = "Behavioral Discipline Engine",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(40.dp))

        OnboardingCard(
            title = "Real-Time Monitoring Call",
            desc = "Incoming mentor interventions when focus drifts — no swipe-to-dismiss."
        )
        Spacer(modifier = Modifier.height(12.dp))
        OnboardingCard(
            title = "Enforced Lockdown Sandboxing",
            desc = "Strict sandbox preserves schedule margins after violations."
        )
        Spacer(modifier = Modifier.height(12.dp))
        OnboardingCard(
            title = "Dynamic Performance Index",
            desc = "Discipline score updates from live behavioral telemetry."
        )

        Spacer(modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onNavigateToSetup,
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .fillMaxWidth()
                .height(buttonHeight)
                .semantics { contentDescription = "Start setup" },
            shape = RoundedCornerShape(12.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("START SETUP", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(8.dp))
                Icon(Icons.Default.ChevronRight, contentDescription = null)
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun OnboardingCard(title: String, desc: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
            .border(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.2f), RoundedCornerShape(16.dp))
            .padding(16.dp)
            .semantics {
                contentDescription = "$title. Details: $desc"
            }
    ) {
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = desc,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    }
}

@Composable
fun LoginScreen(
    viewModel: AuthViewModel,
    onLoginSuccess: () -> Unit,
    onNavigateToSignUp: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val accessState = LocalAccessibilityState.current
    val fieldHeight = if (accessState.touchTargetScaling) 60.dp else 52.dp
    val buttonHeight = if (accessState.touchTargetScaling) 64.dp else 56.dp

    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) {
            onLoginSuccess()
            viewModel.resetState()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.Security,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "SECURE AUTH",
                style = MaterialTheme.typography.displayMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
            Text(
                text = "Enter credential key to access console",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
            Spacer(modifier = Modifier.height(40.dp))

            // Inputs Frosted Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .border(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f), RoundedCornerShape(24.dp))
                    .padding(24.dp)
            ) {
                Column {
                    OutlinedTextField(
                        value = state.username,
                        onValueChange = { viewModel.onUsernameChange(it) },
                        label = { Text("COMMANDER USERNAME") },
                        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(fieldHeight)
                            .semantics {
                                contentDescription = "Enter your commander username"
                            },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(
                        value = state.pass,
                        onValueChange = { viewModel.onPasswordChange(it) },
                        label = { Text("DISCIPLINE KEY") },
                        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(fieldHeight)
                            .semantics {
                                contentDescription = "Enter your discipline password key"
                            },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
                        )
                    )

                    state.error?.let { err ->
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = err,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                            modifier = Modifier.semantics {
                                contentDescription = "Error message: $err"
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    Button(
                        onClick = { viewModel.login() },
                        enabled = !state.isLoading,
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(buttonHeight)
                            .semantics {
                                contentDescription = "Trigger authentication check"
                            },
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        if (state.isLoading) {
                            CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary, modifier = Modifier.size(24.dp))
                        } else {
                            Text(
                                "AUTHENTICATE CORE",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onPrimary
                                )
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "New Commander? Initialize Key →",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
                    .clickable { onNavigateToSignUp() }
                    .padding(8.dp)
                    .semantics {
                        contentDescription = "Navigate to initialization and sign up screen"
                    }
            )
        }
    }
}

@Composable
fun SignUpScreen(
    viewModel: AuthViewModel,
    onSignUpSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val accessState = LocalAccessibilityState.current
    val fieldHeight = if (accessState.touchTargetScaling) 60.dp else 52.dp
    val buttonHeight = if (accessState.touchTargetScaling) 64.dp else 56.dp

    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) {
            onSignUpSuccess()
            viewModel.resetState()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.Security,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "INITIALIZE KEY",
                style = MaterialTheme.typography.displayMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
            Text(
                text = "Register a new monitor node within the loop",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
            Spacer(modifier = Modifier.height(40.dp))

            // Frosted Inputs Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .border(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f), RoundedCornerShape(24.dp))
                    .padding(24.dp)
            ) {
                Column {
                    OutlinedTextField(
                        value = state.username,
                        onValueChange = { viewModel.onUsernameChange(it) },
                        label = { Text("PROPOSED USERNAME") },
                        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(fieldHeight)
                            .semantics {
                                contentDescription = "Enter proposed username for registration"
                            },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.secondary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(
                        value = state.pass,
                        onValueChange = { viewModel.onPasswordChange(it) },
                        label = { Text("ESTABLISH DISCIPLINE KEY") },
                        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(fieldHeight)
                            .semantics {
                                contentDescription = "Establish dynamic password key"
                            },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.secondary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
                        )
                    )

                    state.error?.let { err ->
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = err,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                            modifier = Modifier.semantics {
                                contentDescription = "Error message: $err"
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    Button(
                        onClick = { viewModel.signUp() },
                        enabled = !state.isLoading,
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(buttonHeight)
                            .semantics {
                                contentDescription = "Trigger signup creation"
                            },
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        if (state.isLoading) {
                            CircularProgressIndicator(color = MaterialTheme.colorScheme.onSecondary, modifier = Modifier.size(24.dp))
                        } else {
                            Text(
                                "REGISTER NODE",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSecondary
                                )
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Registered Terminal? Console Login →",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.secondary,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
                    .clickable { onNavigateToLogin() }
                    .padding(8.dp)
                    .semantics {
                        contentDescription = "Navigate to secure console login"
                    }
            )
        }
    }
}
