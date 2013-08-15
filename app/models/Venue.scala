package models

import play.api.db.DB
import org.apache.commons.lang3.StringUtils
import org.codehaus.jackson.annotate.{JsonIgnore}

case class Venue(id: String = StringUtils.EMPTY, name: String = StringUtils.EMPTY, address1: String = StringUtils.EMPTY, address2: String = StringUtils.EMPTY,
                 city: String = StringUtils.EMPTY, state: String = StringUtils.EMPTY, postalCode: String = StringUtils.EMPTY, url: String = StringUtils.EMPTY)

object Venue {
  import anorm._
  import anorm.SqlParser._
  import play.api.db._
  import play.api.Play.current

  val venue = {
    get[String]("id") ~
      get[String]("name") ~
      get[String]("address1") ~
      get[String]("address2") ~
      get[String]("city") ~
      get[String]("state") ~
      get[String]("postal_code") ~
      get[String]("url") map {
        case id~name~address1~address2~city~state~postalCode~url =>
          Venue(id, name, address1, address2, city, state, postalCode, url)
      }
  }

  def list(): List[Venue] = DB.withConnection { implicit c =>
    SQL("select * from venue").as(venue *)
  }

  def find(id: String) = DB.withConnection{ implicit c =>
    SQL("select * from venue where id ='"+id+"'").as(venue *)
  }

  def add(id: String, venue: Venue) = DB.withConnection { implicit c =>
      SQL("insert into venue values ({id}, {name}, {address1}, {address2}, {city}, {state}, {postalCode}, {url})").on(
        'id -> id,
        'name -> venue.name,
        'address1 -> venue.address1,
        'address2 -> venue.address2,
        'city -> venue.city,
        'state -> venue.state,
        'postalCode -> venue.postalCode,
        'url -> venue.url
      ).executeUpdate()
    }

  def remove(id: String) =
    DB.withConnection { implicit c =>
      SQL("delete from venue where id = {id}").on(
        'id -> id
      ).executeUpdate()
    }
}