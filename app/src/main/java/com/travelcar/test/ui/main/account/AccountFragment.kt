package com.travelcar.test.ui.main.account

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.travelcar.test.R
import com.travelcar.test.model.User
import com.travelcar.test.utils.DateUtils
import kotlinx.android.synthetic.main.account_fragment.*
import java.io.IOException
import java.util.*


class AccountFragment : Fragment(), DatePickerDialog.OnDateSetListener {


    private lateinit var viewModel: AccountViewModel
    private var isEditMode = false
    private var userBirthDay = Date()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
        return inflater.inflate(R.layout.account_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeUserInformation()

        account_button_edit_save.setOnClickListener {
            if (isEditMode) {
                onClickSaveUserInformation()
            } else {
                updateUiEditSaveMode(enable = true)
                isEditMode = isEditMode.not()
            }
        }

        account_user_birthday.setOnClickListener {
            showDatePickerForSelectBirthDay()
        }

        account_profile_picture.setOnClickListener {
            showPictureDialog()
        }
    }

    /**
     * dialod to select capture Mode
     */
    private fun showPictureDialog() {
        val pictureDialog: AlertDialog.Builder = AlertDialog.Builder(context!!)
        pictureDialog.setTitle(getString(R.string.select_action))
        val pictureDialogItems = arrayOf(
            getString(R.string.select_gallery),
            getString(R.string.select_camera)
        )
        pictureDialog.setItems(
            pictureDialogItems
        ) { _, which ->
            when (which) {
                GALLERY -> choosePhotoFromGallary()
                CAMERA -> takePhotoFromCamera()
            }
        }
        pictureDialog.show()
    }

    /**
     * launch camera
     */
    private fun takePhotoFromCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA)
    }

    /**
     * launch galery to choose picture
     */
    private fun choosePhotoFromGallary() {
        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        startActivityForResult(galleryIntent, GALLERY)
    }

    private fun observeUserInformation() {
        viewModel.getUser().observe(this, Observer {
            it?.let {
                displayUserInformation(user = it)
            }
        })
    }

    /**
     * display user data from db
     */
    private fun displayUserInformation(user: User) {
        account_user_name.setText(user.firstName)
        account_last_name.setText(user.lastName)
        account_user_email.setText(user.address)
        userBirthDay = user.birthDate
        account_user_birthday.text = DateUtils.convertDateToString(user.birthDate)
    }

    /**
     * Edit User Interface Mode (editbale/ not edistable)
     */
    private fun updateUiEditSaveMode(enable: Boolean) {
        val saveEditButoonSrc: Drawable? = if (enable) {
            account_button_edit_save.text = getString(R.string.save_btn)
            ContextCompat.getDrawable(activity!!.applicationContext, R.drawable.ic_tick)
        } else {
            account_button_edit_save.setText(R.string.edit_profil)
            ContextCompat.getDrawable(activity!!.applicationContext, R.drawable.ic_edit)
        }
        saveEditButoonSrc?.setBounds(0, 0, 60, 60)
        account_button_edit_save.setCompoundDrawables(saveEditButoonSrc, null, null, null);
        account_user_name.isEnabled = enable
        account_last_name.isEnabled = enable
        account_user_email.isEnabled = enable
        account_user_birthday.isEnabled = enable
        account_profile_picture.isEnabled = enable
    }

    /**
     * Save User Data on local Db
     */
    private fun onClickSaveUserInformation() {
        if (account_user_name.text.toString().isEmpty() or account_user_email.text.toString().isEmpty() or account_user_birthday.text.toString().isEmpty()) {
            Toast.makeText(activity, getString(R.string.all_fields_required), Toast.LENGTH_SHORT)
                .show()
        } else {
            val user = User(
                1,
                account_user_name.text.toString(),
                account_last_name.text.toString(),
                userBirthDay,
                account_user_email.text.toString()
            )
            viewModel.saveUser(user)
            isEditMode = isEditMode.not()
            updateUiEditSaveMode(enable = false)
            Snackbar.make(
                account_root_view,
                getString(R.string.success_update_profile),
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    /**
     * show calandar Dialog to choose birth day
     */
    private fun showDatePickerForSelectBirthDay() {
        val calendar = GregorianCalendar().apply {
            time = userBirthDay
        }
        val datePickerDialog = DatePickerDialog(
            activity!!,
            this,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH) + 1,
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        userBirthDay = calendar.time
        account_user_birthday.text = DateUtils.convertDateToString(userBirthDay)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY) {
            if (data != null) {
                val contentURI = data.data
                try {
                    val bitmap =
                        MediaStore.Images.Media.getBitmap(activity?.contentResolver, contentURI)
                    //val path = saveImage(bitmap) //TODO save path on local DB
                    account_profile_picture!!.setImageBitmap(bitmap)

                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }

        } else if (requestCode == CAMERA) {
            val thumbnail = data!!.extras!!.get("data") as Bitmap
            account_profile_picture!!.setImageBitmap(thumbnail)
            // saveImage(thumbnail)  //TODO
        }
    }


    companion object {
        fun newInstance() = AccountFragment()
        private const val IMAGE_DIRECTORY = "/test"
        private const val CAMERA = 1
        private const val GALLERY = 0
    }
}
