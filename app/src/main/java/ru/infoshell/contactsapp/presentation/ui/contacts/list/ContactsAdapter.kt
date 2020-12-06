package ru.infoshell.contactsapp.presentation.ui.contacts.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ru.infoshell.contactsapp.R
import ru.infoshell.contactsapp.databinding.ItemContactBinding
import ru.infoshell.contactsapp.entities.Contact
import ru.infoshell.contactsapp.presentation.ext.loadFile

class ContactsAdapter(private var items: List<Contact>? = null) :
    RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {

    var clickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout: ItemContactBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_contact, parent, false
        )
        return ViewHolder(layout)
    }

    override fun getItemCount() = items?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        items?.let { holder.bind(it[position]) }
    }

    fun setNewsList(items: List<Contact>) {
        this.items = items
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemContactBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.root.setOnClickListener(this)
        }

        fun bind(contact: Contact) {
            binding.contact = contact
            binding.photoThumbIV.loadFile(contact.photoThumbnailUri)
            binding.executePendingBindings()
        }

        override fun onClick(v: View?) {
            items?.let { clickListener?.onItemClick(it[adapterPosition]) }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(contact: Contact)
    }
}
