package com.anwesh.uiprojects.linkedballtrackbouncyview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.anwesh.uiprojects.balltrackbouncyview.BallTrackBouncyView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BallTrackBouncyView.create(this)
    }
}