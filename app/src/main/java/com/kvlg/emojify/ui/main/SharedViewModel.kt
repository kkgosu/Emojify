package com.kvlg.emojify.ui.main

import android.content.SharedPreferences
import androidx.core.content.edit
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
    private val interactor: EmojiInteractor,
    private val preferences: SharedPreferences
) : ViewModel() {

    private val emojiMap = mutableMapOf<String, EmojiItem>()
    private var emojiList2 = emptyList<EmojiItem2>()
    private val _emojiText = MutableLiveData<String>()
    private val _loading = MutableLiveData<Boolean>()
    private val _scrollToTop = MutableLiveData<Boolean>()
    private val _showInAppReview = MutableLiveData<Boolean>()

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

        checkForInAppReview()
    }

    val history: LiveData<Result<List<EmojifyedText>>> = interactor.getAllTexts()
    val emojiText: LiveData<String> = _emojiText
    val loading: LiveData<Boolean> = _loading
    val scrollToTop: LiveData<Boolean> = _scrollToTop
    val showInAppReview: LiveData<Boolean> = _showInAppReview

    fun emojifyText(input: String) {
        viewModelScope.launch {
            _loading.value = true
            val result = modifyText(input)
            _emojiText.value = result
            _loading.value = false
            saveText(result)
        }
    }

    fun setScrollToTop(value: Boolean) {
        _scrollToTop.value = value
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

    private fun StringBuilder.appendResult(word: String, specialSymbols: MutableList<Char> = mutableListOf()): StringBuilder? {
        val filteredWord = word.toLowerCase(Locale.getDefault())
        val result = emojiMap.values.find { em -> em.keywords.contains(filteredWord) }
        var result2: EmojiItem2? = null
        if (result == null) {
            result2 = emojiList2.find { it.aliases.contains(filteredWord) || it.description == filteredWord || it.tags.contains(filteredWord) }
        }
        return when {
            result != null -> {
                append("$word${appendS(specialSymbols)} ${result.char}${appendSpecialSymbols(specialSymbols)} ")
            }
            result2 != null -> {
                append("$word${appendS(specialSymbols)} ${result2.emoji}${appendSpecialSymbols(specialSymbols)} ")
            }
            word.isLastSpecialSymbol() -> {
                specialSymbols.add(word.last())
                appendResult(word.dropLast(1), specialSymbols)
            }
            word.isPossiblePlural() -> {
                specialSymbols.add(word.last())
                appendResult(word.dropLast(1), specialSymbols)
            }
            else -> {
                append("$word${appendS(specialSymbols)}${appendSpecialSymbols(specialSymbols)} ")
            }
        }
    }

    private fun appendS(specialSymbols: MutableList<Char>) = specialSymbols.filter { it == 's' }.joinToString("")

    private fun appendSpecialSymbols(specialSymbols: MutableList<Char>) = specialSymbols.filter { it != 's' }.reversed().joinToString("")

    private fun String.isPossiblePlural() = if (isNotEmpty()) last() == 's' else false

    private fun String.isLastSpecialSymbol() = if (isNotEmpty()) !last().isLetter() else false

    private fun checkForInAppReview() {
        val current = preferences.getInt(APP_ENTERS_COUNT_KEY, 1)
        if (current == 10 || current == 20) {
            _showInAppReview.value = true
        }
        preferences.edit {
            putInt(APP_ENTERS_COUNT_KEY, current + 1)
        }
    }

    companion object {
        private const val TAG = "SharedViewModel"
        private const val STRING_TO_TEST = "hello hellos hellos] hello])}/ face."

        private const val APP_ENTERS_COUNT_KEY = "APP_ENTERS_COUNT_KEY"
    }
}