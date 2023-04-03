package com.bmt_team.logoquiz.base

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import com.bmt_team.logoquiz.BR
import dagger.android.support.AndroidSupportInjection

abstract class BaseDialogFragment<VM : BaseViewModel, VB : ViewDataBinding> : DialogFragment() {
    private lateinit var internalViewModel : VM
    private lateinit var internalViewBinding : VB
    private lateinit var progressDialog: CircleProgressDialog

    val viewModel : VM
        get() = internalViewModel
    val viewBinding : VB
        get() = internalViewBinding

    abstract fun onCreateViewModel(): VM

    @SuppressLint("SupportAnnotationUsage")
    @LayoutRes
    abstract fun initView()
    abstract fun layoutId() : Int
    abstract fun observerData()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progressDialog = CircleProgressDialog(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        internalViewBinding = DataBindingUtil.inflate(inflater, layoutId(),container, false)
        return internalViewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        internalViewModel = onCreateViewModel()
        internalViewBinding.lifecycleOwner = viewLifecycleOwner
        internalViewBinding.root.isClickable = true
        internalViewBinding.executePendingBindings()
       //internalViewBinding.setVariable(com.bmt_team.logoquiz.BR.viewModel, internalViewModel)
        initView()
        observerData()
    }
}