package org.example.model

import kotlinx.serialization.Serializable

@Serializable
data class Company2(
    val name: String? = null,
    val userInfos: List<UserInfo2>? = null
) {
    @Override
    override fun toString(): String {
        return "Company{" +
                "name='" + name + '\'' +
                ", userInfos=" + userInfos +
                '}'
    }
}
