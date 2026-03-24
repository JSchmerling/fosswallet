package ing.schmerl.dphi_keyboard.shortcut



class SquareWidget : GlanceAppWidget() {

    override val sizeMode = SizeMode.Responsive(
        setOf(
            DpSize(110.dp, 110.dp), // Small
            DpSize(180.dp, 180.dp), // Medium
            DpSize(250.dp, 250.dp), // Large
        )
    )

    @Composable
    override fun Content() {
        val state = currentState<Preferences>()
        val title = state[stringPreferencesKey("title")]
        val subtitle = state[stringPreferencesKey("subtitle")] ?: ""
        val imagePath = state[stringPreferencesKey("image_path")] ?: ""

        GlanceTheme {
            ImageWidgetContent(
                title = title,
                subtitle = subtitle,
                imagePath = imagePath
            )
        }
    }
}
