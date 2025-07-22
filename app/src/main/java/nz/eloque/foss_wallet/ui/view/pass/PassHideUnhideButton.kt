package nz.eloque.foss_wallet.ui.view.pass

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityLock
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import nz.eloque.foss_wallet.biometric
import nz.eloque.foss_wallet.R

@Composable
fun WalletHideUnhideButton(
    isAuthenticated: Boolean,
    isHidden: Boolean,
    context: Context,
    onClick: () -> Unit
) {
    var showBiometricPrompt by remember { mutableStateOf(false) }

    if (showBiometricPrompt) {
        BiometricAuthPrompt(
            context = context,
            onSuccess = {
                showBiometricPrompt = false
                onClick()
            },
            onError = {
                showBiometricPrompt = false
            }
        )
    }

    if (isAuthenticated && isHidden) {
        IconButton(onClick = onClick) {
            Icon(
                imageVector = Icons.Filled.Visibility,
                contentDescription = stringResource(R.string.unhide_pass)
            )
        }
    } else if (!isHidden) {
        IconButton(onClick = isAuthenticated ? onClick : { showBiometricPrompt = true }) {
            Icon(
                imageVector = Icons.Filled.VisibilityOff,
                contentDescription = stringResource(R.string.hide_pass)
            )
        }
    } else {
        IconButton(onClick = { showBiometricPrompt = true }) {
            Icon(
                imageVector = Icons.Filled.VisibilityLock,
                contentDescription = stringResource(R.string.unhide_pass)
            )
        }
    }
}
