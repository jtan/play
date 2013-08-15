package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json.{Format, Json}
import models.Venue
import java.util.{Date, UUID}
import java.text.SimpleDateFormat

object Application extends Controller {

  def generateIdPrefix = {
    val format = new SimpleDateFormat("yyyyMMdd")
    format.format(new Date()) + "-" + UUID.randomUUID()
  }

  def index = Action {
    Ok(views.html.index("Welcome to the Venue API!"))
  }

  def list = Action {
    implicit val venueWrites = Json.writes[Venue]

    val venues = Venue.list
    Ok(Json.toJson(venues))
  }

  def getById(id: String) = Action {
    find(id)
  }

  def post() = Action(parse.json) { request =>
    implicit val venueFormat: Format[Venue] = Json.format[Venue]

    val venueToAdd = request.body.as[Venue]
    val id = generateIdPrefix + "-" + venueToAdd.id
    Venue.add(id, venueToAdd) match {
      case numUpdated: Int if numUpdated > 0 => find(id)
      case _ => BadRequest("There was an error adding venue.")
    }
  }

  def put() = post

  private def find(id: String) = {
    implicit val venueWrites = Json.writes[Venue]

    val venue = Venue.find(id)
    Ok(Json.toJson(venue))
  }

}