package com.example.yellowpdf

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.File

class PdfViewerActivity : AppCompatActivity() {
    private lateinit var pdfView: PDFView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf_viewer)

        pdfView = findViewById(R.id.pdfView)
        progressBar = findViewById(R.id.progressBar)

        val pdfPath = intent.getStringExtra("pdf_path")
        pdfPath?.let { loadPdf(it) }
    }

    private fun loadPdf(path: String) {
        progressBar.visibility = View.VISIBLE

        pdfView.fromFile(File(path))
            .enableSwipe(true)
            .swipeHorizontal(false)
            .enableDoubletap(true)
            .defaultPage(0)
            .onLoad { nbPages ->
                progressBar.visibility = View.GONE
            }
            .onError { t ->
                progressBar.visibility = View.GONE
                Toast.makeText(this, "Error loading PDF: ${t.message}", Toast.LENGTH_SHORT).show()
            }
            .onPageError { page, t ->
                Toast.makeText(this, "Error on page $page: ${t.message}", Toast.LENGTH_SHORT).show()
            }
            .load()
    }
}