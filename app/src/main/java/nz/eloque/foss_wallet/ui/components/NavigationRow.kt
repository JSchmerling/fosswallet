package nz.eloque.foss_wallet.ui.components

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material.icons.outlined.Archive
import androidx.compose.material.icons.outlined.Wallet
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import nz.eloque.foss_wallet.R
import nz.eloque.foss_wallet.ui.Screen

@Composable
fun NavigationRow(
    navController: NavController,
    walletViewModel: WalletViewModel
) {
    var selectedTabIndex by remember { mutableStateOf(1) }
    var imeVisible by remember { mutableStateOf(false) }
    var navBarVisible by remember { mutableStateOf(true) }

    BackHandler(enabled = imeVisible) { 
        imeVisible = false
        navBarVisible = true
    }
    
    AnimatedVisibility(
        visible = imeVisible,
        enter = fadeIn() + expandVertically(),
        exit = fadeOut() + shrinkVertically()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            FilterBar(
                onSearch = { walletViewModel.filter(it) },
                modifier = Modifier
                    .padding(start = 4.dp, bottom = 4.dp)
                    .weight(1f)
            )
            if (imeVisible) {
                IconButton(
                    onClick = { imeVisible = false }
                ) {
                    Icon(
                        imageVector = Icons.Default.SearchOff,
                        contentDescription = stringResource(R.string.reduce_ime)
                    )
                }
            } else {
                IconButton(
                    onClick = { imeVisible = false }
                ) {
                    Icon(
                        imageVector = Icons.Default.SearchOff,
                        contentDescription = stringResource(R.string.show_navbar)
                    )
                }
            }
        }
    }
    
    AnimatedVisibility(
        visible = navBarVisible,
        enter = fadeIn() + expandVertically(),
        exit = fadeOut() + shrinkVertically()
    ) {
        NavigationBar {
            NavigationBarItem(
                selected = selectedTabIndex == 0,
                onClick = {
                    selectedTabIndex = 0
                    navBarVisible = false
                    imeVisible = true
                },
                icon = { Icon(Icons.Default.Search, contentDescription = stringResource(R.string.search)) },
                label = { Text(stringResource(R.string.search)) }
            )
            NavigationBarItem(
                selected = selectedTabIndex == 1,
                onClick = {
                    selectedTabIndex = 1
                    navController.navigate(Screen.Wallet.route) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                    }
                },
                icon = { Icon(
                    imageVector = if (selectedTabIndex == 1) Icons.Filled.Wallet else Icons.Outlined.Wallet,
                    contentDescription = stringResource(R.string.wallet)
                ) },
                label = { Text(stringResource(R.string.wallet)) }
            )
            NavigationBarItem(
                selected = selectedTabIndex == 2,
                onClick = {
                    selectedTabIndex = 2
                    navController.navigate(Screen.Archive.route) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                    }
                },
                icon = { Icon(
                    imageVector = if (selectedTabIndex == 2) Icons.Filled.Archive else Icons.Outlined.Archive,
                    contentDescription = stringResource(R.string.the_archive)
                ) },
                label = { Text(stringResource(R.string.the_archive)) }
            )
        }
    }
}
