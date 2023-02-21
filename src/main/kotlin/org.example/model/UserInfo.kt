package org.example.model

import kotlinx.serialization.Serializable

@Serializable
data class UserInfo(
    val name: String? = null,
    val age: Int = 0,
) {

    @Override
    override fun toString(): String {
        return "UserInfo{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}'
    }
}
