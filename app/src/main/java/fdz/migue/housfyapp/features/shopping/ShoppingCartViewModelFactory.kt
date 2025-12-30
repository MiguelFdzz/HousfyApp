package fdz.migue.housfyapp.features.shopping

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import fdz.migue.housfyapp.dao.shopping.ShoppingCartRepository

class ShoppingViewModelFactory(private val repository: ShoppingCartRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ShoppingViewModel::class.java)) {
            return ShoppingViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}