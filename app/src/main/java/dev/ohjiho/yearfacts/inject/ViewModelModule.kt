package dev.ohjiho.yearfacts.inject

import dev.ohjiho.yearfacts.ui.YearFactsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { YearFactsViewModel() }
}