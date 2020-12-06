package ru.infoshell.contactsapp.presentation.ui.base

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI

abstract class BaseFragment<ViewBinding : ViewDataBinding> : Fragment() {

    protected lateinit var viewBinding: ViewBinding

    abstract var layoutId: Int
    protected open var optionMenuId: Int? = null

    protected var toolbar: Toolbar? = null

    private var arrayContactsPermission = arrayOf(Manifest.permission.READ_CONTACTS)

    private val backPressCallback: OnBackPressedCallback =
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onBackPressedDispatch()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(optionMenuId != null);
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return DataBindingUtil.inflate<ViewBinding>(inflater, layoutId, container, false).let {
            viewBinding = it
            it.root
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, backPressCallback)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        optionMenuId?.let { inflater.inflate(it, menu) }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            CONTACTS_PERMISSION_CODE -> onContactPermissionRequest(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        }
    }

    open fun onBackPressedDispatch() {
        if (!findNavController().navigateUp()) requireActivity().finish()
    }

    fun checkContactsPermission(): Boolean {
        return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            requireActivity().checkSelfPermission(Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED
        } else true
    }

    fun requestContactsPermission() {
        requestPermissions(arrayContactsPermission, CONTACTS_PERMISSION_CODE)
    }


    protected fun initToolBar(toolbar: Toolbar?) {
        toolbar?.let {
            val activity = activity

            if (activity is AppCompatActivity) {
                activity.setSupportActionBar(toolbar)

                val navController = findNavController()
                val appBarConfiguration = AppBarConfiguration.Builder(navController.graph).build()
                NavigationUI.setupActionBarWithNavController(
                    activity,
                    navController,
                    appBarConfiguration
                )

                activity.supportActionBar?.setDisplayHomeAsUpEnabled(false)
            }
        }
    }

    protected open fun onContactPermissionRequest(isGranted: Boolean) {
    }


    companion object {
        const val CONTACTS_PERMISSION_CODE = 1000
    }
}