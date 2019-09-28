package com.example.eventsurprise

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.transition.Slide
import android.view.Gravity
import android.view.View
import android.widget.Toast
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class MainActivity : AppCompatActivity() {

    var lastLocation: Location? = null

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setAnimation()
        setContentView(R.layout.activity_main)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        btnSurpriseMe.setOnClickListener{
            val intent = Intent(this, SurpriseActivity::class.java).apply {
            }
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        checkIfGpsEnabled()
        fusedLocationClient.lastLocation
            .addOnSuccessListener {
                lastLocation = it
            }
    }

    fun changeToIntermediate(view: View) {
        val changeToIntermediateIntent = Intent(this, IntermediateActivity::class.java)
        changeToIntermediateIntent.putExtra("lat", lastLocation!!.latitude)
        changeToIntermediateIntent.putExtra("lng", lastLocation!!.longitude)
        startActivity(changeToIntermediateIntent)
    }

    fun setAnimation() {
        val slide = Slide()
        slide.slideEdge = Gravity.RIGHT
        slide.duration = 400
        slide.interpolator = DecelerateInterpolator()
        window.exitTransition = slide
    }

    private fun checkIfGpsEnabled() {
        val manager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessage()
        }
    }

    private fun buildAlertMessage() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
            .setCancelable(false)
            .show()
    }
}
