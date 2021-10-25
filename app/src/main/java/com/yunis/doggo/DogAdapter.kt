package com.yunis.doggo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class DogAdapter( private val dataset: ArrayList<DogImage>) :
        RecyclerView.Adapter<DogAdapter.ViewHolder>() {

    // Specify which layout to use for each item inside the recyclerview
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val items = inflater.inflate(R.layout.item_layout, parent, false)
        // Return a new holder instance
        return ViewHolder(items)
    }

    // Involves populating data into the item through holder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataset[position]
        Picasso.get().load(item.imgUrl).resize(600, 600).into(holder.imageView)


    }

    // Tells RecyclerView how many items it needs to lay out
    override fun getItemCount(): Int {
        return dataset.size
    }

    // Make layout more efficient
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView = itemView.findViewById(R.id.imageView1)

    }




}