package com.example.eventsurprise

import android.content.Intent
import android.os.Bundle
import android.transition.Slide
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setAnimation()
        setContentView(R.layout.activity_main)

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

    fun changeToIntermediate(view: View) {
        val changeToIntermediateIntent = Intent(this, IntermediateActivity::class.java)
        startActivity(changeToIntermediateIntent)
    }

    fun changeToMap(view: View) {
        val changeToMapIntent = Intent(this, MapsActivity::class.java)
        startActivity(changeToMapIntent)
    }

    fun setAnimation() {
        val slide = Slide()
        slide.slideEdge = Gravity.RIGHT
        slide.duration = 400
        slide.interpolator = DecelerateInterpolator()
        window.exitTransition = slide
    }
}
