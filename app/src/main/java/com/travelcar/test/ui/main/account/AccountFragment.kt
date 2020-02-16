package com.travelcar.test.ui.main.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.travelcar.test.R
import kotlinx.android.synthetic.main.account_fragment.*

class AccountFragment : Fragment() {


    private lateinit var viewModel: AccountViewModel
    private var isEditMode = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.account_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        account_button_edit_save.setOnClickListener {
            if (isEditMode) {
                updateUiEditSaveMode(enable = false)
            } else {
                updateUiEditSaveMode(enable = true)
            }

            isEditMode = isEditMode.not()
        }
    }


    private fun updateUiEditSaveMode(enable: Boolean) {
        val saveEditButoonSrc = if (enable) R.drawable.ic_tick else R.drawable.ic_edit
        account_button_edit_save.setImageResource(saveEditButoonSrc)
        account_user_name.isEnabled = enable
        account_user_email.isEnabled = enable
        account_user_birthday.isEnabled = enable
        account_change_profile_picture.visibility = if (enable) VISIBLE else GONE
    }


    companion object {
        fun newInstance() = AccountFragment()
    }
}
