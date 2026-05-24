

@Composable
fun TripleToggleButton(
    state: ToggleState,
    onStateChange: (ToggleState) -> Unit,
    labels: Triple<String, String, String> = Triple("An", "Auto", "Aus"),
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
    ) {
        // Gedrehter Toggle
        HorizontalThreeStateToggle(
            state = state,
            onStateChange = onStateChange,
            modifier = Modifier
                .rotate(90f)
                .layout { measurable, constraints ->
                    val placeable = measurable.measure(constraints)
                    layout(placeable.height, placeable.width) {
                        placeable.placeRelative(
                            x = -(placeable.width / 2 - placeable.height / 2),
                            y = -(placeable.height / 2 - placeable.width / 2)
                        )
                    }
                }
        )

        // Labels nur rechts, aktiver Zustand hervorgehoben
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.height(120.dp)
        ) {
            listOf(
                ToggleState.TOP to labels.first,
                ToggleState.MIDDLE to labels.second,
                ToggleState.BOTTOM to labels.third
            ).forEach { (toggleState, label) ->
                Text(
                    text = label,
                    fontWeight = if (state == toggleState) FontWeight.Bold else FontWeight.Normal,
                    color = if (state == toggleState) Color(0xFF6200EE) else Color.Gray
                )
            }
        }
    }
}
