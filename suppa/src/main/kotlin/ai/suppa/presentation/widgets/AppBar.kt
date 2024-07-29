@file:OptIn(ExperimentalMaterial3Api::class)

package ai.suppa.presentation.widgets

import ai.suppa.R
import ai.suppa.presentation.theme.SuppaTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview

@Composable
internal fun AppBar(
    modifier: Modifier = Modifier,
    title: String,
    description: String? = null,
    backgroundColor: Color = Color.Black,
    textColor: Color = Color.White,
    onBackButtonClick: () -> Unit = {},
) {
    TopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(containerColor = backgroundColor),
        title = {
            AppBarTitle(
                title = title,
                description = description,
                textColor = textColor,
            )
        },
        navigationIcon = {
            AppBarBackButton(
                iconColor = textColor,
                onClick = onBackButtonClick,
            )
        },
    )
}

@Composable
private fun AppBarTitle(
    modifier: Modifier = Modifier,
    title: String,
    description: String? = null,
    textColor: Color,
) {
    Column(modifier = modifier) {
        Text(
            text = title,
            maxLines = 1,
            color = textColor,
            style = MaterialTheme.typography.titleLarge,
        )

        description?.let {
            Text(
                text = it,
                maxLines = 1,
                color = textColor,
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}

@Composable
private fun AppBarBackButton(
    modifier: Modifier = Modifier,
    iconColor: Color,
    onClick: () -> Unit,
) {
    IconButton(
        modifier = modifier,
        onClick = onClick,
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Default.ArrowBack,
            tint = iconColor,
            contentDescription = stringResource(id = R.string.cd_back_button),
        )
    }
}

@Composable
@Preview
private fun AppBarPreview() {
    SuppaTheme {
        AppBar(
            title = "Suppa",
            description = "Suppa is a chat application",
            backgroundColor = Color.DarkGray,
            textColor = Color.White,
        )
    }
}
