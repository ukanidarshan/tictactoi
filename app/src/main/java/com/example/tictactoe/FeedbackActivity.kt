package com.example.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

class FeedbackActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)

        val viewPager: ViewPager = findViewById(R.id.viewPager)
        val tabs: TabLayout = findViewById(R.id.tabs)

        val adapter = FeedbackPagerAdapter(supportFragmentManager)
        adapter.addFragment(SuggestionFragment(), "Suggestion")
        adapter.addFragment(ReportBugFragment(), "Report Bug")
        viewPager.adapter = adapter

        tabs.setupWithViewPager(viewPager)
    }
}