package com.example.eventsurprise

import android.content.Intent
import android.os.Bundle
import android.transition.Slide
import android.view.Gravity
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setAnimation()
        setContentView(R.layout.activity_main)


    }

    fun changeToIntermediate(view: View) {
        val changeToIntermediateIntent = Intent(this, IntermediateActivity::class.java)
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
