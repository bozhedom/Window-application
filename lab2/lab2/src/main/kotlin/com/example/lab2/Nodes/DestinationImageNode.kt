package com.example.lab2

import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.paint.Paint
import javafx.stage.FileChooser
import org.opencv.core.Mat
import org.opencv.imgcodecs.Imgcodecs
import java.io.IOException

class DestinationImageNode : ImgNode() {
    @FXML
    var saveButton: Button? = null

    override fun getValue(): Mat? {
        return nodes["firstLink"]!!.second?.getValue() as Mat? ?: return null
    }

    override fun addInit() {
        rootPane!!.onDragDetected = null

        nodes["firstLink"] = Triple(firstLink!!, null, NodeTypes.IMAGE)

        (firstLink!!.children.find { it is Label } as Label).text = "img"

        saveButton!!.onAction = EventHandler {
            val mat = nodes["firstLink"]!!.second?.getValue() as Mat? ?: return@EventHandler

            val fileChooser = FileChooser()
            fileChooser.title = "Save Picture"
            fileChooser.extensionFilters.addAll(FileChooser.ExtensionFilter("Image Files", "*.png"))
            val dir = fileChooser.showSaveDialog(scene.window)
            if (dir != null) {
                try {
                    Imgcodecs.imwrite(dir.absolutePath, mat)
                } catch (e: IOException) {
                    println(e)
                }
            }
        }
    }

    override fun updateNode() {
        val tmp = getValue()
        if (tmp != null) {
            imageView!!.isVisible = true
            imageView!!.image = ImageTransf.matToImage(tmp)
            saveButton!!.textFill = Paint.valueOf(Colors.GREY)
        } else {
            saveButton!!.textFill = Paint.valueOf(Colors.RED)
        }
    }

    init {
        initFxml("DestinationImageNode.fxml")
    }
}