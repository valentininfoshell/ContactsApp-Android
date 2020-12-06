package ru.infoshell.contactsapp.presentation.ui.contacts.list

import android.os.Bundle
import android.view.MenuItem
import androidx.navigation.fragment.findNavController
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.infoshell.contactsapp.R
import ru.infoshell.contactsapp.databinding.FragmentContactListBinding
import ru.infoshell.contactsapp.entities.Contact
import ru.infoshell.contactsapp.presentation.ui.base.BaseFragment

class ContactListFragment : BaseFragment<FragmentContactListBinding>(),
    ContactsAdapter.OnItemClickListener {

    override var layoutId = R.layout.fragment_contact_list
    override var optionMenuId: Int? = R.menu.menu_contact_list

    private val viewModel: ContactListViewModel by viewModel()

    private val contactsAdapter: ContactsAdapter by inject()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initToolBar(viewBinding.appBar.toolbar)

        viewBinding.viewModel = viewModel
        viewBinding.lifecycleOwner = this

        configViews()
        updateContent()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_item_refresh -> {
                updateContent()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onContactPermissionRequest(isGranted: Boolean) {
        if (isGranted) viewModel.getContactList()
        else viewModel.onPermissionDenied()
    }

    override fun onItemClick(contact: Contact) {
        val action = ContactListFragmentDirections.actionListToDetail(contact)
        findNavController().navigate(action)
    }

    private fun updateContent() {
        if (checkContactsPermission()) viewModel.getContactList()
        else requestContactsPermission()
    }

    private fun configViews() {
        contactsAdapter.clickListener = this
        viewBinding.contactsRV.adapter = contactsAdapter
        viewModel.contactListLiveData.observe(viewLifecycleOwner) { contactsAdapter.setNewsList(it) }
    }
}