package com.example.particles

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.preference.PreferenceManager
import com.example.particles.databinding.ActivityParticleEditBinding
import java.io.FileOutputStream
import java.io.ObjectOutputStream

class ParticleEditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityParticleEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityParticleEditBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }

    fun a() {
        // Manera 1
        val sp: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = sp.edit()
//        editor.putStringSet()

        editor.apply()


        // Manera 2
        val fos: FileOutputStream = openFileOutput("fileName", Context.MODE_PRIVATE)
        val os = ObjectOutputStream(fos)
        os.writeObject(this)
        os.close()
        fos.close()
    }

}

