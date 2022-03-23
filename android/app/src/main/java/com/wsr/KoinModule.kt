package com.wsr

import androidx.room.Room
import com.wsr.edit.EditViewModel
import com.wsr.index.IndexViewModel
import com.wsr.index.dialog.IndexCreatePasswordGroupDialogViewModel
import com.wsr.infra.PassonDatabase
import com.wsr.infra.passwordgroup.PasswordGroupEntityDao
import com.wsr.infra.passwordgroup.RoomPasswordGroupRepositoryImpl
import com.wsr.infra.passwordgroup.queryservice.RoomGetAllPasswordGroupQueryServiceImpl
import com.wsr.infra.passwordgroup.queryservice.RoomGetPasswordGroupQueryServiceImpl
import com.wsr.infra.passwordgroup.queryservice.RoomUpdatePasswordGroupQueryServiceImpl
import com.wsr.infra.passworditem.PasswordItemEntityDao
import com.wsr.infra.passworditem.RoomPasswordItemRepositoryImpl
import com.wsr.infra.passworditem.queryservice.RoomGetAllPasswordItemQueryServiceImpl
import com.wsr.login.LoginViewModel
import com.wsr.passwordgroup.PasswordGroupRepository
import com.wsr.passwordgroup.create.CreatePasswordGroupUseCase
import com.wsr.passwordgroup.create.CreatePasswordGroupUseCaseImpl
import com.wsr.passwordgroup.get.GetPasswordGroupQueryService
import com.wsr.passwordgroup.get.GetPasswordGroupUseCase
import com.wsr.passwordgroup.get.GetPasswordGroupUseCaseImpl
import com.wsr.passwordgroup.getall.GetAllPasswordGroupQueryService
import com.wsr.passwordgroup.getall.GetAllPasswordGroupUseCase
import com.wsr.passwordgroup.getall.GetAllPasswordGroupUseCaseImpl
import com.wsr.passwordgroup.update.UpdatePasswordGroupQueryService
import com.wsr.passwordgroup.update.UpdatePasswordGroupUseCase
import com.wsr.passwordgroup.update.UpdatePasswordGroupUseCaseImpl
import com.wsr.passworditem.PasswordItemRepository
import com.wsr.passworditem.create.CreatePasswordItemUseCase
import com.wsr.passworditem.create.CreatePasswordItemUseCaseImpl
import com.wsr.passworditem.getall.GetAllPasswordItemQueryService
import com.wsr.passworditem.getall.GetAllPasswordItemUseCase
import com.wsr.passworditem.getall.GetAllPasswordItemUseCaseImpl
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
    viewModel { ShowViewModel(get(), get()) }
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

    // Password
    factory<GetAllPasswordItemUseCase> { GetAllPasswordItemUseCaseImpl(get()) }
    single<UpsertPasswordItemUseCase> { UpsertPasswordItemUseCaseImpl(get()) }
    single<CreatePasswordItemUseCase> { CreatePasswordItemUseCaseImpl() }


    /*** QueryService ***/
    // Password Group
    single<GetAllPasswordGroupQueryService> { RoomGetAllPasswordGroupQueryServiceImpl(get()) }
    single<GetPasswordGroupQueryService> { RoomGetPasswordGroupQueryServiceImpl(get()) }
    single<UpdatePasswordGroupQueryService> { RoomUpdatePasswordGroupQueryServiceImpl(get()) }

    // Password Item
    single<GetAllPasswordItemQueryService> { RoomGetAllPasswordItemQueryServiceImpl(get()) }

    /*** Repository ***/
    single<PasswordGroupRepository> { RoomPasswordGroupRepositoryImpl(get()) }
    single<PasswordItemRepository> { RoomPasswordItemRepositoryImpl(get()) }

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
