package com.example.app_loja_de_bolos.cake_category_list.data.remote

import com.example.app_loja_de_bolos.cake_category_list.model.CakeCategory


interface CakeCategoryListRemoteDatasource {
    suspend fun getCakeCategories(cakeType: String?): List<CakeCategory>
}