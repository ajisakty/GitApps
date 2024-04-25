import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.github.data.response.UsersResponseItem
import com.example.github.databinding.UserItemBinding

class UsersAdapter(private val onItemClick: (UsersResponseItem) -> Unit) : ListAdapter<UsersResponseItem, UsersAdapter.MyViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UsersResponseItem>() {
            override fun areItemsTheSame(oldItem: UsersResponseItem, newItem: UsersResponseItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: UsersResponseItem, newItem: UsersResponseItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    class MyViewHolder(private val binding: UserItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UsersResponseItem) {
            binding.tvUserName.text = user.login
            Glide.with(binding.ivUser)
                .load(user.avatarUrl)
                .into(binding.ivUser)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)

        holder.itemView.setOnClickListener {
            onItemClick(user)
        }
    }
}