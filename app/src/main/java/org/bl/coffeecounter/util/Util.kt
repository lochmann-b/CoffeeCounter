package org.bl.coffeecounter.util

import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.os.Parcelable
import org.bl.coffeecounter.db.entities.Coffee
import org.json.JSONObject
import org.json.JSONTokener
import java.lang.Exception
import java.text.DecimalFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.function.Consumer
import kotlin.experimental.and

fun formatDateTime(dateTime: LocalDateTime): String {
    return dateTime.format(DateTimeFormatter.ofPattern("dd.MMyyyy HH:mm:ss"))
}

fun formatDate(dateTime: LocalDateTime): String {
    return dateTime.format(DateTimeFormatter.ofPattern("dd.MMyyyy"))
}

fun formatTime(dateTime: LocalDateTime): String {
    return dateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"))
}

fun formatCurrency(amount: Double): String {
    return DecimalFormat.getCurrencyInstance().format(amount)
}

fun parseNFCMessage(rawMessages: Array<Parcelable>, callback: Consumer<Coffee>, exceptionHandler: Consumer<Exception>) {

    val messages: List<NdefMessage> = rawMessages.map { it as NdefMessage }
    messages.forEach { m ->
        m.records.forEach { r ->
            if (Arrays.equals(NdefRecord.RTD_TEXT, r.type)) {
                val payload = r.payload
                if (payload.isNotEmpty()) {
                    try {
                        val textEncoding =
                            if (payload[0] and 128.toByte() == 0.toByte()) "UTF-8" else "UTF-16"
                        val langCodeLength = payload[0] and 63.toByte()
                        val inMessage = String(
                            payload,
                            langCodeLength + 1,
                            payload.count() - langCodeLength - 1,
                            charset(textEncoding)
                        )


                        val jsonObject = JSONTokener(inMessage).nextValue() as JSONObject

                        val cost: Byte = jsonObject.getInt("cost").toByte()
                        val flavour: String = jsonObject.getString("flavour")

                        val coffee = Coffee(flavour = flavour, cost = cost, fromNFC = true)
                        callback.accept(coffee)
                    }catch (e: Exception) {
                        exceptionHandler.accept(e)
                    }
                }
            }
        }
    }



}