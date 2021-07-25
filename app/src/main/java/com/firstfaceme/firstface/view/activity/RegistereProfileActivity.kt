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
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.firstfaceme.firstface.R
import com.firstfaceme.firstface.controller.utills.*
import com.firstfaceme.firstface.model.registerModel.RegisterProfileResponse
import com.firstfaceme.firstface.view.viewmodel.RegisterViewModel
import com.sea.seaconnect.controller.utills.PermissionCheckUtil
import kotlinx.android.synthetic.main.activity_registere_profile.*
import pub.devrel.easypermissions.EasyPermissions
import java.io.File

class RegistereProfileActivity : AppCompatActivity(), View.OnClickListener {
    var genderSelected = 0
    var userGender = 0
    var auth_token = ""
    var mLatitude = 0.0
    var mLongitude = 0.0
    var gps: Gps? = null
    private var mUserId = ""

    private var viewModel: RegisterViewModel? = null
    var mImagefile: File? = null
    private val PICK_FROM_GALLARY = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registere_profile)
        initView()
    }

    private fun initView() {
        auth_token = AppPreferences.init(this).getString(Constants.AUTH_TOKEN)
        mUserId = AppPreferences.init(this).getString(Constants.USER_ID)

        viewModel = ViewModelProviders.of(this).get(RegisterViewModel::class.java)


        // get current location
        getLocations()


        val genderSp = resources.getStringArray(R.array.Gender)
        ll_save.setOnClickListener(this)
        ivProfile.setOnClickListener(this)
        //.........................................interested in  spinner..................................................//

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
        //>..................................................gender................................................

        if (sp_user_gender != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_dropdown_item, genderSp
            )
            sp_user_gender.adapter = adapter
            sp_user_gender.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    (view as TextView).setTextColor(Color.BLACK)
                    if (position == 0) {
                        userGender = 0
                    } else {
                        userGender = position
                        Log.e("gender", userGender.toString() + "")
                    }

                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                }
            }
        }
        // check permission
        PermissionCheckUtil.create(this, object :
            PermissionCheckUtil.onPermissionCheckCallback {
            override fun onPermissionSuccess() {

            }
        })


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PermissionCheckUtil.PERMISSION_REQUEST_CODE) {

        }
    }
    //.......................get current locations.......................................

    private fun getLocations() {
        gps = Gps(this)

        if (gps!!.canGetLocation()) {
            mLatitude = gps!!.getLatitude()
            mLongitude = gps!!.getLongitude()
            Log.e("current_lat", mLatitude.toString() + "")
            Log.e("current_long", mLongitude.toString() + "")

        } else {
            gps!!.showSettingsAlert()
        }

    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ll_save -> {

                if (mImagefile == null) {
                    showSnackBar("Please Select Image")

                } else {
                    if (Validations.isRegisterProfile(
                            this,
                            et_first_name,
                            et_last_name,
                            et_age,
                            et_bio,
                            et_job
                        )
                    ) {
                        if (userGender == 0) {
                            showSnackBar("Please Select Gender")
                        } else if (genderSelected == 0) {
                            showSnackBar("Please Select Gender")

                        } else {
                            doRegister()
                        }

                    }
                }
            }
            R.id.ivProfile -> {
                pickPicture()
            }
        }

    }

    private fun doRegister() {
        clLoaderRegister.visibility = View.VISIBLE
        viewModel?.registerProfile(
            auth_token,
            intent.getStringExtra("mobileNumber"),
            intent.getStringExtra("countryCode"),
            et_first_name.text.toString().trim(),
            et_last_name.text.toString().trim(),
            et_age.text.toString().trim(),
            et_bio.text.toString().trim(),
            et_job.text.toString().trim(),
            userGender,
            genderSelected, mLatitude, mLongitude
        )?.observe(
            this,
            { registerProfileResponse ->
                clLoaderRegister.visibility = View.GONE
                val statusCode = registerProfileResponse?.statusCode
                if (statusCode == 200) {
                    AppPreferences.init(App.getAppContext())
                        .putString(Constants.USER_ID, registerProfileResponse.data.id)
                    updateProfileImageApi()
                } else {
                    showSnackBar(registerProfileResponse!!.message)

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
                mImagefile = File(getRealPathFromURI(selectedImage))
                Log.e("imagefile", mImagefile.toString())
                ivProfile?.setImageURI(selectedImage)
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
        clLoaderRegister.visibility = View.VISIBLE

        viewModel!!.updateProfileImage(
            auth_token,
            AppPreferences.init(App.getAppContext())
                .getString(Constants.USER_ID), mImagefile!!
        )
            .observe(
                this,
                { pojoUserProfileData ->
                    clLoaderRegister.visibility = View.GONE

                    val statusCode = pojoUserProfileData?.statusCode
                    if (statusCode == 200) {
                        AppPreferences.init(this)
                            .putString("USERIMAGE", pojoUserProfileData.data.profileImage)
                        AppPreferences.init(App.getAppContext())
                            .putBoolean(Constants.IS_LOGIN, true)
                        val intent = Intent(applicationContext, MainActivity::class.java)
                        startActivity(intent)
                        finishAffinity()
                    } else {
                        showSnackBar(pojoUserProfileData!!.message)

                    }
                })
    }
}
