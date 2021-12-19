package com.example.particles

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import com.example.particles.databinding.ActivityLoginBinding
import com.example.particles.ui.main.MainActivity
import com.example.particles.utils.applyTransparency
import com.example.particles.utils.toast
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    val ANIMATION_DURATION = 30000L // ms

    lateinit var binding: ActivityLoginBinding
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        // Init
        startAnimations()
        firebaseAuth = FirebaseAuth.getInstance()

        // Quan l'usuari acaba d'escriure i passa al següent camp (es perd el focus), llavors comprovem
        // que les dades que ha introduit són correctes
        binding.usernameInput.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                binding.usernameInput.error =
                        // Si les dades són correctes, esborrem l'error (per si n'hi hagués)
                    if (binding.usernameInput.text.toString().isValidEmail())
                        null

                    // Si les dades no són correctes, mostrem aquest error
                    else
                        "Invalid username"
            }
        }

        // Fem el mateix check, però pel password
        binding.passwordInput.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                binding.passwordInput.error =
                    if (binding.passwordInput.text.toString().isValidPassword())
                        null
                    else
                        "Invalid password"
            }
        }

        // Incorporem funcionalitats al botó de login
        binding.loginButton.setOnClickListener {

            val user = binding.usernameInput.text.toString()
            val pass = binding.passwordInput.text.toString()

            // Comprovem que les dades al formulari siguin correctes
            if (!checkLogin(user, pass)) {
                toast("Check the data")
                return@setOnClickListener
            }

            firebaseAuth.signInWithEmailAndPassword(user, pass).addOnSuccessListener {
                // Passem les dades a la següent activity a través de l'Intent
                val intent = Intent(this, MainActivity::class.java).apply {
                    putExtra(MainActivity.USER_EXTRA, user.split("@").first())
                }

                startActivity(intent)

                // Matem la LoginAcitivity, perquè no volem que quedi "darrere" de la MainActivity
                finish()
            }.addOnFailureListener {
                toast("Incorrect user or password")
            }
        }

        // Skip login
        binding.particleIcon.setOnClickListener {
            toast("> Skip login <")
            startActivity(Intent(this, MainActivity::class.java))
        }
    }


    private fun checkLogin(user: String?, pass: String?): Boolean {
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
            binding.particleIcon, "colorFilter",
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
            binding.loginBackground, "backgroundColor",
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


