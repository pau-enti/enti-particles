package com.example.particles

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.particles.databinding.ActivityNasaPhotosBinding

class NASAPhotosActivity : AppCompatActivity() {
    lateinit var binding: ActivityNasaPhotosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(binding.root)
    }
}