package xyz.rtxux.utrip.server.utils

import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.Geometry
import org.locationtech.jts.util.GeometricShapeFactory
import xyz.rtxux.utrip.server.model.dto.LocationBean

object GeometryUtils {


    fun createCircle(center: LocationBean, radius: Double): Geometry {
        val shapeFactory = GeometricShapeFactory()
        shapeFactory.setNumPoints(32)
        shapeFactory.setCentre(Coordinate(center.longitude, center.latitude))
        shapeFactory.setSize(radius * 2)
        val bound = shapeFactory.createCircle()
        bound.srid = 4326
        return bound
    }
}