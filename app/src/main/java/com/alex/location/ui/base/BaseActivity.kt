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
 * 统一父类， 将权限控制下沉
 */
open class BaseActivity : AppCompatActivity() {

    companion object {
        const val TAG = "BaseActivity"
        const val REQ_LOCATION_PERMISSION = 0x01
    }

    /**
     * 检测app是否有位置权限
     */
    fun checkHasLocationPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(this,
            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    /**
     * 请求权限,打开系统权限弹框
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
            if (isPermissionGranted) {//已授权
                onPermissionsSucc()
            } else {//未授权
                showMissingPermissionDialog()
            }
        }
    }

    /**
     * 授权成功
     */
    open fun onPermissionsSucc() {

    }

    /**
     * 校验授权结果
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
     * 显示未授权提示对话框
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
     * 跳转到 App 的设置详情界面
     */
    private fun startAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.data = Uri.parse("package:${packageName}")
        startActivity(intent)
    }

    /**
     * 开启定位服务
     */
    fun startLocationService() {
        val intent = Intent(this@BaseActivity, NotificationService::class.java)
        startService(intent)
    }

    /**
     * 停止定位服务
     */
    fun stopLocationService() {
        val intent = Intent(this@BaseActivity, NotificationService::class.java)
        stopService(intent)
    }

    /**
     * 返回键
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