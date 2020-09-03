package com.example.chekins_19

import kotlin.collections.ArrayList

class FoursquareAPIRequestVenues {

    var response: FoursquareResponseVenues? = null
    var meta:Meta? = null

}

class Meta{
    var code:Int = 0
    var errorDetail:String = ""
}

class FoursquareResponseVenues{
    var venues: ArrayList<Venue>? = null
}

class Venue{
    var id:String = ""
    var name:String = ""
    var location:Location? = null
    var category:ArrayList<Category>? = null
    var stats:Stats? = null
}

class Location{
    var lat:Double = 0.0
    var lng:Double = 0.0
    var state:String = ""
    var country:String = ""
}

class Category{
    var id:String = ""
    var name:String = ""
    var icon:Icon? = null
}

class Icon{
    var prefix:String? = ""
    var suffix:String? = null
}

class Stats{
    var tipCount:Int? = 0
    var usersCount:Int? = 0
    var checkinsCount:Int = 0
}