package com.itechcom.passwordgenerator.presenter

import androidx.lifecycle.lifecycleScope
import com.itechcom.passwordgenerator.adapter.SavePasswordAdapter
import com.itechcom.passwordgenerator.base.BaseActivity
import com.itechcom.passwordgenerator.base.SingleViewModel
import com.itechcom.passwordgenerator.databinding.ActivityAccountListBinding
import com.itechcom.passwordgenerator.extension.backFinish
import com.itechcom.passwordgenerator.extension.collect
import com.itechcom.passwordgenerator.extension.intentTo
import com.itechcom.passwordgenerator.extension.mapper
import com.itechcom.passwordgenerator.extension.mockAdapterData
import com.itechcom.passwordgenerator.extension.transparentStatusBar
import com.itechcom.passwordgenerator.extension.useEmptyView
import com.itechcom.passwordgenerator.model.SavePasswordItem
import com.itechcom.passwordgenerator.storage.StringKeys
import com.itechcom.passwordgenerator.storage.room.UserEntity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AccountListActivity : BaseActivity<ActivityAccountListBinding, SingleViewModel>(
    ActivityAccountListBinding::inflate,
    SingleViewModel::class) {

    private val savePasswordAdapter = SavePasswordAdapter()

    override fun onResume() {
        super.onResume()
        viewModel.getAllData()
    }

    override fun SingleViewModel.initObserver() {
        collect(allData, ::initData )
    }

    private fun initData(data : List<UserEntity?>){
        savePasswordAdapter.submitList(data.mapper())
    }


    override fun ActivityAccountListBinding.initialize() {
        transparentStatusBar()
        initView()
    }

    private fun initView(){
        binding.apply {
            accountsRecycler.adapter = savePasswordAdapter
            savePasswordAdapter.useEmptyView()
            backBtn.backFinish()
            addAccountBtn.setOnClickListener {
                intentTo<AddOrViewAccount>{}
            }
        }
        initAdapterClick()
    }

    private fun initAdapterClick(){
        savePasswordAdapter.setOnItemClickListener { adapter, _, position ->
            val data = adapter.items[position]
            intentTo<AddOrViewAccount>{
                it.putExtra(StringKeys.SAVED_ID_KEY, data.id)
                it.putExtra(StringKeys.SAVED_ACCOUNT_TYPE_KEY, data.accountType)
                it.putExtra(StringKeys.SAVED_DESC_KEY, data.accountDescription)
                it.putExtra(StringKeys.SAVED_PASSWORD_KEY, data.accountPassword)
            }
        }
    }


}