package com.yohanes.ugd3_a_0891

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setCurrentFragment(FragmentSearch())
        var bottomMenu: BottomNavigationView = findViewById(R.id.bottomMenu)

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.search){
            setCurrentFragment(FragmentSearch())
        } else if (item.itemId == R.id.favorite) {
            setCurrentFragment(FragmentFavorite())
        } else if (item.itemId == R.id.kos) {
            setCurrentFragment(FragmentKos())
        } else if (item.itemId == R.id.chat) {
            setCurrentFragment(FragmentChat())
        } else {
            setCurrentFragment(FragmentProfile())
        }
        return super.onOptionsItemSelected(item)
    }

}