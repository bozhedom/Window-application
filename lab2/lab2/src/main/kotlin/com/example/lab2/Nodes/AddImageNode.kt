package com.example.lab2

import javafx.fxml.FXML
import javafx.scene.control.Label
import javafx.scene.layout.AnchorPane
import org.opencv.core.Mat

class AddImageNode : ImgNode() {
    @FXML
    var secondLink: AnchorPane? = null

    @FXML
    var thirdLink: AnchorPane? = null

    @FXML
    var fourthLink: AnchorPane? = null

    override fun addInit() {
        titleBar!!.text = "Add image"

        nodes[Links.FIRST] = Triple(firstLink!!, null, NodeTypes.IMAGE)
        nodes[Links.SECOND] = Triple(secondLink!!, null, NodeTypes.IMAGE)
        nodes[Links.THIRD] = Triple(thirdLink!!, null, NodeTypes.INT)
        nodes[Links.FOURTH] = Triple(fourthLink!!, null, NodeTypes.INT)

        (firstLink!!.children.find { it is Label } as Label).text = "img"
        (secondLink!!.children.find { it is Label } as Label).text = "img"
        (thirdLink!!.children.find { it is Label } as Label).text = "ix"
        (fourthLink!!.children.find { it is Label } as Label).text = "iy"
    }

    override fun getValue(): Mat? {
        val image = nodes[Links.FIRST]!!.second?.getValue() as Mat? ?: return errorNode(Links.FIRST)
        val image2 = nodes[Links.SECOND]!!.second?.getValue() as Mat? ?: return errorNode(Links.SECOND)
        val sx = nodes[Links.THIRD]!!.second?.getValue() as Int? ?: return errorNode(Links.THIRD)
        val sy = nodes[Links.FOURTH]!!.second?.getValue() as Int? ?: return errorNode(Links.FOURTH)

        val newImage = Mat()
        image.copyTo(newImage)

        val imageData = ByteArray(((image.total() * image.channels()).toInt()))
        image.get(0, 0, imageData)
        val imageData2 = ByteArray(((image.total() * image.channels()).toInt()))
        image2.get(0, 0, imageData2)

        for (y in 0 until image2.rows()) {
            for (x in 0 until image2.cols()) {
                for (c in 0 until image2.channels()) {
                    if (0 <= sy + y && sy + y < image.rows() && 0 <= sx + x && sx + x < image.cols()) {
                        imageData[((sy + y) * image.cols() + sx + x) * image.channels() + c] =
                            imageData2[(y * image2.cols() + x) * image2.channels() + c]
                    }
                }
            }
        }
        newImage.put(0, 0, imageData)

        goodNodes()
        return newImage
    }

    init {
        initFxml("InputFourNodes.fxml")
    }
}