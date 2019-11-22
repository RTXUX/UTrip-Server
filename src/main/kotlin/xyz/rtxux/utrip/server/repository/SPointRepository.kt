package xyz.rtxux.utrip.server.repository

import org.locationtech.jts.geom.Geometry
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import xyz.rtxux.utrip.server.model.po.SPoint
import xyz.rtxux.utrip.server.model.po.User

@Repository
interface SPointRepository : JpaRepository<SPoint, Int> {
    @Suppress("SyntaxError")
    //@Query(value = "select p from SPoint p where p.associatedTrack is null and within(p.location, :filter) = true")
    @Query(value = "select p.* from spoint p where p.associated_track_id is null and st_dwithin(p.location\\:\\:geography, :filter\\:\\:geography, :radius)", nativeQuery = true)
    fun findAllStandalonePointAround(filter: Geometry, radius: Double): List<SPoint>

    fun findAllByUserAndAssociatedTrackIsNull(user: User): List<SPoint>
}