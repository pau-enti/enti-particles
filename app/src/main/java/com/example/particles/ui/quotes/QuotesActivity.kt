package com.example.particles.ui.quotes

import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.particles.databinding.ActivityQuotesBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class QuotesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQuotesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        binding = ActivityQuotesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        nextQuote()

        binding.nextQuoteButton.setOnClickListener {
            transitionToRandomColor()
            nextQuote()
        }
    }

    private fun nextQuote() {
        val outside = Retrofit.Builder()
            .baseUrl("http://quotes.stormconsultancy.co.uk/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = outside.create(ApiQuotes::class.java)

        api.getPhrase().enqueue(object : Callback<Quote> {
            override fun onResponse(
                call: Call<Quote>,
                response: Response<Quote>,
            ) {
                val quote = response.body()

                binding.quoteTextView.text = quote?.quote ?: "Something went wrong :("
                binding.authorTextView.text = quote?.author ?: ""
            }

            override fun onFailure(call: Call<Quote>, t: Throwable) {
                binding.quoteTextView.text = "Something went wrong :("
                binding.authorTextView.text = ""
                binding.quotesBackground.animation.cancel()
                binding.quotesBackground.setBackgroundColor(Color.RED)
            }

        })
    }

    var lastColor = 0

    private fun transitionToRandomColor() {
        val newColor: Int =
            Color.argb(155, (0..256).random(), (0..256).random(), (0..256).random())
        binding.quotesBackground.setBackgroundColor(newColor)

        ObjectAnimator.ofArgb(
            binding.quotesBackground, "backgroundColor",
            lastColor,
            newColor,
        ).apply {
            duration = 2000
            start()
        }
        lastColor = newColor
    }
}
