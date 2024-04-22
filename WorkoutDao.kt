@Dao
abstract class WorkoutDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun addWorkout(workoutEnity: WorkoutEntity): Long

    @Query("Select * from `workout-lib-tbl` where user_id = :userId")
    abstract fun getAllWorkouts(userId: String?): Flow<List<WorkoutEntity>>

    @Query("Select * from `workout-lib-tbl` where user_id = :userId and id = :id")
    abstract fun getWorkoutById(userId: String?, id: Long): Flow<WorkoutEntity>

    @Update
    abstract suspend fun updateWorkout(workoutEnity: WorkoutEntity)

    @Delete
    abstract suspend fun deleteWorkout(workoutEnity: WorkoutEntity)

}
