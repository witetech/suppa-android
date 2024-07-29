package ai.suppa.presentation.widgets

import ai.suppa.R
import ai.suppa.presentation.theme.SuppaTheme
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.*
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
internal fun UserInput(
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.Black,
    iconColor: Color = Color.White,
    showBranding: Boolean = true,
    onMessageSent: (String) -> Unit = {},
) {
    var textFieldFocusState by remember { mutableStateOf(false) }
    var textState by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }

    Column(modifier = modifier.padding(horizontal = 16.dp)) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
                    .border(
                        border = BorderStroke(1.dp, Color.Gray),
                        shape = RoundedCornerShape(percent = 20),
                    ),
            ) {
                UserInputTextField(
                    modifier = Modifier.align(Alignment.CenterStart),
                    value = textState,
                    onValueChange = { textState = it },
                    onFocusChanged = { focusState ->
                        textFieldFocusState = focusState
                    },
                    onMessageSent = {
                        onMessageSent(it)
                        textState = TextFieldValue()
                    },
                )

                if (textState.text.isEmpty() && !textFieldFocusState) {
                    UserInputHint(modifier = Modifier.align(Alignment.CenterStart))
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            UserInputSendButton(
                modifier = Modifier.align(Alignment.CenterVertically),
                backgroundColor = backgroundColor,
                iconColor = iconColor,
                onSendClick = {
                    if (textState.text.isNotBlank()) {
                        onMessageSent(textState.text)
                        textState = TextFieldValue()
                    }
                },
            )
        }

        if (showBranding) {
            Branding(
                modifier =
                    Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(all = 8.dp),
            )
        } else {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun UserInputTextField(
    modifier: Modifier = Modifier,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    onFocusChanged: (Boolean) -> Unit,
    onMessageSent: (String) -> Unit,
) {
    BasicTextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp)
            .onFocusChanged { focusState ->
                onFocusChanged(focusState.isFocused)
            },
        value = value,
        onValueChange = onValueChange,
        maxLines = 1,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Send,
        ),
        keyboardActions = KeyboardActions {
            if (value.text.isNotBlank()) {
                onMessageSent(value.text)
            }
        },
    )
}

@Composable
private fun UserInputHint(modifier: Modifier = Modifier) {
    Text(
        modifier = modifier.padding(start = 32.dp),
        text = stringResource(id = R.string.user_input_hint),
    )
}

@Composable
private fun UserInputSendButton(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    iconColor: Color,
    onSendClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .size(36.dp)
            .background(backgroundColor, RoundedCornerShape(percent = 20)),
    ) {
        IconButton(
            modifier = modifier.align(Alignment.Center),
            onClick = onSendClick,
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Default.Send,
                tint = iconColor,
                contentDescription = stringResource(id = R.string.cd_send_button),
            )
        }
    }
}

@Composable
@Preview
private fun UserInputPreview() {
    SuppaTheme {
        UserInput(
            modifier = Modifier.background(Color.White),
            backgroundColor = Color.Red,
            iconColor = Color.White,
            showBranding = true,
        )
    }
}
