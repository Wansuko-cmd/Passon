package com.wsr

import androidx.room.Room
import com.wsr.edit.EditViewModel
import com.wsr.index.IndexViewModel
import com.wsr.index.dialog.IndexCreatePasswordGroupDialogViewModel
import com.wsr.infra.PassonDatabase
import com.wsr.infra.password.PasswordEntityDao
import com.wsr.infra.password.RoomPasswordRepositoryImpl
import com.wsr.infra.passwordgroup.PasswordGroupEntityDao
import com.wsr.infra.passwordgroup.RoomPasswordGroupRepositoryImpl
import com.wsr.login.LoginViewModel
import com.wsr.password.PasswordRepository
import com.wsr.password.create.CreatePasswordUseCase
import com.wsr.password.create.CreatePasswordUseCaseImpl
import com.wsr.password.getall.GetAllPasswordUseCase
import com.wsr.password.getall.GetAllPasswordUseCaseImpl
import com.wsr.password.upsert.UpsertPasswordUseCase
import com.wsr.password.upsert.UpsertPasswordUseCaseImpl
import com.wsr.passwordgroup.PasswordGroupRepository
import com.wsr.passwordgroup.create.CreatePasswordGroupUseCase
import com.wsr.passwordgroup.create.CreatePasswordGroupUseCaseImpl
import com.wsr.passwordgroup.get.GetPasswordGroupUseCase
import com.wsr.passwordgroup.get.GetPasswordGroupUseCaseImpl
import com.wsr.passwordgroup.getall.GetAllPasswordGroupUseCase
import com.wsr.passwordgroup.getall.GetAllPasswordGroupUseCaseImpl
import com.wsr.passwordgroup.update.UpdatePasswordGroupUseCase
import com.wsr.passwordgroup.update.UpdatePasswordGroupUseCaseImpl
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

    /*** UseCase ***/
    // Password Group
    single<GetAllPasswordGroupUseCase> { GetAllPasswordGroupUseCaseImpl(get()) }
    single<GetPasswordGroupUseCase> { GetPasswordGroupUseCaseImpl(get()) }
    single<CreatePasswordGroupUseCase> { CreatePasswordGroupUseCaseImpl(get()) }
    single<UpdatePasswordGroupUseCase> { UpdatePasswordGroupUseCaseImpl(get()) }

    // Password
    single<GetAllPasswordUseCase> { GetAllPasswordUseCaseImpl(get()) }
    single<UpsertPasswordUseCase> { UpsertPasswordUseCaseImpl(get()) }
    single<CreatePasswordUseCase> { CreatePasswordUseCaseImpl() }

    /*** Repository ***/
    single<PasswordGroupRepository> { RoomPasswordGroupRepositoryImpl(get()) }
    single<PasswordRepository> { RoomPasswordRepositoryImpl(get()) }

    single<PassonDatabase> {
        Room.databaseBuilder(
            androidApplication(),
            PassonDatabase::class.java,
            "passon_database"
        ).build()
    }

    /*** DAO ***/
    single<PasswordEntityDao> {
        val database by inject<PassonDatabase>()
        database.passwordEntityDao()
    }
    single<PasswordGroupEntityDao> {
        val database by inject<PassonDatabase>()
        database.passwordGroupEntityDao()
    }
}
