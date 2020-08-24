package me.kolotilov.indeedmovies.ui.common

import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

/**
 * Simple method to check permissions.
 */
fun Fragment.checkSelfPermission(permission: String): Boolean {
    val fragment = this
    return ContextCompat.checkSelfPermission(fragment.requireContext(), permission) == PackageManager.PERMISSION_GRANTED
}