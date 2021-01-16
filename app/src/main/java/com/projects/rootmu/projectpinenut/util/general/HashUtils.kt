package com.projects.rootmu.projectpinenut.util.general

import java.security.MessageDigest

enum class HashAlgorithm(name: String) {
    MD5("MD5"),
    SHA1("SHA-1"),
    SHA224("SHA-224"),
    SHA256("SHA-256"),
    SHA384("SHA-384"),
    SHA512("SHA-512")
}

/**
 * Supported algorithms on Android:
 *
 * Algorithm	Supported API Levels
 * MD5          1+
 * SHA-1	    1+
 * SHA-224	    1-8,22+
 * SHA-256	    1+
 * SHA-384	    1+
 * SHA-512	    1+
 */
private fun String.toHash(algorithm: HashAlgorithm): String {
    val HEX_CHARS = "0123456789ABCDEF"
    val bytes = MessageDigest
        .getInstance(algorithm.name)
        .digest(toByteArray())
    val result = StringBuilder(bytes.size * 2)

    bytes.forEach {
        val i = it.toInt()
        result.append(HEX_CHARS[i shr 4 and 0x0f])
        result.append(HEX_CHARS[i and 0x0f])
    }

    return result.toString()
}

fun String.md5() = toHash(HashAlgorithm.MD5)
fun String.sha1() = toHash(HashAlgorithm.SHA1)
fun String.sha224() = toHash(HashAlgorithm.SHA224)
fun String.sha256() = toHash(HashAlgorithm.SHA256)
fun String.sha384() = toHash(HashAlgorithm.SHA384)
fun String.sha512() = toHash(HashAlgorithm.SHA512)


