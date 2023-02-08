package com.example.lab2

import javafx.scene.control.Label
import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.imgproc.Imgproc

class GrayFilterNode : ImgNode() {
    override fun addInit() {
        titleBar!!.text = "Gray filter"

        nodes[Links.FIRST] = Triple(firstLink!!, null, NodeTypes.IMAGE)

        (firstLink!!.children.find { it is Label } as Label).text = "img"
    }

    override fun getValue(): Mat? {
        val mat = nodes[Links.FIRST]!!.second?.getValue() as Mat? ?: return errorNode(Links.FIRST)

        val mat2 = Mat()
        mat.copyTo(mat2)
        Imgproc.cvtColor(mat, mat2, Imgproc.COLOR_BGR2GRAY)

        val newImage = Mat()

        Core.merge(List(3) { mat2 }, newImage)

        return newImage
    }

    init {
        initFxml("InputOneNode.fxml")
    }

}