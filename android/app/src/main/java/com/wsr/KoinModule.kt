package com.wsr

import com.wsr.edit.EditViewModel
import com.wsr.index.IndexViewModel
import com.wsr.index.dialog.IndexCreatePasswordGroupDialogViewModel
import com.wsr.password.PasswordRepository
import com.wsr.password.TestPasswordRepositoryImpl
import com.wsr.password.create.CreatePasswordUseCase
import com.wsr.password.create.CreatePasswordUseCaseImpl
import com.wsr.password.getall.GetAllPasswordUseCase
import com.wsr.password.getall.GetAllPasswordUseCaseImpl
import com.wsr.password.upsert.UpsertPasswordUseCase
import com.wsr.password.upsert.UpsertPasswordUseCaseImpl
import com.wsr.passwordgroup.PasswordGroupRepository
import com.wsr.passwordgroup.TestPasswordGroupRepositoryImpl
import com.wsr.passwordgroup.create.CreatePasswordGroupUseCase
import com.wsr.passwordgroup.create.CreatePasswordGroupUseCaseImpl
import com.wsr.passwordgroup.get.GetPasswordGroupUseCase
import com.wsr.passwordgroup.get.GetPasswordGroupUseCaseImpl
import com.wsr.passwordgroup.getall.GetAllPasswordGroupUseCase
import com.wsr.passwordgroup.getall.GetAllPasswordGroupUseCaseImpl
import com.wsr.passwordgroup.update.UpdatePasswordGroupUseCase
import com.wsr.passwordgroup.update.UpdatePasswordGroupUseCaseImpl
import com.wsr.show.ShowViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val module = module {

    /*** View Model ***/
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
    single<PasswordGroupRepository> { TestPasswordGroupRepositoryImpl() }
    single<PasswordRepository> { TestPasswordRepositoryImpl() }
}
