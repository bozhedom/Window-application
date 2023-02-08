package com.example.lab2

import javafx.fxml.FXML
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.image.ImageView
import javafx.scene.layout.AnchorPane
import javafx.scene.paint.Paint
import javafx.scene.shape.Circle
import org.opencv.core.*
import org.opencv.imgproc.Imgproc
import kotlin.math.roundToInt

abstract class ValueNode : DraggableNode() {
    @FXML
    var valueField: TextField? = null

    init {
        initFxml("ValueNode.fxml")
    }

    override fun updateNode() {
        if (getValue() != null) {
            (outputLinkHandle!!.children.find { it is Circle } as Circle).fill = Paint.valueOf(Colors.GREY)
        } else {
            (outputLinkHandle!!.children.find { it is Circle } as Circle).fill = Paint.valueOf(Colors.RED)
        }
    }

    override fun toData(): NodeData {
        val data = super.toData()
        data.data = valueField!!.text
        return data
    }

    override fun fromData(nodeData: NodeData) {
        super.fromData(nodeData)
        valueField!!.text = nodeData.data
    }
}