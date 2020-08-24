package me.kolotilov.indeedmovies.ui.login

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.google.zxing.integration.android.IntentIntegrator
import com.journeyapps.barcodescanner.CaptureActivity
import kotlinx.android.synthetic.main.fragment_login.*
import me.kolotilov.indeedmovies.R
import me.kolotilov.indeedmovies.ui.base.BaseFragment
import me.kolotilov.indeedmovies.ui.base.showMessage
import me.kolotilov.indeedmovies.ui.common.checkSelfPermission
import me.kolotilov.indeedmovies.ui.common.logError
import me.kolotilov.indeedmovies.ui.common.toVisibleOrGone

class LoginFragment : BaseFragment<LoginViewModel, LoginViewModel.Factory>(LoginViewModel::class) {

    private companion object {
        const val CAMERA_REQUEST_CODE = 0
    }

    // region Lifecycle

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_register.setOnClickListener { onRegisterClick() }
        btn_login.setOnClickListener { onLoginClick() }
        btn_fingerprint.apply {
            visibility = isFingerPrintSupported().toVisibleOrGone()
            setOnClickListener { showBioPrompt() }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            CAMERA_REQUEST_CODE -> {
                if (grantResults.first() == PackageManager.PERMISSION_GRANTED)
                    openQrScanner()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            IntentIntegrator.REQUEST_CODE -> {
                if (resultCode != AppCompatActivity.RESULT_OK) {
                    showMessage(getString(R.string.invalid_qr))
                    return
                }
                val content = IntentIntegrator.parseActivityResult(requestCode, resultCode, data).contents
                viewModel.register(content)
                    .subscribe({
                        showMessage(getString(R.string.successfully_registered))
                    }, {
                        showMessage(getString(R.string.invalid_qr))
                    })
            }
        }
    }

    // endregion

    private fun onLoginClick() {
        val login = et_login.text.toString()
        val password = et_password.text.toString()
        viewModel.logIn(login, password)
            .subscribe({
                navigateToMovieList()
            }, {
                showMessage(getString(R.string.invalid_login_or_password))
            })
            .disposeOnStop()
    }

    private fun onRegisterClick() {
        if (checkSelfPermission(Manifest.permission.CAMERA))
            openQrScanner()
        else
            requestPermissions(arrayOf(Manifest.permission.CAMERA), CAMERA_REQUEST_CODE)
    }

    private fun openQrScanner() {
        IntentIntegrator(activity).apply {
            captureActivity = QrScannerActivity::class.java
            setPrompt(getString(R.string.scan_registration_qr_code))
        }.initiateScan()
    }

    private fun isFingerPrintSupported(): Boolean {
        val biometricManager = BiometricManager.from(requireContext())
        return biometricManager.canAuthenticate() == BiometricManager.BIOMETRIC_SUCCESS
    }

    private fun showBioPrompt() {
        val callback = object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                viewModel.isRegistered()
                    .subscribe({
                    if (it)
                        navigateToMovieList()
                    else
                        showMessage(getString(R.string.you_are_not_registered_yet))
                    }, ::logError)
                    .disposeOnStop()
            }
        }

        val prompt = BiometricPrompt(this, ContextCompat.getMainExecutor(requireContext()), callback)
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(getString(R.string.log_in_using_fingerprint))
            .setNegativeButtonText(getString(R.string.log_in_with_password))
            .build()

        prompt.authenticate(promptInfo)
    }

    private fun navigateToMovieList() {
        findNavController().navigate(R.id.action_loginFragment_to_movieListFragment)
    }
}

class QrScannerActivity : CaptureActivity()