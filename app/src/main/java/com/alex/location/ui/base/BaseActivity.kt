package com.alex.location.ui.base

import android.Manifest
import android.R
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.alex.location.service.NotificationService

/**
 * Unify parent class and sink access control
 */
open class BaseActivity : AppCompatActivity() {

    companion object {
        const val TAG = "BaseActivity"
        const val REQ_LOCATION_PERMISSION = 0x01
    }

    /**
     * Detect if the app has location permissions
     */
    fun checkHasLocationPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(this,
            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    /**
     * Request permissions, open system permissions popup box
     */
    fun startPermission() {
        ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            REQ_LOCATION_PERMISSION)
    }


    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQ_LOCATION_PERMISSION) {
            var isPermissionGranted = verifyPermissions(grantResults)
            if (isPermissionGranted) { // Authorized
                onPermissionsSucc()
            } else { // Not Authorized
                showMissingPermissionDialog()
            }
        }
    }

    /**
     * Authorized
     */
    open fun onPermissionsSucc() {

    }

    /**
     * Verify authorization results
     */
    private fun verifyPermissions(grantResults: IntArray): Boolean {
        for (result in grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    /**
     * Display the Unauthorized Alert dialog box
     */
    private fun showMissingPermissionDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Location permission has not been granted.")
            .setNegativeButton("Cancel") { dialog, which ->
                finish()
            }.setPositiveButton("Setting") { dialog, which ->
                startAppSettings()
            }
        builder.show()
    }

    /**
     * Jump to the App's settings details screen
     */
    private fun startAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.data = Uri.parse("package:${packageName}")
        startActivity(intent)
    }

    /**
     * Start location services
     */
    fun startLocationService() {
        val intent = Intent(this@BaseActivity, NotificationService::class.java)
        startService(intent)
    }

    /**
     * Stop location services
     */
    fun stopLocationService() {
        val intent = Intent(this@BaseActivity, NotificationService::class.java)
        stopService(intent)
    }

    /**
     * Back Button
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}