package xyz.rtxux.utrip.server.model.po

import java.time.Instant
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Entity
data class Track(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Int? = null,
        @NotBlank
        @Column(nullable = false)
        var name: String? = null,
        @NotBlank
        @Column(nullable = false)
        var description: String? = null,
        @ManyToOne(fetch = FetchType.EAGER, optional = false)
        @NotNull
        var user: User? = null,
        @NotNull
        @Column(nullable = false)
        var like: Int? = null,
        @NotNull
        @Column(nullable = false)
        var createdTime: Instant? = null,
        @NotNull
        @Column(nullable = false)
        var beginTime: Instant? = null,
        var endTime: Instant? = null,
        @OneToMany(mappedBy = "associatedTrack")
        var points: List<SPoint>? = null,
        @NotNull
        @Column(nullable = false)
        var status: Int? = null
)