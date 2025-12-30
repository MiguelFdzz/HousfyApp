package fdz.migue.housfyapp.features.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import fdz.migue.housfyapp.dao.profile.ProfileRepository

class ProfileViewModelFactory(private val repository: ProfileRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProfileViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}