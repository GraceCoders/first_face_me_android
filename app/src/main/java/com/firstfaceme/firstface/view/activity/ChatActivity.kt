package com.firstfaceme.firstface.view.activity

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.firstfaceme.firstface.R
import com.firstfaceme.firstface.controller.utills.AppPreferences
import com.firstfaceme.firstface.controller.utills.Constants
import com.firstfaceme.firstface.controller.utills.SnackbarUtil
import com.firstfaceme.firstface.model.chat.PojoMessage
import com.firstfaceme.firstface.model.friends.Data
import com.firstfaceme.firstface.view.adapter.MessagesListAdapter
import com.firstfaceme.firstface.view.viewmodel.ProfileViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_chat.*
import org.json.JSONObject


class ChatActivity : AppCompatActivity(), View.OnClickListener {
    private var mAuth: FirebaseAuth? = null
    var mMessageList = mutableListOf<PojoMessage>()
    private var viewModel: ProfileViewModel? = null
    var auth_token = ""

    private var chatRoom = ""
    private var mFirebaseDatabaseInstances: FirebaseFirestore? = null
    private var mUserId = ""
    private var mOtherUserId = ""
    private val mChatListAdapter by lazy { MessagesListAdapter(this) }

    private val MAIN_COLLECTION_PATH = "AllMessages"
    private val MAIN_SUB_COLLECTION_PATH = "Messages"
    private val MAIN_FIELD_MESSAGE = "message"
    private val MAIN_FIELD_TIME = "mTime"
    private val MAIN_FIELD_USER_ID = "fromUser"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        mUserId = AppPreferences.init(this).getString(Constants.USER_ID)

        // set adapter
        rvChat.adapter = mChatListAdapter


        // firebase
        mAuth = FirebaseAuth.getInstance()
        mFirebaseDatabaseInstances = FirebaseFirestore.getInstance()


        // get data from intent
        if (intent.extras != null) {
            val data = intent!!.extras!!.getParcelable<Data>("MessageData")
            chatRoom = data!!.chatRoom
            tvName.text =
                data.otherId.firstName + " " + data.otherId.lastName
            mOtherUserId = data.otherId._id
            Picasso.with(this).load(data.otherId.profileImage)
                .into(cvUserImage)
            // get data
            getDataFromFireBase(data.chatRoom)

        }


        // set click listener
        ivSend.setOnClickListener(this)
        ivBack.setOnClickListener(this)
        ivBlock.setOnClickListener(this)
    }

    // get data from firebase////////////////////////////////////////////
    fun getDataFromFireBase(chatRoom: String) {

        val docRef =
            mFirebaseDatabaseInstances!!.collection(MAIN_COLLECTION_PATH).document(chatRoom)
                .collection(MAIN_SUB_COLLECTION_PATH)
                .orderBy(MAIN_FIELD_TIME, Query.Direction.ASCENDING)
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    try {
                        pbCenter.visibility = View.GONE
                        mMessageList.clear()

                        for (snapshot in document.documents) {
                            val job = snapshot.data!!
                            val jObject = JSONObject(snapshot.data)

                            val messagge = jObject.getString(MAIN_FIELD_MESSAGE)
                            val time = jObject.getString(MAIN_FIELD_TIME)
                            val userID = jObject.getString(MAIN_FIELD_USER_ID)
                            val pojoMessage = PojoMessage(userID, time, messagge)
                            mMessageList.add(pojoMessage)
                            Log.e("TAG", "mMessageList: " + mMessageList.size)
                            val pojoMessages = PojoMessage()
                            updateAdapter(
                                mMessageList
                            )
                        }
                    } catch (e: Exception) {

                    }

                } else {
                    pbCenter.visibility = View.GONE

                    Log.d("TAG", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                pbCenter.visibility = View.GONE

                Log.d("TAG", "get failed with ", exception)
            }

        listenForPostsValueChanges(chatRoom)
    }


    // listen data change ////////////////////////////////////////////////
    private fun listenForPostsValueChanges(chatRoom: String) {
        mFirebaseDatabaseInstances!!.collection(MAIN_COLLECTION_PATH).document(chatRoom)
            .collection(MAIN_SUB_COLLECTION_PATH)
            .orderBy(MAIN_FIELD_TIME, Query.Direction.ASCENDING)
            .addSnapshotListener(object : EventListener<QuerySnapshot?> {

                override fun onEvent(snapshots: QuerySnapshot?, e: FirebaseFirestoreException?) {
                    if (e != null) {
                        Log.w("TAG", "listen:error", e)
                        return
                    }
                    try {


                        if (snapshots != null) {
                            mMessageList.clear()
                            for (snapshot in snapshots.documents) {
                                val job = snapshot.data!!
                                val jObject = JSONObject(snapshot.data)

                                val messagge = jObject.getString(MAIN_FIELD_MESSAGE)
                                val time = jObject.getString(MAIN_FIELD_TIME)
                                val userID = jObject.getString(MAIN_FIELD_USER_ID)
                                val pojoMessage = PojoMessage(userID, time, messagge)
                                mMessageList.add(pojoMessage)
                                Log.e("TAG", "mMessageList: " + mMessageList.size)
                                val pojoMessages = PojoMessage()

                                updateAdapter(
                                    mMessageList
                                )
                                pbCenter.visibility = View.GONE
                            }

                        }
                    } catch (e: Exception) {

                    }

                }
            })
    }


    fun addData(message: String) {
        val city = hashMapOf(
            MAIN_FIELD_MESSAGE to message,
            MAIN_FIELD_USER_ID to mUserId,
            MAIN_FIELD_TIME to getTimeStamp()
        )

        mFirebaseDatabaseInstances!!.collection(MAIN_COLLECTION_PATH).document(chatRoom)
            .collection(MAIN_SUB_COLLECTION_PATH)
            .add(city)
            .addOnSuccessListener { Log.d("TAG", "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w("TAG", "Error writing document", e) }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ivSend -> {

                if (etMessage.text.toString().trim() != "") {
                    addData(etMessage.text.toString())
                    etMessage.setText("")
                }
            }
            R.id.ivBack -> {
                finish()

            }

            R.id.ivBlock -> {
                AlertDialog.Builder(this)
                    .setTitle("Block User")
                    .setMessage("Are you sure you want to block this user?")
                    .setPositiveButton(android.R.string.yes,
                        DialogInterface.OnClickListener { dialog, which ->
                            blockUser(mOtherUserId)
                        }).setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show()

            }
        }
    }


    // get current time stamp/////////////////////////////////
    fun getTimeStamp(): String {
        val timestamp = System.currentTimeMillis() / 1000
        return timestamp.toString()
    }


    /// update adapter //////////////////////////////////////
    private fun updateAdapter(mMessageLists: MutableList<PojoMessage>) {

        mChatListAdapter.addToMessagesList(null, mMessageLists)
        rvChat.scrollToPosition(mMessageLists.size - 1)

    }

    //.....................................Block User   ............................................

    fun blockUser(otherUserId: String) {
        pbCenter.visibility = View.VISIBLE
        viewModel!!.blockUser(
            auth_token,
            mUserId, otherUserId
        )
            .observe(
                this,
                { pojoUserProfileData ->
                    pbCenter.visibility = View.GONE

                    val statusCode = pojoUserProfileData?.statusCode
                    if (statusCode == 200) {


                    } else {
                        showSnackBar(pojoUserProfileData!!.message)

                    }
                })
    }

    private fun showSnackBar(message: String?) {
        SnackbarUtil.showWarningShortSnackbar(this, message)
    }
}
