package com.example.lab2

import javafx.application.Application
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.image.Image
import javafx.scene.layout.*
import javafx.stage.Stage
import org.opencv.core.Core
import javax.swing.GrayFilter

class Config {
    val imageIcon =
        "https://upload.wikimedia.org/wikipedia/commons/thumb/a/af/Adobe_Photoshop_CC_icon.svg/1200px-Adobe_Photoshop_CC_icon.svg.png";
    val backgroundMainColor = "-fx-background-color: BEIGE;";
    val backgroundNavBarColor = "-fx-background-color: RED;";
    val buttons: Array<String> = arrayOf(
        "Float",
        "Int",
        "String",
        "Image",
        "Add text",
        "Add image",
        "Gray filter",
        "Brightness",
        "Sepia",
        "Invert filter",
        "Blur filter",
        "Transform move",
        "Transform scale",
        "Transform rotate"
    )
}

class Editor {
    var config = Config();
    private val windows = Stage();
    private val paneButton = HBox(10.0);
    private val paneMain = AnchorPane();
    private val pane = BorderPane();
    private val scene = Scene(pane, 1500.0, 750.0);

    fun start() {
        windows.show();
    }

    private fun createButton(string: String) {
        val button = Button(string);
        button.onAction = EventHandler {
            val node = showNode(string);
            node.layoutX = 50.0;
            node.layoutY = 50.0;

            paneMain.children.add(node);
        }
        paneButton.children.add(button);
    }

    private fun insertButton() {
        for (i in 0..13) {
            createButton(config.buttons[i]);
        }

    }

    private fun build() {
        windows.title = "Image Editor";
        windows.icons.add(Image(config.imageIcon));

        insertButton();

        pane.style = config.backgroundMainColor;
        paneButton.padding = Insets(10.0, 0.0, 0.0, 10.0);

        pane.top = paneButton;
        pane.center = paneMain;

        val destinationImageNode = DestinationImageNode();
        destinationImageNode.layoutX = 1500.0 - destinationImageNode.rootPane!!.prefWidth - 10;
        destinationImageNode.layoutY = 750.0 / 2;

        paneMain.children.add(destinationImageNode)

        windows.scene = scene;

    }

    class Builder() {
        fun build(): Editor {
            val editor = Editor();
            editor.build();

            return editor;
        }
    }

    private fun showNode(str: String): DraggableNode {
        return when (str) {
            "Float" -> FloatNode()
            "Int" -> IntNode()
            "String" -> StringNode()
            "Image" -> ImageNode()
            "Add text" -> AddTextNode()
            "Add image" -> AddImageNode()
            "Gray filter" -> GrayFilterNode()
            "Brightness" -> BrightnessNode()
            "Sepia" -> SepiaNode()
            "Invert filter" -> InvertFilterNode()
            "Blur filter" -> BlurFilterNode()
            "Transform move" -> TransformMoveNode()
            "Transform scale" -> TransformScaleNode()
            "Transform rotate" -> TransformRotateNode()
            DestinationImageNode::class.simpleName -> DestinationImageNode()
            else -> IntNode()
        }
    }
}


class Main : Application() {
    override fun start(stage: Stage) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        val editor = Editor.Builder()
            .build();
        editor.start()

    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(Main::class.java)
        }
    }
}
