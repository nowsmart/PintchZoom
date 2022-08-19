package com.halifax.pintchzoom

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.pow
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {
    private val STEP = 200f
    private lateinit var mytv: TextView
    private var mRatio = 1.0F
    private var mBaseDist: Int = 0
    private var mBaseRatio: Float = 0.0F
    private var fontsize = 13.0F

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mytv = findViewById(R.id.mytv)
        mytv.textSize = mRatio + fontsize
        mytv.setOnTouchListener { _: View, m: MotionEvent ->
            onTouchEvent(m)
            true
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.pointerCount == 2) {
            val action = event.action
            val pureaction = action and MotionEvent.ACTION_MASK
            if (pureaction == MotionEvent.ACTION_POINTER_DOWN) {
                mBaseDist = getDistance(event)
                mBaseRatio = mRatio
            } else {
                val delta: Float = (getDistance(event) - mBaseDist) / STEP
                val multi: Float = 2.0.pow(delta.toDouble()).toFloat()
                mRatio = 1024.0f.coerceAtMost(0.1f.coerceAtLeast(mBaseRatio * multi))
                mytv.textSize = mRatio + fontsize
            }
        }
        return true
    }

    private fun getDistance(event: MotionEvent): Int {
        val dx = event.getX(0) - event.getX(1)
        val dy = event.getY(0) - event.getY(1)
        return sqrt((dx * dx + dy * dy).toDouble()).toInt()
    }
}