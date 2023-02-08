package com.example.lab2

import javafx.fxml.FXML
import javafx.scene.control.Label
import javafx.scene.layout.AnchorPane
import org.opencv.core.Mat
import org.opencv.core.Point
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc

class TransformRotateNode : ImgNode() {
    @FXML
    var secondLink: AnchorPane? = null

    override fun addInit() {
        titleBar!!.text = "Rotate"

        nodes[Links.FIRST] = Triple(firstLink!!, null, NodeTypes.IMAGE)
        nodes[Links.SECOND] = Triple(secondLink!!, null, NodeTypes.FLOAT)

        (firstLink!!.children.find { it is Label } as Label).text = "img"
        (secondLink!!.children.find { it is Label } as Label).text = "fd"
    }

    override fun getValue(): Mat? {
        val mat = nodes[Links.FIRST]!!.second?.getValue() as Mat? ?: return errorNode(Links.FIRST)
        val deg = nodes[Links.SECOND]!!.second?.getValue() as Float? ?: return errorNode(Links.SECOND)

        val newImage = Mat()
        mat.copyTo(newImage)

        val rotMat = Imgproc.getRotationMatrix2D(Point(mat.cols() / 2.0, mat.rows() / 2.0), deg.toDouble(), 1.0)

        Imgproc.warpAffine(mat, newImage, rotMat, Size(mat.cols().toDouble(), mat.rows().toDouble()))

        return newImage
    }

    init {
        initFxml("InputTwoNodes.fxml")
    }
}
