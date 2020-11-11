package com.kvlg.emojify.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.kvlg.emojify.domain.ResourceManager
import com.kvlg.emojify.model.EmojiItem
import com.kvlg.emojify.model.Emojis
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject

/**
 * @author Konstantin Koval
 * @since 11.11.2020
 */
class SharedViewModel(
    resourceManager: ResourceManager
) : ViewModel() {

    private val emojiMap = mutableMapOf<String, EmojiItem>()

    init {
        val gson = Gson()
        val textJson = resourceManager.getAsset("emojis.json").bufferedReader().use { it.readText() }
        val jsonObject = JSONObject(textJson)
        val emojiKeys = gson.fromJson(textJson, Emojis::class.java)

        emojiKeys.keys.forEach {
            emojiMap[it] = gson.fromJson(jsonObject[it].toString(), EmojiItem::class.java)
        }
    }

    private val _emojiText = MutableLiveData<String>()
    val emojiText: LiveData<String> = _emojiText

    fun emojifyText(input: String) {
        val result = viewModelScope.launch {
            println("Start modifying")
            _emojiText.value = modifyText(input)
            println("Stop modifying")
        }
    }

    private suspend fun modifyText(input: String): String {
        return withContext(Dispatchers.IO) {
            buildString {
                input.split(" ").forEach { word ->
                    val result = emojiMap.values.find { em -> em.keywords.contains(word) }
                    if (result != null) append("$word ${result.char}")
                    else append(word)
                }
            }
        }
    }
}