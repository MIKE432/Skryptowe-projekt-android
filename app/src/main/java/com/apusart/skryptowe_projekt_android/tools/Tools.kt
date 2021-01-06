package com.apusart.skryptowe_projekt_android.tools

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.loader.content.CursorLoader
import com.apusart.skryptowe_projekt_android.api.models.TrainingType

object Defaults {
    const val baseUrl = "http://10.0.2.2:8000/"
    const val imagesUrl = "http://10.0.2.2:8000"
}

enum class TrainingTypes(val trainingType: TrainingType) {
    AEROBIC(
        TrainingType(
            "AEROBIC",
            "Your breathing and heart rate will increase during aerobic activities. Aerobic exercise helps keep your heart, lungs, and circulatory system healthy."
        )
    ),
    BALANCE(
        TrainingType(
            "BALANCE",
            "Doing balance exercises can be intense, like some very challenging yoga poses. Others are as simple as standing on one leg for a few seconds. Or you can use equipment that forces your body to stabilize itself, like a Bosu half-circle stability ball or a balance board you use along with a video game."
        )
    ),
    CARDIO(
        TrainingType(
            "CARDIO",
            "Cardio,\" which is fitness slang for cardiovascular activity, may be one of the most important types of physical activity to do on the regular."
        )
    ),
    CIRCUIT(
        TrainingType(
            "CIRCUIT",
            "Circuit training is a form of body conditioning that involves endurance training, resistance training, high-intensity aerobics, and exercises performed in a circuit, similar to High-intensity interval training. It targets strength building and muscular endurance. An exercise \"circuit\" is one completion of all set exercises in the program"
        )
    ),
    FUNCTIONAL(
        TrainingType(
            "FUNCTIONAL",
            "Functional fitness means training your body for everyday activities, and to make daily motion easier and safer."
        )
    ),
    HIIT(
        TrainingType(
            "HIIT",
            "HIIT is a training technique in which you give all-out, one hundred percent effort through quick, intense bursts of exercise, followed by short, sometimes active, recovery periods. This type of training gets and keeps your heart rate up and burns more fat in less time."
        )
    ),
    INTERVAL(
        TrainingType(
            "INTERVAL",
            "Interval training is a type of training that involves a series of high intensity workouts interspersed with rest or relief periods. The high-intensity periods are typically at or close to anaerobic exercise, while the recovery periods involve activity of lower intensity."
        )
    ),
    RECOVERY(
        TrainingType(
            "RECOVERY",
            "Rest and recovery are a crucial parts of any exercise program. After putting your body through a significant amount of stress during a grueling workout, you have to give it time to recover, repair, and ultimately, come back stronger."
        )
    ),
    STRENGTH(
        TrainingType(
            "STRENGTH",
            "Strength exercise, or resistance training, works your muscles by using resistance, like a dumbbell or your own body weight. This type of exercise increases lean muscle mass, which is particularly important for weight loss, because lean muscle burns more calories than other types of tissue."
        )
    ),
    STRETCHING(
        TrainingType(
            "STRETCHING",
            "Stretching is a form of physical exercise in which a specific muscle or tendon (or muscle group) is deliberately flexed or stretched in order to improve the muscle's felt elasticity and achieve comfortable muscle tone. The result is a feeling of increased muscle control, flexibility, and range of motion."
        )
    ),
    SUPERSET(
        TrainingType(
            "SUPERSET",
            "A superset is a form of strength training in which you move quickly from one exercise to a separate exercise without taking a break for rest in between the two exercises. Typically, you will take a brief break to catch your breath or grab a drink of water between sets of an exercise."
        )
    ),
    TABATA(
        TrainingType(
            "TABATA",
            "This workout is a form of high intensity interval training designed to get your heart rate up in that very hard anaerobic zone for short periods of time. By doing this, you train all of your energy systems, something that regular cardio workouts usually don't do."
        )
    ),
    OTHER(
        TrainingType(
            "OTHER",
            "Can`t find definition of training you want to create? Don`t worry, click here to create unusual training"
        )
    )
}

fun getSelectedTrainingType(type: String?): Int {
    if (type == null)
        return 0

    val listOfTypes = TrainingTypes.values().map { it.trainingType.type }
    val index = listOfTypes.indexOf(type)
    return if (index == -1) 0 else index + 1
}

object Tools {

    fun hideKeyboard(fragment: Fragment?) {
        val view = fragment?.requireActivity()?.currentFocus
        view?.let { v ->
            val imm = fragment.requireActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(v.windowToken, 0)
        }
    }

    fun hideKeyboard(activity: Activity?) {
        val view = activity?.currentFocus
        view?.let { v ->
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(v.windowToken, 0)
        }
    }


}

object Codes {
    const val GET_PHOTO_CODE = 1
}

fun getPathFromUri(uri: Uri, applicationContext: Context): String {

    val projection = Array(1) {
        MediaStore.Images.Media.DATA
    }
    val loader = CursorLoader(applicationContext, uri, projection, null, null, null)
    val cursor = loader.loadInBackground()
    val columnIdx = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
    cursor?.moveToFirst()
    val result = columnIdx?.let { cursor.getString(it) }
    cursor?.close()
    return result!!
}
