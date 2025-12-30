package fdz.migue.housfyapp.features.shopping

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fdz.migue.housfyapp.dao.shopping.ShoppingCart
import fdz.migue.housfyapp.dao.shopping.ShoppingCartRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ShoppingViewModel(private val repository: ShoppingCartRepository) : ViewModel() {

    private val _carts = MutableStateFlow<List<ShoppingCart>>(emptyList())
    val carts: StateFlow<List<ShoppingCart>> = _carts.asStateFlow()

    private val _selectedCart = MutableStateFlow<ShoppingCart?>(null)
    val selectedCart: StateFlow<ShoppingCart?> = _selectedCart.asStateFlow()

    init {
        observeCarts()
    }

    private fun observeCarts() {
        viewModelScope.launch {
            repository.getCarts().collect { carts ->
                _carts.value = carts
            }
        }
    }

    fun selectCart(cart: ShoppingCart) {
        _selectedCart.value = cart
    }

    fun clearSelection() {
        _selectedCart.value = null
    }

    fun createCart(name: String) {
        viewModelScope.launch {
            val newCart = ShoppingCart(
                name = name,
                content = ""
            )
            repository.saveCart(newCart)
        }
    }

    fun updateCart(cart: ShoppingCart) {
        viewModelScope.launch {
            repository.saveCart(cart)
        }
    }

    fun deleteCart(cart: ShoppingCart) {
        viewModelScope.launch {
            repository.deleteCart(cart)
        }
    }

    fun deleteCartById(id: Int) {
        viewModelScope.launch {
            repository.deleteCartById(id.toString())
        }
    }
}