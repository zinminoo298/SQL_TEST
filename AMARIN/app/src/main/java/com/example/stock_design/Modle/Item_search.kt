package com.example.stock_design.Modle

class Item_search {

    var location: String?=null
    var date: String?=null
    var itm: Int=0
    var qty: Int=0


    //    override fun toString(): String {
//        return date.toString()
//    }
    constructor() {}

    constructor(location: String?, date: String?, itm: Int, qty: Int) {

        this.location=location
        this.date=date
        this.itm=itm
        this.qty=qty
    }

}