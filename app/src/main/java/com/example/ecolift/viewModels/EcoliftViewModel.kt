package com.example.ecolift.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.ecolift.Database.PostDao
import com.example.ecolift.Database.PostPerson
import kotlinx.coroutines.launch

class EcoliftViewModel(private val postDao:PostDao):ViewModel() {


//    fun insertData_VM(postPerson: PostPerson) = viewModelScope.launch {
//
//        postDao.insert(postPerson)
//    }
//
//    fun deleteAllData_VM() = viewModelScope.launch {
//
//        postDao.deleteAllOwner()
//    }
//
//    fun getMatchedData_VM(destination:String) = viewModelScope.launch {
//
//        postDao.getMatchedRide(destination)
//    }


}




// custom dao
class EcoliftViewModelFactory(private val postDao: PostDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EcoliftViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EcoliftViewModel(postDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}