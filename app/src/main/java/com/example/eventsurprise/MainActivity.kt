package com.example.eventsurprise

import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.transition.Slide
import android.view.Gravity
import android.view.View
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
    }

    override fun onStart() {
        super.onStart()
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
}
