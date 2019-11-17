package com.qii.ntsk.qii.utils

import java.text.SimpleDateFormat
import java.util.*

class DateFormatUtil {
    companion object {
        private const val PATTERN_TIME_AND_DATE = "yyyy-MM-dd'T'HH:mm:ss+09:00"
        private const val PATTERN_TIME_AND_DATE_JAPANESE = "yyyy年M月d日 HH時mm分"

        fun formatTimeAndDate(date: String): String {
            val formatFrom = SimpleDateFormat(PATTERN_TIME_AND_DATE, Locale.JAPAN)
            val formatTo = SimpleDateFormat(PATTERN_TIME_AND_DATE_JAPANESE, Locale.JAPAN)
            val formatDate = formatFrom.parse(date)
            return formatTo.format(formatDate)
        }
    }
}