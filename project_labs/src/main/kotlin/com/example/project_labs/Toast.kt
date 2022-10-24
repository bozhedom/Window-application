package com.example.project_labs

import javafx.animation.FadeTransition
import javafx.application.Application
import javafx.application.Platform
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.animation.TranslateTransition
import javafx.scene.control.Label
import javafx.scene.image.Image
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import javafx.scene.media.Media
import javafx.scene.media.MediaPlayer
import javafx.scene.paint.Color
import javafx.scene.paint.ImagePattern
import javafx.scene.shape.Circle
import javafx.scene.shape.Rectangle
import javafx.scene.text.Font
import javafx.stage.Screen
import javafx.stage.Stage
import javafx.stage.StageStyle
import javafx.util.Builder
import javafx.util.Duration
import java.nio.file.Paths


enum class ImageStyle {
    CIRCLE, RECTANGLE
}

enum class Position {
    RIGHT_BOTTOM,
    RIGHT_TOP,
    LEFT_BOTTOM,
    LEFT_TOP
}

enum class Animation {
    TRANSLATE,
    FADE
}

class Config {
    var alpha = 0.9
    var openTime = 7000.0
    var imageType = ImageStyle.RECTANGLE
    var title = "TITLE"
    var message = "MESSAGE"
    var appName = "APP NAME"
    var buttonReply = "Reply"
    var buttonClose = "Close"
    var buttonBolean = true
    var position = Position.RIGHT_BOTTOM
    var animation = Animation.TRANSLATE
    var notifyPath = "C:/Users/ccffc/Downloads/mixkit-guitar-notification-alert-2320.wav"
    var image = "https://nanosemantics.ai/wp-content/uploads/2019/12/%D0%9F%D1%80%D0%BE%D0%B3%D1%80%D0%B0%D0%BC%D0%BC%D0%B8%D1%80%D0%BE%D0%B2%D0%B0%D0%BD%D0%B8%D0%B5-%D0%B4%D0%BB%D1%8F-%D0%B4%D0%B5%D1%82%D0%B5%D0%B9-1024x683.jpg"
}


class Toast {
    var config = Config()
    private val windows = Stage()
    private var root = BorderPane()
    private var box = HBox()
    private val vbox = VBox()
    private val buttons = HBox()
    private var primaryScreenBounds = Screen.getPrimary().visualBounds
    var Height = 140.0
    var Width = 300.0

    class Builder {
        private var config = Config()

        fun setTitle(str: String): Builder {
            config.title = str
            return this
        }

        fun setMessage(str: String): Builder {
            config.message = str
            return this
        }

        fun setAppName(str: String): Builder {
            config.appName = str
            return this
        }

        fun setTitleButton1(str: String): Builder {
            config.buttonReply = str
            return this
        }

        fun setTitleButton2(str: String): Builder {
            config.buttonClose = str
            return this
        }

        fun build(): Toast {
            var toast = Toast()
            toast.config = config
            toast.build()

            return toast
        }

    }

