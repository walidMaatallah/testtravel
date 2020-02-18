package com.travelcar.test.mapper

import com.travelcar.test.model.User
import com.travelcar.test.source.db.tables.UserEntity
import java.util.*

object UserMapper {

    fun mapUserEntityToUserDomain(userEntity: UserEntity?): User? {
        userEntity ?: return null
        return User(
            id = userEntity.id,
            firstName = userEntity.firstName,
            lastName = userEntity.lastName,
            birthDate = userEntity.birthDay ?: Date(),
            address = userEntity.address
        )
    }

    fun mapUserDomainToUserEntity(user: User): UserEntity {
        return UserEntity(
            id = user.id,
            firstName = user.firstName,
            lastName = user.lastName,
            birthDay = user.birthDate,
            address = user.address
        )
    }
}