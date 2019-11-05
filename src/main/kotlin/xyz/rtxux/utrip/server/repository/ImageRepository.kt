package xyz.rtxux.utrip.server.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import xyz.rtxux.utrip.server.model.po.Image

@Repository
interface ImageRepository : JpaRepository<Image, Int>
