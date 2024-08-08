package ai.suppa.presentation.widgets

import android.widget.TextView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import io.noties.markwon.Markwon

@Composable
internal fun MarkdownText(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color,
) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            val markwon = Markwon.create(context)
            TextView(context).apply {
                markwon.setMarkdown(this, text)
                setTextColor(textColor.toArgb())
            }
        },
    )
}

@Composable
@Preview
private fun MarkdownTextPreview() {
    MarkdownText(
        text = """
            # Hello, world!

            This is a simple markdown text.

            - Item 1
            - Item 2
            - Item 3
        """.trimIndent(),
        textColor = Color.Black,
    )
}
