package com.wsr

import androidx.room.Room
import com.wsr.create.CreatePasswordGroupUseCase
import com.wsr.create.CreatePasswordGroupUseCaseImpl
import com.wsr.create.CreatePasswordItemUseCase
import com.wsr.create.CreatePasswordItemUseCaseImpl
import com.wsr.delete.DeletePasswordGroupUseCase
import com.wsr.delete.DeletePasswordGroupUseCaseImpl
import com.wsr.edit.EditViewModel
import com.wsr.get.GetAllPasswordGroupUseCase
import com.wsr.get.GetAllPasswordGroupUseCaseImpl
import com.wsr.get.FetchAllPasswordGroupUseCaseQueryService
import com.wsr.get.GetPasswordPairUseCase
import com.wsr.get.GetPasswordPairUseCaseImpl
import com.wsr.get.FetchPasswordPairUseCaseQueryService
import com.wsr.index.IndexViewModel
import com.wsr.index.dialog.IndexCreatePasswordGroupDialogViewModel
import com.wsr.infra.PassonDatabase
import com.wsr.infra.passwordgroup.LocalPasswordGroupRepositoryImpl
import com.wsr.infra.passwordgroup.PasswordGroupEntityDao
import com.wsr.infra.passworditem.LocalPasswordItemRepositoryImpl
import com.wsr.infra.passworditem.PasswordItemEntityDao
import com.wsr.infra.queryservice.LocalFetchAllPasswordGroupUseCaseQueryServiceImpl
import com.wsr.infra.queryservice.LocalFetchPasswordPairUseCaseQueryServiceImpl
import com.wsr.infra.queryservice.LocalSyncPasswordPairUseCaseQueryServiceImpl
import com.wsr.login.LoginViewModel
import com.wsr.passwordgroup.PasswordGroupRepository
import com.wsr.passworditem.PasswordItemRepository
import com.wsr.show.ShowViewModel
import com.wsr.sync.SyncPasswordPairUseCase
import com.wsr.sync.SyncPasswordPairUseCaseImpl
import com.wsr.sync.SyncPasswordPairUseCaseQueryService
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
    factory<GetAllPasswordGroupUseCase> { GetAllPasswordGroupUseCaseImpl(get()) }
    single<FetchAllPasswordGroupUseCaseQueryService> { LocalFetchAllPasswordGroupUseCaseQueryServiceImpl(get()) }
    factory<GetPasswordPairUseCase> { GetPasswordPairUseCaseImpl(get()) }
    single<FetchPasswordPairUseCaseQueryService> { LocalFetchPasswordPairUseCaseQueryServiceImpl(get(), get()) }

    // sync
    single<SyncPasswordPairUseCase> { SyncPasswordPairUseCaseImpl(get(), get(), get()) }
    single<SyncPasswordPairUseCaseQueryService> { LocalSyncPasswordPairUseCaseQueryServiceImpl(get()) }

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
