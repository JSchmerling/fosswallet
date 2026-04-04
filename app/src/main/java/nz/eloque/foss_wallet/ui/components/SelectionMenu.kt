package nz.eloque.foss_wallet.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import nz.eloque.foss_wallet.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T, F> SelectionMenu(
    options: List<T>,
    selectedOption: T,
    filterOptions: Collection<F>,
    selectedFilterOptions: Collection<F>,
    onOptionSelected: (T) -> Unit,
    onFilterSelected: (F) -> Unit,
    onFilterDeselected: (F) -> Unit,
    optionLabel: (T) -> String,
    filterLabel: (F) -> String,
    modifier: Modifier = Modifier,
    @StringRes contentDescription: Int = R.string.more_options,
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        IconButton(onClick = { expanded = !expanded }) {
            Icon(Icons.Default.Menu, contentDescription = stringResource(contentDescription))
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            filterOptions.forEach { filter -> // multi select
                val selected = selectedFilterOptions.contains(filter)
                DropdownMenuItem(
                    text = { Text(filterLabel(filter)) },
                    leadingIcon = {
                        if (selected) {
                            Icon(Icons.Default.Check, contentDescription = stringResource(R.string.selected))
                        } else {
                            Icon(Icons.Default.FilterList, contentDescription = null)
                        }
                    },
                    onClick = {
                        if (selected) onFilterDeselected(filter) else onFilterSelected(filter)
                    }
                )
            }

            HorizontalDivider()

            options.forEach { option -> // single select
                DropdownMenuItem(
                    text = { Text(optionLabel(option)) },
                    trailingIcon = {
                        if (option == selectedOption) {
                            Icon(Icons.Default.Check, stringResource(R.string.selected))
                        }
                    },
                    onClick = { onOptionSelected(option) }
                )
            }
        }
    }
}
