package com.travelcar.test.ui.main.account

import android.app.DatePickerDialog
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.travelcar.test.R
import com.travelcar.test.model.User
import com.travelcar.test.utils.DateUtils
import kotlinx.android.synthetic.main.account_fragment.*
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
                handleClickSaveUserInformation()
            } else {
                updateUiEditSaveMode(enable = true)
                isEditMode = isEditMode.not()
            }
        }

        account_user_birthday.setOnClickListener {
            showDatePickerForSelectBirthDay()
        }
    }


    private fun observeUserInformation() {
        viewModel.getUser().observe(this, Observer {
            it?.let {
                displayUserInformation(user = it)
            }
        })
    }


    private fun displayUserInformation(user: User) {
        account_user_name.setText(user.firstName)
        account_last_name.setText(user.lastName)
        account_user_email.setText(user.adress)
        userBirthDay = user.birthDate
        account_user_birthday.text = DateUtils.convertDateToString(user.birthDate)
    }


    private fun updateUiEditSaveMode(enable: Boolean) {
        val saveEditButoonSrc: Drawable? = if (enable) {
            ContextCompat.getDrawable(activity!!.applicationContext, R.drawable.ic_tick)
        } else {
            ContextCompat.getDrawable(activity!!.applicationContext, R.drawable.ic_edit)
        }
        saveEditButoonSrc?.setBounds(0, 0, 60, 60)
        account_button_edit_save.setCompoundDrawables(saveEditButoonSrc, null, null, null);
        account_user_name.isEnabled = enable
        account_last_name.isEnabled = enable
        account_user_email.isEnabled = enable
        account_user_birthday.isEnabled = enable
    }

    private fun handleClickSaveUserInformation() {
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


    companion object {
        fun newInstance() = AccountFragment()
    }
}
