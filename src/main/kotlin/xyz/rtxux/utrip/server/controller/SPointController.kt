package xyz.rtxux.utrip.server.controller

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import xyz.rtxux.utrip.server.model.dto.LocationBean
import xyz.rtxux.utrip.server.model.dto.PointDTO
import xyz.rtxux.utrip.server.model.vo.ApiResponseVO
import xyz.rtxux.utrip.server.model.vo.PointVO


@RestController
@RequestMapping("/point")
@Api(tags = arrayOf("点管理"))
class SPointController @Autowired constructor(

) {

    @PostMapping("")
    @ApiOperation("创建新点")
    fun newSPoint(@RequestBody pointDTO: PointDTO): ApiResponseVO<PointVO>? = null

    @GetMapping("/{id}")
    @ApiOperation("获取点信息")
    fun getSPointInfo(@ApiParam("点ID", example = "1", required = true) @PathVariable("id") pointId: Int): ApiResponseVO<PointVO>? = null

    @DeleteMapping("/{id}")
    @ApiOperation("删除点")
    fun deleteSPoint(@ApiParam("点ID", example = "1", required = true) @PathVariable("id") pointId: Int): ApiResponseVO<Unit>? = null

    @GetMapping("/around")
    @ApiOperation("获取附近点")
    fun getSPointAround(@RequestBody location: LocationBean): ApiResponseVO<List<PointVO>>? = null

    @PostMapping("/{id}")
    @ApiOperation("修改点信息")
    fun modifyPoint(@ApiParam("点ID", example = "1", required = true) @PathVariable("id") pointId: Int, @RequestBody pointDTO: PointDTO): ApiResponseVO<PointVO>? = null
}