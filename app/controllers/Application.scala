package controllers

import models._
import akka.actor._
import akka.util.duration._
import play.api._
import play.api.mvc._
import play.api.libs.json._
import play.api.libs.iteratee._

object Application extends Controller {
  
  // Display the home page
  def index = Action { implicit request =>
    Ok(views.html.index())
  }
  
  // Display the chat room page
  def chatRoom(username: String) = Action { implicit request =>
      Ok(views.html.chatRoom(username))
  }
  
  // Welcome a new chatter
  def chat(username: String) = WebSocket.async[JsValue] { request  =>
    ChatRoom.join(username)
  }
  
}
