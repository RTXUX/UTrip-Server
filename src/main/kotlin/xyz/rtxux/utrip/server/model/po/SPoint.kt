package xyz.rtxux.utrip.server.model.po

import org.locationtech.jts.geom.Point
import java.time.Instant
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Entity
data class SPoint(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Int? = null,
        @NotBlank
        @Column(nullable = false)
        var name: String? = null,
        @NotBlank
        @Column(nullable = false)
        var description: String? = null,
        @NotNull
        @ManyToOne(fetch = FetchType.EAGER, optional = false)
        var user: User? = null,
        @NotNull
        @Column(nullable = false)
        var like: Int? = null,
        @NotNull
        @Column(nullable = false)
        var createdTime: Instant? = null,
        @NotNull
        @Column(nullable = false)
        var timestamp: Instant? = null,
        @NotNull
        @Column(nullable = false, columnDefinition = "geometry(Point,4326)")
        //@Type(type = "org.hibernate.spatial.GeometryType")
        var location: Point? = null,
        @ManyToOne(fetch = FetchType.EAGER)
        var associatedTrack: Track? = null,
        @OneToMany(mappedBy = "point", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
        var images: List<Image>? = null
)
