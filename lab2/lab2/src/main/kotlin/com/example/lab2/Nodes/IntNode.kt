package com.example.lab2

class IntNode : ValueNode() {
    override val nodeType: NodeTypes = NodeTypes.INT

    override fun addInit() {
        valueField!!.text = "0"
        titleBar!!.text = "Int"

        valueField!!.textProperty().addListener { _, _, _ ->
            updateNode()
            outputLink?.kickAction()
        }
    }

    override fun getValue(): Int? {
        return valueField!!.text.toIntOrNull()
    }

}