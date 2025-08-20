package nz.eloque.foss_wallet.ui.view.wallet

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VisibilityLock
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.FragmentActivity
import nz.eloque.foss_wallet.R
import nz.eloque.foss_wallet.utils.BiometricPromptManager

@Composable
fun VisibilityToggleButton(
    authStatus: Boolean,
    onClick: () -> Unit
) {
    val context = LocalContext.current
    val activity = remember(context) { context as FragmentActivity }
    val biometricPromptManager = remember { BiometricPromptManager(activity) }

    LaunchedEffect(biometricPromptManager) {
        biometricPromptManager.promptResults.collect { result ->
            when (result) {
                is BiometricPromptManager.BiometricResult.AuthenticationSuccess -> {
                    onClick()
                }
                is BiometricPromptManager.BiometricResult.AuthenticationError -> {
                    // Handle error if needed
                }
                is BiometricPromptManager.BiometricResult.AuthenticationFailed -> {
                    // Handle failed authentication if needed
                }
                is BiometricPromptManager.BiometricResult.HardwareUnavailable,
                is BiometricPromptManager.BiometricResult.FeatureUnavailable,
                is BiometricPromptManager.BiometricResult.AuthenticationNotSet -> {
                    // Handle unavailable biometric features if needed
                    // Maybe fallback to direct onClick() or show alternative auth
                }
            }
        }
    }

    if (authStatus) {
        IconButton(onClick = onClick) {
            Icon(
                imageVector = Icons.Filled.VisibilityLock,
                contentDescription = stringResource(R.string.unhide)
            )
        }
    } else {
        IconButton(onClick = { 
            biometricPromptManager.showBiometricPrompt(
                title = stringResource(R.string.auth),
                description = stringResource(R.string.auth_description)
            )
        }) {
            Icon(
                imageVector = Icons.Filled.VisibilityOff,
                contentDescription = stringResource(R.string.hide)
            )
        }
    }
}
