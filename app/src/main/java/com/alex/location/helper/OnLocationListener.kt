package com.alex.location.helper

import android.location.Location
import android.location.LocationListener
import android.os.Bundle

/**
 * Location callback, locationclient add timeout function, callback change to success or failure.
 */
interface OnLocationListener {
    /**
     * There are a lot of parameters needed, and the Location object is returned directly.
     */
    fun onLocationSucc(location: Location)
    fun onLocationFail()
    fun onGetSatellite(total: Int, fix: Int)
    fun onGetSatelliteFail()
}