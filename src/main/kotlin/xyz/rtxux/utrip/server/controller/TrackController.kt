package xyz.rtxux.utrip.server.controller

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import xyz.rtxux.utrip.server.model.dto.TrackDTO
import xyz.rtxux.utrip.server.model.vo.ApiResponseVO
import xyz.rtxux.utrip.server.model.vo.TrackVO

@RestController
@RequestMapping("/track")
@Api(tags = arrayOf("轨迹管理"))
class TrackController @Autowired constructor(

) {

    @PostMapping("")
    @ApiOperation("创建轨迹")
    fun newTrack(@RequestBody trackDTO: TrackDTO): ApiResponseVO<TrackVO>? = null

    @PostMapping("/{id]")
    @ApiOperation("修改轨迹")
    fun modifyTrack(@PathVariable("id") trackId: Int?, @RequestBody trackDTO: TrackDTO): ApiResponseVO<TrackVO>? = null

    @PostMapping("/{id}/save")
    @ApiOperation("保存并发布")
    fun saveTrack(@PathVariable("id") trackId: Int?): ApiResponseVO<TrackVO>? = null

    @GetMapping("/{id}")
    @ApiOperation("获取轨迹")
    fun getTrack(@PathVariable("id") trackId: Int?): ApiResponseVO<TrackVO>? = null

    @GetMapping("/list")
    @ApiOperation("获取轨迹")
    fun queryTrack(@RequestParam("sort") sort: String): ApiResponseVO<List<TrackVO>>? = null

    @DeleteMapping("/{id}")
    @ApiOperation("删除轨迹")
    fun deleteTrack(@PathVariable("id") trackId: Int?): ApiResponseVO<Unit>? = null
}