package com.example.kmmecommerce.graphql

import com.apollographql.apollo.ApolloClient
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString


@Serializable
data class ProductDTO(
    public val price: Double,
    public val productId: String,
    public val productImageURL: String?,
    public val productName: String,
    public val quantity: Int
)

object Network {
    var json = Json { ignoreUnknownKeys = true };
    suspend fun fetchProducts(url: String): List<ProductDTO> {
        val apolloClient = ApolloClient.Builder()
            .serverUrl(url)
            .build()
        // Execute your query. This will suspend until the response is received.
        val response = apolloClient.query(ProductsQuery()).execute()
        val products = response.data?.products ?: emptyList();
        val productDTOList = products.map {
            ProductDTO(
                it.price,
                it.productId,
                it.productImageURL,
                it.productName,
                it.quantity
            )
        }
        return productDTOList;
    }

    fun productsAsJson(products: List<ProductDTO>): String {
        return json.encodeToString(products);
    }
}
