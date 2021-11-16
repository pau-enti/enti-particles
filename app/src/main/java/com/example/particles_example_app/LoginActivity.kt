package com.example.particles_example_app

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.particles_example_app.ui.main.MainActivity
import com.example.particles_example_app.utils.applyTransparency
import com.example.particles_example_app.utils.toast
import com.google.android.material.textfield.TextInputLayout


class LoginActivity : AppCompatActivity() {

    val ANIMATION_DURATION = 30000L // ms

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        // Amaga la appBar
        supportActionBar?.hide()

        // Comencem animacions
        startAnimations()
        val usernameInput = findViewById<TextInputLayout>(R.id.usernameInput).editText
        val passwordInput = findViewById<TextInputLayout>(R.id.passwordInput).editText

        usernameInput?.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                usernameInput.error = if (usernameInput.text.isValidEmail())
                    null
                 else
                    "Invalid username"
            }
        }

        passwordInput?.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                passwordInput.error = if (passwordInput.text.isValidPassword())
                    null
                else
                    "Invalid password"
            }
        }

        // Incorporem funcionalitats al botó de login
        findViewById<Button>(R.id.loginButton).setOnClickListener {
            // Comprovem que les dades al formulari siguin correctes
            if (validateData()) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

                // Matem lla LoginAcitivity, perquè no volem que quedi "darrere" de la MainActivity
                finish()
            } else {
                toast("Check the data")
            }
        }
    }

    private fun validateData(): Boolean {
        return findViewById<TextInputLayout>(R.id.usernameInput).editText?.text.isValidEmail()
                && findViewById<TextInputLayout>(R.id.passwordInput).editText?.text.isValidPassword()
    }

    private fun CharSequence?.isValidEmail(): Boolean {
        return if (this.isNullOrEmpty()) false
        else Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }

    private fun CharSequence?.isValidPassword(): Boolean {
        return !this.isNullOrEmpty() && this.length >= 5
    }

    private fun TextInputLayout.isValidPassword(): Boolean {
        val text = this.editText?.text
        return if (text.isNullOrEmpty() || text.length < 5) {
            this.error = "Invalid password"
            false
        } else {
            this.error = null
            true
        }
    }

    private fun startAnimations() {
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
            applicationContext.getColor(R.color.gauge_bosons).applyTransparency(),
            applicationContext.getColor(R.color.higgs).applyTransparency(),
            applicationContext.getColor(R.color.leptons).applyTransparency(),
            applicationContext.getColor(R.color.quarks).applyTransparency()
        ).apply {
            duration = ANIMATION_DURATION
            setEvaluator(ArgbEvaluator())
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.REVERSE
            start()
        }
    }


}