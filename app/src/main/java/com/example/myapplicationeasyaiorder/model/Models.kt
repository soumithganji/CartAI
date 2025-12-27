package com.example.myapplicationeasyaiorder.model

data class ProductResponse(
    val data: List<Product>
)

data class Product(
    val productId: String,
    val description: String,
    val items: List<ProductItem>,
    val images: List<ProductImage>
)

data class ProductItem(
    val itemId: String,
    val price: ProductPrice?,
    val size: String
)

data class ProductPrice(
    val regular: Double,
    val promo: Double?
)

data class ProductImage(
    val url: String,
    val perspective: String // front, back, etc.
)

data class CartResponse(
    val data: Cart
)

data class Cart(
    val cartId: String,
    val items: List<CartItem>
)

data class CartItem(
    val itemId: String,
    val quantity: Int,
    val price: Double
)

data class CartUpdateRequest(
    val items: List<CartItemRequest>
)

data class CartItemRequest(
    val upc: String,
    val quantity: Int
)

data class TokenResponse(
    val access_token: String,
    val expires_in: Int,
    val token_type: String
)
