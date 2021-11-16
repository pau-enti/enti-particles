package com.example.particles_example_app

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
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
                usernameInput.error =
                        // Si les dades són correctes, esborrem l'error (per si n'hi hagués)
                    if (usernameInput.text.toString().isValidEmail())
                        null

                    // Si les dades no són correctes, mostrem aquest error
                    else
                        "Invalid username"
            }
        }

        // Fem el mateix check, però pel password
        passwordInput.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                passwordInput.error = if (passwordInput.text.toString().isValidPassword())
                    null
                else
                    "Invalid password"
            }
        }

        // Incorporem funcionalitats al botó de login
        findViewById<Button>(R.id.loginButton).setOnClickListener {

            val user = usernameInput.text.toString()
            val pass = passwordInput.text.toString()

            // Comprovem que les dades al formulari siguin correctes
            if (checkLogin(user, pass)) {
                doLogin(user, pass)

                val intent = Intent(this, MainActivity::class.java)

                // Passem les dades a la següent activity a través de l'Intent
                intent.putExtra(MainActivity.USER_EXTRA, user.split("@").first())

                startActivity(intent)

                // Matem lla LoginAcitivity, perquè no volem que quedi "darrere" de la MainActivity
                finish()
            } else {
                toast("Check the data")
            }
        }

        // Skip login
        findViewById<ImageView>(R.id.particleIcon).setOnClickListener {
            toast("> Skip login <")
            startActivity(Intent(this, MainActivity::class.java))
        }
    }


    private fun doLogin(user: String, pass: String) {
        // TODO: Aquí l'app hauria de memoritzar les dades per tal que el login fos efectiu
    }

    private fun checkLogin(user: String?, pass: String?): Boolean {
        // TODO: Aquí també s'ha de consultar a la BBDD que la contrasenya és correcta
        return user.isValidEmail() && pass.isValidPassword()
    }


    private fun String?.isValidEmail(): Boolean {
        return if (this.isNullOrEmpty()) false
        else Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }

    private fun String?.isValidPassword(): Boolean {
        return !this.isNullOrEmpty() && this.length >= 5
    }


    private fun startAnimations() {
        // Anima icono
        ObjectAnimator.ofInt(
            findViewById(R.id.particleIcon), "colorFilter",
            applicationContext.getColor(R.color.leptons),
            applicationContext.getColor(R.color.quarks),
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
            applicationContext.getColor(R.color.quarks).applyTransparency(),
            applicationContext.getColor(R.color.leptons).applyTransparency(),
            applicationContext.getColor(R.color.gauge_bosons).applyTransparency(),
            applicationContext.getColor(R.color.higgs).applyTransparency()
        ).apply {
            duration = ANIMATION_DURATION
            setEvaluator(ArgbEvaluator())
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.REVERSE
            start()
        }
    }


}