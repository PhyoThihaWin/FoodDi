package com.pthw.food.view

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.pthw.food.R
import com.pthw.food.base.BaseActivity
import com.pthw.food.databinding.ActivityMainBinding
import com.pthw.food.data.model.Food
import com.pthw.food.utils.ConstantValue
import com.pthw.food.utils.Rabbit
import com.pthw.food.utils.inflater

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(inflater())
    }


    private lateinit var itemAdapter: ItemAdapter
    private lateinit var foodViewModel: FoodViewModel
    var initialState = true
    lateinit var loading: ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        foodViewModel = ViewModelProvider(this).get(FoodViewModel::class.java)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        loading = ProgressDialog(this, R.style.customizedAlert)
        setupUI()
    }


    private fun setupUI() {
        binding.recycler.setHasFixedSize(true)
        var mLayoutManager: LayoutManager = LinearLayoutManager(this)
        binding.recycler.layoutManager = mLayoutManager

        foodViewModel.getAllFood()
        foodViewModel.allFood.observe(this) { foods ->
            itemAdapter = ItemAdapter(foods, ConstantValue.lang)
            Log.i("Data", itemAdapter.foodList.joinToString())

            binding.recycler.adapter = itemAdapter
            if (initialState) {
                getArray(foods)
                initialState = false
            }
        }


        //--recycler start position
        binding.imgSearch.setOnClickListener {
            binding.recycler.smoothScrollToPosition(0)
            binding.motionlayout.transitionToStart()
        }

        binding.fullMenu.setOnClickListener {
            val dialog = FullScreenDialog()
            val ft = supportFragmentManager.beginTransaction()
            dialog.show(ft, FullScreenDialog.TAG)
        }

        //--search edit text
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (binding.etSearch.length() >= 2)
                    binding.imgDeleteSearch.visibility = View.VISIBLE
                else binding.imgDeleteSearch.visibility = View.INVISIBLE
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        //--Set an item click listener for auto complete text view
        binding.etSearch.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val selectedItem = parent.getItemAtPosition(position).toString()
                binding.etSearch.hideKeyboard()
                foodViewModel.getSearchFood(
                    if (ConstantValue.lang != "zawgyi") selectedItem else Rabbit.zg2uni(
                        selectedItem
                    )
                )
            }


        //--cross-image to delete search
        binding.imgDeleteSearch.setOnClickListener {
            binding.etSearch.setText("")
            foodViewModel.getAllFood()
        }

    }


    //--hide keyboard method
    fun View.hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }


    // convert object list ot string array for autocomplete textview
    private fun getArray(food: List<Food>) {
        var k = 0
        val str = arrayOfNulls<String>(food.size * 2)

        when (ConstantValue.lang) {
            "unicode" -> {
                for (i in food.indices) {
                    str[k] = food[i].oneMM
                    str[k + 1] = food[i].twoMM
                    k += 2
                }
            }

            "zawgyi" -> {
                for (i in food.indices) {
                    str[k] = Rabbit.uni2zg(food[i].oneMM)
                    str[k + 1] = Rabbit.uni2zg(food[i].twoMM)
                    k += 2
                }
            }

            else -> {
                for (i in food.indices) {
                    str[k] = food[i].oneEN
                    str[k + 1] = food[i].twoEN
                    k += 2
                }
            }
        }

        //set adapter for autocomplete textview
        val adapter =
            ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, str.distinct())
        binding.etSearch.setAdapter(adapter)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    //--set menu item title at activity runtime
    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        when (ConstantValue.lang) {
            "unicode" -> {
                binding.etSearch.setHint(R.string.search)
                menu.findItem(R.id.all).setTitle(R.string.all)
                menu.findItem(R.id.food).setTitle(R.string.food)
                menu.findItem(R.id.fruit).setTitle(R.string.fruit)
                menu.findItem(R.id.vegetable).setTitle(R.string.vegetable)
                menu.findItem(R.id.meat).setTitle(R.string.meat)
                menu.findItem(R.id.snack).setTitle(R.string.snack)
                loading.setMessage(getString(R.string.data))
            }

            "zawgyi" -> {
                binding.etSearch.setHint(R.string.searchZ)
                menu.findItem(R.id.all).setTitle(R.string.allZ)
                menu.findItem(R.id.food).setTitle(R.string.foodZ)
                menu.findItem(R.id.fruit).setTitle(R.string.fruitZ)
                menu.findItem(R.id.vegetable).setTitle(R.string.vegetableZ)
                menu.findItem(R.id.meat).setTitle(R.string.meatZ)
                menu.findItem(R.id.snack).setTitle(R.string.snackZ)
                loading.setMessage(getString(R.string.dataZ))
            }

            else -> {
                binding.etSearch.setHint(R.string.searchE)
                menu.findItem(R.id.all).setTitle(R.string.allE)
                menu.findItem(R.id.food).setTitle(R.string.foodE)
                menu.findItem(R.id.fruit).setTitle(R.string.fruitE)
                menu.findItem(R.id.vegetable).setTitle(R.string.vegetableE)
                menu.findItem(R.id.meat).setTitle(R.string.meatE)
                menu.findItem(R.id.snack).setTitle(R.string.snackE)
                loading.setMessage(getString(R.string.dataE))
            }
        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.all -> {
                foodViewModel.getAllFood()
                binding.seasonal.setText(R.string.app_name)
            }

            R.id.food -> {
                foodViewModel.getFoodByType("food")
                binding.seasonal.text = item.title
            }

            R.id.fruit -> {
                foodViewModel.getFoodByType("fruit")
                binding.seasonal.text = item.title
            }

            R.id.vegetable -> {
                foodViewModel.getFoodByType("vegetable")
                binding.seasonal.text = item.title
            }

            R.id.meat -> {
                foodViewModel.getFoodByType("meat")
                binding.seasonal.text = item.title
            }

            R.id.snack -> {
                foodViewModel.getFoodByType("snack")
                binding.seasonal.text = item.title
            }
        }

        binding.etSearch.hideKeyboard()
        return super.onOptionsItemSelected(item)
    }
}
