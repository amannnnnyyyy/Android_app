package com.example.myapplication1.workout.ui.workoutplan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication1.workout.models.DaysOfWeek
import com.example.myapplication1.workout.models.WorkoutPlan
import com.example.myapplication1.workout.ui.workoutplan.Utils.LIST_OF_DATE_INDEXES
import com.example.myapplication1.workout.utils.Resource
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WorkoutPlanViewModel: ViewModel() {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val reference = db.collection("workouts")

    private lateinit var listener: ListenerRegistration
    private val _workouts: MutableStateFlow<Resource<MutableList<WorkoutPlan>>> = MutableStateFlow(Resource.Loading())

    val workouts = _workouts.asStateFlow()


    init {
       viewModelScope.launch {
          listener =  db.collection("workouts").addSnapshotListener { snapshot, e ->
               if (e!=null){
                   _workouts.value = Resource.Error(message = e.message?:"Unknown error")
                   return@addSnapshotListener
               }

               if (snapshot!=null && !snapshot.isEmpty){

                   val workoutList = mutableListOf<WorkoutPlan>()

                   for (document in snapshot.documents)
                   {
                     //  val _data = snapshot.toObjects(WorkoutPlan::class.java)

                       val data = document.data

                       val date: DaysOfWeek = DaysOfWeek.entries.find { it.name == data?.get("date") }?: DaysOfWeek.UNKNOWN

                       workoutList.add(WorkoutPlan(LIST_OF_DATE_INDEXES.get(date)?:0,date,data?.get("workoutCategory").toString()))

                   }
                   workoutList.sortBy { it.id }
                   _workouts.value = Resource.Success(workoutList)

               }

               }


           }
       }



    fun savePlan(date: DaysOfWeek, workout:String){
        reference.document(date.toString()).set(WorkoutPlan(LIST_OF_DATE_INDEXES.get(date)?:0, date, workout))
            .addOnSuccessListener {
            }
            .addOnFailureListener {
            }
    }

    fun updatePlan(date: DaysOfWeek, workout:String){

        reference.document(date.toString()).delete()

        reference.document(date.toString()).set(WorkoutPlan(LIST_OF_DATE_INDEXES.get(date)?:0, date, workout))
            .addOnSuccessListener {
            }
            .addOnFailureListener {
            }
    }


    fun multipleUpdate(plans: List<WorkoutPlan>){
        for (plan in plans){
            reference.document(plan.date.toString()).set(plan)
        }
    }


    fun deletePlan(plan: WorkoutPlan){
        reference.document(plan.date.toString()).delete()
    }


    fun removeListener(){
        listener.remove()
    }

    }