package com.celaloglu.zafer.todos.base

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.celaloglu.zafer.todos.R

abstract class BaseActivity<B : ViewDataBinding> : AppCompatActivity() {

    protected lateinit var binding: B

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, getLayoutId())
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left)
    }

    @LayoutRes
    protected abstract fun getLayoutId(): Int

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right)
    }
}