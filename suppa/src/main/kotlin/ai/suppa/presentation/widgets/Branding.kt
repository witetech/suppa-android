package ai.suppa.presentation.widgets

import ai.suppa.R
import ai.suppa.presentation.theme.SuppaTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.res.*
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
internal fun Branding(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        BrandingImage()
        Spacer(modifier = Modifier.width(8.dp))
        BrandingText()
    }
}

@Composable
private fun BrandingImage(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.width(24.dp),
        painter = painterResource(id = R.drawable.ic_suppa),
        colorFilter = ColorFilter.tint(Color.DarkGray),
        contentDescription = stringResource(id = R.string.cd_branding_icon),
    )
}

@Composable
private fun BrandingText(modifier: Modifier = Modifier) {
    Text(
        modifier = modifier,
        text = getBrandingString(),
        style = MaterialTheme.typography.bodyMedium,
        fontWeight = FontWeight.Normal,
        color = Color.DarkGray,
    )
}

@Composable
private fun getBrandingString(): AnnotatedString {
    return buildAnnotatedString {
        append(stringResource(id = R.string.branding_part_1))
        append(" ")
        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
            append(stringResource(id = R.string.branding_part_2))
        }
    }
}

@Composable
@Preview
private fun BrandingPreview() {
    SuppaTheme {
        Branding()
    }
}
