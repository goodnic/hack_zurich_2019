package com.example.eventsurprise

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.*

class IntermediateActivity : AppCompatActivity() {

    private var lastLocation: LatLng? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intermediate)
        lastLocation = LatLng(
            intent.getDoubleExtra("lat", 47.376888),
            intent.getDoubleExtra("lng", 8.541694)
        )
    }

    override fun onResume() {
        super.onResume()
        // fetch data from service in the background
        runBlocking {
            val locations = getLocationsAsync().await()
            val pois = getPOIsAsync().await()
        }
    }

    private fun getLocationsAsync(): Deferred<String> {
        return GlobalScope.async {
            val authToken = getAuthToken()
            getLocationData(authToken)
        }
    }

    private fun getPOIsAsync(): Deferred<ArrayList<PointOfInterest>> {
        return GlobalScope.async {
            val poiList = ArrayList<PointOfInterest>()
            var resultList = getTimeMap(lastLocation!!)
            resultList.results.forEach {
                it.shapes.forEach{
                    poiList.addAll(getPOIs(it.shell))
                }
            }
            poiList
        }
    }

    fun startActivity() {
        val intent = Intent(this, MainActivity::class.java)
        val options = ActivityOptions.makeSceneTransitionAnimation(this)
        startActivity(intent, options.toBundle())
    }
}
