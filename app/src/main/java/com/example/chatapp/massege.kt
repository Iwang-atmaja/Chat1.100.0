package com.example.chatapp

class massege {
    var Message : String? = null
    var senderid : String? = null

    constructor(){
    }

    constructor(message : String?, senderid: String){
        this.Message = message
        this.senderid=senderid
    }
}