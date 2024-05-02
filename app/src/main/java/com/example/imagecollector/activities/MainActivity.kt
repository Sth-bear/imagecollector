package com.example.imagecollector.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.imagecollector.R
import com.example.imagecollector.databinding.ActivityMainBinding
import com.example.imagecollector.fragments.BookMarkFragment
import com.example.imagecollector.fragments.SearchFragment

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.apply {
            btnSearch.setOnClickListener {
                setFragment(SearchFragment())
            }
            btnBook.setOnClickListener {
                setFragment(BookMarkFragment())
            }
            setFragment(SearchFragment())
        }
    }

    private fun setFragment(frag:Fragment) {
        supportFragmentManager.commit {
            replace(binding.frameLayout.id, frag) // id값이 필요 -> binding한 frame의 id를주면됌.
            setReorderingAllowed(true)
            addToBackStack("")
        }
    }

}