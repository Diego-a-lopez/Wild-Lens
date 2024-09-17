package es.dallpro.wildlens.presentation

import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import es.dallpro.wildlens.domain.Classification
import es.dallpro.wildlens.domain.LandmarkClassifier

class ImageAnalyzer(
    private val classifier: LandmarkClassifier,
    private val onResults: (List<Classification>) -> Unit
): ImageAnalysis.Analyzer {

    private var frameSkipCounter = 0

    override fun analyze(image: ImageProxy) {
        if(frameSkipCounter % 60 == 0) {
            val rotationDegrees = image.imageInfo.rotationDegrees
            val bitmap = image
                .toBitmap()
                .centerCrop(321, 321)

            val results = classifier.classify(bitmap, rotationDegrees)
            onResults(results)
        }
        frameSkipCounter++

        image.close()
    }
}