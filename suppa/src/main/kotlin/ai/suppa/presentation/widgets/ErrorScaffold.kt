package ai.suppa.presentation.widgets

import ai.suppa.R
import ai.suppa.presentation.*
import ai.suppa.presentation.ChatViewModel.State
import ai.suppa.presentation.theme.SuppaTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.res.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
internal fun ErrorScaffold(
    state: State.Error,
    onRetryClick: () -> Unit = {},
) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(
                modifier = Modifier.padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    modifier = Modifier.width(48.dp),
                    painter = painterResource(id = R.drawable.ic_suppa),
                    contentDescription = stringResource(id = R.string.cd_branding_icon),
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = state.message)
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = onRetryClick) {
                    Text(text = stringResource(id = R.string.retry_button))
                }
            }
        }
    }
}

@Composable
@Preview
private fun ErrorScaffoldPreview() {
    SuppaTheme {
        ErrorScaffold(
            state = State.Error(message = "Something went wrong, please try again"),
        )
    }
}
