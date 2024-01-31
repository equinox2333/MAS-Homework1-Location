package com.alex.location.helper

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.*
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.annotation.NonNull
import androidx.core.app.ActivityCompat


/**
 * Interface Implementation, LocationClient
 */
class LocationClient(context: Context) : ILocationClient {

    private val mContext: Context = context.applicationContext

    private val mLocationManager by lazy {
        mContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    private var mLocationOption = LocationOption()

    private var mOnLocationListener: OnLocationListener? = null

    private var mOnExceptionListener: OnExceptionListener? = null

    private var mErrorCode = LocationErrorCode.UNKNOWN_EXCEPTION

    private var handler: Handler = Handler(Looper.getMainLooper())

    // Timeout Period
    private var outTime = 10 * 1000L

    /**
     * Location Listener
     */
    private val mLocationListener by lazy {
        return@lazy object : LocationListener {
            override fun onLocationChanged(location: Location) {
                mOnLocationListener?.onLocationSucc(location)
                stopLocation()
            }

            override fun onProviderEnabled(provider: String) {
            }

            override fun onProviderDisabled(provider: String) {
            }

            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
            }
        }
    }

    /**
     * Whether or not Location has started
     */
    private var isStarted = false

    companion object {
        internal const val TAG = "LocationClient"
    }


    override fun setLocationOption(locationOption: LocationOption) {
        this.mLocationOption = locationOption
    }

    override fun getLocationOption(): LocationOption {
        return mLocationOption
    }

    override fun startLocation() {
        try {
            if (isStarted) { // If localization has already begun, direct interception
                Log.d(TAG, "isStarted = $isStarted")
                return
            }
            if (ActivityCompat.checkSelfPermission(mContext,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    mContext,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
            ) {
                mErrorCode = LocationErrorCode.PERMISSION_EXCEPTION
                // No permission to get location information
                throw SecurityException("Requires ACCESS_COARSE_LOCATION or ACCESS_FINE_LOCATION permission.")
            }

            // If provider is empty and unspecified, get best provider
            val provider = mLocationOption.mProvider ?: mLocationManager.getBestProvider(
                mLocationOption.criteria,
                true)

            Log.d(TAG, "provider: $provider")

            if (provider == null) {
                mErrorCode = LocationErrorCode.PROVIDER_EXCEPTION
                throw throw IllegalArgumentException("Provider is null or doesn't exist.")
            }

            if (mLocationOption.isLastKnownLocation) {
                Log.d(TAG, "Get last known location.")
                val location = mLocationManager.getLastKnownLocation(provider)
                if (location != null) {
                    mOnLocationListener?.onLocationSucc(location)
                    if (mLocationOption.isOnceLocation) { // If it is located only once, it is directly intercepted
                        return
                    }
                }
            }

            // Listen for location updates
            mLocationManager.requestLocationUpdates(provider,
                mLocationOption.minTime,
                mLocationOption.minDistance,
                mLocationListener)
            // Number of satellites
            if (provider.contains("gps")) {
                mLocationManager.registerGnssStatusCallback(mGnssStatusCallback)
            }
            // Timeout Stop Positioning
            handler.postDelayed({
                stopLocation()
                mOnLocationListener?.onLocationFail()
                mOnLocationListener?.onGetSatelliteFail()
            }, outTime)

            isStarted = true
            Log.d(TAG, "Start location")
        } catch (e: Exception) {
            mOnExceptionListener?.onException(mErrorCode, e)
            e.printStackTrace()
        }

    }

    var mGnssStatusCallback = object : GnssStatus.Callback() {
        override fun onSatelliteStatusChanged(status: GnssStatus) {
            var mKnownSatellites = status.satelliteCount
            var mUsedInLastFixSatellites = 0
            for (i in 0 until mKnownSatellites) {
                if (status.usedInFix(i)) {
                    mUsedInLastFixSatellites++
                }
            }
            mOnLocationListener?.onGetSatellite(mKnownSatellites, mUsedInLastFixSatellites)
            mLocationManager.unregisterGnssStatusCallback(this)
        }
    }

    override fun stopLocation() {
        try {
            if (!isStarted) { // If localization has already begun, direct interception
                Log.d(TAG, "isStarted = $isStarted")
                return
            }
            mLocationManager.removeUpdates(mLocationListener)
            mLocationManager.unregisterGnssStatusCallback(mGnssStatusCallback)
            isStarted = false
            handler.removeCallbacksAndMessages(null)
            Log.d(TAG, "Stop location")
        } catch (e: Exception) {
            mErrorCode = LocationErrorCode.UNKNOWN_EXCEPTION
            mOnExceptionListener?.onException(mErrorCode, e)
            e.printStackTrace()
        }
    }

    override fun isStarted(): Boolean {
        return isStarted
    }

    override fun setOnLocationListener(listener: OnLocationListener?) {
        mOnLocationListener = listener
    }

    override fun setOnExceptionListener(listener: OnExceptionListener?) {
        mOnExceptionListener = listener
    }

    /**
     * Providing access to the outside [LocationManager]
     */
    fun getLocationManager(): LocationManager {
        return mLocationManager
    }
}