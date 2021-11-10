package com.example.particles_example_app.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

class SimpleAdapter<T>(
    val list: ArrayList<T>,
    @LayoutRes val listLayout: Int,
    @LayoutRes val itemLayout: Int,
    inflater: LayoutInflater,
    container: ViewGroup?,
    val onGetItem: ((itemView: View, element: T) -> Unit)?
) :
    RecyclerView.Adapter<SimpleAdapter<T>.ViewHolder>() {

    var onGetItemIndexed: ((itemView: View, element: T, i: Int) -> Unit)? = null

    constructor(
        list: ArrayList<T>,
        @LayoutRes listLayout: Int,
        @LayoutRes itemLayout: Int,
        inflater: LayoutInflater,
        container: ViewGroup?,
        onGetItemIndexed: (itemView: View, element: T, i: Int) -> Unit
    ) : this(list, listLayout, itemLayout, inflater, container, null) {
        this.onGetItemIndexed = onGetItemIndexed
    }

    val view: View

    init {
        view = inflater.inflate(listLayout, container, false) as RecyclerView
        view.adapter = this
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