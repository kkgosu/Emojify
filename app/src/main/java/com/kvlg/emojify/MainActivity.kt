package com.kvlg.emojify

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import com.kvlg.emojify.model.EmojiItem
import com.kvlg.emojify.model.Emojis
import com.kvlg.emojify.ui.main.SectionsPagerAdapter
import org.json.JSONObject


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
        val fab: FloatingActionButton = findViewById(R.id.fab)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        val gson = Gson()

        val textJson = assets.open("emojis.json").bufferedReader().use { it.readText() }
        val jsonObject = JSONObject(textJson)
        val emojiKeys = gson.fromJson(textJson, Emojis::class.java)

        val mapOfEmojis = mutableMapOf<String, EmojiItem>()
        emojiKeys.keys.forEach {
            val emojiValue = jsonObject[it]
            val result = gson.fromJson(emojiValue.toString(), EmojiItem::class.java)
            mapOfEmojis[it] = result
        }

        println(mapOfEmojis.values.size)
    }
}