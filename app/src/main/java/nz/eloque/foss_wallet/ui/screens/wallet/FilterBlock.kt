package nz.eloque.foss_wallet.ui.screens.wallet

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import nz.eloque.foss_wallet.R
import nz.eloque.foss_wallet.model.PassType
import nz.eloque.foss_wallet.model.SortOption
import nz.eloque.foss_wallet.model.Tag
import nz.eloque.foss_wallet.ui.components.ChipSelector
import nz.eloque.foss_wallet.ui.components.FilterBar
import nz.eloque.foss_wallet.ui.components.SelectionMenu
import nz.eloque.foss_wallet.ui.components.tag.TagRow

@Composable
fun FilterBlock(
    walletViewModel: WalletViewModel,
    onSortChange: (SortOption) -> Unit,
    tags: Set<Tag>,
) {
    val sortOption = walletViewModel.sortOptionState.collectAsState().value
    val passTypesToShow = remember { PassType.all().toMutableStateList() }
    val tagToFilterFor = remember { mutableStateOf<Tag?>(null) }

    SelectionMenu(
        singleOptions = SortOption.all(),
        multiOptions = PassType.all(),
        singleOptionLabel = { resources.getString(it.l18n) },
        multiOptionLabel = { resources.getString(it.label) },
        selectedSingleOption = sortOption,
        selectedMultiOptions = passTypesToShow,
        onSingleOptionSelected = { walletViewModel.setSortOption(it) },
        onMultiOptionSelected = { passTypesToShow.add(it) },
        onMultiOptionDeselected = { passTypesToShow.remove(it) },
        contentDescription = R.string.filter,
    )
    TagRow(
        tags = tags,
        selectedTag = tagToFilterFor.value,
        onTagSelected = { tagToFilterFor.value = it },
        onTagDeselected = { tagToFilterFor.value = null },
        walletViewModel = walletViewModel,
        modifier = Modifier,
    )
}
