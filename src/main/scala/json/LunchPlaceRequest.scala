package json

final case class LunchPlaceRequest(name: String, googleMapsUrl: String)

final case class ApiJsonMessage(message: String)