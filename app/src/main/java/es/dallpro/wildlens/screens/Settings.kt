package es.dallpro.wildlens.screens

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import es.dallpro.wildlens.R
import es.dallpro.wildlens.ui.theme.WildLensTheme
import es.dallpro.wildlens.ui.utils.BackButton
import es.dallpro.wildlens.ui.utils.ButtonItem
import es.dallpro.wildlens.ui.utils.Logo
import es.dallpro.wildlens.ui.utils.OutlineTextSection
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    onExit: () -> Unit,
    onSpanish: () -> Unit,
    onEnglish: () -> Unit,
    preferencesLanguageFlow: Flow<String>,
) {
    Box(
        modifier,
        contentAlignment = Alignment.Center,
    ) {
        SettingsScreenLayout(
            modifier.fillMaxSize(),
            onSpanish,
            onEnglish,
            preferencesLanguageFlow,
        )

        BackButton(
            onExit, Modifier
                .align(Alignment.TopEnd)
                .padding(top = 20.dp)
        )
    }
}

@Composable
fun SettingsScreenLayout(
    modifier: Modifier = Modifier,
    onSpanish: () -> Unit,
    onEnglish: () -> Unit,
    preferencesLanguageFlow: Flow<String>,
) {
    val ctx = LocalContext.current

    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceAround,
    ) {
        BottomButtonsSection(
            ctx,
            onSpanish,
            onEnglish,
            preferencesLanguageFlow,
        )
    }
}

@Composable
fun BottomButtonsSection(
    ctx: Context,
    onSpanish: () -> Unit,
    onEnglish: () -> Unit,
    preferencesLanguageFlow: Flow<String>,
) {
    val buttonModifier = Modifier
        .padding(vertical = 8.dp)
        .fillMaxWidth()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        OutlineTextSection(
            stringResource(R.string.settings),
            textSize = MaterialTheme.typography.displayMedium.fontSize
        )
        Spacer(modifier = Modifier.height(16.dp))
        Logo(modifier = Modifier.align(Alignment.CenterHorizontally))
        LabeledSetting(stringResource(R.string.language))
        LanguageSettings(ctx, onSpanish, onEnglish, preferencesLanguageFlow)
    }
}

@Composable
fun LabeledSetting(label: String) {
    OutlineTextSection(
        label,
        contentAlignment = Alignment.CenterStart,
        textSize = MaterialTheme.typography.displayMedium.fontSize
    )
}

@Composable
fun LanguageSettings(
    ctx: Context,
    onSpanish: () -> Unit,
    onEnglish: () -> Unit,
    preferencesLanguageFlow: Flow<String>
) {
    val languageState by preferencesLanguageFlow.collectAsState(initial = "en")
    val buttonModifier = Modifier
        .padding(vertical = 8.dp)
        .fillMaxWidth()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(vertical = 8.dp),
    ) {
        ButtonItem(
            text = stringResource(R.string.english),
            onClick = { onEnglish() },
            modifier = buttonModifier,
            enabled = languageState != "en"
        )
        ButtonItem(
            text = stringResource(R.string.spanish),
            onClick = { onSpanish() },
            modifier = buttonModifier,
            enabled = languageState != "es"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsPreview() {
    WildLensTheme {
        SettingsScreen(
            onExit = { println("Exit") },
            onEnglish = { println("English") },
            onSpanish = { println("English") },
            preferencesLanguageFlow = flow { "English" },
        )
    }
}