package com.example.inovatest.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.inovatest.databinding.ActivityMainBinding
import com.example.inovatest.domain.DataModel
import com.example.inovatest.viewModels.DataViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: DataViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getJsonData()
    }

    private fun getJsonData() {
        viewModel.fetchData()
        viewModel.dataModel.observe( this) { dataModel ->
            if (dataModel != null) {
                setUiData(dataModel)
            }
        }
    }

    private fun setUiData(dataModel: DataModel){
        binding.title.text = dataModel.title
        binding.content.text = dataModel.content
    }
}