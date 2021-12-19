package com.example.particles

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.particles.databinding.ActivityLoginBinding
import com.example.particles.ui.main.MainActivity
import com.example.particles.utils.applyTransparency
import com.example.particles.utils.toast
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth

    companion object {
        private const val ANIMATION_DURATION = 30000L // ms

        fun checkLogin(user: String?, pass: String?): Boolean {
            return user.isValidEmail() && pass.isValidPassword()
        }

        fun String?.isValidEmail(): Boolean {
            return if (this.isNullOrEmpty()) false
            else Patterns.EMAIL_ADDRESS.matcher(this).matches()
        }

        fun String?.isValidPassword(): Boolean {
            return !this.isNullOrEmpty() && this.length >= 5
        }
    }

    private val registerLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                finish()
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        // Init
        startAnimations()
        firebaseAuth = FirebaseAuth.getInstance()

        setDataCheckers()

        // Incorporem funcionalitats al botó de login
        binding.loginButton.setOnClickListener {

            val user = binding.emailInput.text.toString()
            val pass = binding.passwordInput.text.toString()

            binding.loginProgress.visibility = View.VISIBLE

            // Comprovem que les dades al formulari siguin correctes
            if (!checkLogin(user, pass)) {
                toast(getString(R.string.err_login_data))
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
                toast(getString(R.string.err_login))
            }.addOnCompleteListener {
                binding.loginProgress.visibility = View.INVISIBLE
            }
        }

        binding.register.setOnClickListener {
            registerLauncher.launch(Intent(this, RegisterActivity::class.java))
        }

        // Skip login
        binding.particleIcon.setOnClickListener {
            toast("> Skip login <")
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun setDataCheckers() {
        // Quan l'usuari acaba d'escriure i passa al següent camp (es perd el focus), llavors comprovem
        // que les dades que ha introduit són correctes
        binding.emailInput.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                binding.emailInput.error =
                        // Si les dades són correctes, esborrem l'error (per si n'hi hagués)
                    if (binding.emailInput.text.toString().isValidEmail())
                        null

                    // Si les dades no són correctes, mostrem aquest error
                    else
                        getString(R.string.err_invalid_username)
            }
        }

        // Fem el mateix check, però pel password
        binding.passwordInput.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                binding.passwordInput.error =
                    if (binding.passwordInput.text.toString().isValidPassword())
                        null
                    else
                        getString(R.string.err_invalid_password)
            }
        }
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


