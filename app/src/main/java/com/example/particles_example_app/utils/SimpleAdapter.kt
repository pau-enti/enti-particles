package com.example.particles_example_app.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

abstract class SimpleAdapter<T>(
    val list: ArrayList<T>,
    @LayoutRes val listLayout: Int,
    @LayoutRes val itemLayout: Int,
    inflater: LayoutInflater,
    container: ViewGroup?
) :
    RecyclerView.Adapter<SimpleAdapter<T>.ViewHolder>() {

    init {
        val view = inflater.inflate(listLayout, container, false)
    }

    abstract fun onGetItem(itemView: View, element: T)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(itemLayout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        onGetItem(holder.view, list[position])
    }

    override fun getItemCount(): Int = list.size

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)
}