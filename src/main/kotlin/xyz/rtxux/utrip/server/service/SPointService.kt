package xyz.rtxux.utrip.server.service

import xyz.rtxux.utrip.server.model.dto.LocationBean
import xyz.rtxux.utrip.server.model.dto.PointDTO
import xyz.rtxux.utrip.server.model.po.SPoint
import xyz.rtxux.utrip.server.model.po.User

interface SPointService {
    fun findAllStandaloneSPointAround(location: LocationBean, radius: Double): List<SPoint>

    fun saveSPoint(pointDTO: PointDTO, user: User): SPoint

    fun findPoint(pointId: Int): SPoint

    fun deleteSPoint(pointId: Int, user: User)

    fun findPointByUser(user: User): List<SPoint>
}