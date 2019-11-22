package xyz.rtxux.utrip.server.service.impl

import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.GeometryFactory
import org.locationtech.jts.geom.PrecisionModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import xyz.rtxux.utrip.server.exception.AppException
import xyz.rtxux.utrip.server.model.dto.LocationBean
import xyz.rtxux.utrip.server.model.dto.PointDTO
import xyz.rtxux.utrip.server.model.po.SPoint
import xyz.rtxux.utrip.server.model.po.User
import xyz.rtxux.utrip.server.repository.ImageRepository
import xyz.rtxux.utrip.server.repository.SPointRepository
import xyz.rtxux.utrip.server.repository.TrackRepository
import xyz.rtxux.utrip.server.service.SPointService
import java.time.Instant
import javax.persistence.EntityManager

@Service
class SPointServiceImpl @Autowired constructor(
        private val entityManager: EntityManager,
        private val sPointRepository: SPointRepository,
        private val trackRepository: TrackRepository,
        private val imageRepository: ImageRepository
) : SPointService {
    override fun findAllStandaloneSPointAround(location: LocationBean, radius: Double): List<SPoint> {
//        val filter = GeometryUtils.createCircle(location, radius)
//        //entityManager.transaction.begin()
//        val query = entityManager.createQuery("select p from SPoint p where within(p.location, :filter) = true", SPoint::class.java)
//        query.setParameter("filter", filter)
//        return query.resultList
        //return sPointRepository.findAllStandalonePointAround(GeometryUtils.createCircle(location, radius))
        val point = GeometryFactory(PrecisionModel(), 4326).createPoint(Coordinate(location.longitude, location.latitude))
        return sPointRepository.findAllStandalonePointAround(point, radius)
    }

    @Transactional
    override fun saveSPoint(pointDTO: PointDTO, user: User): SPoint {
        val associatedTrack = pointDTO.associatedTrack?.let {
            trackRepository.findById(it).orElseThrow { AppException(400, 2001, "未找到相关轨迹") }.apply {
                if (this.user!!.id != user.id) throw AppException(403, 2002, "未授权的操作")
            }
        }
        val images = imageRepository.findAllById(pointDTO.images).apply {
            forEach {
                if (it.user!!.id != user.id) throw AppException(403, 2002, "未授权的操作")
            }
        }
        val sPoint = SPoint(
                name = pointDTO.name,
                description = pointDTO.description,
                associatedTrack = associatedTrack,
                location = GeometryFactory(PrecisionModel(), 4326).createPoint(Coordinate(pointDTO.location.longitude, pointDTO.location.latitude)),
                images = images,
                user = user,
                like = 0,
                createdTime = Instant.now(),
                timestamp = Instant.now()
        )
        val savedSPoint = sPointRepository.save(sPoint)
        images.forEach {
            it.point = savedSPoint
        }
        imageRepository.saveAll(images)
        return savedSPoint
    }

    @Transactional(readOnly = true)
    override fun findPoint(pointId: Int): SPoint {
        return sPointRepository.findById(pointId).orElseThrow { AppException(404, 2003, "未找到点") }
    }

    @Transactional
    override fun deleteSPoint(pointId: Int, user: User) {
        val point = sPointRepository.findById(pointId).orElseThrow { AppException(404, 3003, "点不存在") }
        if (point.user!!.id!! != user.id!!) {
            throw AppException(403, 2002, "未授权的操作")
        }
        sPointRepository.delete(point)
    }

    override fun findPointByUser(user: User): List<SPoint> {
        return sPointRepository.findAllByUserAndAssociatedTrackIsNull(user)
    }
}