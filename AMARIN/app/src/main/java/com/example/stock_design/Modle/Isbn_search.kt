package com.example.stock_design.Modle

class Isbn_search{
    var edt_branch:String? =null
    var edt_location:String? =null
    var edt_isbn:String? =null
    var edt_code:String? =null
    var edt_price:String? =null
    var edt_description:String? =null
    var edt_qty:String? =null

    constructor(
        edt_branch: String?,
        edt_location: String?,
        edt_isbn: String?,
        edt_code: String?,
        edt_price: String?,
        edt_description: String?,
        edt_qty: String?
    ) {
        this.edt_branch = edt_branch
        this.edt_location = edt_location
        this.edt_isbn = edt_isbn
        this.edt_code = edt_code
        this.edt_price = edt_price
        this.edt_description = edt_description
        this.edt_qty = edt_qty
    }


}