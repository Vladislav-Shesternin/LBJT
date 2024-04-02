package com.education.lbjt.game.manager

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.education.lbjt.util.AbstractDataStore

object GameDataStoreManager: AbstractDataStore() {
    override val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "GAME_DATA_STORE")



//    object Balance: AbstractDataStore.DataStoreElement<Long>() {
//        override val key = longPreferencesKey("balance_key")
//    }

}

