@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Friends(friendsViewModel: FriendsViewModel = koinViewModel<FriendsViewModel>()) {

    val scaffoldState = rememberScaffoldState()
    var searchText by remember { mutableStateOf("") }
    var searchStatus by remember { mutableStateOf(false) }
    var selectedTab by remember { mutableStateOf(TabType.FRIENDS) }

    LaunchedEffect(key1 = Unit) {
       friendsViewModel.onLoadPendingFriendRequests()
       friendsViewModel.onLoadFriends()
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { AppBarView(title = stringResource(id = R.string.friends)) },
    ) {
        Box {
            Column {
                SearchBar(
                    trailingIcon = {
                        if (searchStatus) {
                            Image(
                                modifier = Modifier.clickable {
                                    if (searchText.isNotEmpty()) {
                                        searchText = ""
                                    } else {
                                        searchStatus = false
                                    }
                                },
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close Icon"
                            )
                        }
                    },
                    leadingIcon = { Image(imageVector = Icons.Default.Search, contentDescription = "Search Icon") },
                    placeholder = ( { Text(text = "Search") }),
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp).fillMaxWidth(),
                    query = searchText,
                    onQueryChange = { searchText = it },
                    onSearch = { searchStatus = false },
                    active = searchStatus,
                    onActiveChange = { searchStatus = it }
                ) {
                    LazyColumn(modifier = Modifier.fillMaxSize().padding(it)) {
                        friendsViewModel.onGetUsers()

                        val listOfAllUsers = mutableListOf<UserData>()
                        val listOfUsersCurrentFriends = mutableListOf<UserData>()

                        listOfAllUsers += friendsViewModel.listOfUsers.value
                        listOfUsersCurrentFriends += friendsViewModel.usersFriends.value

                        // Filter out friends the user already has
                        if (listOfAllUsers.isNotEmpty() && searchText.isNotEmpty()) {
                            val friendsNotIncluded = listOfAllUsers.filterNot { user ->
                                listOfUsersCurrentFriends.any { it.userId == user.userId }
                            }
                            val filteredUsernames = friendsNotIncluded.filter { user ->
                                user.username!!.contains(searchText, ignoreCase = true)
                            }
                            items(filteredUsernames) {
                                UsersCard(userData = it, friendsViewModel = friendsViewModel)
                            }
                        }
                    }
                }
                
                Column(modifier = Modifier.fillMaxSize().padding(top = 16.dp, start = 8.dp, end = 8.dp)) {

                    val unselectedBackgroundColor = Color.Transparent
                    val selectedBackground = Color.DarkGray.copy(0.2f)

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
                            .padding(start = 8.dp, end = 20.dp, top = 8.dp)
                            .wrapContentHeight()
                            .background(
                                color = Color.DarkGray.copy(0.1f),
                                shape = RoundedCornerShape(25.dp)
                            )
                    ) {
                        TabView(
                            contactCount = friendsViewModel.usersFriends.value.count(),
                            modifier = Modifier.weight(1f),
                            backgroundColor = if (selectedTab == TabType.FRIENDS) {
                                selectedBackground
                            } else {
                                unselectedBackgroundColor
                            },
                            tabName = "Friends",
                            selectedTab = selectedTab
                        ) {
                            selectedTab = TabType.FRIENDS
                        }

                        TabView(
                            modifier = Modifier.weight(1f),
                            backgroundColor = if (selectedTab == TabType.REQUESTS) {
                                selectedBackground
                            } else {
                                unselectedBackgroundColor
                            },
                            tabName = "Requests",
                            contactCount = friendsViewModel.pendingFriends.value.count(),
                            selectedTab = selectedTab
                        ) { selectedTab = TabType.REQUESTS }
                    }

                    when (selectedTab) {
                        TabType.FRIENDS -> {
                            ConfirmedFriendsState(friendsViewModel = friendsViewModel)
                        }

                        TabType.REQUESTS -> {
                            PendingFriends(friendsViewModel = friendsViewModel)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun UsersCard(userData: UserData, friendsViewModel: FriendsViewModel) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, start = 8.dp, end = 8.dp)
            .background(Color.Transparent)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .align(Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = userData.profilePictureUrl,
                contentDescription = "profile picture",
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = userData.username!!
            )
            Spacer(modifier = Modifier.weight(1f))
            Button(onClick = {
                friendsViewModel.onFriendRequestSent(userData)
            }) {
                Text(color = Color.White, text = "Add")
            }
        }
    }
}
