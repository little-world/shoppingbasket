name := "shopping basket"

version := "0.1.0"

scalaVersion := "2.11.7"

resolvers ++= Seq("Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
                  "Spray Repository"    at "http://repo.spray.io")
 
libraryDependencies ++= {
  val akkaVersion       = "2.3.12"
  val sprayVersion      = "1.3.3"
  val scalaTestVersion	= "2.2.4"
  Seq(
    "com.typesafe.akka" %% "akka-actor"      % akkaVersion,
    "io.spray"          %% "spray-can"       % sprayVersion,
    "io.spray"          %% "spray-routing"   % sprayVersion,
    "io.spray"          %% "spray-json"      % "1.3.2",
    "com.typesafe.akka" %% "akka-slf4j"      % akkaVersion,
    "ch.qos.logback"    %  "logback-classic" % "1.1.2",
    "com.typesafe.akka" %% "akka-testkit"    % akkaVersion  	% "test",
    "io.spray"          %% "spray-testkit"   % sprayVersion 	% "test",
    "org.scalatest"     %% "scalatest"       % scalaTestVersion % "test"
  )
}