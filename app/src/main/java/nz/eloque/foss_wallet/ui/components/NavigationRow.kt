package nz.eloque.foss_wallet.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material.icons.outlined.Archive
import androidx.compose.material.icons.outlined.Wallet
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import nz.eloque.foss_wallet.R

@Composable
fun NavigationRow() {
    var selectedTabIndex by remember { mutableStateOf(1) }

    NavigationBar {
        NavigationBarItem(
            selected = selectedTabIndex == 0,
            onClick = { selectedTabIndex = 0 },
            icon = { Icon(Icons.Default.Search, contentDescription = stringResource(R.string.search)) },
            label = { Text(stringResource(R.string.search)) }
        )
        NavigationBarItem(
            selected = selectedTabIndex == 1,
            onClick = { selectedTabIndex = 1 },
            icon = { Icon(
                imageVector = if (selectedTabIndex == 1) Icons.Filled.Wallet else Icons.Outlined.Wallet,
                contentDescription = stringResource(R.string.wallet)
            ) },
            label = { Text(stringResource(R.string.wallet)) }
        )
        NavigationBarItem(
            selected = selectedTabIndex == 2,
            onClick = { selectedTabIndex = 2 },
            icon = { Icon(
                imageVector = if (selectedTabIndex == 2) Icons.Filled.Archive else Icons.Outlined.Archive,
                contentDescription = stringResource(R.string.the_archive)
            ) },
            label = { Text(stringResource(R.string.the_archive)) }
        )
    }
}