    fun start() {
        windows.show()
        openAnimation()
        playNotify()


        val thread = Thread {
            try {
                Thread.sleep(config.openTime.toLong())
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            closeAnimation()
        }
        Thread(thread).start()
    }


    private fun build() {
        windows.initStyle(StageStyle.TRANSPARENT)

        windows.scene = Scene(root)
        windows.scene.fill = Color.TRANSPARENT
        windows.sizeToScene()

        root.style = "-fx-background-color: BEIGE;"

//        root.setPrefSize(Width, Height)
        root.padding = Insets(7.0, 7.0, 7.0, 7.0)

        setImage()

        val titleButtonReply = Label(config.buttonReply)
        val titleButtonClose = Label(config.buttonClose)

        if (config.buttonBolean) {
            val buttonReply = Button()
            val buttonClose = Button()
            buttonReply.text = titleButtonReply.text
            buttonClose.text = titleButtonClose.text

            buttonReply.style =
                "-fx-font: normal bold 20px 'serif'; -fx-text-fill: green; -fx-background-color: #a8e7ff"
            buttonClose.style =
                "-fx-font: normal bold 20px 'serif'; -fx-text-fill: green; -fx-background-color: #a8e7ff"
            HBox.setHgrow(buttonReply, Priority.ALWAYS)
            HBox.setHgrow(buttonClose, Priority.ALWAYS)
            buttonReply.maxWidth = Double.MAX_VALUE
            buttonClose.maxWidth = Double.MAX_VALUE
            HBox.setMargin(buttonReply, Insets(10.0, 10.0, 0.0, 0.0))
            HBox.setMargin(buttonClose, Insets(10.0, 0.0, 0.0, 0.0))
            buttons.children.addAll(buttonReply, buttonClose)
        }

        val title = Label(config.title)
        val message = Label(config.message)
        val appName = Label(config.appName)
        title.style = "-fx-font: normal bold 20px 'serif'; -fx-text-fill: green; -fx-padding: 0 0 10 10;"

        message.style = "-fx-font: normal bold 20px 'serif'; -fx-text-fill: green; -fx-padding: 0 0 10 10;"

        appName.style = "-fx-font: normal bold 20px 'serif'; -fx-text-fill: green; -fx-padding: 0 0 0 10;"

        vbox.children.addAll(title, message, appName)


        box.children.add(vbox)
        root.center = box
        root.bottom = buttons


    }

    private fun setImage() {
        if (config.image.isEmpty()) {
            return
        }

        val iconBorder = if (config.imageType == ImageStyle.RECTANGLE) {
            Rectangle(Height / 2 , Height /2, Height, Height)
        }
        else {
            Circle(Height / 2, Height / 2, Height / 2)
        }
        iconBorder.fill = ImagePattern(Image(config.image))
        box.children.add(iconBorder)
    }

    private fun openAnimation() {
        when (config.position) {
            Position.RIGHT_TOP -> {
                windows.x = primaryScreenBounds.width - windows.scene.width
                windows.y = 0.0
            }
            Position.RIGHT_BOTTOM -> {
                windows.x = primaryScreenBounds.width - windows.scene.width
                windows.y = primaryScreenBounds.height - windows.scene.height
            }
            Position.LEFT_TOP -> {
                windows.x = 0.0
                windows.y = 0.0
            }
            else -> {
                windows.x = 0.0
                windows.y = primaryScreenBounds.height - windows.scene.height
            }
        }

        if (config.animation == Animation.TRANSLATE) {
            val anim = TranslateTransition(Duration.millis(1500.0), root)

            when (config.position) {
                Position.RIGHT_TOP -> {
                    anim.fromX = windows.scene.width
                    anim.byX = -windows.scene.width
                }

                Position.RIGHT_BOTTOM -> {
                    anim.fromY = windows.scene.height
                    anim.byY = -windows.scene.height
                }

                Position.LEFT_TOP -> {
                    anim.fromY = -windows.scene.height
                    anim.byY = windows.scene.height
                }

                else -> {
                    anim.fromX = -windows.scene.width
                    anim.byX = windows.scene.width
                }
            }
            anim.cycleCount = 1
            anim.play()
        }
        else {
            val anim = FadeTransition(Duration.millis(1500.0), root)
            anim.fromValue = 0.0
            anim.toValue = config.alpha
            anim.cycleCount = 1
            anim.play()
        }


    }

    private fun closeAnimation() {
        if (config.animation == Animation.TRANSLATE) {
            val anim = TranslateTransition(Duration.millis(1500.0), root)

            when (config.position) {
                Position.RIGHT_TOP -> {
                    anim.byX = windows.scene.width
                }

                Position.RIGHT_BOTTOM -> {
                    anim.byY = windows.scene.height
                }

                Position.LEFT_TOP -> {
                    anim.byY = -windows.scene.height
                }

                else -> {
                    anim.byX = -windows.scene.width
                }
            }
            anim.cycleCount = 1
            anim.onFinished = EventHandler {
                Platform.exit()
                System.exit(0)
            }
            anim.play()
        }
        else {
            val anim = FadeTransition(Duration.millis(1500.0), root)
            anim.fromValue = config.alpha
            anim.toValue = 0.0
            anim.cycleCount = 1
            anim.onFinished = EventHandler {
                Platform.exit()
                System.exit(0)
            }
            anim.play()
        }

    }

    private fun playNotify() {
        val media = Media(Paths.get(config.notifyPath).toUri().toString())
        val mediaPlayer = MediaPlayer(media)
        mediaPlayer.play()
        mediaPlayer.volume = 1.0
    }

}


class SomeClass : Application() {
    override fun start(p0: Stage?) {
        var toast = Toast.Builder()
            .setTitle("TeamLead")
            .setMessage("Good!")
            .setAppName("Telegram")
            .setTitleButton1("Replay")
            .setTitleButton2("Close")
            .build()
        toast.start()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(SomeClass::class.java)
        }
    }

}