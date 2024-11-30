package com.example.app_loja_de_bolos.cake_category_list.data

import android.util.Log
import com.example.app_loja_de_bolos.cake_category_list.data.remote.CakeCategoryListRemoteDatasource

class CakeCategoryListRepositoryImpl(
    private val remoteDatasource: CakeCategoryListRemoteDatasource
) : CakeCategoryListRepository {
    override suspend fun getCakeCategories(cakeType: String?): List<String> {
        return remoteDatasource.getCakeCategories(cakeType).map { category ->
            Log.d("CakeCategoryListRepositoryImpl", "Category: $category")
            category.replace("_", " ").capitalize()
        }
    }
}