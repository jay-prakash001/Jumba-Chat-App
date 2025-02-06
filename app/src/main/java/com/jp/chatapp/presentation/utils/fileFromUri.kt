package com.jp.chatapp.old.presentation.utils

import android.content.Context
import android.net.Uri
import java.io.FileNotFoundException

 fun getFileBytesFromUri(context: Context, uri: Uri): ByteArray {
    return context.contentResolver.openInputStream(uri)?.use { inputStream ->
        inputStream.readBytes()
    } ?: throw FileNotFoundException("Unable to open URI: $uri")
}