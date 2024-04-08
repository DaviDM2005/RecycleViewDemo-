import android.content.Context
import android.content.Intent
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import dav.project.recycleviewdemo.Item
import dav.project.recycleviewdemo.R
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class ItemAdapter(private val context: Context, private val items: List<Item>) :
    RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    private val handler = Handler()
    private lateinit var runnable: Runnable

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_custom_row, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem = items[position]
        holder.bind(currentItem)

        holder.ivLike.setOnClickListener {
            // Check current image resource
            val currentImageResource = (it as ImageView).drawable

            // Change image resource based on current state
            val newImageResource =
                if (currentImageResource.constantState == ContextCompat.getDrawable(
                        context,
                        R.drawable.heart
                    )?.constantState
                ) {
                    // If the current image is the heart, change it to liked_heart
                    R.drawable.liked_heart
                } else {
                    // If the current image is liked_heart, change it to heart
                    R.drawable.heart
                }

            // Set the new image resource
            holder.ivLike.setImageResource(newImageResource)
        }

    }

    override fun getItemCount() = items.size

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val postTitle: TextView = itemView.findViewById(R.id.tvTitle)
        private val postDescription: TextView = itemView.findViewById(R.id.tvDescription)
        private val imageView: ImageView = itemView.findViewById(R.id.ivMedia)
        private val dateTextView: TextView = itemView.findViewById(R.id.tvDate)
        val ivLike: ImageView = itemView.findViewById(R.id.ivLike)


        fun bind(item: Item) {
            postTitle.text = item.postTitle
            postDescription.text = item.postDescription
            imageView.setImageResource(item.postImage)
            updateDateText(item)

        }

        private fun updateDateText(item: Item) {
            runnable = Runnable {
                val diffInMillis = System.currentTimeMillis() - item.date.time
                val minutes = TimeUnit.MILLISECONDS.toMinutes(diffInMillis)

                dateTextView.text = when {
                    minutes < 1 -> "Just now"
                    minutes < 60 -> "$minutes minutes ago"
                    diffInMillis < TimeUnit.HOURS.toMillis(24) -> "Less than 24 hours ago"
                    else -> SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(item.date)
                }

                // Schedule the next update after 1 minute
                handler.postDelayed(runnable, TimeUnit.MINUTES.toMillis(1))
            }

            // Start the first update immediately
            runnable.run()
        }
    }

    // Stop the handler when adapter is detached
    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        handler.removeCallbacks(runnable)
    }
}
