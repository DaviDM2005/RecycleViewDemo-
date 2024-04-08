package dav.project.recycleviewdemo

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView // Added import for RecyclerView
import ItemAdapter // Added import for ItemAdapter
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }



        val recyclerView: RecyclerView = findViewById(R.id.recycleView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val itemAdapter = ItemAdapter(this, getItemsList())

        recyclerView.adapter = itemAdapter
    }

    private fun getItemsList(): List<Item> {
        val list = ArrayList<Item>()
        val currentDate = Calendar.getInstance().time

        list.add(Item("საქართველო ევროპაზეა!", "26.03.2024 - თარიღი, რომელიც ისტორიაში ოქროს ასოებით ჩაიწერება! " +
                "საქართველო ევრო 2024 ჩემპიონატზე ითამაშებს!", R.drawable.georgia, currentDate))
        list.add(Item("Porsche", "Porsche 911 new model", R.drawable.image1, currentDate))
        list.add(
            Item(
                "Also PorschePorschePorschePorsche",
                "Porsche 911 carrera revealed Porsche 911 carrera revealed Porsche 911 carrera revealed Porsche 911 carrera revealed Porsche 911 carrera revealed",
                R.drawable.image2,
                currentDate
            )
        )
        list.add(Item("Also Porsche", "Porsche 911 turbo s", R.drawable.image3, currentDate))


        return list
    }
}
