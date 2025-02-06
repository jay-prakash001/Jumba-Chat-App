package com.jp.chatapp.old.presentation.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.jp.chatapp.MainActivity

fun requestPermission(context : Context, activity: MainActivity){
    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
        val hasPermission = ContextCompat.checkSelfPermission(context,Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED

        if(!hasPermission){
            ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.POST_NOTIFICATIONS),0)
        }

    }
}