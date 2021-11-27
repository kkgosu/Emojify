package com.kvlg.emojify.ui.main

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.kvlg.emojify.domain.AnalyticsInteractor
import com.kvlg.emojify.domain.EmojiInteractor
import com.kvlg.emojify.domain.ResourceManager
import com.kvlg.emojify.domain.Result
import com.kvlg.emojify.model.EmojiItem
import com.kvlg.emojify.model.EmojiItem2
import com.kvlg.emojify.model.EmojifyedText
import com.kvlg.emojify.model.Emojis
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.util.*
import javax.inject.Inject
import kotlin.collections.set

/**
 * @author Konstantin Koval
 * @since 11.11.2020
 */
@HiltViewModel
class SharedViewModel @Inject constructor(
    resourceManager: ResourceManager,
    private val interactor: EmojiInteractor,
    private val preferences: SharedPreferences,
    private val analyticsInteractor: AnalyticsInteractor
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
        analyticsInteractor.onEmojifyClick()
        viewModelScope.launch {
            _loading.value = true
            val result = modifyText(input)
            _emojiText.value = result
            _loading.value = false
            if (result.isNotEmpty()) {
                saveText(result)
            }
        }
    }

    fun onCreateTabOpen() {
        analyticsInteractor.onCreateTabOpen()
    }

    fun onHistoryTabOpen() {
        analyticsInteractor.onHistoryTabOpen()
    }

    fun onThemeChange() {
        analyticsInteractor.onThemeChange()
    }

    fun onShareClick() {
        analyticsInteractor.onShareClick()
    }

    fun onCopyClick() {
        analyticsInteractor.onCopyClick()
    }

    fun onClearClick() {
        analyticsInteractor.onClearClick()
    }

    fun setScrollToTop(value: Boolean) {
        _scrollToTop.value = value
    }

    private suspend fun saveText(text: String) {
        interactor.saveText(text)
    }

    private suspend fun modifyText(input: String): String {
        return withContext(Dispatchers.IO) {
            appendResult(input)
        }
    }

    private fun appendResult(input: String): String {
        return buildString {
            val lastWord = mutableListOf<Char>()
            input.trim().forEach {
                if (it.isLetter()) {
                    lastWord.add(it)
                } else {
                    checkForEmojiAndPossiblyAppend(lastWord)
                    append(it)
                    lastWord.clear()
                }
            }
            checkForEmojiAndPossiblyAppend(lastWord)
        }
    }

    private fun StringBuilder.checkForEmojiAndPossiblyAppend(lastWord: MutableList<Char>) {
        if (lastWord.isNotEmpty()) {
            val fullWord = lastWord.joinToString("")
            if (fullWord.isNotEmpty()) {
                val emoji = findEmoji(fullWord)
                append(fullWord)
                emoji?.let { em ->
                    append(" $em")
                }
            }
        }
    }

    private fun findEmoji(word: String): String? {
        val filteredWord = word.toLowerCase(Locale.getDefault())
        val result = emojiMap.values.find { em -> em.keywords.contains(filteredWord) }
        return result?.char
            ?: emojiList2.find { it.aliases.contains(filteredWord) || it.description == filteredWord || it.tags.contains(filteredWord) }?.emoji
    }

    private fun checkForInAppReview() {
        val current = preferences.getInt(APP_ENTERS_COUNT_KEY, 1)
        if (current % 20 == 0) {
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