package com.example.stock_design.Modle

class Item {

    var _id: String? = null
    var quantity: Int=0
    var code:String? = null
    var count: String?=null
    var name: String?=null
    var location:String? = null
//    var quantity:Int = 0

    constructor() {}

    constructor(
        _id: String,
        quantity: Int,
        location: String

    ) {
        this._id=_id
        this.location = location
        this.quantity=quantity
    }

}