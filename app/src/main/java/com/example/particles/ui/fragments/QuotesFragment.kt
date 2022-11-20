package com.example.particles.ui.fragments

import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.particles.databinding.ActivityQuotesBinding
import com.example.particles.ui.quotes.ApiQuotes
import com.example.particles.ui.quotes.Quote
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class QuotesFragment : Fragment() {
    private lateinit var binding: ActivityQuotesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        super.onCreate(savedInstanceState)

        binding = ActivityQuotesBinding.inflate(inflater, container, false)

        nextQuote()

        binding.nextQuoteButton.setOnClickListener {
            transitionToRandomColor()
            nextQuote()
        }

        return binding.root
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
            }

        })

        ///////////// HANGMAN TEST
        val TEST = true
        if (TEST) {
            val hangman = Retrofit.Builder()
                .baseUrl("https://hangman-api.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiQuotes::class.java)

            hangman.newGame().enqueue(object : Callback<ApiQuotes.HangmanGame> {
                override fun onResponse(
                    call: Call<ApiQuotes.HangmanGame>,
                    response: Response<ApiQuotes.HangmanGame>,
                ) {
                    Toast.makeText(this@QuotesFragment.context,
                        "${response.body()?.hangman}",
                        Toast.LENGTH_SHORT).show()

                    response.body()?.token?.let {
                        hangman.getSolution(it).enqueue(object : Callback<ApiQuotes.HangmanGame> {
                            override fun onResponse(
                                call: Call<ApiQuotes.HangmanGame>,
                                response: Response<ApiQuotes.HangmanGame>,
                            ) {
                                Toast.makeText(this@QuotesFragment.context,
                                    "${response.body()?.solution}",
                                    Toast.LENGTH_SHORT).show()
                            }

                            override fun onFailure(
                                call: Call<ApiQuotes.HangmanGame>,
                                t: Throwable
                            ) {
                                Toast.makeText(this@QuotesFragment.context,
                                    "error: $t",
                                    Toast.LENGTH_SHORT).show()
                            }

                        })
                    }
                }

                override fun onFailure(call: Call<ApiQuotes.HangmanGame>, t: Throwable) {
                    Toast.makeText(this@QuotesFragment.context, "$t", Toast.LENGTH_SHORT).show()
                }

            })
        }
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
