package com.lifting.app.feature_profile.domain.use_case

import android.net.Uri
import com.lifting.app.common.util.Resource
import com.lifting.app.feature_home.data.repository.FirebaseRepositoryImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddImageToStorageUseCase @Inject constructor(
    private val firebaseRepositoryImpl: FirebaseRepositoryImpl
) {

    suspend operator fun invoke(uri: Uri): Flow<Resource<Uri>> = flow {
        emit(Resource.Loading)
        val addStorage = firebaseRepositoryImpl.addImageToFirebaseStorage(uri)
        emit(Resource.Success(addStorage))
    }.catch {  throwable ->
        emit(Resource.Error(throwable.message.toString()))
    }

}