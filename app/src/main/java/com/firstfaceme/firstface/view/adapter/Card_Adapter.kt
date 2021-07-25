package com.firstfaceme.firstface.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.firstfaceme.firstface.R
import com.firstfaceme.firstface.model.home.Data
import com.firstfaceme.firstface.model.home.Result
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_view.view.*


class Card_Adapter(val activity: FragmentActivity?, var data: Data) :
    RecyclerView.Adapter<Card_Adapter.MyViewHolderFav>() {

    var onItemClick: ((pos: Int, view: View, Result: Result) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolderFav {
        return MyViewHolderFav(
            LayoutInflater.from(activity).inflate(R.layout.card_view, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return data.result.size
    }

    override fun onBindViewHolder(holder: MyViewHolderFav, position: Int) {
        if (data.result[position].profileImage != null && data.result[position].profileImage != "") {
            Picasso.with(activity!!.applicationContext).load(data.result[position].profileImage)
                .into(holder.itemView.ivImage)
        }

        holder.itemView.tvName.text =
            data.result[position].firstName + " " + data.result[position].lastName + "," + data.result[position].age
        holder.itemView.tvBio.text = data.result[position].bio
        if (data.result[position].isOnline) {
            holder.itemView.tvOnline.text = activity!!.getString(R.string.online)

        } else {
            holder.itemView.tvOnline.text = activity!!.getString(R.string.offline)

        }
        holder.itemView.tvDistance.text = data.result[position].distance

    }

    inner class MyViewHolderFav(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        override fun onClick(v: View) {
            onItemClick?.invoke(adapterPosition, v, data.result[adapterPosition])
        }

        init {
            itemView.setOnClickListener(this)
        }
    }
}