package com.example.cleangallery

import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.yalantis.ucrop.UCrop
import java.io.File

class EditorActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private var currentUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editor)

        imageView = findViewById(R.id.imageView)
        val uriString = intent.getStringExtra("image_uri")
        currentUri = Uri.parse(uriString)
        imageView.setImageURI(currentUri)

        findViewById<Button>(R.id.btnCrop).setOnClickListener {
            startCrop()
        }
    }

    private fun startCrop() {
        currentUri?.let { sourceUri ->
            val destinationFileName = "CROP_${System.currentTimeMillis()}.jpg"
            val destinationUri = Uri.fromFile(File(cacheDir, destinationFileName))
            
            UCrop.of(sourceUri, destinationUri)
                .withAspectRatio(1f, 1f)
                .start(this)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode: Int, resultCode: Int, data)
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            val resultUri = UCrop.getOutput(data!!)
            imageView.setImageURI(resultUri)
        }
    }
}
