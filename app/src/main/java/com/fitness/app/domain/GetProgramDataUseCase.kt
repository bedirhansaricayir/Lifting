package com.fitness.app.domain

import com.fitness.app.core.util.Resource
import com.fitness.app.data.remote.AntrenmanProgramlari
import com.fitness.app.data.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

/**
 * Repository katmanından aldığı parametre ile ağ isteğinin yapıldığı ve sonuçlarının yönetildiği, yalnızca business logic'in bulunduğu dosya.
 */
class GetProgramDataUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke(): Flow<Resource<AntrenmanProgramlari>> = flow {
        emit(Resource.Loading)
        try {
            val response = repository.getProgramData()
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage))
        } catch (e: IOException) {
            emit(Resource.Error(e.localizedMessage))
        }
    }
}