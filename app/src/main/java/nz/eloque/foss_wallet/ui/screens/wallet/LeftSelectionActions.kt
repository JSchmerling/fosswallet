package nz.eloque.foss_wallet.ui.screens.wallet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AppShortcut
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshots.SnapshotStateSet
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import nz.eloque.foss_wallet.R
import nz.eloque.foss_wallet.model.Pass
import nz.eloque.foss_wallet.shortcut.Shortcut
import nz.eloque.foss_wallet.utils.isScrollingUp

@Composable
fun LeftSelectionActions(
    selectedPasses: SnapshotStateSet<Pass>,
    listState: LazyListState,
    passViewModel: PassViewModel,
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.End
    ) {
        FloatingActionButton(
            onClick = {
                Shortcut.create(context, pass, pass.description)
            }
        ) {
            Icon(imageVector = Icons.Default.AppShortcut, contentDescription = stringResource(R.string.shortcut))
        }
        FloatingActionButton(
            onClick = {
                coroutineScope.launch(Dispatchers.IO) { passViewModel.delete(pass) }
                navController.popBackStack()
            }
        ) {
            Icon(imageVector = Icons.Default.Sync, contentDescription = stringResource(R.string.update))
        }
    }
}
