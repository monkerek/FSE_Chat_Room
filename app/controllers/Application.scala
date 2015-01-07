package controllers

import models._
import akka.actor._
import akka.util.duration._
import play.api._
import play.api.mvc._
import play.api.libs.json._
import play.api.libs.iteratee._

object Application extends Controller {

  // Just display the home page
  // Mark the request parameter as implicit so it can be
  // implicitly used by other APIs that need it
  def index = Action { implicit request =>
    Ok(views.html.index())
  }
  
  // Display the chat room page
  def chatRoom(username: Option[String]) = Action { implicit request =>
    username.filterNot(_.isEmpty).map { username =>
      Ok(views.html.chatRoom(username))
    }.getOrElse {
      // handles situations when empty username is given
      Redirect(routes.Application.index).flashing(
        "error" -> "Please choose a valid username."
      )
    }
  }
  
  // Handles the chat websocket
  def chat(username: String) = WebSocket.async[JsValue] { implicit request  =>
    ChatRoom.join(username)
  }
  
}
