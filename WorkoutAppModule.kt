val workoutAppModule = module {

    single<WorkoutLibraryRepository> {
        Graph.workoutLibraryRepository
    }

    viewModel {
        WorkoutLibraryViewModel(
            _workoutLibraryRepository = get(),
            _accountViewModel = get()
        )
    }

}
