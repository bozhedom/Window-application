package com.example.lab2

import javafx.scene.control.Label
import org.opencv.core.Core
import org.opencv.core.CvType
import org.opencv.core.Mat

class SepiaNode : ImgNode() {

    override fun addInit() {
        titleBar!!.text = "Sepia"

        nodes[Links.FIRST] = Triple(firstLink!!, null, NodeTypes.IMAGE)

        (firstLink!!.children.find { it is Label } as Label).text = "img"
    }

    override fun getValue(): Mat? {
        val mat = nodes[Links.FIRST]!!.second?.getValue() as Mat? ?: return errorNode(Links.FIRST)

        val colMat = Mat(3, 3, CvType.CV_64FC1)
        val row = 0
        val col = 0
        colMat.put(
            row, col, 0.272, 0.534, 0.131, 0.349, 0.686, 0.168, 0.393, 0.769, 0.189
        )

        val mat2 = Mat()
        mat.copyTo(mat2)
        Core.transform(mat, mat2, colMat)

        return mat2
    }

    init {
        initFxml("InputOneNode.fxml")
    }

}