package com.wsr

import androidx.room.Room
import com.wsr.edit.EditViewModel
import com.wsr.index.IndexViewModel
import com.wsr.index.dialog.IndexCreatePasswordGroupDialogViewModel
import com.wsr.infra.PassonDatabase
import com.wsr.infra.password.PasswordPairEntityDao
import com.wsr.infra.password.RoomPasswordPairRepositoryImpl
import com.wsr.infra.passwordgroup.PasswordGroupEntityDao
import com.wsr.infra.passwordgroup.RoomPasswordGroupRepositoryImpl
import com.wsr.login.LoginViewModel
import com.wsr.passwordgroup.PasswordGroupRepository
import com.wsr.passwordgroup.create.CreatePasswordGroupUseCase
import com.wsr.passwordgroup.create.CreatePasswordGroupUseCaseImpl
import com.wsr.passwordgroup.get.GetPasswordGroupUseCase
import com.wsr.passwordgroup.get.GetPasswordGroupUseCaseImpl
import com.wsr.passwordgroup.getall.GetAllPasswordGroupUseCase
import com.wsr.passwordgroup.getall.GetAllPasswordGroupUseCaseImpl
import com.wsr.passwordgroup.update.UpdatePasswordGroupUseCase
import com.wsr.passwordgroup.update.UpdatePasswordGroupUseCaseImpl
import com.wsr.passwordpair.PasswordPairRepository
import com.wsr.passwordpair.create.CreatePasswordPairUseCase
import com.wsr.passwordpair.create.CreatePasswordPairUseCaseImpl
import com.wsr.passwordpair.getall.GetAllPasswordPairUseCase
import com.wsr.passwordpair.getall.GetAllPasswordPairUseCaseImpl
import com.wsr.passwordpair.upsert.UpsertPasswordPairUseCase
import com.wsr.passwordpair.upsert.UpsertPasswordPairUseCaseImpl
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
    single<UpdatePasswordGroupUseCase> { UpdatePasswordGroupUseCaseImpl(get()) }

    // Password
    factory<GetAllPasswordPairUseCase> { GetAllPasswordPairUseCaseImpl(get()) }
    single<UpsertPasswordPairUseCase> { UpsertPasswordPairUseCaseImpl(get()) }
    single<CreatePasswordPairUseCase> { CreatePasswordPairUseCaseImpl() }

    /*** Repository ***/
    single<PasswordGroupRepository> { RoomPasswordGroupRepositoryImpl(get()) }
    single<PasswordPairRepository> { RoomPasswordPairRepositoryImpl(get()) }

    single<PassonDatabase> {
        Room.databaseBuilder(
            androidApplication(),
            PassonDatabase::class.java,
            "passon_database"
        ).build()
    }

    /*** DAO ***/
    single<PasswordPairEntityDao> {
        val database by inject<PassonDatabase>()
        database.passwordEntityDao()
    }
    single<PasswordGroupEntityDao> {
        val database by inject<PassonDatabase>()
        database.passwordGroupEntityDao()
    }
}
