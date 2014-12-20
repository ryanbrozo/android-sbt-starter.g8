package $package$

import android.widget.TextView
import org.junit._
import org.junit.Assert._
import org.junit.runner.RunWith
import org.robolectric._
import org.robolectric.annotation.Config

@RunWith(classOf[RobolectricTestRunner])
@Config(manifest="src/main/AndroidManifest.xml", emulateSdk = 18)
class MainActivityTest {

  @Test
  def doSomething(): Unit = {
    val activity = Robolectric.buildActivity(classOf[MainActivity]).create().get()
    val label = activity.findViewById(R.id.lblMain).asInstanceOf[TextView]
    assertEquals(label.getText.toString, "Hello World, MainActivity")

  }

}
