package com.example.pdfviewer

import android.content.ContentValues.TAG
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentActivity
import androidx.fragment.compose.AndroidFragment
import androidx.pdf.viewer.fragment.PdfViewerFragment
import com.example.pdfviewer.ui.theme.PdfViewerTheme

class MainActivity : FragmentActivity() {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 13)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PdfViewerTheme {
                Screen()
            }
        }
    }
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 13)
@Composable
fun Screen() {
    var pdfURI: Uri? by remember { mutableStateOf(null) }

    val pickFileLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                pdfURI = uri
            }
        }

    Column(
        modifier = Modifier
            .safeContentPadding()
            .padding(32.dp)
    ) {

        ElevatedButton(
            onClick = { pickFileLauncher.launch("application/pdf") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Add, contentDescription = null)
                Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                Text("Pick PDF")
            }
        }

// Show PdfFragment when pdfURI is not null
        pdfURI?.let {
            Log.d(TAG, "Selected URI: $pdfURI")
            AndroidFragment<PdfViewerFragment>(
                arguments = bundleOf("documentUri" to pdfURI),
                modifier = Modifier.fillMaxSize()
            ) { pdfViewerFragment ->
                Log.d(TAG, "URI ${pdfViewerFragment.documentUri}")
            }

        }

    }
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 13)
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PdfViewerTheme {
        Screen()
    }
}