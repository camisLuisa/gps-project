package com.example.camil.gpsproject

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import android.location.LocationManager
import android.location.LocationListener
import android.location.Location


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_gps.setOnClickListener({ v -> askPermission() })
    }

    private fun askPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 1)
        } else
            serviceConfig()
    }

    private fun serviceConfig() {
        try {
            val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

            val locationListener = object : LocationListener {
                override fun onLocationChanged(location: Location) {
                    updateLocation(location)
                }

                override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}

                override fun onProviderEnabled(provider: String) {}

                override fun onProviderDisabled(provider: String) {}
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, locationListener)
        } catch (ex: SecurityException) {
            Toast.makeText(this, ex.message, Toast.LENGTH_LONG).show()
        }
    }

    private fun updateLocation(location: Location) {
        var latPoint = location.latitude
        var longPoint = location.longitude

        latitude_text.text = latPoint.toString()
        longitude_text.text = longPoint.toString()

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    serviceConfig()
                } else {
                    Toast.makeText(this, "It will not work!!!", Toast.LENGTH_LONG).show()
                }
                return
            }
        }
    }
}
