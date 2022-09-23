package com.yohanes.ugd3_a_0891

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.yohanes.ugd3_a_0891.fragment.*

class HomeActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)



        setCurrentFragment(FragmentSearch())
        val bottomMenu: BottomNavigationView = findViewById(R.id.bottomMenu)

        bottomMenu.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.search->setCurrentFragment(FragmentSearch())
                R.id.favorite->setCurrentFragment(FragmentFavorite())
                R.id.kos->setCurrentFragment(FragmentKos())
                R.id.chat->setCurrentFragment(FragmentChat())
                R.id.profile->setCurrentFragment(FragmentProfile())
            }
            true
        }

    }




    fun setCurrentFragment(fragment: Fragment?) {
        if (fragment != null){
            getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.layout_fragment,fragment)
                .commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        return super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.home_menu, menu)
    }

}