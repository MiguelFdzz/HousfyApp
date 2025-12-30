package fdz.migue.housfyapp.features.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fdz.migue.housfyapp.dao.profile.Profile
import fdz.migue.housfyapp.dao.profile.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ProfileViewModel(private val profileRepository: ProfileRepository) : ViewModel() {

    val profile: Flow<Profile?> = profileRepository.getProfile()

    fun insertProfile(profile: Profile) {
        viewModelScope.launch {
            profileRepository.insertProfile(profile)
        }
    }

    fun saveProfile(profile: Profile) {
        viewModelScope.launch {
            profileRepository.saveProfile(profile)
        }
    }

    fun updateProfile(profile: Profile) {
        viewModelScope.launch {
            profileRepository.updateProfile(profile)
        }
    }

    fun deleteProfile(profile: Profile) {
        viewModelScope.launch {
            profileRepository.deleteProfile(profile)
        }
    }
}