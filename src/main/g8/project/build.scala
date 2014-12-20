import android.Keys._
import sbt.Keys._
import sbt._

import scala.collection.JavaConversions._

object BuildDependencies {

  val appCompat =         aar("com.android.support"     % "appcompat-v7"        % "21.0.0")
  val cardView =          aar("com.android.support"     % "cardview-v7"         % "21.0.0")
  val support =           aar("com.android.support"     % "support-v4"          % "21.0.0")

  // Test Dependencies
  val junit =             "junit"           % "junit"               % "4.8.2"   % "test"
  val junit_interface =   "com.novocode"    % "junit-interface"     % "0.8"     % "test->default"
  val robolectric =       "org.robolectric" % "robolectric"         % "2.3"     % "test"
}

object BuildSettings {
  import BuildDependencies._

  val SCALA_VERSION = "2.11.4"
  val APP_VERSION = "0.1"

  lazy val commonSettings = Seq(
    organization        := "$package$",
    version             := APP_VERSION,
    scalaVersion        := SCALA_VERSION,
    scalacOptions       ++= Seq("-feature", "-deprecation"),
    libraryDependencies ++= Seq(
      appCompat,
      support,
      cardView,
      junit,
      junit_interface,
      robolectric
    ),
    // android-sbt-plugin settings
    platformTarget in Android := "android-21",
    minSdkVersion in Android := "16",
    typedResources := true,
    useProguard in Android := true,
    apkbuildExcludes in Android += "LICENSE.txt",
    proguardOptions in Android ++= IO.readLines(new File("project/proguard.txt")),

    // android-sbt-plugin settings specific to testing
    debugIncludesTests := false,
    debugIncludesTests in Android := false,
    // or else @Config throws an exception, yay
    unmanagedClasspath in Test ++= (builder in Android).value.getBootClasspath map
      Attributed.blank,
    managedClasspath in Test <++= (platformJars in Android, baseDirectory) map {
      case ((j,_), b) =>
        Seq(Attributed.blank(b / "bin" / "classes"), Attributed.blank(file(j)))
    },
    fullClasspath in Test <+= (sourceDirectory in Test) map { s =>
      Attributed.blank(s / "resources")
    },
    testOptions in Test ++= Seq(
      Tests.Argument("-oD"),
      Tests.Argument("sequential")
    ),
    // Robolectric tests should run in main thread
    parallelExecution in Test := false
  )
}

object AndroidBuild extends android.AutoBuild {
  import BuildSettings._

  lazy val root = Project(
    id = "$name$",
    base = file(".")
  )
    .settings(commonSettings: _*)

}
