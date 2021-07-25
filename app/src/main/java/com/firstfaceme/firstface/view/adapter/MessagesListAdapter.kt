package com.firstfaceme.firstface.view.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.firstfaceme.firstface.R
import com.firstfaceme.firstface.controller.utills.AppPreferences
import com.firstfaceme.firstface.controller.utills.Constants
import com.firstfaceme.firstface.model.chat.PojoMessage
import kotlinx.android.synthetic.main.row_receiver_text_message.view.*
import kotlinx.android.synthetic.main.row_sender_text_message.view.*
import java.text.SimpleDateFormat
import java.util.*

@Suppress("DEPRECATION")
class MessagesListAdapter(private val mMessagesFragment: Activity) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val ROW_TYPE_SENDER_TEXT_MESSAGE = 0
        private const val ROW_TYPE_RECEIVER_TEXT_MESSAGE = 1
        private const val ROW_TYPE_LOAD_EARLIER_MESSAGES = 5

        const val MESSAGE_TYPE_TEXT_MESSAGE = 1
    }

    private var mMessagesList = mutableListOf<PojoMessage>()
    private var isLoadEarlierMessages: Boolean = false


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {

            ROW_TYPE_SENDER_TEXT_MESSAGE -> {
                SenderTextMessageViewHolder(
                    parent
                        .inflate(layoutRes = R.layout.row_sender_text_message)
                )
            }
            else -> {
                ReceiverTextMessageViewHolder(
                    parent
                        .inflate(layoutRes = R.layout.row_receiver_text_message)
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return mMessagesList.size
    }


    override fun getItemViewType(position: Int): Int {
        return if (mMessagesList[position].fromUser == AppPreferences.init(mMessagesFragment).getString(
                Constants.USER_ID)
        ) {
            0
        } else {

            1
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {

            ROW_TYPE_SENDER_TEXT_MESSAGE -> {
                (holder as SenderTextMessageViewHolder)
                    .bindSenderTextMessage(mMessagesList[position])
            }
            else -> {
                (holder as ReceiverTextMessageViewHolder)
                    .bindReceiverTextMessage(mMessagesList[position])
            }
        }
    }

    fun addToMessagesList(message: PojoMessage? = null, messagesList: List<PojoMessage>? = null) {
        when {
            null == message && null == messagesList -> return
            null != message -> {
                this.mMessagesList.add(message)
                notifyItemInserted(this.mMessagesList.size)
            }
            else -> {
                mMessagesList.clear()
                this.mMessagesList.addAll(messagesList!!)
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }


    inner class SenderTextMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindSenderTextMessage(messages: PojoMessage) {
            with(messages) {

                try {
                    itemView.tvSenderMessage.text = this.message
                    itemView.tvSenderTime.text = getDateCurrentTimeZone(messages.mTime.toLong())


                }catch (e:Exception)
                {

                }

            }

        }
    }

    inner class ReceiverTextMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindReceiverTextMessage(messages: PojoMessage) {

            with(messages) {

                try {
                    itemView.tvReceiverMessage.text = this.message
                    itemView.tvReceiverTime.text = getDateCurrentTimeZone(messages.mTime.toLong())

                } catch (e: Exception) {


                }
            }}

    }


    fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
        return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
    }





    fun getDateCurrentTimeZone(timestamp: Long): String? {
        try {
            val c = Calendar.getInstance()
            c.timeInMillis = timestamp.toInt() * 1000L
            val d = c.time
            val sdf = SimpleDateFormat("hh:mm a")
            return sdf.format(d)
        } catch (e: Exception) {
        }
        return ""
    }
}
