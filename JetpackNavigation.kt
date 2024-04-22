@Composable
fun UniteFitnessNavigation(
    navController: NavHostController,
    pd: PaddingValues
) {

    val mainActivity = LocalMainActivity.current
    val accountViewModel: AccountViewModel = koinInject()

    NavHost(
        navController = navController,
        startDestination = Screen.SignInScreen.route) {

        composable(Screen.BottomScreens.Friends.bRoute) {
            Friends()
        }

        composable(Screen.BottomScreens.Workouts.bRoute) {
            MyWorkoutsLibrary(navController = navController)
        }

        composable(Screen.BottomScreens.Schedule.bRoute) {
            ScheduleWorkout(navController = navController)
        }

        composable(Screen.ScheduleWorkoutDateScreen.route) {
            NewSchedule(navController = navController)
        }

        composable(Screen.ScheduleWorkoutTimeScreen.route) {
            ScheduleWorkoutTime(navController = navController)
        }

        // Workout library -> Workout builder -> Exercise library
        composable(route = Screen.WorkoutBuilderScreen.route + "/{workoutId}", arguments = listOf(
            navArgument("workoutId") {
                type = NavType.LongType
                defaultValue = 0
                nullable = false
            }
        )) {entry ->
           var workoutId = entry.arguments?.getLong("workoutId") ?: 0L

            if (workoutId == 0L) {
                val backStackId = entry.savedStateHandle.get<Long>("workoutId")
                backStackId?.let { workoutId = it }
            }

            WorkoutBuilder(workoutId = workoutId, navController = navController)
        }

        composable(route = Screen.ExercisesLibraryScreen.route + "/{workoutId}", arguments = listOf(
            navArgument("workoutId") {
                type = NavType.LongType
                defaultValue = 0
                nullable = false
            })
        ) { entry ->
            val workoutId = entry.arguments?.getLong("workoutId") ?: 0L
            ExercisesLibrary(workoutId = workoutId, navController = navController)
        }
    }
}
