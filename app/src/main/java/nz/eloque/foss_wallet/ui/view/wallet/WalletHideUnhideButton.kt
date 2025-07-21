package nz.eloque.foss_wallet.ui.view.pass

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import nz.eloque.foss_wallet.R

@Composable
fun WalletHideUnhideButton(
    isAuthenticated: Boolean,
    onClick: () -> Unit
) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = if (isAuthenticated) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
            contentDescription = if (isAuthenticated) stringResource(R.string.unhide) else stringResource(R.string.hide)
        )
    }
}
