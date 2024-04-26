package io.atomic.sdk


import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
	viewModel { BoilerPlateViewModel() }
}
