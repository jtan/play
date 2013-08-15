package test

import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._
import play.api.libs.json.Json
import models.Venue

class ApplicationSpec extends Specification {
  
  "Application" should {

    "be able to add and find venues" in {
      running(FakeApplication()) {
        Venue.add("A1", Venue("A1", "Apple", "25 Broad", "Suite 201", "NY", "NY", "11103", "tansrc.com"))
        Venue.add("B1", Venue("B1", "Banana", "", "", "", "", "", ""))
        Venue.add("C1", Venue("C1", "Cat", "100 Mott", "", "NY", "NY", "10013", ""))

        var venues = Venue.list
        venues.size must equalTo (3)

        venues = Venue.find("A1")
        venues.size must equalTo (1)
        venues.filter(_.name == "Apple").head.name must be ("Apple")
      }
    }
  }
}