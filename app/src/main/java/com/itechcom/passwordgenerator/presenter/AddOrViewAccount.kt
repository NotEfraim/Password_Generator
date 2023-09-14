package com.itechcom.passwordgenerator.presenter

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.lifecycleScope
import com.itechcom.passwordgenerator.R
import com.itechcom.passwordgenerator.base.BaseActivity
import com.itechcom.passwordgenerator.base.SingleViewModel
import com.itechcom.passwordgenerator.databinding.ActivityAddViewAccountBinding
import com.itechcom.passwordgenerator.extension.backFinish
import com.itechcom.passwordgenerator.extension.intentTo
import com.itechcom.passwordgenerator.extension.toastUtil
import com.itechcom.passwordgenerator.extension.transparentStatusBar
import com.itechcom.passwordgenerator.storage.StringKeys
import com.itechcom.passwordgenerator.storage.room.UserEntity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddOrViewAccount : BaseActivity<ActivityAddViewAccountBinding, SingleViewModel>(
    ActivityAddViewAccountBinding::inflate,
    SingleViewModel::class) {

    private val descChoices = listOf("Facebook","Instagram", "Others")
    private var userEntity : UserEntity? = null
    private var selectedSpinnerItem = ""
    private var toUpdate = false
    private var accountId = 0

    override fun ActivityAddViewAccountBinding.initialize() {
        transparentStatusBar()
        backBtn.backFinish()
        initSpinner()
        getUpdateDataFromIntent()
        saveInfoFunc()
        deleteFunc()
    }

    private fun getUpdateDataFromIntent(){
        accountId = intent.getIntExtra(StringKeys.SAVED_ID_KEY, 0)
        val desc = intent.getStringExtra(StringKeys.SAVED_DESC_KEY)
        val type = intent.getStringExtra(StringKeys.SAVED_ACCOUNT_TYPE_KEY)
        val pass = intent.getStringExtra(StringKeys.SAVED_PASSWORD_KEY)
        if(accountId == 0 || desc.isNullOrEmpty() || type.isNullOrEmpty() || pass.isNullOrEmpty()) return

        binding.apply {
            toUpdate = true
            selectedSpinnerItem = type
            val selectedFromIntent = descChoices.indexOf(type)
            customSpinner.getSpinner().setSelection(selectedFromIntent)
            descriptionTextField.setFieldText(desc)
            passwordTextField.setFieldText(pass)
            confirmPasswordTextField.setFieldText(pass)
        }
    }

    private fun initSpinner(){
        binding.apply {
            val adapter = ArrayAdapter(this@AddOrViewAccount, android.R.layout.simple_spinner_item, descChoices)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            customSpinner.setSpinnerAdapter(adapter)

            customSpinner.getSpinner().onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    selectedSpinnerItem = descChoices[position]
                    when(descChoices[position]){
                        "Facebook" -> {
                            customSpinner.setSpinnerIcon(R.mipmap.facebook)
                        }
                        "Instagram" -> {
                            customSpinner.setSpinnerIcon(R.mipmap.instagram)
                        }
                        "Others" -> {
                            customSpinner.setSpinnerIcon(R.mipmap.link)
                        }
                    }

                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }
    }

    private fun confirmPassword() : Boolean {
        binding.apply {
            val pass = passwordTextField.getFieldText()
            val cPass = confirmPasswordTextField.getFieldText()
            if (pass != cPass){
                toastUtil("Password must be the same!")
                return false
            }
        }
        return true
    }

    private fun saveInfoFunc(){
        binding.apply {
            saveBtn.setOnClickListener {
                getFieldDataToEntity()
                if(!confirmPassword()) return@setOnClickListener
                userEntity?.let { entity ->
                    if( entity.password.isNullOrEmpty()
                        || entity.accountType.isNullOrEmpty() ||
                        entity.description.isNullOrEmpty()) {
                        toastUtil("All fields is required!")
                    }
                    else{
                        lifecycleScope.launch(Dispatchers.IO) {
                            if(toUpdate){
                                viewModel.getRoomManager().updateData(
                                    id = accountId,
                                    description = entity.description,
                                    accountType = entity.accountType,
                                    password = entity.password
                                )

                            }else{
                                viewModel.getRoomManager().insertData(entity)
                            }
                            viewModel.getAllData().run {
                                delay(1000)
                                finish()
                            }

                        }
                        toastUtil("Saved!")
                    }
                }
            }
        }
    }


    private fun deleteFunc(){
        binding.deleteBtn.setOnClickListener {
            if(toUpdate){
                lifecycleScope.launch(Dispatchers.IO){
                    getFieldDataToEntity()
                    viewModel.getRoomManager().deleteData(accountId).run {
                        viewModel.getAllData().run {
                            delay(1000)
                            finish()
                        }
                    }
                }
                toastUtil("Deleted!")
            }
            else toastUtil("No records to delete.")
        }
    }

    private fun getFieldDataToEntity(){
        binding.apply {
            userEntity = UserEntity(
                description = descriptionTextField.getFieldText(),
                accountType = selectedSpinnerItem,
                password = passwordTextField.getFieldText()
            )
        }
    }

}