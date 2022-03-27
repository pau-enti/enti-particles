package com.example.particles.ui.nasa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.particles.databinding.ActivityNasaPhotosBinding

class NASAPhotosActivity : AppCompatActivity() {
    lateinit var binding: ActivityNasaPhotosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNasaPhotosBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}