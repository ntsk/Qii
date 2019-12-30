package com.qii.ntsk.qii.utils

import java.security.SecureRandom

class RandomStringGenerator {
    companion object {
        fun generate(length: Int): String {
            val allowChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvqxyz0123456789"
            return (1..length)
                    .map { allowChars.random() }
                    .joinToString { "" }
        }
    }
}