package klo0812.mlaserna.bayadmuna.utilities

import java.text.DecimalFormat

fun formatMoney(amount: Double): String {
    val formatter: DecimalFormat = DecimalFormat("#,##0.00")
    return formatter.format(amount)
}

fun hideMoney(amount: Double): String {
    val formatted = formatMoney(amount)
    val chars = formatted.toCharArray()
    for (i in chars.indices) {
        val char = chars[i]
        if (char != ',' && char != '.') {
            chars[i] = '*'
        }
    }
    return String(chars).replace(",", "")
}