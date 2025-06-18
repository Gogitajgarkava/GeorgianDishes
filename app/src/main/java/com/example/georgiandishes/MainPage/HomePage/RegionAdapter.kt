package com.example.georgiandishes.MainPage.HomePage

import android.R.attr.onClick
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.georgiandishes.MainPage.HomePage.Database.Region
import com.example.georgiandishes.R
import com.squareup.picasso.Picasso

class RegionAdapter(
    private val regions: List<Region>,
    private val onItemClick: (Region) -> Unit
) : RecyclerView.Adapter<RegionAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val regionImageView: ImageView = view.findViewById(R.id.regionImageView)
        val regionNameTextView: TextView = view.findViewById(R.id.regionNameTextView)

        fun bind(region: Region) {
            regionNameTextView.text = region.name
            if (!region.imageUrl.isNullOrEmpty()) {
                Picasso.get()
                    .load(region.imageUrl)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(regionImageView)
            } else {
                regionImageView.setImageResource(R.drawable.ic_launcher_background)
            }

            itemView.setOnClickListener {
                onItemClick(region)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.region_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val region = regions[position]
        holder.bind(region)
    }

    override fun getItemCount(): Int = regions.size
}

