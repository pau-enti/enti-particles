package com.example.particles_example_app

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.particles_example_app.ui.main.MainActivity
import com.example.particles_example_app.utils.applyTransparency
import com.example.particles_example_app.utils.toast
import com.google.android.material.textfield.TextInputLayout


class LoginActivity : AppCompatActivity() {

    val ANIMATION_DURATION = 30000L // ms

    lateinit var usernameInput: EditText
    lateinit var passwordInput: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        // Amaga la appBar
        supportActionBar?.hide()

        usernameInput = findViewById<TextInputLayout>(R.id.usernameInput).editText ?: return
        passwordInput = findViewById<TextInputLayout>(R.id.passwordInput).editText ?: return

        // Comencem animacions
        startAnimations()

        // Quan l'usuari acaba d'escriure i passa al següent camp (es perd el focus), llavors comprovem
        // que les dades que ha introduit són correctes
        usernameInput.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                usernameInput.error = if (usernameInput.text.isValidEmail())
                // Si les dades són correctes, esborrem l'error (per si n'hi hagués)
                    null
                else
                // Si les dades no són correctes, mostrem aquest error
                    "Invalid username"
            }
        }

        // Fem el mateix check, però pel password
        passwordInput.setOnFocusChangeListener { _, hasFocus ->
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

    /**
     * Retorna true si totes les dades són correctes
     */
    private fun validateData(): Boolean {
        return usernameInput.text.isValidEmail()
                && passwordInput.text.isValidPassword()
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