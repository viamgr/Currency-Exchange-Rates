package app.vahid.datasource.remote.base

import app.vahid.datasource.remote.BuildConfig
import java.security.KeyFactory
import java.security.PublicKey
import java.security.spec.X509EncodedKeySpec
import java.util.Base64
import javax.crypto.Cipher

object SecureKey {
    /**
     * A native method that is implemented by the 'secure-lib' native library,
     * which is packaged with this application.
     */
    external fun publicKey(): String

    init {
        System.loadLibrary("secure-lib")
    }

    fun getUrl(): String {
        return decrypt(encryptedUrl = BuildConfig.ENCRYPTED_BASE_URL_KEY, publicKey())
    }

    private fun decrypt(encryptedUrl: String, publicKey: String): String {
        val keyBytes = Base64.getDecoder().decode(publicKey)
        val keySpec = X509EncodedKeySpec(keyBytes)
        val keyFactory = KeyFactory.getInstance("RSA")
        val pubKey = keyFactory.generatePublic(keySpec) as PublicKey

        val cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
        cipher.init(Cipher.DECRYPT_MODE, pubKey)
        val decryptedUrlBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedUrl))

        return String(decryptedUrlBytes)
    }

}