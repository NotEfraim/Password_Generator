package com.itechcom.passwordgenerator.adapter

import android.content.Context
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.QuickViewHolder
import com.itechcom.passwordgenerator.R
import com.itechcom.passwordgenerator.model.SavePasswordItem
import com.itechcom.passwordgenerator.widget.CustomTextField

class SavePasswordAdapter : BaseQuickAdapter<SavePasswordItem, QuickViewHolder>() {

    override fun onBindViewHolder(holder: QuickViewHolder, position: Int, item: SavePasswordItem?) {
        val textField = holder.getView<CustomTextField>(R.id.itemTextField)
        item?.let {
            when(item.accountType){
                "Facebook" -> {
                    textField.setStartIcon(R.mipmap.facebook)
                }
                "Instagram" -> {
                    textField.setStartIcon(R.mipmap.instagram)
                }
                "Others" -> {
                    textField.setStartIcon(R.mipmap.link)
                }
            }
            textField.initOnClick(it.accountPassword)
            textField.setFieldText(it.accountDescription)
        }
    }

    override fun onCreateViewHolder(
        context: Context,
        parent: ViewGroup,
        viewType: Int
    ): QuickViewHolder {
        return QuickViewHolder(R.layout.item_accounts_recycler, parent)
    }
}