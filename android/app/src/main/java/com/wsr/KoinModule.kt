package com.wsr

import com.wsr.index.IndexViewModel
import com.wsr.password.GetAllPasswordUseCase
import com.wsr.password.GetAllPasswordUseCaseImpl
import com.wsr.password.PasswordRepository
import com.wsr.password.TestPasswordRepositoryImpl
import com.wsr.passwordgroup.PasswordGroupRepository
import com.wsr.passwordgroup.TestPasswordGroupRepositoryImpl
import com.wsr.passwordgroup.get.GetPasswordGroupUseCase
import com.wsr.passwordgroup.get.GetPasswordGroupUseCaseImpl
import com.wsr.passwordgroup.getall.GetAllPasswordGroupUseCase
import com.wsr.passwordgroup.getall.GetAllPasswordGroupUseCaseImpl
import com.wsr.show.ShowViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val module = module {

    /*** View Model ***/
    viewModel { IndexViewModel(get()) }
    viewModel { ShowViewModel(get(), get()) }

    /*** UseCase ***/
    // Password Group
    single<GetAllPasswordGroupUseCase> { GetAllPasswordGroupUseCaseImpl(get()) }
    single<GetPasswordGroupUseCase> { GetPasswordGroupUseCaseImpl(get()) }

    // Password
    single<GetAllPasswordUseCase> { GetAllPasswordUseCaseImpl(get()) }

    /*** Repository ***/
    single<PasswordGroupRepository> { TestPasswordGroupRepositoryImpl() }
    single<PasswordRepository> { TestPasswordRepositoryImpl() }
}
