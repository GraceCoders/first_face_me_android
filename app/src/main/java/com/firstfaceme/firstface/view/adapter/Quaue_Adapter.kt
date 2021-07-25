package com.firstfaceme.firstface.view.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.firstfaceme.firstface.R
import com.firstfaceme.firstface.model.Queue.Data
import com.firstfaceme.firstface.view.activity.MatchActivity
import com.squareup.picasso.Picasso
import com.firstfaceme.firstface.view.activity.twilioVoice.VideoActivity
import kotlinx.android.synthetic.main.item_queaue.view.*

class Quaue_Adapter(val activity: FragmentActivity?, var result: List<Data>) :
    RecyclerView.Adapter<Quaue_Adapter.MyViewHolderFav>() {
    var onItemClick: ((pos: Int, view: View, result: Data) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolderFav {
        return MyViewHolderFav(
            LayoutInflater.from(activity).inflate(R.layout.item_queaue, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return result.size
    }

    override fun onBindViewHolder(holder: MyViewHolderFav, position: Int) {
        Picasso.with(activity).load(result[position].queId.profileImage).into(holder.itemView.ivImage)
        holder.itemView.tvName.text = result[position].queId.firstName + " " + result[position].queId.lastName

        holder.itemView.setOnClickListener {

            val intent = Intent(activity, MatchActivity::class.java)
            intent.putExtra("QUEUEData",result[position])
            activity!!.startActivity(intent)
        }
    }

    inner class MyViewHolderFav(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        override fun onClick(v: View) {
            onItemClick?.invoke(adapterPosition, v, result[adapterPosition])
        }

        init {
            itemView.setOnClickListener(this)
        }
    }
}
