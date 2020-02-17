package com.travelcar.test.ui.main.account

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
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
        account_user_name.setText(user.firstName)
        account_last_name.setText(user.lastName)
        account_user_email.setText(user.adress)
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
            Toast.makeText(activity, "All fields required", Toast.LENGTH_SHORT).show()
        } else {
            //TODO:CHANGE WITH REAL DATA
            val user = User(
                1,
                account_user_name.text.toString(),
                account_last_name.text.toString(),
                Date(),
                account_user_email.text.toString()
            )
            viewModel.saveUser(user)
        }

    }


    companion object {
        fun newInstance() = AccountFragment()
    }
}
