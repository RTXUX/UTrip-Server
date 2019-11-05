package xyz.rtxux.utrip.server.model.po

import java.time.Instant
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
data class Image(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Int? = null,
        @ManyToOne(fetch = FetchType.EAGER, optional = true)
        var point: SPoint? = null,
        @NotNull
        @Column(nullable = false)
        var like: Int? = null,
        var imageUrl: String? = null,
        @NotNull
        @Column(nullable = false)
        var finished: Boolean? = null,
        @NotNull
        @Column(nullable = false)
        var timestamp: Instant? = null
)