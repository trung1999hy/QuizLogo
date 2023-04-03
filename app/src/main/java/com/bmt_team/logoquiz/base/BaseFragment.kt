package com.bmt_team.logoquiz.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.bmt_team.logoquiz.BR

abstract class BaseFragment<VM : BaseViewModel, VB : ViewDataBinding> : Fragment(){
    private lateinit var internalViewModel : VM
    private lateinit var internalViewBinding : VB
    private lateinit var progressDialog : CircleProgressDialog

    val viewModel : VM
        get() = internalViewModel
    val viewBinding : VB
        get() = internalViewBinding
    

    @LayoutRes
    abstract fun layoutId(): Int
    abstract fun initView()
    abstract fun observerData()
    abstract fun onCreateViewModel(): VM

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        internalViewBinding =  DataBindingUtil.inflate(inflater, layoutId(), container, false)
        return internalViewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressDialog = CircleProgressDialog(requireActivity())

        internalViewModel =  onCreateViewModel()

        internalViewBinding.apply {
           // setVariable(com.bmt_team.logoquiz.BR.viewModel, internalViewModel)
            internalViewBinding.lifecycleOwner = viewLifecycleOwner
            root.isClickable = true
            executePendingBindings()
        }

        initView()
        observerData()


    }

}