package ai.suppa.presentation.widgets

import ai.suppa.domain.models.*
import ai.suppa.presentation.theme.SuppaTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.util.Date

@Composable
internal fun Messages(
    modifier: Modifier = Modifier,
    messages: List<Message>,
    backgroundColor: Color = Color.Black,
    textColor: Color = Color.White,
    loading: Boolean = false,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(all = 12.dp),
        reverseLayout = false,
    ) {
        for (index in messages.indices) {
            item {
                Message(
                    message = messages[index],
                    backgroundColor = backgroundColor,
                    textColor = textColor,
                )

                Spacer(modifier = Modifier.height(12.dp))
            }
        }

        if (loading) {
            item {
                LoadingMessage(
                    backgroundColor = backgroundColor,
                    textColor = textColor,
                )
            }
        }
    }
}

@Composable
private fun Message(
    modifier: Modifier = Modifier,
    message: Message,
    backgroundColor: Color,
    textColor: Color,
) {
    Row(modifier = modifier.fillMaxWidth()) {
        if (message.role == Message.Role.USER) {
            Spacer(modifier = Modifier.weight(1f))
        } else {
            Spacer(modifier = Modifier.width(4.dp))
        }

        val surfaceBackgroundColor = if (message.role == Message.Role.USER) {
            backgroundColor.copy(alpha = 0.85f)
        } else {
            backgroundColor
        }

        Surface(
            modifier = Modifier.align(Alignment.CenterVertically),
            color = surfaceBackgroundColor,
            shape = if (message.role == Message.Role.USER) {
                RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp, bottomStart = 8.dp)
            } else {
                RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp, bottomEnd = 8.dp)
            },
        ) {
            Text(
                modifier = Modifier.padding(all = 12.dp),
                text = message.content.content!!,
                color = textColor,
            )
        }

        if (message.role != Message.Role.USER) {
            Spacer(modifier = Modifier.weight(1f))
        } else {
            Spacer(modifier = Modifier.width(4.dp))
        }
    }
}

@Composable
private fun LoadingMessage(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    textColor: Color,
) {
    Row(modifier = modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.width(4.dp))

        Surface(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .width(96.dp)
                .height(36.dp),
            color = backgroundColor,
            shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp, bottomEnd = 8.dp),
        ) {
            Box {
                LoadingIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = textColor,
                    animationType = AnimationType.Fade,
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
@Preview
private fun MessagesPreview() {
    SuppaTheme {
        Messages(
            messages = arrayListOf(
                Message(
                    id = "0",
                    content = MessageContent(
                        content = "Hello, how can I help you?",
                        type = MessageContent.Type.MESSAGE,
                        value = null,
                    ),
                    role = Message.Role.ASSISTANT,
                    createdAt = Date(),
                ),
                Message(
                    id = "0",
                    content = MessageContent(
                        content = "I need help with my order",
                        type = MessageContent.Type.USER_MESSAGE,
                        value = null,
                    ),
                    role = Message.Role.USER,
                    createdAt = Date(),
                ),
            ),
            loading = true,
        )
    }
}
