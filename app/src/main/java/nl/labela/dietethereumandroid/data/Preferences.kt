package nl.labela.dietethereumandroid.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

val WALLET_FILE_NAME = stringPreferencesKey("wallet_file_name")

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "wallet")

suspend fun <T> DataStore<Preferences>.get(key: Preferences.Key<T>): T? {
    return data.map { it[key] }.first()
}

suspend fun <T> DataStore<Preferences>.store(key: Preferences.Key<T>, data: T): T? {
    return edit { it[key] = data }[key]
}