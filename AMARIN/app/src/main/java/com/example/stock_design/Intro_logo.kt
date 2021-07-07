package com.example.stock_design

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.stock_design.Database.DataBaseHelper


class Intro_logo : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()
//        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_intro_logo)


        val imageview=findViewById<ImageView>(R.id.imageView1)
        /*intro logo animation*/
        val animation=AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in)
        imageview.animation=animation
        val db=DataBaseHelper(this)
        db.openDatabase()

        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
                finish()
                startActivity(Intent(applicationContext, MainActivity::class.java))

            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
    }


}
