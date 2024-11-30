package com.example.app_loja_de_bolos.cake_category_list.data


interface CakeCategoryListRepository {
    suspend fun getCakeCategories(cakeType: String?): List<String>
}