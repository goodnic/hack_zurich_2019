package com.example.eventsurprise

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun changeToMap(view: View) {
        val changeToMapIntent = Intent(this, MapsActivity::class.java)
        startActivity(changeToMapIntent)
    }
}
