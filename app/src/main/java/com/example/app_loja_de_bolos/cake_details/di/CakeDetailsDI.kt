package com.example.app_loja_de_bolos.cake_details.di

class CakeDetailsDI {
    object CakeDetailsDI {

        private val remoteDataSource: CakeDetailsRemoteDataSource = CakeDetailsRemoteDataSourceImpl(ApiService.create())

        private fun CakeDetailsRemoteDataSourceImpl(create: Any): CakeDetailsRemoteDataSource {

        }

        val repository: CakeDetailsRepository<Any?> = CakeDetailsRepository(remoteDataSource)
    }

    class CakeDetailsRepository<CakeDetails> {
        fun fetchCakeDetails(cakeId: String): CakeDetails {

        }

    }

    class ApiService {
        fun getTamanhos(): Any {

        }

        fun getCaldaChocolate(): Boolean {

        }

        fun getEmbalagemPlastica(): Boolean {

        }

        companion object {
            fun create(): Any {

            }
        }

    }

}

class CakeDetailsRemoteDataSource {

}
