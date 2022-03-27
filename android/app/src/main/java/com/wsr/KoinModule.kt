package com.wsr

import androidx.room.Room
import com.wsr.edit.EditViewModel
import com.wsr.index.IndexViewModel
import com.wsr.index.dialog.IndexCreatePasswordGroupDialogViewModel
import com.wsr.infra.PassonDatabase
import com.wsr.infra.passwordgroup.LocalPasswordGroupRepositoryImpl
import com.wsr.infra.passwordgroup.PasswordGroupEntityDao
import com.wsr.infra.passwordgroup.queryservice.LocalDeletePasswordGroupUseCaseQueryImpl
import com.wsr.infra.passwordgroup.queryservice.LocalGetAllPasswordGroupUseCaseQueryServiceImpl
import com.wsr.infra.passwordgroup.queryservice.LocalGetPasswordGroupUseCaseQueryServiceImpl
import com.wsr.infra.passwordgroup.queryservice.LocalUpdatePasswordGroupUseCaseQueryServiceImpl
import com.wsr.infra.passworditem.LocalPasswordItemRepositoryImpl
import com.wsr.infra.passworditem.PasswordItemEntityDao
import com.wsr.infra.passworditem.queryservice.LocalDeletePasswordItemUseCaseQueryServiceImpl
import com.wsr.infra.passworditem.queryservice.LocalGetAllPasswordItemUseCaseQueryServiceImpl
import com.wsr.login.LoginViewModel
import com.wsr.passwordgroup.PasswordGroupRepository
import com.wsr.create.CreatePasswordGroupUseCase
import com.wsr.create.CreatePasswordGroupUseCaseImpl
import com.wsr.passwordgroup.delete.DeletePasswordGroupUseCase
import com.wsr.passwordgroup.delete.DeletePasswordGroupUseCaseImpl
import com.wsr.passwordgroup.delete.DeletePasswordGroupUseCaseQueryService
import com.wsr.passwordgroup.get.GetPasswordGroupUseCase
import com.wsr.passwordgroup.get.GetPasswordGroupUseCaseImpl
import com.wsr.passwordgroup.get.GetPasswordGroupUseCaseQueryService
import com.wsr.passwordgroup.getall.GetAllPasswordGroupUseCase
import com.wsr.passwordgroup.getall.GetAllPasswordGroupUseCaseImpl
import com.wsr.passwordgroup.getall.GetAllPasswordGroupUseCaseQueryService
import com.wsr.passwordgroup.update.UpdatePasswordGroupUseCase
import com.wsr.passwordgroup.update.UpdatePasswordGroupUseCaseImpl
import com.wsr.passwordgroup.update.UpdatePasswordGroupUseCaseQueryService
import com.wsr.passworditem.PasswordItemRepository
import com.wsr.create.CreatePasswordItemUseCase
import com.wsr.create.CreatePasswordItemUseCaseImpl
import com.wsr.passworditem.delete.DeletePasswordItemUseCase
import com.wsr.passworditem.delete.DeletePasswordItemUseCaseImpl
import com.wsr.passworditem.delete.DeletePasswordItemUseCaseQueryService
import com.wsr.passworditem.getall.GetAllPasswordItemUseCase
import com.wsr.passworditem.getall.GetAllPasswordItemUseCaseImpl
import com.wsr.passworditem.getall.GetAllPasswordItemUseCaseQueryService
import com.wsr.passworditem.upsert.UpsertPasswordItemUseCase
import com.wsr.passworditem.upsert.UpsertPasswordItemUseCaseImpl
import com.wsr.show.ShowViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val module = module {

    /*** View Model ***/
    viewModel { LoginViewModel() }
    viewModel { IndexViewModel(get(), get()) }
    viewModel { IndexCreatePasswordGroupDialogViewModel() }
    viewModel { ShowViewModel(get(), get(), get(), get()) }
    viewModel { EditViewModel(get(), get(), get(), get(), get()) }

    /**
     * UseCase
     *
     * StateFlowを持つUseCase -> factory
     * StateFlowを持たないUseCase -> single
     */
    // Password Group
    factory<GetAllPasswordGroupUseCase> { GetAllPasswordGroupUseCaseImpl(get()) }
    factory<GetPasswordGroupUseCase> { GetPasswordGroupUseCaseImpl(get()) }
    single<CreatePasswordGroupUseCase> { CreatePasswordGroupUseCaseImpl(get()) }
    single<UpdatePasswordGroupUseCase> { UpdatePasswordGroupUseCaseImpl(get(), get()) }
    single<DeletePasswordGroupUseCase> { DeletePasswordGroupUseCaseImpl(get(), get()) }

    // Password
    factory<GetAllPasswordItemUseCase> { GetAllPasswordItemUseCaseImpl(get()) }
    single<UpsertPasswordItemUseCase> { UpsertPasswordItemUseCaseImpl(get()) }
    single<CreatePasswordItemUseCase> { CreatePasswordItemUseCaseImpl() }
    single<DeletePasswordItemUseCase> { DeletePasswordItemUseCaseImpl(get(), get()) }

    /*** QueryService ***/
    // Password Group
    single<GetAllPasswordGroupUseCaseQueryService> { LocalGetAllPasswordGroupUseCaseQueryServiceImpl(get()) }
    single<GetPasswordGroupUseCaseQueryService> { LocalGetPasswordGroupUseCaseQueryServiceImpl(get()) }
    single<UpdatePasswordGroupUseCaseQueryService> { LocalUpdatePasswordGroupUseCaseQueryServiceImpl(get()) }
    single<DeletePasswordGroupUseCaseQueryService> { LocalDeletePasswordGroupUseCaseQueryImpl(get()) }

    // Password Item
    single<GetAllPasswordItemUseCaseQueryService> { LocalGetAllPasswordItemUseCaseQueryServiceImpl(get()) }
    single<DeletePasswordItemUseCaseQueryService> { LocalDeletePasswordItemUseCaseQueryServiceImpl(get()) }

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
        database.passwordEntityDao()
    }
    single<PasswordGroupEntityDao> {
        val database by inject<PassonDatabase>()
        database.passwordGroupEntityDao()
    }
}
