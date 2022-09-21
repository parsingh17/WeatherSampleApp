package com.parul.imdbapplication.common

import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

object PermissionsUtil {

    /**
     * Checks whether the app has the permission already with activity context
     */
    private fun checkPermissionAccess(context: Context, permission: String): Boolean {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
    }

    /**
     * Checks whether the app has the permission already with fragment context
     */
    fun checkPermissionAccess(fragment: Fragment, permission: String): Boolean {
        return checkPermissionAccess(fragment.requireContext(), permission)
    }

    /**
     * Single permission request
     */
    fun requestPermissionAccess(fragment: Fragment, permission: String, requestCode: Int) {
        // if (checkSelfPermission(activity, permission) == PackageManager.PERMISSION_DENIED) {
        fragment.requestPermissions(
            arrayOf(permission), requestCode
        )
        //}
    }

    fun isLocationOn(context: Context): Boolean {

        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.GPS_PROVIDER)
    }

}