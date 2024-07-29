package com.life.plus.tv.utils

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import coil.imageLoader
import coil.request.ImageRequest
import com.life.plus.tv.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

@SuppressLint("ClickableViewAccessibility")
fun View.setBounceClickListener(onClick: ((View) -> Unit)? = null) {
    this.setOnTouchListener { v, event ->
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                val scaleDownX = ObjectAnimator.ofFloat(
                    v, "scaleX", 0.9f
                )
                val scaleDownY = ObjectAnimator.ofFloat(
                    v, "scaleY", 0.9f
                )
                scaleDownX.duration = 200
                scaleDownY.duration = 200

                val scaleDown = AnimatorSet()
                scaleDown.play(scaleDownX).with(scaleDownY)
                scaleDown.start()

            }

            MotionEvent.ACTION_UP -> {
                val scaleDownX2 = ObjectAnimator.ofFloat(
                    v, "scaleX", 1f
                )
                val scaleDownY2 = ObjectAnimator.ofFloat(
                    v, "scaleY", 1f
                )
                scaleDownX2.duration = 200
                scaleDownY2.duration = 200

                val scaleDown2 = AnimatorSet()
                scaleDown2.play(scaleDownX2).with(scaleDownY2)

                scaleDown2.start()
            }
        }
        false
    }
    this.setOnClickListener { onClick?.invoke(it) }
}

/**Flow collect with `repeatOnLifecycle` on` lifecycleScope` till `RESUMED` */
context(Fragment)
fun <T> Flow<T>.collectWithLifecycle(
    context: CoroutineContext = EmptyCoroutineContext,
    minActiveState: Lifecycle.State = Lifecycle.State.RESUMED,
    block: suspend CoroutineScope.(T) -> Unit,
) {
    lifecycleScope.launch(context) {
        viewLifecycleOwner.repeatOnLifecycle(minActiveState) {
            collect { value ->
                if (isAdded)
                    block(value)
            }
        }
    }
}

/**Flow collect with `repeatOnLifecycle` on` lifecycleScope` till `RESUMED` */
context(AppCompatActivity)
fun <T> Flow<T>.collectWithLifecycle(
    context: CoroutineContext = EmptyCoroutineContext,
    minActiveState: Lifecycle.State = Lifecycle.State.RESUMED,
    block: suspend CoroutineScope.(T) -> Unit,
) {
    lifecycleScope.launch(context) {
        repeatOnLifecycle(minActiveState) {
            collect { value ->
                block(value)
            }
        }
    }
}

/**
 * Navigate to a destination safely, handling cases where the destination cannot be found.
 *
 * @param resId The resource ID of the destination to navigate to.
 * @param args Arguments to pass to the destination.
 */
context(Fragment)
fun NavController.navigateSafe(@IdRes resId: Int, args: Bundle? = null) {
    // Get the destination ID from the action associated with the given resource ID.
    val destinationId = currentDestination?.getAction(resId)?.destinationId ?: 0
    // Get the current navigation node.
    currentDestination?.let { node ->
        // Determine the parent node, which is either the NavGraph or the parent of the current destination.
        val currentNode = when (node) {
            is NavGraph -> node
            else -> node.parent
        }
        // If the destination ID is valid, find the destination node and navigate to it.
        if (destinationId != 0) {
            currentNode?.findNode(destinationId)?.let {
                if (this@Fragment.lifecycle.currentState == Lifecycle.State.RESUMED)
                    navigate(resId, args)
            }
        }
    }
}

/**
 * Navigate to a destination safely, handling cases where the destination cannot be found.
 *
 * @param resId The resource ID of the destination to navigate to.
 * @param args Arguments to pass to the destination.
 */
context(AppCompatActivity)
fun NavController.navigateSafe(@IdRes resId: Int, args: Bundle? = null) {
    // Get the destination ID from the action associated with the given resource ID.
    val destinationId = currentDestination?.getAction(resId)?.destinationId ?: 0
    // Get the current navigation node.
    currentDestination?.let { node ->
        // Determine the parent node, which is either the NavGraph or the parent of the current destination.
        val currentNode = when (node) {
            is NavGraph -> node
            else -> node.parent
        }
        // If the destination ID is valid, find the destination node and navigate to it.
        if (destinationId != 0) {
            currentNode?.findNode(destinationId)?.let {
                if (this@AppCompatActivity.lifecycle.currentState == Lifecycle.State.RESUMED)
                    navigate(resId, args)
            }
        }
    }
}


context(Fragment)
fun String.showToast(){
    context?.let {
        Toast.makeText( it,this, Toast.LENGTH_SHORT).show()
    }
}

fun String.isValidUsername(): Boolean {
    val regex = "^[a-zA-Z_][a-zA-Z0-9_]{1,19}$".toRegex()
    return regex.matches(this)
}

fun ImageView.loadDrawable(url: String?) {
    val request = ImageRequest.Builder(this.context)
        .setHeader("Cache-Control", "max-age=31536000")
        .data(url)
        .target(this)
        .build()
    context.imageLoader.enqueue(request)
}

fun View.gone(){
    visibility = View.GONE
}

fun View.visible(){
    visibility = View.VISIBLE
}

fun View.invisible(){
    visibility = View.INVISIBLE
}

fun Activity.setSystemBarAppearance(
    statusBarColor: Int,
    navigationBarColor: Int,
    isAppearanceLightStatusBars: Boolean,
    isAppearanceLightNavigationBars: Boolean
) {
    window.statusBarColor = ContextCompat.getColor(this, statusBarColor)
    window.navigationBarColor = ContextCompat.getColor(this, navigationBarColor)
    val windowInsetController = WindowCompat.getInsetsController(window, window.decorView)
    windowInsetController.isAppearanceLightStatusBars = isAppearanceLightStatusBars
    windowInsetController.isAppearanceLightNavigationBars = isAppearanceLightNavigationBars
}

fun Activity?.setStatusAppearance(isLight: Boolean) {
    if(this == null) return
    val windowInsetController = WindowCompat.getInsetsController(window, window.decorView)
    windowInsetController.isAppearanceLightStatusBars = !isLight
}

fun Activity?.closeKeyboard(nextFocus: View? = null) {
    if(this == null) return
    val view = currentFocus
    if (view is EditText) {
        val manager =
            this.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
        manager.hideSoftInputFromWindow(view.getWindowToken(), 0)
        nextFocus?.requestFocus()
    }
}
