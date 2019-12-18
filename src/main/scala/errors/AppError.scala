package errors

sealed trait AppError

object AppError {

  final case class Missing(message: String) extends AppError

  final case class General(message: String) extends AppError

}