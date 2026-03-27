package nz.eloque.foss_wallet.ui.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import nz.eloque.foss_wallet.R

@Composable
fun <T> ChipSelector(
    options: Collection<T>,
    selectedOptions: Collection<T>,
    onOptionSelected: (T) -> Unit,
    onOptionDeselected: (T) -> Unit,
    optionLabel: (T) -> String,
    modifier: Modifier = Modifier,
    selectedIcon: ImageVector = Icons.Default.Check,
    dropdownOptions: Map<String, List<String>>? = null, // Label -> Optionen
    onDropdownOptionSelected: ((String, String) -> Unit)? = null,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .horizontalScroll(rememberScrollState())
    ) {
        options.forEach { option ->
            val selected = selectedOptions.contains(option)
            val label = optionLabel(option)
            val hasDropdown = dropdownOptions?.containsKey(label) == true

            if (hasDropdown) {
                var expanded by remember { mutableStateOf(false) }

                Box {
                    FilterChip(
                        selected = selected,
                        leadingIcon = {
                            if (selected) {
                                Icon(
                                    imageVector = selectedIcon,
                                    contentDescription = stringResource(R.string.selected)
                                )
                            }
                        },
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowDown,
                                contentDescription = null
                            )
                        },
                        onClick = { expanded = !expanded },
                        label = { Text(label) },
                    )

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        dropdownOptions!![label]!!.forEach { dropdownOption ->
                            DropdownMenuItem(
                                text = { Text(dropdownOption) },
                                onClick = {
                                    onDropdownOptionSelected?.invoke(label, dropdownOption)
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            } else {
                FilterChip(
                    selected = selected,
                    leadingIcon = {
                        if (selected) {
                            Icon(
                                imageVector = selectedIcon,
                                contentDescription = stringResource(R.string.selected)
                            )
                        }
                    },
                    onClick = {
                        if (selected) onOptionDeselected(option)
                        else onOptionSelected(option)
                    },
                    label = { Text(label) },
                )
            }
        }
    }
}
