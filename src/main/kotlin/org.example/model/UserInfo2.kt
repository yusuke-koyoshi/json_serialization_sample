package org.example.model

import kotlinx.serialization.Serializable

@Serializable
data class UserInfo2(
    val name: String? = null,
    val height: Int = 0,
) {

    @Override
    override fun toString(): String {
        return "UserInfo{" +
                "name='" + name + '\'' +
                ", height=" + height +
                '}'
    }
}
