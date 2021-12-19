package com.example.particles

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.particles.LoginActivity.Companion.isValidEmail
import com.example.particles.LoginActivity.Companion.isValidPassword
import com.example.particles.databinding.ActivityRegisterBinding
import com.example.particles.ui.main.MainActivity
import com.example.particles.utils.toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    private fun String?.isValidName(): Boolean {
        return !this.isNullOrEmpty()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        setDataCheckers()

        binding.signUpButton.setOnClickListener {
            val email = binding.emailInput.text.toString()
            val name = binding.nameInput.text.toString()
            val surname = binding.lastNameInput.text.toString()
            val pass = binding.passwordInput.text.toString()
            val rpass = binding.repeatPasswordInput.text.toString()


            if (checkSignUp(email, name, surname, pass, rpass)) {
                binding.singnUpProgress.visibility = View.VISIBLE

                firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnSuccessListener {
                    // Fem login
                    firebaseAuth.signInWithEmailAndPassword(email, pass)

                    // Guardem les dades de l'usuari
                    db.collection("users").document(email).set(
                        hashMapOf(
                            "name" to name,
                            "last_names" to surname
                        )
                    )

                    // TODO should check errors on this 2 calls

                    toast(getString(R.string.register_ok))

                    // Notify the user has registered successfully
                    setResult(Activity.RESULT_OK)
                    finish()
                    startActivity(Intent(this, MainActivity::class.java))
                }.addOnFailureListener {
                    toast(getString(R.string.err_general))
                }.addOnCompleteListener {
                    binding.singnUpProgress.visibility = View.INVISIBLE
                }

            } else
                toast(getString(R.string.err_login_data))

        }

    }

    private fun checkSignUp(
        email: String,
        name: String,
        surname: String,
        pass: String,
        rpass: String
    ): Boolean {
        return name.isValidName() && surname.isValidName() && email.isValidEmail() && pass.isValidPassword() && rpass == pass
    }

    private fun setDataCheckers() {
        binding.emailInput.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus && !binding.emailInput.text.toString().isValidEmail())
                binding.emailInput.error = getString(R.string.err_login_data)
        }

        binding.nameInput.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus && !binding.nameInput.text.toString().isValidName())
                binding.nameInput.error = getString(R.string.err_login_data)
        }

        binding.lastNameInput.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus && !binding.lastNameInput.text.toString().isValidName())
                binding.lastNameInput.error = getString(R.string.err_login_data)
        }

        binding.passwordInput.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus && !binding.passwordInput.text.toString().isValidPassword())
                binding.passwordInput.error = getString(R.string.err_login_data)
        }

        binding.repeatPasswordInput.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus && binding.repeatPasswordInput.text == binding.passwordInput.text)
                binding.repeatPasswordInput.error = getString(R.string.err_password_mismatches)
        }
    }
}