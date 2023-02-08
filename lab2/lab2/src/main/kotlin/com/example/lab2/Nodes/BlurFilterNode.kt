package com.example.lab2

import javafx.fxml.FXML
import javafx.scene.control.Label
import javafx.scene.layout.AnchorPane
import org.opencv.core.Mat
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc

class BlurFilterNode : ImgNode() {
    @FXML
    var secondLink: AnchorPane? = null

    override fun addInit() {
        titleBar!!.text = "Blur filter"

        nodes[Links.FIRST] = Triple(firstLink!!, null, NodeTypes.IMAGE)
        nodes[Links.SECOND] = Triple(secondLink!!, null, NodeTypes.INT)

        (firstLink!!.children.find { it is Label } as Label).text = "img"
        (secondLink!!.children.find { it is Label } as Label).text = "int"
    }


    override fun getValue(): Mat? {
        val image = nodes[Links.FIRST]!!.second?.getValue() as Mat? ?: return errorNode(Links.FIRST)
        var kernelSize = nodes[Links.SECOND]!!.second?.getValue() as Int? ?: return errorNode(Links.SECOND)

        kernelSize = kernelSize * 2 + 1
        if (kernelSize <= 0 || kernelSize > 100)
            return null

        val newImage = Mat()
        image.copyTo(newImage)

        Imgproc.GaussianBlur(image, newImage, Size(kernelSize.toDouble(), kernelSize.toDouble()), 0.0)

        return newImage
    }

    init {
        initFxml("InputTwoNodes.fxml")
    }

}