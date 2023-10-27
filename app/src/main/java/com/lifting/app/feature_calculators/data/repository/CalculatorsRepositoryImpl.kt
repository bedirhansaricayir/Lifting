package com.lifting.app.feature_calculators.data.repository

import com.lifting.app.R
import com.lifting.app.common.util.Resource
import com.lifting.app.feature_calculators.domain.model.CalculatorCategory
import com.lifting.app.feature_calculators.domain.model.GridItemData
import com.lifting.app.feature_calculators.domain.repository.CalculatorsRepository
import javax.inject.Inject

class CalculatorsRepositoryImpl @Inject constructor(
): CalculatorsRepository {

    private val gridItemList by lazy {
        listOf(
            GridItemData(
                image = R.drawable.pulse,
                title = R.string.bmr_calculator_title_label,
                infoTitle = R.string.bmr_calculator_info_title,
                infoDescription = R.string.bmr_calculator_info_description,
                category = CalculatorCategory.BMR
            ),
            GridItemData(
                image = R.drawable.icon_barbell,
                title = R.string.rm_calculator_title_label,
                infoTitle = R.string.rm_calculator_info_title,
                infoDescription = R.string.rm_calculator_info_description,
                category = CalculatorCategory.RM
            ),
            GridItemData(
                image = R.drawable.icon_bmi,
                title = R.string.bmi_calculator_title_label,
                infoTitle = R.string.bmi_calculator_info_title,
                infoDescription = R.string.bmi_calculator_info_description,
                category = CalculatorCategory.BMI
            )
        )
    }
    override suspend fun getCalculators(): Resource<List<GridItemData>> {
       return Resource.Success(gridItemList)
    }
}