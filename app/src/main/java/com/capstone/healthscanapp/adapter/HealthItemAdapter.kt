package com.capstone.healthscanapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.capstone.healthscanapp.R
import com.capstone.healthscanapp.data.HealthItem

class HealthItemAdapter(private val healthItems: List<HealthItem>) :
    RecyclerView.Adapter<HealthItemAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.itemTitle)
        val descriptionTextView: TextView = itemView.findViewById(R.id.itemDescription)
        val itemImageView: ImageView = itemView.findViewById(R.id.itemImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_kesehatan, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val healthItem = healthItems[position]

        holder.titleTextView.text = healthItem.title
        holder.descriptionTextView.text = healthItem.description
        holder.itemImageView.setImageResource(healthItem.imageResId)
    }

    override fun getItemCount(): Int {
        return healthItems.size
    }
}
