// This is just a small sample of the FriendsViewModel

class FriendsViewModel(
    private val notificationViewModel: NotificationControlViewModel,
    private val firebase: FirebaseDatabase = Firebase.database): ViewModel() {

    private val _listOfUsers = mutableStateOf(emptyList<UserData>())
    val listOfUsers: State<List<UserData>> = _listOfUsers

    private val _usersFriends = mutableStateOf(emptyList<UserData>())
    val usersFriends: State<List<UserData>> = _usersFriends

    private val _pendingFriends = mutableStateOf(emptyList<PendingFriendRequest>())
    val pendingFriends: State<List<PendingFriendRequest>> = _pendingFriends

    fun onLoadFriends() {
        // TODO - eventually we want to store the friends in Room and make updates when friends are added

        val friends = mutableListOf<UserData>()
        val currentUserId = Firebase.auth.currentUser?.uid
        val friendsNodeReference = firebase.getReference("/friends/$currentUserId")

        friendsNodeReference.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                for (i in snapshot.children) {

                    friends.add(
                        UserData(
                            userId = i.child("userId").getValue(String::class.java)!!,
                            username = i.child("username").getValue(String::class.java)!!,
                            profilePictureUrl = i.child("profilePictureUrl").getValue(String::class.java)!!
                        )
                    )
                }

                _usersFriends.value += friends
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("onLoadFriends()", "Error retrieving data - '${error.message}'")
            }
        })
    }

    fun onFriendRequestedDeclined(pendingFriendRequest: PendingFriendRequest) {

        val pendingFriendRequestNode =
            firebase.getReference("/friendRequests/${pendingFriendRequest.requestNode}")

        pendingFriendRequestNode.removeValue()
            .addOnSuccessListener {
                val currentPendingFriends = _pendingFriends.value.toMutableList()
                currentPendingFriends.remove(pendingFriendRequest)
                _pendingFriends.value = currentPendingFriends
            }
            .addOnFailureListener {
                Log.d("onFriendRequestedDeclined()", "Error fetching data - '${it.message}'")
            }
    }
}
