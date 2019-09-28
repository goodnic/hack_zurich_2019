package com.example.eventsurprise

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.gms.maps.model.LatLng

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getTimeMap(LatLng(1.0, 1.0))
    }

    fun changeToMap(view: View) {
        val changeToMapIntent = Intent(this, MapsActivity::class.java)
        startActivity(changeToMapIntent)
    }
}
