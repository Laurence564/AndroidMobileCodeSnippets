// Please note that in my most recent project, I use Jetpack compose and do not use adapters. This is just a demonstration of when I built a version of the app without Jetpack compose and needed the adapter.

class UserPoolAdapter(val arrayList: ArrayList<UserPoolUserObject>, val context: Context,_onTeamUpClickListener: ViewHolder.OnTeamUpClickListener) : RecyclerView.Adapter<UserPoolAdapter.ViewHolder>() {
    private var onTeamUpClickListener: ViewHolder.OnTeamUpClickListener

    init {
        onTeamUpClickListener = _onTeamUpClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_userpool_section, parent, false)
        return ViewHolder(v, onTeamUpClickListener)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(arrayList[position])
    }

    class ViewHolder(itemView: View, _onTeamUpClickListener: OnTeamUpClickListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var onTeamUpClickListener: OnTeamUpClickListener? = null
        val mainActivity = MainActivity()

        init {
            onTeamUpClickListener = _onTeamUpClickListener
            itemView.btn_team_up.setOnClickListener(this)
        }

        interface OnTeamUpClickListener {
            fun onTeamUpClick(position: Int)
        }

        @TargetApi(Build.VERSION_CODES.CUR_DEVELOPMENT)
        @SuppressLint("SetTextI18n")
        fun bindItems(userpoolObject : UserPoolUserObject) {
            itemView.lbl_username.text = userpoolObject._name
            itemView.lbl_date_userpool.text = userpoolObject.date.format(DateTimeFormatter.ofPattern("MMM dd"))
            itemView.lbl_start_end_time.text = "${userpoolObject._startTime} - ${userpoolObject._endTime}"
            itemView.lbl_muscle_selection_usrpool.text = userpoolObject._bodyParts.joinToString()
            try {
                Picasso.get().load(userpoolObject.profilePicUrl).into(itemView.img_profile_icon_messages)
            } catch (e: IllegalArgumentException) {
                // In case a new user is created for testing a no profile img is provided
            }
        }

        override fun onClick(v: View?) {
            when(v!!.id) {
                R.id.btn_team_up -> onTeamUpClickListener!!.onTeamUpClick(adapterPosition)
            }
        }
    }
}
