package xyz.rtxux.utrip.server.repository

import org.locationtech.jts.geom.Geometry
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import xyz.rtxux.utrip.server.model.po.SPoint

@Repository
interface SPointRepository : JpaRepository<SPoint, Int> {
    @Query(value = "select p from SPoint p where p.associatedTrack is null and within(p.location, :filter) = true")
    fun findAllStandalonePointAround(filter: Geometry): List<SPoint>
}