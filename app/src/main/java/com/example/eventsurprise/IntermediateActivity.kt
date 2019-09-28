package com.example.eventsurprise

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.*

class IntermediateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intermediate)
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

    private fun getPOIsAsync(): Deferred<Array<PointOfInterest>> {
        return GlobalScope.async {
            val poiList = ArrayList<PointOfInterest>()
            var resultList = getTimeMap(LatLng(47.376888, 8.541694))
            resultList.results.forEach {
                it.shapes.forEach{
                    poiList.addAll(getPOIs(it.shell))
                }
            }
            poiList.toArray() as Array<PointOfInterest>
        }
    }

    fun startActivity() {
        val intent = Intent(this, MainActivity::class.java)
        val options = ActivityOptions.makeSceneTransitionAnimation(this)
        startActivity(intent, options.toBundle())
    }
}
