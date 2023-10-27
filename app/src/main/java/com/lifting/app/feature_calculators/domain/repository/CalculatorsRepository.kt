package com.lifting.app.feature_calculators.domain.repository

import com.lifting.app.common.util.Resource
import com.lifting.app.feature_calculators.domain.model.GridItemData

interface CalculatorsRepository {

    suspend fun getCalculators(): Resource<List<GridItemData>>
}