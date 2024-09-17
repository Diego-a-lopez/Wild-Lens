package es.dallpro.wildlens.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import es.dallpro.wildlens.R
import es.dallpro.wildlens.ui.theme.WildLensTheme
import es.dallpro.wildlens.ui.utils.IconOnlyButtonItem
import es.dallpro.wildlens.ui.utils.Logo
import es.dallpro.wildlens.ui.utils.Title

@Composable
fun HomeScreen(
    onExit: () -> Unit,
    onSettings: () -> Unit,
    onCamera: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier,
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Title(
                Modifier
                    .fillMaxWidth()
            )
            Logo()
            BottomButtonsSection(
                onExit,
                onSettings,
                onCamera
            )
        }
    }
}

@Composable
fun BottomButtonsSection(
    onSignOut: () -> Unit,
    onSettings: () -> Unit,
    onCamera : () -> Unit,
) {
    val buttonModifier = Modifier
        .fillMaxWidth(0.65f)
        .padding(vertical = 8.dp)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            val smallButtonModifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)

            // CAMERA
            IconOnlyButtonItem(
                stringResource(R.string.settings),
                Icons.Outlined.PlayArrow,
                onCamera,
                smallButtonModifier
            )

            // SETTINGS
            IconOnlyButtonItem(
                stringResource(R.string.settings),
                Icons.Outlined.Settings,
                onSettings,
                smallButtonModifier
            )

            // EXIT
            IconOnlyButtonItem(
                stringResource(R.string.exit),
                Icons.Outlined.ExitToApp,
                onSignOut,
                smallButtonModifier
            )
        }
    }
}

@Preview(showBackground = false, showSystemUi = true)
@Composable
fun HomePreview() {
    WildLensTheme {
        HomeScreen({}, {}, {})
    }
}