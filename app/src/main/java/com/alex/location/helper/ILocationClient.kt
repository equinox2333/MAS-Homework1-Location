package com.alex.location.helper

/**
 * ILocationClient Interface
 */
interface ILocationClient {

    /**
     * Setting Location Configuration Parameters
     */
    fun setLocationOption(locationOption: LocationOption)

    /**
     * Getting Location Configuration Parameters
     */
    fun getLocationOption(): LocationOption

    /**
     * Start Location
     */
    fun startLocation()

    /**
     * Stop Location
     */
    fun stopLocation()

    /**
     * Whether or not Location has started
     */
    fun isStarted(): Boolean

    /**
     * Setting up a Location Listener
     */
    fun setOnLocationListener(listener: OnLocationListener?)

    /**
     * Setting up an Exception Listener
     */
    fun setOnExceptionListener(listener: OnExceptionListener?)


}