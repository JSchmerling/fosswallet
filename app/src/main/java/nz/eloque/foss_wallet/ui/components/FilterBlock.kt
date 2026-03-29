package nz.eloque.foss_wallet.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalResources
import nz.eloque.foss_wallet.model.PassType
import nz.eloque.foss_wallet.model.Tag
import nz.eloque.foss_wallet.ui.components.ChipSelector
import nz.eloque.foss_wallet.ui.components.tag.TagRow

@Composable
fun FilterBlock(
    walletViewModel: WalletViewModel,
    listState: LazyListState,
    passTypesToShow: SnapshotStateList<PassType>,
    tags: Set<Tag>,
    tagToFilterFor: MutableState<Tag?>,
) {
    val resources = LocalResources.current
    val isScrollingUp = rememberIsScrollingUp(listState)

    Column {

        AnimatedVisibility(
            visible = isScrollingUp,
            enter = expandVertically(
                animationSpec = tween(durationMillis = 300)
            ) + fadeIn(animationSpec = tween(300)),
            exit = shrinkVertically(
                animationSpec = tween(durationMillis = 300)
            ) + fadeOut(animationSpec = tween(300))
        ) {
            Column(
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
}

@Composable
private fun rememberIsScrollingUp(listState: LazyListState): Boolean {
    var previousIndex by remember(listState) { mutableIntStateOf(listState.firstVisibleItemIndex) }
    var previousOffset by remember(listState) { mutableIntStateOf(listState.firstVisibleItemScrollOffset) }

    return remember(listState) {
        derivedStateOf {
            if (previousIndex != listState.firstVisibleItemIndex) {
                (previousIndex > listState.firstVisibleItemIndex).also {
                    previousIndex = listState.firstVisibleItemIndex
                }
            } else {
                (previousOffset >= listState.firstVisibleItemScrollOffset).also {
                    previousOffset = listState.firstVisibleItemScrollOffset
                }
            }
        }
    }.value
}
