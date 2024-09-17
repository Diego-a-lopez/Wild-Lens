package es.dallpro.wildlens.screens

import android.content.Context
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import es.dallpro.wildlens.data.TfLiteLandmarkClassifier
import es.dallpro.wildlens.domain.Classification
import es.dallpro.wildlens.presentation.CameraPreview
import es.dallpro.wildlens.presentation.ImageAnalyzer
import es.dallpro.wildlens.ui.theme.WildLensTheme

@Composable
fun CameraScreen (onExit: () -> Unit) {

    val applicationContext : Context = LocalContext.current

    WildLensTheme {
        var classifications by remember {
            mutableStateOf(emptyList<Classification>())
        }
        val analyzer = remember {
            ImageAnalyzer(
                classifier = TfLiteLandmarkClassifier(
                    context = applicationContext
                ),
                onResults = {
                    classifications = it
                }
            )
        }
        val controller = remember {
            LifecycleCameraController(applicationContext).apply {
                setEnabledUseCases(CameraController.IMAGE_ANALYSIS)
                setImageAnalysisAnalyzer(
                    ContextCompat.getMainExecutor(applicationContext),
                    analyzer
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            CameraPreview(controller, Modifier.fillMaxSize())

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
            ) {
                classifications.forEach {
                    Text(
                        text = it.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.primaryContainer)
                            .padding(8.dp),
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}
