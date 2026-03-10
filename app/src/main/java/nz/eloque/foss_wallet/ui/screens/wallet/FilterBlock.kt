package nz.eloque.foss_wallet.ui.screens.wallet

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalResources
import nz.eloque.foss_wallet.model.PassType
import nz.eloque.foss_wallet.model.SortOption
import nz.eloque.foss_wallet.model.Tag
import nz.eloque.foss_wallet.ui.components.ChipSelector
import nz.eloque.foss_wallet.ui.components.tag.TagRow

@Composable
fun FilterBlock(
    walletViewModel: WalletViewModel,
    sortOption: SortOption,
    onSortChange: (SortOption) -> Unit,
    passTypesToShow: SnapshotStateList<PassType>,
    tags: Set<Tag>,
    tagToFilterFor: MutableState<Tag?>,
) {
    val resources = LocalResources.current

    var lastScrollIndex by remember { mutableIntStateOf(0) }
    var filtersShown by remember { mutableStateOf(true) }

    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .collect { currentIndex ->
                filtersShown = currentIndex <= lastScrollIndex
                lastScrollIndex = currentIndex
            }
    }

    AnimatedVisibility(
        visible = filtersShown,
        enter = expandVertically(
            animationSpec = tween(durationMillis = 300)) + fadeIn(animationSpec = tween(300)),
        exit = shrinkVertically(
            animationSpec = tween(durationMillis = 300)) + fadeOut(animationSpec = tween(300))
    ) {
        Row(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            ChipSelector(
                options = PassType.all(),
                selectedOptions = passTypesToShow,
                onOptionSelected = { passTypesToShow.add(it) },
                onOptionDeselected = { passTypesToShow.remove(it) },
                optionLabel = { resources.getString(it.label) },
                modifier = Modifier.fillMaxWidth()
            )
            TagRow(
                tags = tags,
                selectedTag = tagToFilterFor.value,
                onTagSelected = { tagToFilterFor.value = it },
                onTagDeselected = { tagToFilterFor.value = null },
                walletViewModel = walletViewModel,
                modifier = Modifier.fillMaxWidth()
           )
        }
    }
}
