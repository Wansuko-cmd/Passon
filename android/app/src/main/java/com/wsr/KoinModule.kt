package com.wsr

import androidx.room.Room
import com.wsr.auth.LoginUseCase
import com.wsr.auth.LoginUseCaseImpl
import com.wsr.auth.ResetUseCase
import com.wsr.auth.ResetUseCaseImpl
import com.wsr.auth.SignUpUseCase
import com.wsr.auth.SignUpUseCaseImpl
import com.wsr.create.CreatePasswordGroupUseCase
import com.wsr.create.CreatePasswordGroupUseCaseImpl
import com.wsr.create.CreatePasswordItemUseCase
import com.wsr.create.CreatePasswordItemUseCaseImpl
import com.wsr.delete.DeletePasswordGroupUseCase
import com.wsr.delete.DeletePasswordGroupUseCaseImpl
import com.wsr.edit.EditViewModel
import com.wsr.get.GetAllPasswordGroupUseCase
import com.wsr.get.GetAllPasswordGroupUseCaseImpl
import com.wsr.get.GetAllUserUseCase
import com.wsr.get.GetAllUserUseCaseImpl
import com.wsr.get.GetPasswordPairUseCase
import com.wsr.get.GetPasswordPairUseCaseImpl
import com.wsr.index.IndexViewModel
import com.wsr.index.dialog.IndexCreatePasswordGroupDialogViewModel
import com.wsr.infra.PassonDatabase
import com.wsr.infra.passwordgroup.LocalPasswordGroupRepositoryImpl
import com.wsr.infra.passwordgroup.PasswordGroupEntityDao
import com.wsr.infra.passworditem.LocalPasswordItemRepositoryImpl
import com.wsr.infra.passworditem.PasswordItemEntityDao
import com.wsr.infra.queryservice.LocalPasswordGroupQueryServiceImpl
import com.wsr.infra.queryservice.LocalPasswordItemQueryServiceImpl
import com.wsr.infra.queryservice.LocalPasswordPairQueryServiceImpl
import com.wsr.infra.queryservice.LocalUserQueryServiceImpl
import com.wsr.infra.user.LocalUserRepositoryImpl
import com.wsr.infra.user.UserEntityDao
import com.wsr.login.LoginViewModel
import com.wsr.passwordgroup.PasswordGroupRepository
import com.wsr.passworditem.PasswordItemRepository
import com.wsr.queryservice.PasswordGroupQueryService
import com.wsr.queryservice.PasswordItemQueryService
import com.wsr.queryservice.PasswordPairQueryService
import com.wsr.queryservice.UserQueryService
import com.wsr.show.ShowViewModel
import com.wsr.signup.SignUpViewModel
import com.wsr.sync.SyncPasswordPairUseCase
import com.wsr.sync.SyncPasswordPairUseCaseImpl
import com.wsr.user.UserRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val module = module {

    /*** View Model ***/
    viewModel { LoginViewModel(get(), get()) }
    viewModel { SignUpViewModel(get()) }
    viewModel { IndexViewModel(get(), get()) }
    viewModel { IndexCreatePasswordGroupDialogViewModel() }
    viewModel { ShowViewModel(get(), get()) }
    viewModel { EditViewModel(get(), get(), get()) }

    /**
     * UseCase
     */
    // create
    single<CreatePasswordGroupUseCase> { CreatePasswordGroupUseCaseImpl(get()) }
    single<CreatePasswordItemUseCase> { CreatePasswordItemUseCaseImpl() }

    // delete
    single<DeletePasswordGroupUseCase> { DeletePasswordGroupUseCaseImpl(get()) }

    // get
    single<GetAllUserUseCase> { GetAllUserUseCaseImpl(get()) }
    single<GetAllPasswordGroupUseCase> { GetAllPasswordGroupUseCaseImpl(get()) }
    single<GetPasswordPairUseCase> { GetPasswordPairUseCaseImpl(get()) }

    // sync
    single<SyncPasswordPairUseCase> { SyncPasswordPairUseCaseImpl(get(), get(), get()) }

    // auth
    single<LoginUseCase> { LoginUseCaseImpl(get()) }
    single<ResetUseCase> { ResetUseCaseImpl(get(), get()) }
    single<SignUpUseCase> { SignUpUseCaseImpl(get()) }

    /*** QueryService ***/

    single<PasswordGroupQueryService> { LocalPasswordGroupQueryServiceImpl(get()) }
    single<PasswordItemQueryService> { LocalPasswordItemQueryServiceImpl(get()) }
    single<PasswordPairQueryService> { LocalPasswordPairQueryServiceImpl(get(), get()) }
    single<UserQueryService> { LocalUserQueryServiceImpl(get()) }

    /*** Repository ***/
    single<PasswordGroupRepository> { LocalPasswordGroupRepositoryImpl(get()) }
    single<PasswordItemRepository> { LocalPasswordItemRepositoryImpl(get()) }
    single<UserRepository> { LocalUserRepositoryImpl(get()) }

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
    single<UserEntityDao> {
        val database by inject<PassonDatabase>()
        database.userEntityDao()
    }
}
