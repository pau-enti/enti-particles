package com.example.particles.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

class SimpleAdapter<T>(
    val list: ArrayList<T>,
    @LayoutRes val itemLayout: Int) :
    RecyclerView.Adapter<SimpleAdapter<T>.ViewHolder>() {

    var onGetItemIndexed: ((itemView: View, element: T, i: Int) -> Unit)? = null
    var onGetItem: ((itemView: View, element: T) -> Unit)? = null

    constructor(
        list: ArrayList<T>,
        @LayoutRes itemLayout: Int,
        onGetItem: (itemView: View, element: T) -> Unit
    ) : this(list,  itemLayout) {
        this.onGetItem = onGetItem
    }

    constructor(
        list: ArrayList<T>,
        @LayoutRes itemLayout: Int,
        onGetItemIndexed: (itemView: View, element: T, i: Int) -> Unit
    ) : this(list, itemLayout) {
        this.onGetItemIndexed = onGetItemIndexed
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(itemLayout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        onGetItem?.let { function ->
            function(holder.view, list[position])
        }

        onGetItemIndexed?.let { function ->
            function(holder.view, list[position], position)
        }
    }

    override fun getItemCount(): Int = list.size

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)
}