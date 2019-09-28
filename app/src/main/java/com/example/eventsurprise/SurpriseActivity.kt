package com.example.eventsurprise

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_surprise.*
import kotlin.random.Random

class SurpriseActivity : AppCompatActivity() {

    var activitiesArr = arrayOf("Hiking",
        "Shopping",
        "Cycling",
        "Music",
        "History",
        "Art",
        "Gastronomy",
        "Water Sports",
        "Technology",
        "Animals",
        "Clubbing")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_surprise)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        btnResurprise.setOnClickListener{ view ->
            val rand = Random.nextInt(activitiesArr.size - 1)
            btnActivity.text = activitiesArr[rand]

            btnPeopleCount.text = "" + (rand + 1) + "                                             "
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

}
