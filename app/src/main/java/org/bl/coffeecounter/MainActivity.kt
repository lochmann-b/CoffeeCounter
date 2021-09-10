package org.bl.coffeecounter

import android.content.Intent
import android.nfc.NfcAdapter
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import org.bl.coffeecounter.ui.main.MainFragment
import org.bl.coffeecounter.ui.main.MainViewModel
import org.bl.coffeecounter.ui.main.MainViewModelFactory
import org.bl.coffeecounter.util.parseNFCMessage

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
              parseNFCMessage(rawMessages, {coffee -> mainViewModel.addACoffee(coffee)}, {exception -> handleException(exception)})
            }
        }
    }

    private fun handleException(e: Exception) {
        Log.e("COFFEE-COUNTER", "Error while parsing nfc data", e)
        Toast.makeText(this, R.string.could_not_parse_nfc_tag, Toast.LENGTH_LONG).show()
    }
}