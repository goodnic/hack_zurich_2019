package com.example.eventsurprise

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class IntermediateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intermediate)
        // fetch data from service in the background
        runBlocking {
            withContext(Dispatchers.IO) {
                var resultList = getTimeMap(LatLng(1.0, 1.0))
                resultList.results.forEach {
                    Log.d("MAP", "ONE OF THE RESULTS: " + it.search_id)
                    it.shapes.forEach{
                        val pois = getPOIs(it.shell)
                        Log.d("POI", pois.toString())
                    }
                }
                // update UI here
                runOnUiThread {
                    Log.d("MAP", "runOnUiThread - yeah!")
                }

                val authToken = getAuthToken()
                Log.d("MAP", "authToken: " + authToken)
                val locations = getLocationData(authToken)
                Log.d("MAP", "locations : " + locations)
            }
        }
    }

    fun startActivity() {
        val intent = Intent(this, MainActivity::class.java)
        val options = ActivityOptions.makeSceneTransitionAnimation(this)
        startActivity(intent, options.toBundle())
    }
}
