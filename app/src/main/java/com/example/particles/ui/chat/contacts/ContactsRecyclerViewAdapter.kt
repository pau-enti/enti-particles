package com.example.particles.ui.chat.contacts

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.RecyclerView
import com.example.particles.R
import com.example.particles.databinding.ItemContactBinding
import com.example.particles.ui.chat.chat.ChatActivity
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase

class ContactsRecyclerViewAdapter(val activity: Activity, val contactsVM: ContactsViewModel) :
    RecyclerView.Adapter<ContactsRecyclerViewAdapter.ViewHolder>() {

    private var contacts: List<Contact> = listOf()
    private var mInterstitialAd: InterstitialAd? = null
    private val firebaseAnalytics: FirebaseAnalytics = Firebase.analytics

    fun loadAd() {
        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(
            activity,
            "ca-app-pub-3940256099942544/1033173712",
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    mInterstitialAd = interstitialAd
                }
            })
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateContacts(contacts: List<Contact>) {
        this.contacts = contacts
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_contact, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contact = contacts[position]
        holder.name.text = contact.name

        holder.itemView.setOnClickListener {
            val intent = Intent(activity, ChatActivity::class.java)
            intent.putExtra(ChatActivity.EXTRA_USER_ID, contact.userId)

            mInterstitialAd?.apply {
                fullScreenContentCallback = object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        activity.startActivity(intent)
                        mInterstitialAd = null
                    }

                    override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                        activity.startActivity(intent)
                        mInterstitialAd = null
                    }

                    override fun onAdShowedFullScreenContent() = Unit
                }

                firebaseAnalytics.logEvent(
                    FirebaseAnalytics.Event.AD_IMPRESSION,

                    bundleOf(
                        FirebaseAnalytics.Param.AD_UNIT_NAME to adUnitId,
                        FirebaseAnalytics.Param.ITEM_NAME to "Interstitial ad",
                        FirebaseAnalytics.Param.CONTENT_TYPE to "ad"
                    ))

                show(activity) // Show ad if it is not null

            } ?: activity.startActivity(intent) // If ad is null
        }

        holder.itemView.setOnLongClickListener {
            contactsVM.deleteContact(activity, contact)
            Toast.makeText(activity, "User ${contact.name} deleted", Toast.LENGTH_SHORT).show()
            true
        }
    }

    override fun getItemCount(): Int = contacts.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemContactBinding.bind(view)

        val name: TextView = binding.contactName
        val image: ImageView = binding.contactImage
    }

}