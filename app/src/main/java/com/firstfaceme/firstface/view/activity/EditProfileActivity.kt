package com.firstfaceme.firstface.view.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.PopupMenu
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.firstfaceme.firstface.R
import com.firstfaceme.firstface.controller.utills.*
import com.firstfaceme.firstface.model.UserImage
import com.firstfaceme.firstface.model.userInfo.PojoUserDetail
import com.firstfaceme.firstface.view.adapter.User_Photos_Adapter
import com.firstfaceme.firstface.view.viewmodel.ProfileViewModel
import com.firstfaceme.firstface.view.viewmodel.RegisterViewModel
import com.sea.seaconnect.view.`interface`.OnImageClickListener
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.activity_edit_profile.roundedImageView
import kotlinx.android.synthetic.main.activity_registere_profile.*
import kotlinx.android.synthetic.main.fragment_profile.*
import pub.devrel.easypermissions.EasyPermissions
import java.io.File

class EditProfileActivity : AppCompatActivity(), View.OnClickListener {
    private var viewModel: ProfileViewModel? = null
    var auth_token = ""
    var mImagefile: File? = null
    var genderSelected = 0
    var isImageList = false

    var userProfile = ""
    private val PICK_FROM_GALLARY = 1
    private var RegisterviewModel: RegisterViewModel? = null
    var mMainImagesList = mutableListOf<UserImage>()
    var mDeleteImage = mutableListOf<String>()

