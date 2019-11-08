package com.anwesh.uiprojects.balltrackbouncyview

/**
 * Created by anweshmishra on 08/11/19.
 */

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Color
import android.view.View
import android.view.MotionEvent
import android.content.Context
import android.app.Activity

val nodes : Int = 5
val scGap : Float = 0.01f
val strokeFactor : Int = 90
val sizeFactor : Float = 4f
val foreColor : Int = Color.parseColor("#1565C0")
val backColor : Int = Color.parseColor("#BDBDBD")
val delay : Long = 20
val rFactor : Float = 3f

fun Int.inverse() : Float = 1f / this
fun Float.maxScale(i : Int, n : Int) : Float = Math.max(0f, this - i * n.inverse())
fun Float.divideScale(i : Int, n : Int) : Float = Math.min(n.inverse(), maxScale(i, n)) * n
fun Float.sinify() : Float = Math.sin(this * Math.PI).toFloat()

fun Canvas.drawBouncyTrack(size : Float, scale : Float, h : Float, paint : Paint) {
    val sf : Float = scale.sinify()
    val sc : Float = scale.divideScale(1, 2)
    val r : Float = h / rFactor
    val y : Float = h / 2 - size * sf
    val ly : Float = (h / 2 - size) * (1 - sc)
    var ballY : Float = y
    if (sc > 0f) {
        ballY = ly
    }
    drawLine(0f, 0f, 0f, y, paint)
    drawCircle(0f, ballY, r, paint)
    drawLine(0f, 0f, 0f, ly, paint)
}

fun Canvas.drawBTBNode(i : Int, scale : Float, paint : Paint) {
    val w : Float = width.toFloat()
    val h : Float = height.toFloat()
    val gap : Float = w / (nodes + 1)
    val size : Float = gap / sizeFactor
    paint.color = foreColor
    paint.strokeCap = Paint.Cap.ROUND
    paint.strokeWidth = Math.min(w, h) / strokeFactor
    save()
    translate(gap * (i + 1), 0f)
    drawBouncyTrack(size, scale, h, paint)
    restore()
}

class BallTrackBouncyView(ctx : Context) : View(ctx) {

    private val paint : Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onDraw(canvas : Canvas) {

    }

    override fun onTouchEvent(event : MotionEvent) : Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {

            }
        }
        return true
    }

    data class State(var scale : Float = 0f, var prevScale : Float = 0f, var dir : Float = 0f) {

        fun update(cb : (Float) -> Unit) {
            scale += scGap * dir
            if (Math.abs(scale - prevScale) > 1) {
                scale = prevScale + dir
                dir = 0f
                prevScale = scale
                cb(prevScale)
            }
        }

        fun startUpdating(cb : () -> Unit) {
            if (dir == 0f) {
                dir = 1f - 2 * prevScale
                cb()
            }
        }
    }

    data class Animator(var view : View, var animated : Boolean = false) {

        fun animate(cb : () -> Unit) {
            if (animated) {
                cb()
                try {
                    Thread.sleep(delay)
                    view.invalidate()
                } catch(ex : Exception) {

                }
            }
        }

        fun start() {
            if (!animated) {
                animated = true
                view.postInvalidate()
            }
        }

        fun stop() {
            if (animated) {
                animated = false
            }
        }
    }
}
