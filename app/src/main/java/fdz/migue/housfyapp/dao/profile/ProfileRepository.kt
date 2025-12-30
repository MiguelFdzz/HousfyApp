package fdz.migue.housfyapp.dao.profile

import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    fun getProfile(): Flow<Profile?>
    suspend fun insertProfile(profile: Profile)
    suspend fun saveProfile(profile: Profile)
    suspend fun updateProfile(profile: Profile)
    suspend fun deleteProfile(profile: Profile)
}

class ProfileRepositoryImpl(private val profileDao: ProfileDao) : ProfileRepository {

    override fun getProfile(): Flow<Profile?> {
        return profileDao.getProfile()
    }

    override suspend fun insertProfile(profile: Profile) {
        profileDao.insertProfile(profile)
    }

    override suspend fun saveProfile(profile: Profile) {
        profileDao.upsertProfile(profile)
    }

    override suspend fun updateProfile(profile: Profile) {
        profileDao.updateProfile(profile)
    }

    override suspend fun deleteProfile(profile: Profile) {
        profileDao.deleteProfile(profile)
    }
}