package $package$

import android.os.Bundle
import android.support.v7.app.ActionBarActivity

class MainActivity extends ActionBarActivity with TypedActivity {
  /**
   * Called when the activity is first created.
   */
  override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.main)
    Option(findView(TR.toolbar)).map(setSupportActionBar)
  }
}
