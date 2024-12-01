package com.example.app_loja_de_bolos.cake_category_list.data

import android.util.Log
import com.example.app_loja_de_bolos.cake_category_list.data.remote.CakeCategoryListRemoteDatasource
import com.example.app_loja_de_bolos.cake_category_list.model.CakeCategory

class CakeCategoryListRepositoryImpl(
    private val remoteDatasource: CakeCategoryListRemoteDatasource
) : CakeCategoryListRepository {
    override suspend fun getCakeCategories(cakeType: String?): List<CakeCategory> {
        return remoteDatasource.getCakeCategories(cakeType);
    }
}