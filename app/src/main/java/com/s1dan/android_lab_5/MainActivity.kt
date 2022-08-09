package com.s1dan.android_lab_5

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import androidx.core.view.GestureDetectorCompat

import android.view.View.OnTouchListener


class MainActivity : AppCompatActivity(), OnTouchListener {
    private lateinit var myDetector: GestureDetectorCompat
    private var upIndex = 0
    private var downIndex = 0
    private var touchFlag = false
    var idView = arrayOfNulls<TextView>(10)
    var xView = arrayOfNulls<TextView>(10)
    var yView = arrayOfNulls<TextView>(10)
    private lateinit var myText: TextView
    private lateinit var firstView: TextView
    private lateinit var secondView: TextView

    @SuppressLint("ClickableViewAccessibility")
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        firstView = findViewById(R.id.myView)
        myText = findViewById(R.id.myText)
        secondView = findViewById(R.id.secondView)
        val myPackage = packageName
        val myResources = resources
        val myGestures = GestureListener()
        myDetector = GestureDetectorCompat(this, myGestures)
        for (i in 0..9) {
            idView[i] = findViewById(myResources.getIdentifier("id$i", "id", myPackage))
            xView[i] = findViewById(myResources.getIdentifier("x$i", "id", myPackage))
            yView[i] = findViewById(myResources.getIdentifier("y$i", "id", myPackage))
        }

        firstView.setOnTouchListener(this)
        secondView.setOnTouchListener(this)
    }

    @SuppressLint("SetTextI18n")
    inner class GestureListener : GestureDetector.OnGestureListener {
        override fun onDown(motionEvent: MotionEvent): Boolean {
            idView[0]!!.text = "On Down"
            xView[0]!!.text = "-"
            yView[0]!!.text = "-"
            return true
        }

        override fun onShowPress(motionEvent: MotionEvent) {
            idView[1]!!.text = "On ShowPress"
            xView[1]!!.text = "-"
            yView[1]!!.text = "-"
        }

        override fun onSingleTapUp(motionEvent: MotionEvent): Boolean {
            idView[2]!!.text = "On Single TapUp"
            xView[2]!!.text = "-"
            yView[2]!!.text = "-"
            return true
        }

        override fun onScroll(
            motionEvent: MotionEvent, motionEvent1: MotionEvent, v: Float, v1: Float): Boolean {
            idView[3]!!.text = "On Scroll"
            xView[3]!!.text = "-"
            yView[3]!!.text = "-"
            return true
        }

        override fun onLongPress(motionEvent: MotionEvent) {
            idView[4]!!.text = "On Long Press"
            xView[4]!!.text = "-"
            yView[4]!!.text = "-"
        }

        override fun onFling(motionEvent: MotionEvent, motionEvent1: MotionEvent, v: Float, v1: Float): Boolean {
            idView[5]!!.text = "On Fling"
            xView[5]!!.text = "-"
            yView[5]!!.text = "-"
            return false
        }
    }

    @SuppressLint("SetTextI18n", "ClickableViewAccessibility")
    override fun onTouch(view: View, event: MotionEvent): Boolean {
        return when (view.id) {
            R.id.secondView -> myDetector.onTouchEvent(event)
            R.id.myView -> {
                val actionMask = event.actionMasked
                val pointerIndex = event.actionIndex
                val pointerCount = event.pointerCount
                when (actionMask) {
                    MotionEvent.ACTION_DOWN -> {
                        touchFlag = true
                        downIndex = pointerIndex
                    }
                    MotionEvent.ACTION_POINTER_DOWN -> downIndex = pointerIndex
                    MotionEvent.ACTION_UP -> {
                        touchFlag = false
                        var i = 0
                        while (i < 10) {
                            idView[i]!!.text = ""
                            xView[i]!!.text = ""
                            yView[i]!!.text = ""
                            myText.text = "Координаты касаний"
                            i++
                        }
                        upIndex = pointerIndex
                    }
                    MotionEvent.ACTION_POINTER_UP -> upIndex = pointerIndex
                    MotionEvent.ACTION_MOVE -> {
                        var i = 0
                        while (i < 10) {
                            idView[i]!!.text = ""
                            xView[i]!!.text = ""
                            yView[i]!!.text = ""
                            if (i < pointerCount) {
                                idView[i]!!.text = event.getPointerId(i).toString()
                                xView[i]!!.text = event.getX(i).toInt().toString()
                                yView[i]!!.text = event.getY(i).toInt().toString()
                            } else {
                                idView[i]!!.text = ""
                                xView[i]!!.text = ""
                                yView[i]!!.text = ""
                            }
                            i++
                        }
                    }
                }
                if (touchFlag) {
                    myText.text =
                        """
                        Количество касаний: $pointerCount
                        Индекс последнего касания: $downIndex
                        Индекс последнего отпускания: $upIndex
                        """.trimIndent()
                }
                true
            }
            else -> true
        }
    }
}