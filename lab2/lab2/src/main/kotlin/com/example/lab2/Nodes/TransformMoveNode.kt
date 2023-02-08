package com.example.lab2

import javafx.fxml.FXML
import javafx.scene.control.Label
import javafx.scene.layout.AnchorPane
import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc

class TransformMoveNode : ImgNode() {
    @FXML
    var secondLink: AnchorPane? = null

    @FXML
    var thirdLink: AnchorPane? = null
    override fun addInit() {
        titleBar!!.text = "Move"

        nodes[Links.FIRST] = Triple(firstLink!!, null, NodeTypes.IMAGE)
        nodes[Links.SECOND] = Triple(secondLink!!, null, NodeTypes.FLOAT)
        nodes[Links.THIRD] = Triple(thirdLink!!, null, NodeTypes.FLOAT)

        (firstLink!!.children.find { it is Label } as Label).text = "img"
        (secondLink!!.children.find { it is Label } as Label).text = "fx"
        (thirdLink!!.children.find { it is Label } as Label).text = "fy"
    }

    override fun getValue(): Mat? {
        val mat = nodes[Links.FIRST]!!.second?.getValue() as Mat? ?: return errorNode(Links.FIRST)
        val px = nodes[Links.SECOND]!!.second?.getValue() as Float? ?: return errorNode(Links.SECOND)
        val py = nodes[Links.THIRD]!!.second?.getValue() as Float? ?: return errorNode(Links.THIRD)

        val newImage = Mat()
        mat.copyTo(newImage)

        val moveMat = Mat(2, 3, CvType.CV_64FC1)
        val row = 0
        val col = 0
        moveMat.put(
            row, col, 1.0, 0.0, (mat.cols() * px / 100.0), 0.0, 1.0, (mat.rows() * py / 100.0)
        )

        Imgproc.warpAffine(mat, newImage, moveMat, Size(mat.cols().toDouble(), mat.rows().toDouble()))

        return newImage
    }

    init {
        initFxml("InputThreeNodes.fxml")
    }

}