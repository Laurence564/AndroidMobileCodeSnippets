class UniteFitnessApp: Application() {

    override fun onCreate() {
        super.onCreate()
        Graph.provide(this)
        startKoin {
            androidLogger()
            androidContext(this@UniteFitnessApp)
            modules(
                signInAppModule,
                workoutAppModule,
                exerciseAppModule,
                accountAppModule,
                scheduleWorkoutAppModule,
                friendsAppModule
            )
        }
    }
}
