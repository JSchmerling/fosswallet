package nz.eloque.foss_wallet.ui.screens.wallet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Unarchive
import androidx.compose.material3.ExtendedFloatingActionButton
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
import nz.eloque.foss_wallet.share.share
import nz.eloque.foss_wallet.utils.isScrollingUp

@Composable
fun SelectionActions(
    isArchive: Boolean,
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
                  
                }
            },
        ) {
            Icon(imageVector = Icons.Default.Unarchive, contentDescription = stringResource(R.string.shortcut))
        }
        FloatingActionButton(
            onClick = {
                
                }
            },
        ) {
            Icon(imageVector = Icons.Default.Share, contentDescription = stringResource(R.string.update))
        }
    }
}
