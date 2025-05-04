package com.myproject.offlinebudgettrackerappproject.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class CameraPermissionControl {

    public static final int REQUEST_CAMERA_PERMISSION = 1001;

    public static boolean checkCameraPermission(Context context) {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED;
    }

    public static void requestCameraPermission(Activity activity) {
        if (!checkCameraPermission(activity)) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.CAMERA},
                    REQUEST_CAMERA_PERMISSION);
        } else {
            Toast.makeText(activity, "Camera permission already granted", Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean handlePermissionResult(int requestCode, int[] grantResults, Context context) {
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(context, "Camera permission granted", Toast.LENGTH_SHORT).show();
                return true;
            } else {
                Toast.makeText(context, "Camera permission denied", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return false;
    }
}
