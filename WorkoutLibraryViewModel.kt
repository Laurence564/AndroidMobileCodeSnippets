class WorkoutLibraryViewModel(
    private val _workoutLibraryRepository: WorkoutLibraryRepository,
    private val _accountViewModel: AccountViewModel
): ViewModel() {

    var workoutTitleState by mutableStateOf("")
    var workoutId by mutableLongStateOf(0L)
    var isWorkoutCreated by mutableStateOf(false)

    val userId by lazy { _accountViewModel.userData.value?.userId }

    lateinit var getAllWorkouts: Flow<List<WorkoutEntity>>

    private val _workoutState = mutableStateOf(WorkoutLibraryState())
    val workoutState: State<WorkoutLibraryState> = _workoutState

    init {
        viewModelScope.launch {
            val response = _workoutLibraryRepository.getWorkouts(userId)
            getAllWorkouts = response
            // TODO - review other areas to handle state properly.
            _workoutState.value = _workoutState.value.copy(
                loading = false,
                workoutList = response
            )
        }
    }

    fun onWorkoutTitleChanged(newWorkoutTitle: String) {
        workoutTitleState = newWorkoutTitle
    }

    suspend fun addWorkout(workoutEntity: WorkoutEntity): Long = withContext(Dispatchers.IO) {
        _workoutLibraryRepository.addWorkout(workoutEntity)
    }

    fun updateWorkout(workoutEntity: WorkoutEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            _workoutLibraryRepository.updateWorkout(workoutEntity)
        }
    }

    fun deleteWorkout(workoutEntity: WorkoutEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            _workoutLibraryRepository.deleteWorkout(workoutEntity)
            getAllWorkouts = _workoutLibraryRepository.getWorkouts(userId)
        }
    }

    fun getWorkoutById(userId:String?, id:Long):Flow<WorkoutEntity> = _workoutLibraryRepository.getWorkoutById(userId, id)

    data class WorkoutLibraryState(
        val loading: Boolean = true,
        val workoutList: Flow<List<WorkoutEntity>> = flowOf(emptyList())
    )

}
