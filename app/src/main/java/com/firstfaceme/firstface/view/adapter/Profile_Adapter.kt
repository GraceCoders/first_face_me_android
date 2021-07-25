package com.firstfaceme.firstface.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firstfaceme.firstface.R
import kotlinx.android.synthetic.main.item_profile_pictures.view.*

class Profile_Adapter(val activity: FragmentActivity?, var images: List<String>) :
    RecyclerView.Adapter<Profile_Adapter.MyViewHolderFav>() {
    var onItemClick: ((pos: Int, view: View) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolderFav {
        return MyViewHolderFav(
            LayoutInflater.from(activity).inflate(R.layout.item_profile_pictures, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return images.size
    }

    override fun onBindViewHolder(holder: MyViewHolderFav, position: Int) {
        Glide.with(activity!!.applicationContext).load(images[position])
            .into(holder.itemView.ivImage)

    }

    inner class MyViewHolderFav(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        override fun onClick(v: View) {
            onItemClick?.invoke(adapterPosition, v)
        }

        init {
            itemView.setOnClickListener(this)
        }
    }
}