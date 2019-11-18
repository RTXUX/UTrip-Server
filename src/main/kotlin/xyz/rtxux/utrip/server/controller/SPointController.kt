package xyz.rtxux.utrip.server.controller

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import springfox.documentation.annotations.ApiIgnore
import xyz.rtxux.utrip.server.exception.AppException
import xyz.rtxux.utrip.server.model.dto.LocationBean
import xyz.rtxux.utrip.server.model.dto.PointDTO
import xyz.rtxux.utrip.server.model.vo.ApiResponseVO
import xyz.rtxux.utrip.server.model.vo.PointVO
import xyz.rtxux.utrip.server.repository.UserRepsitory
import xyz.rtxux.utrip.server.service.SPointService
import xyz.rtxux.utrip.server.service.impl.MyUserDetails


@RestController
@RequestMapping("/point")
@Api(tags = arrayOf("点管理"))
class SPointController @Autowired constructor(
        private val sPointService: SPointService,
        private val userRepsitory: UserRepsitory
) {

    @PostMapping("")
    @ApiOperation("创建新点")
    fun newSPoint(@RequestBody pointDTO: PointDTO, @ApiIgnore @AuthenticationPrincipal userDetails: MyUserDetails): ApiResponseVO<PointVO> {
        val user = userRepsitory.findById(userDetails.userId).orElseThrow { AppException(401, 1002, "用户不存在") }
        val sPoint = sPointService.saveSPoint(pointDTO, user)
        return ApiResponseVO(0, "Success", PointVO(
                pointId = sPoint.id!!,
                name = sPoint.name!!,
                description = sPoint.description!!,
                location = LocationBean("WGS-84", sPoint.location!!.y, sPoint.location!!.x),
                userId = sPoint.user!!.id!!,
                timestamp = sPoint.timestamp!!.toEpochMilli(),
                like = sPoint.like!!,
                images = sPoint.images!!.map { it.id!! },
                associatedTrack = sPoint.associatedTrack?.id
        ))
    }

    @GetMapping("/{id}")
    @ApiOperation("获取点信息")
    fun getSPointInfo(@ApiParam("点ID", example = "1", required = true) @PathVariable("id") pointId: Int): ApiResponseVO<PointVO> {
        val sPoint = sPointService.findPoint(pointId)
        return ApiResponseVO(0, "Success", PointVO(
                pointId = sPoint.id!!,
                name = sPoint.name!!,
                description = sPoint.description!!,
                location = LocationBean("WGS-84", sPoint.location!!.y, sPoint.location!!.x),
                userId = sPoint.user!!.id!!,
                timestamp = sPoint.timestamp!!.toEpochMilli(),
                like = sPoint.like!!,
                images = sPoint.images!!.map { it.id!! },
                associatedTrack = sPoint.associatedTrack?.id
        ))
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除点")
    fun deleteSPoint(@ApiParam("点ID", example = "1", required = true) @PathVariable("id") pointId: Int): ApiResponseVO<Unit>? = null

    @GetMapping("/around")
    @ApiOperation("获取附近点")
    fun getSPointAround(@RequestParam coordinateType: String, @RequestParam latitude: Double, @RequestParam longitude: Double): ApiResponseVO<List<PointVO>> {
        return ApiResponseVO(0, "Success", sPointService.findAllStandaloneSPointAround(LocationBean(
                coordinateType, latitude, longitude
        ), 1.0).map {
            PointVO(
                    pointId = it.id!!,
                    name = it.name!!,
                    description = it.description!!,
                    location = LocationBean("WGS-84", it.location!!.y, it.location!!.x),
                    userId = it.user!!.id!!,
                    timestamp = it.timestamp!!.toEpochMilli(),
                    like = it.like!!,
                    images = it.images!!.map { it.id!! },
                    associatedTrack = null
            )
        })
    }

    @PostMapping("/{id}")
    @ApiOperation("修改点信息")
    fun modifyPoint(@ApiParam("点ID", example = "1", required = true) @PathVariable("id") pointId: Int, @RequestBody pointDTO: PointDTO): ApiResponseVO<PointVO>? = null
}