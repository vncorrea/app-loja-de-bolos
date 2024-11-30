package com.example.app_loja_de_bolos.cake_category_list.data.remote


interface CakeCategoryListRemoteDatasource {
    suspend fun getCakeCategories(cakeType: String?): List<String>
}