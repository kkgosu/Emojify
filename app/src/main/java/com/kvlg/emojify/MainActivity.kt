package com.kvlg.emojify

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.kvlg.emojify.databinding.ActivityMainBinding
import com.kvlg.emojify.model.EmojiItem
import com.kvlg.emojify.model.Emojis
import com.kvlg.emojify.ui.main.SectionsPagerAdapter
import org.json.JSONObject


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.setSystemBarsAppearance(
                APPEARANCE_LIGHT_STATUS_BARS,
                APPEARANCE_LIGHT_STATUS_BARS
            )
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            var flags = window.decorView.systemUiVisibility
            flags = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            window.decorView.systemUiVisibility = flags
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        with(binding) {
            tabs.setupWithViewPager(viewPager)
            viewPager.adapter = sectionsPagerAdapter
            tabs.getTabAt(0)?.setIcon(R.drawable.ic_round_create_24)
            tabs.getTabAt(1)?.setIcon(R.drawable.ic_round_history_24)
        }

        val gson = Gson()

        val textJson = assets.open("emojis.json").bufferedReader().use { it.readText() }
        val jsonObject = JSONObject(textJson)
        val emojiKeys = gson.fromJson(textJson, Emojis::class.java)

        val mapOfEmojis = mutableMapOf<String, EmojiItem>()
        emojiKeys.keys.forEach {
            mapOfEmojis[it] = gson.fromJson(jsonObject[it].toString(), EmojiItem::class.java)
        }
    }
}