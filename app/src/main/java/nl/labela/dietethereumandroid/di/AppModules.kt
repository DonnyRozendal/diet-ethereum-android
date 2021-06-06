package nl.labela.dietethereumandroid.di

import nl.labela.dietethereumandroid.ui.screens.HomeViewModel
import org.koin.androidx.experimental.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel<HomeViewModel>()
}

val repositoryModule = module {
//    single<MainRepository>()
}

val apiModule = module {
//    single<Api>()
}

val appModules = listOf(
    viewModelModule,
    repositoryModule,
    apiModule
)