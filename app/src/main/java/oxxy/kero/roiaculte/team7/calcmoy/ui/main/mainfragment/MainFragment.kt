package oxxy.kero.roiaculte.team7.calcmoy.ui.main.mainfragment

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import oxxy.kero.roiaculte.team7.calcmoy.R
import oxxy.kero.roiaculte.team7.calcmoy.base.BaseFragment
import oxxy.kero.roiaculte.team7.calcmoy.databinding.MainFragmentMainBinding
import oxxy.kero.roiaculte.team7.calcmoy.utils.Async
import oxxy.kero.roiaculte.team7.calcmoy.utils.Fail
import oxxy.kero.roiaculte.team7.calcmoy.utils.Loading
import oxxy.kero.roiaculte.team7.calcmoy.utils.Success
import oxxy.kero.roiaculte.team7.calcmoy.utils.extension.invisible
import oxxy.kero.roiaculte.team7.calcmoy.utils.extension.visible
import oxxy.kero.roiaculte.team7.domain.interactors.None
import oxxy.kero.roiaculte.team7.domain.models.Event
import oxxy.kero.roiaculte.team7.domain.models.Matter
import oxxy.kero.roiaculte.team7.domain.models.Semestre

private const val LOG_TAG="MAIN_FRAGMENT"
class MainFragment: BaseFragment() {
    companion object { fun getInstance()= MainFragment() }

    private val viewModel : MainViewModel by lazy { ViewModelProviders.of(this,viewModelFactory)[MainViewModel::class.java] }
    private val callbacck  : CallbackFromViewModel by lazy { viewModel}
    private val semestrAdapter : MatterAdapetr = MatterAdapetr()
    private val eventAdapter : EventeAdapter = EventeAdapter()

    private lateinit var binding :MainFragmentMainBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding= DataBindingUtil.inflate(inflater  ,R.layout.main_fragment_main, container, false)

        setUpRecyclers()
        if(callbacck.isItFirstTime()){ callbacck.getMainInfo() }

        viewModel.observe(this){
            handleMatterState(it?.matterState)
            handleEventsState(it?.evensAsync)
            handleSemestres(it?.semestres)
            handleEvents(it?.events)
        }

        return binding.root
    }

    private fun setUpRecyclers() {
        binding.mainNoterecycler.adapter = semestrAdapter
        binding.mainEventrecycler.adapter = eventAdapter

        binding.mainNoterecycler.layoutManager = LinearLayoutManager(context)
        binding.mainEventrecycler.layoutManager= LinearLayoutManager(context)

        binding.mainEventrecycler.setHasFixedSize(true)
        binding.mainNoterecycler.setHasFixedSize(true)
    }

    private fun handleEvents(events: List<Event>?) {

    }

    private fun handleSemestres(semestres: List<Semestre>?) {
        val curent = callbacck.getCurentSemestre()
        val matters = semestres!![curent].matters
        matters.sortBy { it.coifficient }
        val sendedList = ArrayList<Matter>()
        val  size = if(matters.size>5) 5 else matters.size
        for(i in 0..size ){
            sendedList.add(matters[i])
        }
        semestrAdapter.replaceAll(sendedList)
    }

    private fun handleEventsState(evensAsync: Async<None>?) {
        when(evensAsync){
            is Loading ->{
                binding.loadingEvents.visible()
                binding.mainEventrecycler.invisible()
            }

            is Success ->{
                binding.loadingEvents.invisible()
                binding.mainEventrecycler.visible()
            }

            is Fail<*,*> -> {
                //TODO show there is no matters
            }
        }
    }

    private fun handleMatterState(matterState: Async<None>?) {
        when(matterState){
            is Loading ->{
                binding.loadingMatter.visible()
                binding.mainNoterecycler.invisible()
            }

            is Success ->{
                binding.loadingMatter.invisible()
                binding.mainNoterecycler.visible()
            }

            is Fail<*,*> -> {
                //TODO show there is no matters
            }
        }
    }

    interface CallbackFromViewModel{
        fun getMainInfo()
        fun isItFirstTime() :  Boolean
        fun getCurentSemestre() : Int
        fun setCurentSemetre(curent : Int)
    }
}