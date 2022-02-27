package com.wsr

import com.wsr.edit.EditViewModel
import com.wsr.index.IndexViewModel
import com.wsr.password.PasswordRepository
import com.wsr.password.TestPasswordRepositoryImpl
import com.wsr.password.create.CreatePasswordUseCase
import com.wsr.password.create.CreatePasswordUseCaseImpl
import com.wsr.password.getall.GetAllPasswordUseCase
import com.wsr.password.getall.GetAllPasswordUseCaseImpl
import com.wsr.password.updateall.UpdateAllPasswordUseCase
import com.wsr.password.updateall.UpdateAllPasswordUseCaseImpl
import com.wsr.passwordgroup.PasswordGroupRepository
import com.wsr.passwordgroup.TestPasswordGroupRepositoryImpl
import com.wsr.passwordgroup.create.CreatePasswordGroupUseCase
import com.wsr.passwordgroup.create.CreatePasswordGroupUseCaseImpl
import com.wsr.passwordgroup.get.GetPasswordGroupUseCase
import com.wsr.passwordgroup.get.GetPasswordGroupUseCaseImpl
import com.wsr.passwordgroup.getall.GetAllPasswordGroupUseCase
import com.wsr.passwordgroup.getall.GetAllPasswordGroupUseCaseImpl
import com.wsr.passwordgroup.upsert.UpdatePasswordGroupUseCase
import com.wsr.passwordgroup.upsert.UpdatePasswordGroupUseCaseImpl
import com.wsr.show.ShowViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val module = module {

    /*** View Model ***/
    viewModel { IndexViewModel(get(), get()) }
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
    single<CreatePasswordUseCase> { CreatePasswordUseCaseImpl(get()) }
    single<UpdateAllPasswordUseCase> { UpdateAllPasswordUseCaseImpl(get()) }

    /*** Repository ***/
    single<PasswordGroupRepository> { TestPasswordGroupRepositoryImpl() }
    single<PasswordRepository> { TestPasswordRepositoryImpl() }
}
