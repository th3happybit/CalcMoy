package oxxy.kero.roiaculte.team7.calcmoy.ui.save_info

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import oxxy.kero.roiaculte.team7.calcmoy.R
import oxxy.kero.roiaculte.team7.calcmoy.base.BaseActivity
import oxxy.kero.roiaculte.team7.calcmoy.ui.save_info.fragmnets.fragment1.Fragment1
import oxxy.kero.roiaculte.team7.calcmoy.ui.save_info.fragmnets.fragment2.Fragment2
import oxxy.kero.roiaculte.team7.calcmoy.utils.extension.inTransaction


class SaveInfoActivity :BaseActivity() {

    companion object { fun getIntent(context : Context) = Intent(context,SaveInfoActivity::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.save_info)
        if(savedInstanceState == null) loadFragment1()
        handleIntentSearch()
    }

    override fun onNewIntent(intent: Intent?) {
        setIntent(intent)
        handleIntentSearch()
    }

    private fun handleIntentSearch() {
        when(intent.action) {
            Intent.ACTION_SEARCH -> {
                // Handle the normal search query case
                intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                    showMessage(R.string.no_university)
                }
            }
            Intent.ACTION_VIEW -> {
                // Handle a suggestions click (because the suggestions all use ACTION_VIEW)
                showResult(intent.dataString)
            }
        }
    }

    private fun showResult(data: String?) {
        data.also {
            //TODO send "data" to fragment
        }
    }

    fun loadFragment1() {
        val fragment1 = Fragment1.getInstance()
        supportFragmentManager.inTransaction { add(R.id.save_info_container, fragment1) }
    }

    fun loadFragment2(bundle: Bundle) {
        val fragment2 = Fragment2.getInstance()
        fragment2.arguments = bundle
        supportFragmentManager.inTransaction {
            setCustomAnimations ( R.anim.entre_from_right,R.anim.exit_to_left,R.anim.entre_from_left,R.anim.exit_to_right )
            .addToBackStack("save_modules")
            .add(R.id.save_info_container, fragment2)
        }
    }
    interface Fragment2CallbackkFromActivity {
        fun getUniversity(data : String)
    }

}