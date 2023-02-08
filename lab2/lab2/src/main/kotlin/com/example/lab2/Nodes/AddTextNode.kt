package com.example.lab2

import javafx.fxml.FXML
import javafx.scene.control.Label
import javafx.scene.layout.AnchorPane
import org.opencv.core.Mat
import org.opencv.core.Point
import org.opencv.core.Scalar
import org.opencv.imgproc.Imgproc

class AddTextNode : ImgNode() {
    @FXML
    var secondLink: AnchorPane? = null

    @FXML
    var thirdLink: AnchorPane? = null

    @FXML
    var fourthLink: AnchorPane? = null

    override fun addInit() {
        titleBar!!.text = "Add Text"

        nodes[Links.FIRST] = Triple(firstLink!!, null, NodeTypes.IMAGE)
        nodes[Links.SECOND] = Triple(secondLink!!, null, NodeTypes.STRING)
        nodes[Links.THIRD] = Triple(thirdLink!!, null, NodeTypes.INT)
        nodes[Links.FOURTH] = Triple(fourthLink!!, null, NodeTypes.INT)

        (firstLink!!.children.find { it is Label } as Label).text = "img"
        (secondLink!!.children.find { it is Label } as Label).text = "str"
        (thirdLink!!.children.find { it is Label } as Label).text = "ix"
        (fourthLink!!.children.find { it is Label } as Label).text = "iy"
    }

    override fun getValue(): Mat? {
        val mat = nodes[Links.FIRST]!!.second?.getValue() as Mat? ?: return errorNode(Links.FIRST)
        val str = nodes[Links.SECOND]!!.second?.getValue() as String? ?: return errorNode(Links.FOURTH)
        val x = nodes[Links.THIRD]!!.second?.getValue() as Int? ?: return errorNode(Links.THIRD)
        val y = nodes[Links.FOURTH]!!.second?.getValue() as Int? ?: return errorNode(Links.FOURTH)

        val mat2 = Mat()
        mat?.copyTo(mat2)

        Imgproc.putText(
            mat2,
            str,
            Point(x.toDouble(), y.toDouble()),
            0,
            5.0,
            Scalar(0.0, 0.0, 0.0),
            5
        )
        return mat2
    }

    init {
        initFxml("InputFourNodes.fxml")
    }
}