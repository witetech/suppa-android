package ai.suppa.presentation.widgets

import ai.suppa.domain.models.*
import ai.suppa.presentation.ChatViewModel.State
import ai.suppa.presentation.theme.SuppaTheme
import ai.suppa.presentation.utils.ColorUtils
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
internal fun ContentScaffold(
    content: State.Content,
    onBackButtonClick: () -> Unit = {},
    onMessageSent: (String) -> Unit = {},
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = ScaffoldDefaults
            .contentWindowInsets
            .exclude(WindowInsets.navigationBars)
            .exclude(WindowInsets.ime),
        topBar = {
            AppBar(
                title = content.config.name,
                description = content.config.description,
                backgroundColor = ColorUtils.hexToColor(content.theme.colorHex),
                textColor = ColorUtils.hexToColor(content.theme.textColorHex),
                onBackButtonClick = onBackButtonClick,
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            Messages(
                modifier = Modifier.weight(1f),
                messages = content.messages,
                backgroundColor = ColorUtils.hexToColor(content.theme.colorHex),
                textColor = ColorUtils.hexToColor(content.theme.textColorHex),
                loading = content.loading,
            )

            UserInput(
                modifier = Modifier
                    .navigationBarsPadding()
                    .imePadding(),
                backgroundColor = ColorUtils.hexToColor(content.theme.colorHex),
                iconColor = ColorUtils.hexToColor(content.theme.textColorHex),
                showBranding = !content.theme.removeBranding,
                onMessageSent = onMessageSent,
            )
        }
    }
}

@Composable
@Preview
fun ContentScaffoldPreview() {
    SuppaTheme {
        ContentScaffold(
            content = State.Content(
                config = Config(
                    chatId = "",
                    name = "Suppa",
                    description = "Suppa is a chat application",
                    apiKey = "",
                ),
                theme = Theme(
                    colorHex = "000000",
                    iconColorHex = "FFFFFF",
                    textColorHex = "FFFFFF",
                    imageBase64 = null,
                    removeBranding = false,
                ),
                messages = arrayListOf(),
                loading = false,
            ),
        )
    }
}
