class WorkoutLibraryRepository(private val _workoutDao: WorkoutDao) {

    suspend fun addWorkout(workoutEntity: WorkoutEntity): Long = _workoutDao.addWorkout(workoutEntity)

    suspend fun updateWorkout(workoutEntity: WorkoutEntity) = _workoutDao.updateWorkout(workoutEntity)

    suspend fun deleteWorkout(workoutEntity: WorkoutEntity) = _workoutDao.deleteWorkout(workoutEntity)

    fun getWorkouts(userId:String?): Flow<List<WorkoutEntity>> = _workoutDao.getAllWorkouts(userId)

    fun getWorkoutById(userId: String?, id:Long): Flow<WorkoutEntity> = _workoutDao.getWorkoutById(userId, id)

}
