package nz.eloque.foss_wallet.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
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
            }
        }
    }

    if (authStatus) {
        IconButton(onClick = onClick) {
            Icon(imageVector = Icons.Filled.Visibility, contentDescription = "Hide")
        }
    } else {
        IconButton(onClick = { 
            biometricPromptManager.showBiometricPrompt(
                description = stringResource(R.string.reveal)
            )
        }) {
            Icon(imageVector = Icons.Filled.VisibilityOff, contentDescription = "Show")
        }
    }
}
