package ai.suppa.presentation.widgets

import ai.suppa.presentation.ChatViewModel.State
import ai.suppa.presentation.theme.SuppaTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
internal fun ContentScaffold(
    content: State.Content,
    onBackClick: () -> Unit = {},
    onMessageSent: (String) -> Unit = {},
) {
}

@Composable
@Preview
fun ContentScaffoldPreview() {
    SuppaTheme {
        ContentScaffold(content = State.Content(a = ""))
    }
}
