package com.example.pertamax.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pertamax.R

class IconAdapter(private val iconList: List<Pair<Int, String>>) :
    RecyclerView.Adapter<IconAdapter.IconViewHolder>() {

    inner class IconViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val iconImage: ImageView = itemView.findViewById(R.id.iconImage)
        val iconLabel: TextView = itemView.findViewById(R.id.iconLabel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IconViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.icon_item, parent, false)
        return IconViewHolder(view)
    }

    override fun onBindViewHolder(holder: IconViewHolder, position: Int) {
        val (iconRes, label) = iconList[position]
        holder.iconImage.setImageResource(iconRes)
        holder.iconLabel.text = label
    }

    override fun getItemCount(): Int = iconList.size
}
