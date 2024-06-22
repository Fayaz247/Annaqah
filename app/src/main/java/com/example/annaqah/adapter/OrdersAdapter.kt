import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.annaqah.R
import com.example.annaqah.data.OrderData
import com.example.annaqah.databinding.OrderItemBinding

class OrdersAdapter(private val ordersList: List<OrderData>) : RecyclerView.Adapter<OrdersAdapter.OrderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = OrderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = ordersList[position]
        holder.bind(order)
    }

    override fun getItemCount() = ordersList.size

    inner class OrderViewHolder(private val binding: OrderItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(order: OrderData) {
            binding.txtOrder.text = order.orderID
            binding.txtOrderName.text = order.itemName
            binding.txtOrderPrice.text = order.itemPrice
            binding.txtOrderStatus.text = order.status
        }
    }
}
