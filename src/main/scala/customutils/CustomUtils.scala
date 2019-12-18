package customutils

import akka.http.scaladsl.marshalling.ToResponseMarshaller
import akka.http.scaladsl.model.{ContentTypes, HttpResponse, StatusCodes, Uri}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.typesafe.scalalogging.Logger
import errors.AppError
import io.circe.generic.auto._
import io.circe.syntax._
import json.ApiJsonMessage
import monix.eval.Task
import monix.execution.Scheduler


object CustomUtils {

  val logger = Logger(getClass)
  def run[A: ToResponseMarshaller](result: Task[Either[AppError, A]])(implicit s: Scheduler): Route = {
    onSuccess(result.runToFuture) {
      case Right(value) => complete(value)
      case Left(AppError.Missing(message)) =>
        logger.error(message)
        complete(
          HttpResponse(status = StatusCodes.NotFound)
            .withEntity(ContentTypes.`application/json`, ApiJsonMessage(message).asJson.noSpaces)
        )

    }
  }

  def runAndRedirect(result: Task[Either[AppError, String]])(implicit s: Scheduler): Route = {
    onSuccess(result.runToFuture) {
      case Right(value) =>
        redirect(Uri(value), StatusCodes.SeeOther)
      case Left(AppError.Missing(message)) =>
        logger.error(message)
        complete(
          HttpResponse(status = StatusCodes.NotFound)
            .withEntity(ContentTypes.`application/json`, ApiJsonMessage(message).asJson.noSpaces)
        )

    }
  }


}
