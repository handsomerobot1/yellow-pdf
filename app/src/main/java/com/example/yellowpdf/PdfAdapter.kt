package com.example.yellowpdf

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PdfAdapter(private val pdfFiles: List<PdfFile>, private val onClick: (PdfFile) -> Unit) :
    RecyclerView.Adapter<PdfAdapter.PdfViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PdfViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pdf, parent, false)
        return PdfViewHolder(view)
    }

    override fun onBindViewHolder(holder: PdfViewHolder, position: Int) {
        holder.bind(pdfFiles[position])
    }

    override fun getItemCount(): Int = pdfFiles.size

    inner class PdfViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val pdfName: TextView = itemView.findViewById(R.id.pdfName)
        private val pdfPath: TextView = itemView.findViewById(R.id.pdfPath)

        fun bind(pdfFile: PdfFile) {
            pdfName.text = pdfFile.name
            pdfPath.text = pdfFile.path

            itemView.setOnClickListener {
                onClick(pdfFile)
            }
        }
    }
}