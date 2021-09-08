package org.bl.coffeecounter

import android.content.Intent
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import org.bl.coffeecounter.db.entities.Coffee
import org.bl.coffeecounter.ui.main.MainFragment
import org.bl.coffeecounter.ui.main.MainViewModel
import org.bl.coffeecounter.ui.main.MainViewModelFactory
import java.util.*
import kotlin.experimental.and

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory((application as CoffeeCounterApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()

            if (intent != null) {
                handleIntent(intent);
            }
        }

    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        if (NfcAdapter.ACTION_NDEF_DISCOVERED == intent.action) {
            intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)?.also { rawMessages ->
                val messages: List<NdefMessage> = rawMessages.map { it as NdefMessage }
                messages.forEach { m ->
                    m.records.forEach { r ->
                        if (Arrays.equals(NdefRecord.RTD_TEXT, r.type)) {
                            val payload = r.payload
                            if (payload.isNotEmpty()) {
                                val textEncoding =
                                    if (payload[0] and 128.toByte() == 0.toByte()) "UTF-8" else "UTF-16"
                                val langCodeLength = payload[0] and 63.toByte()
                                val inMessage = String(
                                    payload,
                                    langCodeLength + 1,
                                    payload.count() - langCodeLength - 1,
                                    charset(textEncoding)
                                )
                                if ("COFFEE!" == inMessage.trim()) {
                                    mainViewModel.insert(Coffee(System.currentTimeMillis()))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}