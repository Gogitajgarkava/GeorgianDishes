import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.georgiandishes.MainPage.HomePage.Database.Dish
import com.example.georgiandishes.R
import com.squareup.picasso.Picasso
import android.os.Bundle


class DishAdapter(private var dishes: List<Dish>) :
    RecyclerView.Adapter<DishAdapter.ViewHolder>() {

    fun updateData(newList: List<Dish>) {
        dishes = newList
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val dishNameTextView: TextView = view.findViewById(R.id.dishNameTextView)
        val dishimage: ImageView = view.findViewById(R.id.dishImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.dish_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dish = dishes[position]
        holder.dishNameTextView.text = dish.name
        Picasso.get()
            .load(dish.imageUrl)
            .into(holder.dishimage)

        holder.itemView.setOnClickListener {
            val bundle = Bundle().apply {
                putString("dish_name", dish.name)
                putString("dish_description", dish.description)
                putString("dish_recipe", dish.recipe)
            }

            findNavController(it).navigate(R.id.action_fragmentHome_to_detailDishFragment, bundle)
        }

    }

    override fun getItemCount(): Int = dishes.size

}
