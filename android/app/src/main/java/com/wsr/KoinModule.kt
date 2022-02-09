package com.wsr

import com.wsr.index.IndexViewModel
import com.wsr.passwordgroup.GetPasswordGroupUseCase
import com.wsr.passwordgroup.GetPasswordGroupUseCaseImpl
import com.wsr.passwordgroup.PasswordGroupRepository
import com.wsr.passwordgroup.TestPasswordGroupRepositoryImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val module = module {

    /* View Model */
    viewModel { IndexViewModel(get()) }

    /* UseCase */
    single<GetPasswordGroupUseCase> { GetPasswordGroupUseCaseImpl(get()) }

    /* Repository */
    single<PasswordGroupRepository> { TestPasswordGroupRepositoryImpl() }
}
