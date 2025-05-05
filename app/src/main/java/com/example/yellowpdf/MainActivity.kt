package com.example.yellowpdf

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.angads25.filepicker.controller.DialogSelectionListener
import com.github.angads25.filepicker.model.DialogConfigs
import com.github.angads25.filepicker.model.DialogProperties
import com.github.angads25.filepicker.view.FilePickerDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.File

class MainActivity : AppCompatActivity() {
    private lateinit var pdfRecyclerView: RecyclerView
    private lateinit var addButton: FloatingActionButton
    private lateinit var pdfAdapter: PdfAdapter
    private val pdfFiles = mutableListOf<PdfFile>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pdfRecyclerView = findViewById(R.id.pdfRecyclerView)
        addButton = findViewById(R.id.addButton)

        setupRecyclerView()
        setupClickListeners()
        loadPdfFiles()
    }

    private fun setupRecyclerView() {
        pdfAdapter = PdfAdapter(pdfFiles) { pdfFile ->
            val intent = Intent(this, PdfViewerActivity::class.java).apply {
                putExtra("pdf_path", pdfFile.path)
            }
            startActivity(intent)
        }

        pdfRecyclerView.layoutManager = LinearLayoutManager(this)
        pdfRecyclerView.adapter = pdfAdapter
    }

    private fun setupClickListeners() {
        addButton.setOnClickListener {
            showFilePicker()
        }
    }

    private fun showFilePicker() {
        val properties = DialogProperties().apply {
            selection_mode = DialogConfigs.SINGLE_MODE
            selection_type = DialogConfigs.FILE_SELECT
            root = File(DialogConfigs.DEFAULT_DIR)
            error_dir = File(DialogConfigs.DEFAULT_DIR)
            extensions = arrayOf(".pdf")
        }

        val dialog = FilePickerDialog(this, properties)
        dialog.setTitle("Select PDF File")

        dialog.setDialogSelectionListener { files ->
            files.firstOrNull()?.let { path ->
                val file = File(path)
                val pdfFile = PdfFile(
                    name = file.name,
                    path = file.absolutePath
                )
                pdfFiles.add(pdfFile)
                pdfAdapter.notifyDataSetChanged()
            }
        }

        dialog.show()
    }

    private fun loadPdfFiles() {
        // Sample PDF - replace with actual loading logic
        val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val sampleFile = File(downloadsDir, "sample.pdf")

        if (sampleFile.exists()) {
            val samplePdf = PdfFile(
                name = sampleFile.name,
                path = sampleFile.absolutePath
            )
            pdfFiles.add(samplePdf)
            pdfAdapter.notifyDataSetChanged()
        }
    }
}