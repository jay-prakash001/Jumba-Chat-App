package com.jp.chatapp.presentation.utils

import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap

fun fileType(context: Context, uri: Uri): String? {

    val mimeType =  context.contentResolver.getType(uri)
    return MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType)
}