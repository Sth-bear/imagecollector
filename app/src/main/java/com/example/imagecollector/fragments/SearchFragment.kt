package com.example.imagecollector.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.imagecollector.adapters.SearchFragAdapter
import com.example.imagecollector.data.ImageItem
import com.example.imagecollector.data.ImageResponse
import com.example.imagecollector.retrofit.NetWorkClient
import com.example.imagecollector.databinding.FragmentSearchBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


private const val ARG_PARAM1 = "param1"


class SearchFragment : Fragment() {
    private var param1: String? = null
    private val binding by lazy { FragmentSearchBinding.inflate(layoutInflater) }
    private val searchAdapter by lazy { SearchFragAdapter() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val sharedPreferences = requireContext().getSharedPreferences("searchFragment", Context.MODE_PRIVATE) // create된 이후 선언해야 requireContext가 가능함.
        defaultBind()
        btnSearch(sharedPreferences)
        loadSearchData(sharedPreferences)
    }

    private fun defaultBind() {
        with(binding.rvSearch) {
            adapter = searchAdapter
            layoutManager = GridLayoutManager(requireContext(),2)
        }
    }

    private fun btnSearch(pref: SharedPreferences) {
        binding.btnSearchFrag.setOnClickListener {
            saveSearchData(pref)
            communicateNetWork(setUpImageParameter(binding.etSearchFrag.text.toString()))

        }
    }

    private fun saveSearchData(pref: SharedPreferences) {
        val edit = pref.edit()
        edit.putString("lastSearch", binding.etSearchFrag.text.toString())
        edit.apply() // 저장완료
    }

    private fun loadSearchData(pref: SharedPreferences) {
        // 1번째 인자는 키, 2번째 인자는 데이터가 존재하지 않을경우의 값
        binding.etSearchFrag.setText(pref.getString("lastSearch",""))
    }

    private fun communicateNetWork(param: HashMap<String,String>) = lifecycleScope.launch() {
        val authKey = ""

        val responseData = NetWorkClient.searchNetWork.getImage(authKey, param)
        Log.d("testResponseData", responseData.toString())
        val itemList = convertToImageItem(responseData)
        Log.d("test", "$itemList")
        withContext(Dispatchers.Main) {
            searchAdapter.listUpdate(itemList)
            binding.rvSearch.layoutManager = GridLayoutManager(requireContext(),2)
        }

    }

    private fun setUpImageParameter(search: String): HashMap<String, String> {
        return hashMapOf(
            "query" to search
        )
    }

    private fun convertToImageItem(imgResponse: ImageResponse): List<ImageItem> {
        val imageItems = mutableListOf<ImageItem>()
        for (item in imgResponse.documents) {
            val imageItem = ImageItem(
                datetime = item.datetime.toString(),
                display_sitename = item.display_sitename,
                image_url = item.thumbnail_url
            )
            imageItems.add(imageItem)
        }
        return imageItems
    }



    companion object {

        @JvmStatic
        fun newInstance(param1: String) =
            SearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}

