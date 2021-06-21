package nl.labela.dietethereumandroid.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import nl.labela.dietethereumandroid.data.EthereumRepository
import nl.labela.dietethereumandroid.data.dataStore
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Singleton
    @Provides
    fun providesDataStore(@ApplicationContext applicationContext: Context): DataStore<Preferences> {
        return applicationContext.dataStore
    }

    @Singleton
    @Provides
    fun providesEthereumRepository(
        @ApplicationContext applicationContext: Context,
        dataStore: DataStore<Preferences>
    ): EthereumRepository {
        return EthereumRepository(applicationContext, dataStore)
    }

}