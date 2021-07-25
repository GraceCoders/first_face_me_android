package com.firstfaceme.firstface.view.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.firstfaceme.firstface.R
import com.firstfaceme.firstface.model.friends.Data
import com.firstfaceme.firstface.view.activity.ChatActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_message.view.*


class Message_Adapter(val activity: FragmentActivity?, var data: List<Data>) :
    RecyclerView.Adapter<Message_Adapter.MyViewHolderFav>() {
    var onItemClick: ((pos: Int, view: View) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolderFav {
        return MyViewHolderFav(
            LayoutInflater.from(activity).inflate(R.layout.item_message, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolderFav, position: Int) {
        try {


            holder.itemView.tvName.text =
                data[position].otherId.firstName + " " + data[position].otherId.lastName
            Picasso.with(activity).load(data[position].otherId.profileImage)
                .into(holder.itemView.cvUserImage)

            holder.itemView.setOnClickListener {
                val intent = Intent(activity, ChatActivity::class.java)
                intent.putExtra("MessageData", data[position])
                activity!!.startActivity(intent)
            }
        }catch (e:Exception)
        {

        }
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