package ru.infoshell.contactsapp.presentation.ui.contacts.detail

import android.os.Bundle
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.infoshell.contactsapp.R
import ru.infoshell.contactsapp.databinding.FragmentContactDetailBinding
import ru.infoshell.contactsapp.presentation.ui.base.BaseFragment

class ContactDetailFragment : BaseFragment<FragmentContactDetailBinding>() {

    override var layoutId = R.layout.fragment_contact_detail

    private val viewModel: ContactDetailViewModel by viewModel()

    private val phoneAdapter: PhonesAdapter by inject()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initToolBar(viewBinding.toolbar)

        viewBinding.viewModel = viewModel
        viewBinding.lifecycleOwner = this

        viewModel.setContact(arguments?.let { ContactDetailFragmentArgs.fromBundle(it).contact })

        configViews()
    }

    private fun configViews() {
        viewBinding.phonesRV.adapter = phoneAdapter

        viewModel.contactLiveData.observe(viewLifecycleOwner) {
            phoneAdapter.setPhoneList(it.allPhones)
            viewBinding.toolbar.title = it.name
        }
    }
}