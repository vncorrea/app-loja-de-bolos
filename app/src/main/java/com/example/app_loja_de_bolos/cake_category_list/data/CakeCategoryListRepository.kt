package com.example.app_loja_de_bolos.cake_category_list.data

import com.example.app_loja_de_bolos.cake_category_list.model.CakeCategory


interface CakeCategoryListRepository {
    suspend fun getCakeCategories(cakeType: String?): List<CakeCategory>
}