package com.example.particles_example_app

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.particles_example_app.utils.makeSemitransparent
import kotlinx.coroutines.NonCancellable.start


class LoginActivity : AppCompatActivity() {

    val ANIMATION_DURATION = 20000L // ms

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        // Amaga la appBar
        supportActionBar?.hide()

        startAnimations()
    }

    fun startAnimations() {
        // Anima icono
        ObjectAnimator.ofInt(
            findViewById(R.id.particleIcon), "colorFilter",
            applicationContext.getColor(R.color.quarks),
            applicationContext.getColor(R.color.leptons),
            applicationContext.getColor(R.color.higgs),
            applicationContext.getColor(R.color.gauge_bosons)
        ).apply {
            duration = ANIMATION_DURATION
            setEvaluator(ArgbEvaluator())
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.REVERSE
            start()
        }

        // Anima fondo
        ObjectAnimator.ofInt(
            findViewById(R.id.loginBackground), "backgroundColor",
            applicationContext.getColor(R.color.gauge_bosons).makeSemitransparent(),
            applicationContext.getColor(R.color.higgs).makeSemitransparent(),
            applicationContext.getColor(R.color.leptons).makeSemitransparent(),
            applicationContext.getColor(R.color.quarks).makeSemitransparent()
        ).apply {
            duration = ANIMATION_DURATION
            setEvaluator(ArgbEvaluator())
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.REVERSE
            start()
        }
    }


}