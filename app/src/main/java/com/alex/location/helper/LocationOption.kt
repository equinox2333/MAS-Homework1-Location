package com.alex.location.helper

import android.location.Criteria
import android.location.LocationManager

/**
 * Locating Configuration Items
 */
class LocationOption {

    internal val criteria by lazy {
        Criteria().apply {
            isCostAllowed = false
            powerRequirement = Criteria.POWER_LOW
        }
    }

    /**
     *
     */
    internal var mProvider: String? = null

    /**
     * Minimum time interval for position update (in milliseconds)
     */
    internal var minTime = 10000L

    /**
     * Minimum distance for position update (in meters)
     */
    internal var minDistance = 0F

    /**
     * Whether to locate only once
     */
    internal var isOnceLocation = false

    /**
     * Whether to get the last known position
     */
    internal var isLastKnownLocation = true

    /**
     * Setting the accuracy requirements for providing the position
     * [accuracy] following values are supported:
     * [Criteria.NO_REQUIREMENT]
     * [Criteria.ACCURACY_COARSE]
     * [Criteria.ACCURACY_FINE]
     *
     * Please check for details: [Criteria.setAccuracy]
     */
    fun setAccuracy(accuracy: Int): LocationOption {
        criteria.accuracy = accuracy
        return this
    }

    /**
     * Setting the accuracy requirements for providing the speed
     * [speedAccuracy] following values are supported:
     * [Criteria.NO_REQUIREMENT]
     * [Criteria.ACCURACY_LOW]
     * [Criteria.ACCURACY_MEDIUM]
     * [Criteria.ACCURACY_HIGH]
     *
     * Please check for details: [Criteria.setSpeedAccuracy]
     */
    fun setSpeedAccuracy(speedAccuracy: Int): LocationOption {
        criteria.speedAccuracy = speedAccuracy
        return this
    }


    /**
     * Setting the accuracy requirements for providing the bearing
     * [bearingAccuracy] following values are supported:
     * [Criteria.NO_REQUIREMENT]
     * [Criteria.ACCURACY_LOW]
     * [Criteria.ACCURACY_MEDIUM]
     * [Criteria.ACCURACY_HIGH]
     *
     * Please check for details: [Criteria.setBearingAccuracy]
     */
    fun setBearingAccuracy(bearingAccuracy: Int): LocationOption {
        criteria.bearingAccuracy = bearingAccuracy
        return this
    }

    /**
     * Setting the power requirements
     * [powerRequirement] following values are supported:
     * [Criteria.NO_REQUIREMENT]
     * [Criteria.POWER_LOW]
     * [Criteria.POWER_MEDIUM]
     * [Criteria.POWER_HIGH]
     *
     * Please check for details: [Criteria.setPowerRequirement]
     */
    fun setPowerRequirement(powerRequirement: Int): LocationOption {
        criteria.powerRequirement = powerRequirement
        return this
    }

    /**
     * Setting whether to provide altitude information; default is false
     *
     * Please check for details: [Criteria.setAltitudeRequired]
     */
    fun setAltitudeRequired(altitudeRequired: Boolean): LocationOption {
        criteria.isAltitudeRequired = altitudeRequired
        return this
    }

    /**
     * Setting whether to provide bearing information; default is false
     *
     * Please check for details: [Criteria.setBearingRequired]
     */
    fun setBearingRequired(bearingRequired: Boolean): LocationOption {
        criteria.isBearingRequired = bearingRequired
        return this
    }

    /**
     * Setting whether to provide speed information; default is false
     *
     * Please check for details: [Criteria.setSpeedRequired]
     */
    fun setSpeedRequired(speedRequired: Boolean): LocationOption {
        criteria.isSpeedRequired = speedRequired
        return this
    }

    /**
     * Set whether to allow incurring charges (using the cellular network to locate the location will inevitably incur traffic charges); default is true
     *
     * Please check for details: [Criteria.setCostAllowed]
     */
    fun setCostAllowed(costAllowed: Boolean): LocationOption {
        criteria.isCostAllowed = costAllowed
        return this
    }

    /**
     * Set the location information provider, e.g. gps, network, etc.; if [provider] is not set or [provider] is empty, the default will be the best provider for the current device
     * [LocationManager.GPS_PROVIDER]
     * [LocationManager.NETWORK_PROVIDER]
     *
     * Please check for details: [LocationManager.getBestProvider]
     */
    fun setProvider(provider: String?): LocationOption {
        mProvider = provider
        return this
    }

    /**
     * Set the minimum time interval between position updates (in milliseconds); default interval: 10000 milliseconds, minimum interval: 1000 milliseconds
     */
    fun setMinTime(minTimeMs: Long): LocationOption {
        minTime = minTimeMs.coerceAtLeast(1000)
        return this
    }

    /**
     * Set the minimum distance for position update (unit: meter); default distance: 0 meter
     */
    fun setMinDistance(minDistanceM: Int): LocationOption {
        minDistance = minDistanceM.toFloat().coerceAtLeast(0F)
        return this
    }

    /**
     * Set whether to position only once, the default is false, when set to true, the positioning will stop automatically after positioning only once.
     */
    fun setOnceLocation(onceLocation: Boolean): LocationOption {
        isOnceLocation = onceLocation
        return this
    }

    /**
     * Sets whether to get the last known location of the cache, defaults to true
     *
     * Please check for details: [LocationManager.getLastKnownLocation]
     */
    fun setLastKnownLocation(lastKnownLocation: Boolean): LocationOption {
        isLastKnownLocation = lastKnownLocation
        return this
    }

}