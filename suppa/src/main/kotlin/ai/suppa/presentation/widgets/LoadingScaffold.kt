package ai.suppa.presentation.widgets

import ai.suppa.R
import ai.suppa.presentation.theme.SuppaTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.res.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
internal fun LoadingScaffold() {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Pulsating {
                Image(
                    modifier = Modifier
                        .width(48.dp)
                        .padding(innerPadding),
                    painter = painterResource(id = R.drawable.ic_suppa),
                    contentDescription = stringResource(id = R.string.cd_branding_icon),
                )
            }
        }
    }
}

@Composable
@Preview
private fun LoadingScaffoldPreview() {
    SuppaTheme {
        LoadingScaffold()
    }
}
