package org.catsproject.project.core.di

import org.catsproject.project.core.HttpClientKtor
import org.catsproject.project.core.SessionManager
import org.catsproject.project.data.database.datasource.CatsDataSource
import org.catsproject.project.data.database.datasource.RealmCatsDataSource
import org.catsproject.project.data.database.domin.GetCatInformationDB
import org.catsproject.project.data.database.domin.AddCatMyFavorite
import org.catsproject.project.data.database.repository.CatsDataBaseRepository
import org.catsproject.project.data.database.repository.CatsDataBaseRepositoryImpl
import org.catsproject.project.data.network.datasource.CatsRemoteDataSource
import org.catsproject.project.data.network.datasource.CatsRemoteSource
import org.catsproject.project.data.network.domain.GetCatsCatalogCatsApi
import org.catsproject.project.data.network.repository.CatsCatalogImpl
import org.catsproject.project.data.network.repository.CatsCatalogRepository
import org.catsproject.project.data.repository.AuthRepository
import org.catsproject.project.domain.AuthUseCase
import org.catsproject.project.ui.viewmodel.CatInformationViewModel
import org.catsproject.project.ui.viewmodel.CatsSearchViewModel
import org.catsproject.project.ui.viewmodel.LoginViewModel
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import com.russhwolf.settings.Settings
import org.catsproject.project.core.ViewModelResetManager
import org.catsproject.project.data.database.datasource.FavoriteCatsDataSource
import org.catsproject.project.data.database.datasource.RealmCatsFavorite
import org.catsproject.project.data.database.domin.DeleteCatMyFavorite
import org.catsproject.project.data.database.domin.GetListFavorites
import org.catsproject.project.data.database.repository.CatsFavoriteBaseRepository
import org.catsproject.project.data.database.repository.CatsFavoriteBaseRepositoryImpl
import org.catsproject.project.data.network.domain.GetCatsPageDataBase
import org.catsproject.project.ui.viewmodel.FavoriteViewModel


val dataBase = module {

    single<CatsDataSource> { RealmCatsDataSource() }

    single<CatsDataBaseRepository> { CatsDataBaseRepositoryImpl(get()) }

    single<CatsDataBaseRepositoryImpl> { CatsDataBaseRepositoryImpl(
        catsDataSource = get()
    ) }


    single<FavoriteCatsDataSource> { RealmCatsFavorite() }

    single<CatsFavoriteBaseRepository> { CatsFavoriteBaseRepositoryImpl(get()) }

    single <CatsFavoriteBaseRepositoryImpl>{
        CatsFavoriteBaseRepositoryImpl(
            favoriteCatsDataSource = get ()
        )
    }


    single<AddCatMyFavorite> {
        AddCatMyFavorite(
           catsFavoriteBaseRepositoryImpl = get()
        )
    }

    single<DeleteCatMyFavorite> {
        DeleteCatMyFavorite(
            get()
        )
    }

    single<GetCatInformationDB> {
        GetCatInformationDB(
             get ()
        )
    }

    single<GetCatsPageDataBase> {
        GetCatsPageDataBase(
            catsDataBaseRepositoryImpl = get()
        )
    }


    single { CatInformationViewModel(
        getCatInformationDB = get(),
        addCatMyFavorite = get(),
        deleteCatMyFavorite = get(),
        sessionManager = get()
    ) }


    single<GetListFavorites> {
        GetListFavorites(
            catsFavoriteBaseRepositoryImpl = get()
        )
    }

    single {
        FavoriteViewModel(
            getListFavorites = get(),
            deleteCatMyFavorite = get(),
            repository = get(),
            sessionManager = get ()
        )
    }
}

val networkModule = module {

    single { HttpClientKtor.httpClient }



    single<CatsRemoteDataSource> {
        CatsRemoteSource(
            client = get()
        )
    }


    single<CatsCatalogRepository> {
        CatsCatalogImpl(
            catsRemoteDataSource = get()
        )
    }


    single<CatsCatalogImpl> {
        CatsCatalogImpl(
            catsRemoteDataSource = get()
        )
    }



    single<GetCatsCatalogCatsApi> {
        GetCatsCatalogCatsApi(
            catsCatalogImpl = get(),
            catsDataBaseRepositoryImpl = get()
        )
    }


    single { CatsSearchViewModel(
        getCatsCatalogCatsApi = get(),
        getCatsPageDataBase = get(),
        addCatMyFavorite = get(),
        deleteCatMyFavorite = get (),
        repository = get(),
        sessionManager = get()
    ) }

}


expect fun provideSettings(): Settings


val appModule = module {

    single { ViewModelResetManager() }
    single<Settings> { provideSettings() }
    single { SessionManager(get(),get()) }
    single { AuthRepository() }
    single { AuthUseCase(get()) }
    single { LoginViewModel(get(), get()) }
}


fun initKoin(config:KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(networkModule,dataBase,appModule)
    }
}

expect fun isDesktop(): Boolean