    private var mUserId = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        initView()
    }

    private fun initView() {
        viewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        RegisterviewModel = ViewModelProviders.of(this).get(RegisterViewModel::class.java)

        auth_token = AppPreferences.init(this).getString(Constants.AUTH_TOKEN)
        mUserId = AppPreferences.init(this).getString(Constants.USER_ID)
        getProfileData()

        // Set click listener
        iv_back.setOnClickListener(this)
        roundedImageView.setOnClickListener(this)
        etInterset.setOnClickListener(this)
        tvSaveProfile.setOnClickListener(this)


        //.........................................interested in  spinner..................................................//
        val genderSp = resources.getStringArray(R.array.Gender)

        if (sp_gender != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_dropdown_item, genderSp
            )
            sp_gender.adapter = adapter
            sp_gender.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    (view as TextView).setTextColor(Color.BLACK)
                    if (position == 0) {
                        genderSelected = 0
                    } else {
                        genderSelected = position
                        Log.e("gender", genderSelected.toString() + "")
                    }

                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                }
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_back -> {
                onBackPressed()
            }
            R.id.roundedImageView -> {
                pickPicture()
                isImageList = false

            }
            R.id.tvSaveProfile -> {

                if (userProfile == "") {
                    if (mImagefile == null) {
                        showSnackBar("Please Select Image")
                    }
                } else {
                    if (Validations.isUpdateProfile(
                            this,
                            etFirstName,
                            etLastName,
                            etBio,
                            etJob
                        )
                    ) {
                        if (genderSelected == 0) {
                            showSnackBar("Please Select Gender")

                        } else {
                            updateProfileApi()
                        }

                    }
                }
            }
            R.id.etInterset -> {
                val popupMenu: PopupMenu = PopupMenu(this, etInterset)
                popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)
                popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                    etInterset.text = item.title
                    when (item.itemId) {
                        R.id.action_male ->
                            genderSelected = 1
                        R.id.action_female ->
                            genderSelected = 2
                        R.id.action_transgender ->
                            genderSelected = 3
                    }
                    true
                })
                popupMenu.show()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }


    //.....................................get  Profile Data ............................................

    fun getProfileData() {
        clLoaderEdit.visibility = View.VISIBLE
        viewModel!!.getProfile(
            auth_token,
            mUserId
        )
            .observe(
                this,
                { pojoUserProfileData ->
                    clLoaderEdit.visibility = View.GONE

                    val statusCode = pojoUserProfileData?.statusCode
                    if (statusCode == 200) {
                        etFirstName.setText(pojoUserProfileData.data.firstName)
                        etLastName.setText(pojoUserProfileData.data.lastName)
                        etBio.setText(pojoUserProfileData.data.bio)
                        userProfile = pojoUserProfileData.data.profileImage
                        Glide.with(this).load(pojoUserProfileData.data.profileImage)
                            .into(roundedImageView)
                        etJob.setText(pojoUserProfileData.data.job)
                        genderSelected = pojoUserProfileData.data.interestedIn.toInt()
                        try {
                            mMainImagesList.clear()
                            for (i in pojoUserProfileData.data.images.indices) {
                                val userImage = UserImage(pojoUserProfileData.data.images[i])
                                mMainImagesList.add(userImage)
                            }
                        } catch (e: Exception) {

                        }

//.................... user image adapter .........................................//
                        rvImages?.layoutManager = GridLayoutManager(this@EditProfileActivity, 2)
                        rvImages?.adapter =
                            User_Photos_Adapter(this@EditProfileActivity, mMainImagesList, object :
                                OnImageClickListener {
                                override fun onImageDelete(imageId: String, postion: String) {

                                    RegisterviewModel?.deleteImage(imageId, auth_token, mUserId)
                                }

                                override fun onLocalImageDelete(position: String) {
                                    mMainImagesList.removeAt(position.toInt())

                                }

                            })
                        rvImages?.isNestedScrollingEnabled = false
                        (rvImages?.adapter as User_Photos_Adapter).onItemClick = { pos, view ->

                            isImageList = true
                            pickPicture()


                        }
                        when (pojoUserProfileData.data.interestedIn) {
                            "1" -> {
                                etInterset.text = getString(R.string.male)

                            }
                            "2" -> {
                                etInterset.text = getString(R.string.female)


                            }
                            "3" -> {
                                etInterset.text = getString(R.string.transgender)


                            }
                        }
                        tvNameAge.text =
                            pojoUserProfileData.data.firstName + " " + pojoUserProfileData.data.lastName + ", " + pojoUserProfileData.data.age

                        Glide.with(this).load(pojoUserProfileData.data.profileImage)
                            .into(roundedImageView)

                    } else {
                        showSnackBar(pojoUserProfileData!!.message)

                    }
                })
    }

    private fun showSnackBar(message: String?) {
        SnackbarUtil.showWarningShortSnackbar(this, message)
    }


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>image from camera>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    private fun pickPicture() {
        if (EasyPermissions.hasPermissions(
                applicationContext,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        ) {
            val galleryIntent = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            startActivityForResult(
                galleryIntent, PICK_FROM_GALLARY
            )
        } else {
            EasyPermissions.requestPermissions(
                this,
                "Need Permission to access your Gallery and Camera", PICK_FROM_GALLARY,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        }

    }


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>on activity result for camera and gallery>>>>>>>>>>>>>>>>>>>>>>>>>

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            PICK_FROM_GALLARY -> if (resultCode == Activity.RESULT_OK) {
                val selectedImage = data?.data

                if (isImageList) {
                    mImagefile = File(getRealPathFromURI(selectedImage))

                    val ImagesList = UserImage(imageURl = mImagefile!!, isNotUploaded = true)

                    mMainImagesList.add(ImagesList)

                    updateUserImagesApi(mImagefile!!)
                    rvImages?.adapter = User_Photos_Adapter(this, mMainImagesList, object :
                        OnImageClickListener {
                        override fun onImageDelete(imageId: String, postion: String) {
                            RegisterviewModel?.deleteImage(imageId, auth_token, mUserId)
                        }

                        override fun onLocalImageDelete(position: String) {
                            mMainImagesList.removeAt(position.toInt())

                        }

                    })
                    rvImages?.isNestedScrollingEnabled = false
                    (rvImages?.adapter as User_Photos_Adapter).onItemClick = { pos, view ->

                        isImageList = true
                        pickPicture()


                    }
                } else {
                    mImagefile = File(getRealPathFromURI(selectedImage))
                    val uri = Uri.fromFile(mImagefile)

                    Log.e("imagefile", mImagefile.toString())
//                updateProfileImageApi()
                    roundedImageView?.setImageURI(uri)
                }
            }
        }
    }

    //.................................getting real path of image....................................

    fun getRealPathFromURI(uri: Uri?): String? {
        @SuppressLint("Recycle") val cursor: Cursor =
            this.contentResolver?.query(uri!!, null, null, null, null)!!
        cursor.moveToFirst()
        val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
        return cursor.getString(idx)
    }

    //.....................................update Profile Image ............................................

    fun updateProfileImageApi() {
        clLoaderEdit.visibility = View.VISIBLE
        RegisterviewModel!!.updateProfileImage(
            auth_token,
            AppPreferences.init(App.getAppContext())
                .getString(Constants.USER_ID), mImagefile!!
        )
            .observe(
                this,

                { pojoUserProfileData ->

                    LocalBroadcastManager.getInstance(this).sendBroadcast(
                        Intent("REFERESH_FRAG"))
                    val statusCode = pojoUserProfileData?.statusCode
                    if (statusCode == 200) {
                        finish()
                    } else {
                        showSnackBar(pojoUserProfileData!!.message)

                    }
                })
    }

    //.....................................update Profile  ............................................

    fun updateProfileApi() {
        clLoaderEdit.visibility = View.VISIBLE


        viewModel!!.updateProfile(
            auth_token,
            mUserId,
            mImagefile,
            etFirstName.text.toString(),
            etLastName.text.toString().trim(),
            etBio.text.toString().trim(),
            etJob.text.toString().trim(),
            genderSelected
        )
            .observe(
                this,
                { pojoUserProfileData ->
                    clLoaderEdit.visibility = View.GONE

                    val statusCode = pojoUserProfileData?.statusCode
                    if (statusCode == 200) {
                        LocalBroadcastManager.getInstance(this).sendBroadcast(
                            Intent("REFERESH_FRAG"))
                        if (mImagefile != null) {
                            updateProfileImageApi()
                        } else {
                            clLoaderEdit.visibility = View.GONE

                            finish()
                        }

                    } else {
                        showSnackBar(pojoUserProfileData!!.message)

                    }
                })
    }

    //.....................................update user images api  ............................................

    fun updateUserImagesApi(mImagefile: File) {

        viewModel!!.updateMultipleImages(mImagefile, auth_token, mUserId)
            .observe(
                this,
                object : Observer<PojoUserDetail?> {
                    override fun onChanged(@Nullable pojoUserProfileData: PojoUserDetail?) {

                        val statusCode = pojoUserProfileData?.statusCode
                        if (statusCode == 200) {


                        } else {
                            showSnackBar(pojoUserProfileData!!.message)

                        }
                    }


                })
    }

}

