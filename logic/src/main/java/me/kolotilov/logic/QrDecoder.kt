package me.kolotilov.logic

import android.net.Uri
import android.util.Base64
import android.util.Log
import androidx.core.net.toUri

/**
 * Decodes registration QR content.
 */
interface QrDecoder {

    /**
     * Decodes registration QR content.
     */
    fun decode(qrContent: String): QrContent
}

class QrDecoderImpl : QrDecoder {

    override fun decode(qrContent: String): QrContent {
        val parsed = Uri.parse(String(Base64.decode(qrContent, Base64.DEFAULT)))
        return QrContent(parsed.getQueryParameter("login")!!, parsed.getQueryParameter("password")!!)
    }
}

data class QrContent(
    val login: String,
    val password: String
)