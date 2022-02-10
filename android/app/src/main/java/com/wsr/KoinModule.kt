package com.wsr

import com.wsr.index.IndexViewModel
import com.wsr.passwordgroup.GetAllPasswordGroupUseCase
import com.wsr.passwordgroup.GetAllPasswordGroupUseCaseImpl
import com.wsr.passwordgroup.PasswordGroupRepository
import com.wsr.passwordgroup.TestPasswordGroupRepositoryImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val module = module {

    /* View Model */
    viewModel { IndexViewModel(get()) }

    /* UseCase */
    single<GetAllPasswordGroupUseCase> { GetAllPasswordGroupUseCaseImpl(get()) }

    /* Repository */
    single<PasswordGroupRepository> { TestPasswordGroupRepositoryImpl() }
}
