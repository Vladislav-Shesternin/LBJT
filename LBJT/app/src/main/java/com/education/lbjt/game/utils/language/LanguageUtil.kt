package com.education.lbjt.game.utils.language


import android.content.Context
import com.education.lbjt.MainActivity
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.Locale

class LanguageUtil(activity: MainActivity) {

    private val configurationContextUK: Context by lazy { with(activity) { resources.configuration.run {
        setLocale(Locale(Language.UK.language))
        createConfigurationContext(this)
    } } }
    private val configurationContextEN: Context by lazy { with(activity) { resources.configuration.run {
        setLocale(Locale(Language.EN.language))
        createConfigurationContext(this)
    } } }


    var languageFlow = MutableStateFlow(getLanguageByLocale(Locale.getDefault()))

    fun getStringResource(
        resourceId: Int,
        language: Language = languageFlow.value,
    ): String = getConfigurationContextByLanguage(language).getString(resourceId)

    fun getStringArrayResources(
        resourceId: Int,
        language: Language = languageFlow.value,
    ): Array<String> = getConfigurationContextByLanguage(language).resources.getStringArray(resourceId)

    private fun getConfigurationContextByLanguage(language: Language): Context = when (language) {
        Language.UK -> configurationContextUK
        Language.EN -> configurationContextEN
    }

    private fun getLanguageByLocale(locale: Locale): Language {
        return when (locale.language) {
            Language.UK.language -> Language.UK
            Language.EN.language -> Language.EN
            else -> Language.EN
        }

    }

}