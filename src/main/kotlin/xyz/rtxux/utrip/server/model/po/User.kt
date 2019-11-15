package xyz.rtxux.utrip.server.model.po

import org.locationtech.jts.geom.Point
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
@Table(uniqueConstraints = arrayOf(
        UniqueConstraint(name = "username", columnNames = arrayOf("username")),
        UniqueConstraint(name = "phone_number", columnNames = arrayOf("phoneNumber")),
        UniqueConstraint(name = "email", columnNames = arrayOf("email"))
))
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
        var images: List<Image>? = null
)