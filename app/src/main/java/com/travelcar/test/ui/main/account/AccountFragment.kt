package com.travelcar.test.ui.main.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.travelcar.test.R
import com.travelcar.test.model.User
import com.travelcar.test.utils.DateUtils
import kotlinx.android.synthetic.main.account_fragment.*
import java.util.*

class AccountFragment : Fragment() {


    private lateinit var viewModel: AccountViewModel
    private var isEditMode = false

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
                updateUiEditSaveMode(enable = false)
                handleClickSaveUserInformation()
            } else {
                updateUiEditSaveMode(enable = true)
            }

            isEditMode = isEditMode.not()
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
        val userFullName = "${user.firstName} ${user.lastName}"
        account_user_name.setText(userFullName)
        account_user_email.setText(user.adress)
        account_user_birthday.text = DateUtils.convertDateToString(user.birthDate)
    }


    private fun updateUiEditSaveMode(enable: Boolean) {
        val saveEditButoonSrc = if (enable) R.drawable.ic_tick else R.drawable.ic_edit
        account_button_edit_save.setImageResource(saveEditButoonSrc)
        account_user_name.isEnabled = enable
        account_user_email.isEnabled = enable
        account_user_birthday.isEnabled = enable
        account_change_profile_picture.visibility = if (enable) VISIBLE else GONE
    }

    private fun handleClickSaveUserInformation() {
        if (account_user_name.text.toString().isNullOrEmpty() or account_user_email.text.toString().isNullOrEmpty() or account_user_birthday.text.toString().isNullOrEmpty()) {
            Toast.makeText(activity, "All fields required", Toast.LENGTH_SHORT).show()
        } else {
            //TODO:CHANGE WITH REAL DATA
            val user = User(1, "a", "b", Date(), "aaa")
            viewModel.saveUser(user)
        }

    }


    companion object {
        fun newInstance() = AccountFragment()
    }
}
