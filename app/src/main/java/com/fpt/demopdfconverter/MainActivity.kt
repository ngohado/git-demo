package com.fpt.demopdfconverter

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import lib.folderpicker.FolderPicker
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.apache.poi.xwpf.usermodel.XWPFDocument
import java.io.File
import java.io.FileInputStream

class MainActivity : AppCompatActivity() {

    private var selectedPath: String? = null

    private val pickFile = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            selectedPath = data?.getStringExtra("data")
            Toast.makeText(this, selectedPath, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkAndAskForStoragePermission()

        findViewById<Button>(R.id.btnOpenFile).setOnClickListener {
            val intent = Intent(this, FolderPicker::class.java)
            intent.putExtra("pickFiles", true)
            intent.putExtra("title", "Select a document file to convert")
            pickFile.launch(intent)
        }

        findViewById<Button>(R.id.btnConvert).setOnClickListener {
            if (selectedPath != null) {
//                val workbook = XSSFWorkbook(selectedPath)
                val xwpfDocument = XWPFDocument(FileInputStream(File(selectedPath!!)))
                val paragraphs = xwpfDocument.paragraphs
                Log.d(TAG, "Paragraphs: ${xwpfDocument.hea}")
                for (paragraph in paragraphs) {
                    Log.d(TAG, "Paragraph: ${paragraph.text}")
                }
//                for (sheet in workbook) {
//                    Log.d(TAG, "Sheet: ${sheet.sheetName}")
//                    for (row in sheet) {
//                        for (cell in row) {
//                            cell.
//                            Log.d(TAG, "Row: ${row.rowNum}, Cell: ${cell.cellType}")
//                        }
//                    }
//                }
            } else {
                Toast.makeText(this, "Please select a file to convert", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkRuntimePermissions(permissions: Array<String>): Boolean {
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    permission,
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
    }

    private fun checkAndAskForStoragePermission() {
        if (!checkRuntimePermissions(PERMISSIONS)) {
            ActivityCompat.requestPermissions(
                this,
                PERMISSIONS,
                99,
            )

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                if (!Environment.isExternalStorageManager()) {
                    val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                    val uri = Uri.fromParts(
                        "package",
                        packageName,
                        null,
                    )
                    intent.setData(uri)
                    startActivity(intent)
                }
            }
        }
    }

    companion object {
        private const val TAG = "MainActivity"
        private val PERMISSIONS = arrayOf(
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
        )
    }
}
