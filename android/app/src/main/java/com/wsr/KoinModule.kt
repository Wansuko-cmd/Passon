package com.wsr

import androidx.room.Room
import com.wsr.create.CreatePasswordGroupUseCase
import com.wsr.create.CreatePasswordGroupUseCaseImpl
import com.wsr.create.CreatePasswordItemUseCase
import com.wsr.create.CreatePasswordItemUseCaseImpl
import com.wsr.delete.DeletePasswordGroupUseCase
import com.wsr.delete.DeletePasswordGroupUseCaseImpl
import com.wsr.edit.EditViewModel
import com.wsr.fetch.FetchAllPasswordGroupUseCase
import com.wsr.fetch.FetchAllPasswordGroupUseCaseImpl
import com.wsr.fetch.FetchAllPasswordGroupUseCaseQueryService
import com.wsr.fetch.FetchPasswordSetUseCase
import com.wsr.fetch.FetchPasswordSetUseCaseImpl
import com.wsr.fetch.FetchPasswordSetUseCaseQueryService
import com.wsr.index.IndexViewModel
import com.wsr.index.dialog.IndexCreatePasswordGroupDialogViewModel
import com.wsr.infra.PassonDatabase
import com.wsr.infra.passwordgroup.LocalPasswordGroupRepositoryImpl
import com.wsr.infra.passwordgroup.PasswordGroupEntityDao
import com.wsr.infra.passworditem.LocalPasswordItemRepositoryImpl
import com.wsr.infra.passworditem.PasswordItemEntityDao
import com.wsr.infra.queryservice.LocalFetchAllPasswordGroupUseCaseQueryServiceImpl
import com.wsr.infra.queryservice.LocalFetchPasswordSetUseCaseQueryServiceImpl
import com.wsr.infra.queryservice.LocalSyncPasswordSetUseCaseQueryServiceImpl
import com.wsr.login.LoginViewModel
import com.wsr.passwordgroup.PasswordGroupRepository
import com.wsr.passworditem.PasswordItemRepository
import com.wsr.show.ShowViewModel
import com.wsr.sync.SyncPasswordSetUseCase
import com.wsr.sync.SyncPasswordSetUseCaseImpl
import com.wsr.sync.SyncPasswordSetUseCaseQueryService
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val module = module {

    /*** View Model ***/
    viewModel { LoginViewModel() }
    viewModel { IndexViewModel(get(), get()) }
    viewModel { IndexCreatePasswordGroupDialogViewModel() }
    viewModel { ShowViewModel(get(), get()) }
    viewModel { EditViewModel(get(), get(), get()) }

    /**
     * UseCase & Repository
     *
     * StateFlowを持つUseCase -> factory
     * StateFlowを持たないUseCase -> single
     */
    // create
    single<CreatePasswordGroupUseCase> { CreatePasswordGroupUseCaseImpl(get()) }
    single<CreatePasswordItemUseCase> { CreatePasswordItemUseCaseImpl() }

    // delete
    single<DeletePasswordGroupUseCase> { DeletePasswordGroupUseCaseImpl(get()) }

    // fetch
    factory<FetchAllPasswordGroupUseCase> { FetchAllPasswordGroupUseCaseImpl(get()) }
    single<FetchAllPasswordGroupUseCaseQueryService> { LocalFetchAllPasswordGroupUseCaseQueryServiceImpl(get()) }
    factory<FetchPasswordSetUseCase> { FetchPasswordSetUseCaseImpl(get()) }
    single<FetchPasswordSetUseCaseQueryService> { LocalFetchPasswordSetUseCaseQueryServiceImpl(get(), get()) }

    // sync
    single<SyncPasswordSetUseCase> { SyncPasswordSetUseCaseImpl(get(), get(), get()) }
    single<SyncPasswordSetUseCaseQueryService> { LocalSyncPasswordSetUseCaseQueryServiceImpl(get()) }

    /*** Repository ***/
    single<PasswordGroupRepository> { LocalPasswordGroupRepositoryImpl(get()) }
    single<PasswordItemRepository> { LocalPasswordItemRepositoryImpl(get()) }

    single<PassonDatabase> {
        Room.databaseBuilder(
            androidApplication(),
            PassonDatabase::class.java,
            "passon_database"
        ).build()
    }

    /*** DAO ***/
    single<PasswordItemEntityDao> {
        val database by inject<PassonDatabase>()
        database.passwordItemEntityDao()
    }
    single<PasswordGroupEntityDao> {
        val database by inject<PassonDatabase>()
        database.passwordGroupEntityDao()
    }
}
