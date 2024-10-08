package computerdatabase

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._

class rising extends Simulation {


  val httpProtocol = http
    .baseUrl("https://coub.com")
    .inferHtmlResources()
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Safari/537.36")

  val scn = scenario("Coub Rising Page Browsing")
    .exec(http("Load Rising Page")
      .get("/rising"))
    .pause(2)
    .repeat(30) {
      exec(http("Scroll Rising Page")
        .get("/rising")
        .check(status.is(200)))
      .pause(2)
    }


  setUp(
    scn.inject(
      rampUsers(10) during (60.seconds) // Плавное увеличение пользователей в течение 60 секунд
    ).protocols(httpProtocol)
  )
}
