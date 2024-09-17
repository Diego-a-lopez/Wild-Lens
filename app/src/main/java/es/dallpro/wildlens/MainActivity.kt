package es.dallpro.wildlens

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import es.dallpro.wildlens.screens.CameraScreen
import es.dallpro.wildlens.screens.HomeScreen
import es.dallpro.wildlens.screens.SettingsScreen
import es.dallpro.wildlens.ui.theme.WildLensTheme
import es.dallpro.wildlens.ui.utils.BasicBackground
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.Locale

class MainActivity : ComponentActivity() {

    private val Context.dataStore by preferencesDataStore(name = "settings")

    private object PreferencesKeys {
        val LANGUAGE_KEY = stringPreferencesKey("language")
        val INITIALIZED_KEY = booleanPreferencesKey("initialized")
    }
    fun updateLocale(context: Context, language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }

    private suspend fun checkAndInitializeDataStore() {
        val preferences = applicationContext.dataStore.data.first()
        val isInitialized = preferences[PreferencesKeys.INITIALIZED_KEY] ?: false
        if (!isInitialized) {
            applicationContext.dataStore.edit { settings ->
                settings[PreferencesKeys.LANGUAGE_KEY] = "en"
                settings[PreferencesKeys.INITIALIZED_KEY] = true
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val languageFlow: Flow<String> = dataStore.data.map { preferences ->
            preferences[PreferencesKeys.LANGUAGE_KEY] ?: "en"
        }

        lifecycleScope.launch {
            checkAndInitializeDataStore()
        }


        setContent {
            val ctx = LocalContext.current
            val navController = rememberNavController()
            val language by languageFlow.collectAsState(initial = "en")

            LaunchedEffect(language) {
                updateLocale(ctx, language)
            }

            WildLensTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    BasicBackground(Modifier.fillMaxSize())
                    NavHost(navController = navController, startDestination = "home") {


                        composable("home") {
                            HomeScreen(
                                onExit = {
                                    lifecycleScope.launch {
                                        Toast.makeText(
                                            applicationContext,
                                            getString(R.string.signed_out),
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                    finishAffinity()
                                },
                                onSettings = {
                                    navController.navigate("settings")
                                },
                                onCamera = {
                                  navController.navigate("camera")
                                }
                            )
                        }

                        composable("camera") {
                            CameraScreen(
                                onExit = {
                                    navController.popBackStack()
                                }
                            )
                        }

                        //Flow variable to the settings datastore to read is values  and pass them to the settings screen
                        val preferencesLanguageFlow: Flow<String> =
                            dataStore.data.map { preferences ->
                                preferences[PreferencesKeys.LANGUAGE_KEY] ?: "en"
                            }
                        composable("settings") {
                            SettingsScreen(
                                onExit = {
                                    navController.popBackStack()
                                },
                                onEnglish = {
                                    lifecycleScope.launch {
                                        dataStore.edit { settings ->
                                            settings[PreferencesKeys.LANGUAGE_KEY] = "en"
                                        }
                                        navController.popBackStack()
                                    }
                                },
                                onSpanish = {
                                    lifecycleScope.launch {
                                        dataStore.edit { settings ->
                                            settings[PreferencesKeys.LANGUAGE_KEY] = "es"
                                        }
                                        navController.popBackStack()
                                    }
                                },
                                preferencesLanguageFlow = preferencesLanguageFlow,
                            )
                        }
                    }
                }
            }
        }
    }

}