package com.bmt_team.logoquiz.base

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.bmt_team.logoquiz.BR
import com.example.logoquiz.R

abstract class BaseActivity<VM : BaseViewModel, VB : ViewDataBinding>: AppCompatActivity() {
    private lateinit var internalViewModel : VM
    private lateinit var internalViewBinding : VB
    val viewModel : VM
    get() = internalViewModel
    val viewBinding : VB
    get() = internalViewBinding


    abstract fun onCreateViewModel(): VM
    lateinit var circleProgressDialog: CircleProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        circleProgressDialog = CircleProgressDialog(this)
        internalViewBinding = DataBindingUtil.setContentView(this, layoutId())
        internalViewModel = onCreateViewModel()

        internalViewBinding.apply {
           // setVariable(BR.viewModel, internalViewModel)
            internalViewBinding.lifecycleOwner = this@BaseActivity
            root.isClickable = true
            executePendingBindings()
        }

        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = ContextCompat.getColor(this, R.color.transparent)

            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        bindView()
        observeData()

    }

    @LayoutRes
    abstract fun layoutId() : Int
    abstract fun bindView()
    abstract fun observeData()

}
