package xyz.rtxux.utrip.server.model.po

import com.vladmihalcea.hibernate.type.json.JsonBinaryType
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import org.hibernate.annotations.TypeDefs
import org.locationtech.jts.geom.Point
import xyz.rtxux.utrip.server.model.vo.UserProfileVO
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
@Table(uniqueConstraints = arrayOf(
        UniqueConstraint(name = "username", columnNames = arrayOf("username")),
        UniqueConstraint(name = "phone_number", columnNames = arrayOf("phoneNumber")),
        UniqueConstraint(name = "email", columnNames = arrayOf("email"))
))
@TypeDefs(TypeDef(name = "jsonb", typeClass = JsonBinaryType::class))
data class User(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Int? = null,
        @NotBlank
        @Column(nullable = false)
        var username: String? = null,
        @NotBlank
        @Column(nullable = false)
        var password: String? = null,
        var nickname: String? = null,
        var avatarUrl: String? = null,
        var phoneNumber: String? = null,
        var email: String? = null,
        var governmentIssuedId: String? = null,
        var enabled: Boolean? = null,
        var lastKnownLocation: Point? = null,
        @OneToMany(mappedBy = "user")
        var spoints: List<SPoint>? = null,
        @OneToMany(mappedBy = "user")
        var tracks: List<Track>? = null,
        @NotBlank
        @Column(nullable = false)
        var gender: String? = null,
        @OneToMany(mappedBy = "user")
        var images: List<Image>? = null,
        @Type(type = "jsonb")
        @Column(columnDefinition = "jsonb")
        var userProfile: UserProfileWrapper? = null
) {
    fun toUserProfileVO(): UserProfileVO = UserProfileVO(
            userId = id!!,
            username = username!!,
            nickname = nickname ?: username!!,
            gender = gender!!,
            avatarUrl = avatarUrl ?: ""
    )
}

data class UserProfileEntry(
        val key: String,
        var hidden: Boolean = true,
        var value: String? = null
)

data class UserProfileWrapper(
        val userProfile: MutableMap<String, UserProfileEntry>
)