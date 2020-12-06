package ru.infoshell.contactsapp.presentation.ui.contacts.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ru.infoshell.contactsapp.R
import ru.infoshell.contactsapp.databinding.ItemPhoneBinding

class PhonesAdapter(private var items: List<String>? = null) :
    RecyclerView.Adapter<PhonesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout: ItemPhoneBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_phone, parent, false
        )
        return ViewHolder(layout)
    }

    override fun getItemCount() = items?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        items?.let { holder.bind(it[position]) }
    }

    fun setPhoneList(items: List<String>) {
        this.items = items
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemPhoneBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.root.setOnClickListener(this)
        }

        fun bind(phone: String) {
            binding.phone = phone
            binding.executePendingBindings()
        }

        override fun onClick(v: View?) {
//            clickListener?.onItemClick(items[adapterPosition])
        }
    }
}
