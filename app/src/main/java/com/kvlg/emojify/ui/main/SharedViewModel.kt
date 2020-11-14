package com.kvlg.emojify.ui.main

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.kvlg.emojify.domain.EmojiInteractor
import com.kvlg.emojify.domain.ResourceManager
import com.kvlg.emojify.domain.Result
import com.kvlg.emojify.model.EmojiItem
import com.kvlg.emojify.model.EmojiItem2
import com.kvlg.emojify.model.EmojifyedText
import com.kvlg.emojify.model.Emojis
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.util.*
import kotlin.collections.set

/**
 * @author Konstantin Koval
 * @since 11.11.2020
 */
class SharedViewModel @ViewModelInject constructor(
    resourceManager: ResourceManager,
    private val interactor: EmojiInteractor
) : ViewModel() {

    private val emojiMap = mutableMapOf<String, EmojiItem>()
    private var emojiList2 = emptyList<EmojiItem2>()
    private val _emojiText = MutableLiveData<String>()

    init {
        val gson = Gson()
        val textJson = resourceManager.getAsset("emojis.json").bufferedReader().use { it.readText() }
        val textJson2 = resourceManager.getAsset("emojis2.json").bufferedReader().use { it.readText() }
        val jsonObject = JSONObject(textJson)
        val emojiKeys = gson.fromJson(textJson, Emojis::class.java)
        emojiList2 = gson.fromJson(textJson2, Array<EmojiItem2>::class.java).toList()

        emojiKeys.keys.forEach {
            emojiMap[it] = gson.fromJson(jsonObject[it].toString(), EmojiItem::class.java)
        }
    }

    val history: LiveData<Result<List<EmojifyedText>>> = interactor.getAllTexts()

    val emojiText: LiveData<String> = _emojiText

    fun emojifyText(input: String) {
        viewModelScope.launch {
            Log.d(TAG, "emojifyText: Start modifying")
            val result = modifyText(input)
            _emojiText.value = result
            Log.d(TAG, "emojifyText: Stop modifying")
            saveText(result)
        }
    }

    private suspend fun saveText(text: String) {
        interactor.saveText(text)
    }

    private suspend fun modifyText(input: String): String {
        return withContext(Dispatchers.IO) {
            buildString {
                input.split(" ").forEach { word ->
                    appendResult(word)
                }
            }
        }
    }

    private fun StringBuilder.appendResult(word: String): StringBuilder? {
        val filteredWord = word.toLowerCase(Locale.getDefault()).replace("[^a-zA-Z]".toRegex(), "")
        val result = emojiMap.values.find { em -> em.keywords.contains(filteredWord) }
        var result2: EmojiItem2? = null
        if (result == null) {
            result2 = emojiList2.find { it.aliases.contains(filteredWord) || it.description == filteredWord || it.tags.contains(filteredWord) }
        }
        return when {
            result != null -> {
                append("$word ${result.char} ")
            }
            result2 != null -> {
                append("$word ${result2.emoji}")
            }
            word.isPossiblePlural() -> {
                appendResult(word.dropLast(1))
            }
            else -> {
                append("$word ")
            }
        }
    }

    private fun String.isPossiblePlural() = if (isNotEmpty()) last() == 's' else false

    companion object {
        private val TAG = "SharedViewModel"
    }
}