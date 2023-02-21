package org.example.model

import kotlinx.serialization.Serializable

@Serializable
data class Company(
    val name: String? = null,
    val userInfos: List<UserInfo>? = null
) {
    @Override
    override fun toString(): String {
        return "Company{" +
                "name='" + name + '\'' +
                ", userInfos=" + userInfos +
                '}'
    }
}
