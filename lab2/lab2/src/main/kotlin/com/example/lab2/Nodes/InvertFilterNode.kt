package com.example.lab2

import javafx.scene.control.Label
import org.opencv.core.Core
import org.opencv.core.Mat

class InvertFilterNode : ImgNode() {
    override fun addInit() {
        titleBar!!.text = "Invert filter"

        nodes[Links.FIRST] = Triple(firstLink!!, null, NodeTypes.IMAGE)

        (firstLink!!.children.find { it is Label } as Label).text = "img"
    }

    override fun getValue(): Mat? {
        val mat = nodes[Links.FIRST]!!.second?.getValue() as Mat? ?: return errorNode(Links.FIRST)

        val mat2 = Mat()
        mat.copyTo(mat2)
        Core.bitwise_not(mat, mat2)

        return mat2
    }

    init {
        initFxml("InputOneNode.fxml")
    }

}