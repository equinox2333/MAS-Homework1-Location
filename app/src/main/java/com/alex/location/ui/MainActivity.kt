package com.alex.location.ui

import android.content.Intent
import android.location.Criteria
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import com.alex.location.bean.ApiBean
import com.alex.location.databinding.ActivityMainBinding
import com.alex.location.helper.LocationClient
import com.alex.location.helper.LocationOption
import com.alex.location.helper.OnLocationListener
import com.alex.location.net.ResultBean
import com.alex.location.net.RetrofitBuilder
import com.alex.location.service.NotificationService
import com.alex.location.ui.base.BaseActivity
import com.google.firebase.database.FirebaseDatabase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 *  Main Page
 */
class MainActivity : BaseActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val locationOptionMix by lazy {
        LocationOption().setProvider(LocationManager.NETWORK_PROVIDER) // Set provider: can be changed to gps
            .setAccuracy(Criteria.ACCURACY_FINE) // Setting position accuracy: High accuracy
            .setPowerRequirement(Criteria.POWER_HIGH) // Setting power consumption: low power consumption
            .setOnceLocation(true) // Set whether to position only once, the default is false, when set to true, the positioning will stop automatically after positioning only once.
            .setLastKnownLocation(false) // Sets whether to get the last known location of the cache, defaults to true
    }
    var dbinstanse = FirebaseDatabase.getInstance().reference

    private var result = mutableListOf<ApiBean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        if (checkHasLocationPermission()) {
        } else {
            startPermission()
        }
        // Click Event
        binding.btnNotificationClose.setOnClickListener {
            stopLocationService()
        }
        binding.btnNotificationOpen.setOnClickListener {
            startLocationService()
        }
        binding.btnGetSensor.setOnClickListener {
            startMixLocation()
        }
        binding.btnNetRequest.setOnClickListener {
            if (mlocation == null) {
                startMixLocation()
            } else {
                sendRequest(mlocation!!.latitude, mlocation!!.longitude, mlocation!!.provider)
            }
        }
        binding.btnChangeColor.setOnClickListener {
            changeColor()
        }
        binding.btnUserInput.setOnClickListener {
            val intent = Intent(this@MainActivity, ProfileActivity::class.java)
            intent.putExtra("mode", i);
            startActivity(intent)
        }
        changeColor()
    }

    var i = 0;

    fun changeColor() {
        i++;
        if (i % 2 == 0) {
            binding.layoutall.setBackgroundColor(resources.getColor(android.R.color.black))
            binding.btnChangeColor.setTextColor(resources.getColor(android.R.color.black))
            binding.btnNotificationOpen.setTextColor(resources.getColor(android.R.color.black))
            binding.btnNotificationClose.setTextColor(resources.getColor(android.R.color.black))
            binding.btnGetSensor.setTextColor(resources.getColor(android.R.color.black))
            binding.btnUserInput.setTextColor(resources.getColor(android.R.color.black))
            binding.btnNetRequest.setTextColor(resources.getColor(android.R.color.black))
            binding.tvSensor.setTextColor(resources.getColor(android.R.color.white))
            binding.tvNet.setTextColor(resources.getColor(android.R.color.white))

        } else {
            binding.layoutall.setBackgroundColor(resources.getColor(android.R.color.white))
            binding.btnChangeColor.setTextColor(resources.getColor(android.R.color.white))
            binding.btnNotificationOpen.setTextColor(resources.getColor(android.R.color.white))
            binding.btnNotificationClose.setTextColor(resources.getColor(android.R.color.white))
            binding.btnGetSensor.setTextColor(resources.getColor(android.R.color.white))
            binding.btnNetRequest.setTextColor(resources.getColor(android.R.color.white))
            binding.btnUserInput.setTextColor(resources.getColor(android.R.color.white))
            binding.tvSensor.setTextColor(resources.getColor(android.R.color.black))
            binding.tvNet.setTextColor(resources.getColor(android.R.color.black))
        }
    }

    private val geocoder by lazy {
        Geocoder(this)
    }
    private val locationClientMix by lazy {
        LocationClient(this)
    }
    var mlocation: Location? = null;

    fun startMixLocation() {
        locationClientMix.setLocationOption(locationOptionMix)
        // Setting up Location Listening
        locationClientMix.setOnLocationListener(object : OnLocationListener {
            override fun onLocationSucc(location: Location) {
                Log.e(NotificationService.TAG,
                    "onLocationSucc mix ${location.latitude}   ${location.longitude}")
                binding.tvSensor.text =
                    "latitude:  ${location.latitude}  longitude: ${location.longitude}"
                mlocation = location
                dbinstanse.child("users").child(App.currentUser?.username.toString())
                    .child("sensorData").setValue(binding.tvSensor.text)
                sendRequest(location.latitude, location.longitude, location.provider)
            }

            override fun onLocationFail() {
                Log.e(NotificationService.TAG, "onLocationFail mix ")
                // db save
            }

            override fun onGetSatellite(total: Int, fix: Int) {
            }

            override fun onGetSatelliteFail() {
            }
        })
        if (locationClientMix.isStarted()) { // If positioning has already started, stop positioning first
            locationClientMix.stopLocation()
        }
        locationClientMix.startLocation()
    }

    /**
     * Network Requests
     */
    fun sendRequest(latitude: Double, longitude: Double, provider: String) {
        val api = RetrofitBuilder.apiService
        api.getValue(latitude, longitude).enqueue(object : Callback<ResultBean> {
            override fun onResponse(call: Call<ResultBean>, response: Response<ResultBean>) {
                var netResponse: ResultBean? = response.body()
                Log.e(NotificationService.TAG, "response succ  ${netResponse}")
                var apiBean = ApiBean()
                apiBean.uv = netResponse?.result?.uv.toString()
                apiBean.uv_time = netResponse?.result?.uv_time.toString()
                apiBean.dawn = netResponse?.result?.sun_info?.sun_times?.dawn
                apiBean.dusk = netResponse?.result?.sun_info?.sun_times?.dusk

                // Obtain location address information based on coordinates latitude and longitude (WGS-84 coordinate system)
                val list = geocoder.getFromLocation(latitude, longitude, 1)
                if (list.isNotEmpty()) {
                    apiBean.geoAddress = list[0].getAddressLine(0)
                }
                // update ui
                binding.tvNet.text = "${apiBean.toString()}"
            }

            override fun onFailure(call: Call<ResultBean>, t: Throwable) {
                Log.e(NotificationService.TAG, "onFailure ${t.message}")
                // Exception handling, can put latitude and longitude into the db and put error messages or something else into the db
            }
        })
    }

}