package es.afmsoft.moviesapp.ui

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import androidx.test.rule.GrantPermissionRule
import es.afmsoft.moviesapp.MockWebServerRule
import es.afmsoft.moviesapp.MoviesApp
import es.afmsoft.moviesapp.OkHttpIdlingResourceRule
import es.afmsoft.moviesapp.R
import es.afmsoft.moviesapp.data.server.MovieServer
import es.afmsoft.moviesapp.ui.main.MainActivity
import java.io.BufferedReader
import java.io.InputStreamReader
import okhttp3.mockwebserver.MockResponse

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.get
import com.google.android.material.appbar.CollapsingToolbarLayout
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.CoreMatchers.`is` as Is

//@RunWith(AndroidJUnit4.class)
class UiTest : KoinTest {

    @get:Rule
    val mockWebServerRule = MockWebServerRule()

    @get:Rule
    val okHttpIdlingResourceRule = OkHttpIdlingResourceRule(get<MovieServer>().okHttpClient)

    @get:Rule
    val activityTestRule = ActivityTestRule(MainActivity::class.java, false, false)

    @get:Rule
    val grantPermissionRule: GrantPermissionRule =
        GrantPermissionRule.grant("android.permission.ACCESS_COARSE_LOCATION")

    @Before
    fun setUp() {
        mockWebServerRule.server.enqueue(

            //MockResponse().setResponseCode(200).setBody("{\"page\":1,\"total_results\":14298,\"total_pages\":715,\"results\":[{\"vote_count\":395,\"id\":429203,\"video\":false,\"vote_average\":6.4,\"title\":\"The Old Man & the Gun\",\"popularity\":321.687,\"poster_path\":\"\\/ok0Zt1kl82GrGihF9LSlHMXZup.jpg\",\"original_language\":\"en\",\"original_title\":\"The Old Man & the Gun\",\"genre_ids\":[35,80,18],\"backdrop_path\":\"\\/8bRIfPGDnmWgdy65LO8xtdcFmFP.jpg\",\"adult\":false,\"overview\":\"Narra una historia real, la de Forrest Tucker, un ladrón de bancos que pasó la mayor parte de su vida en la cárcel o intentando escapar de ella. De hecho, logró fugarse en 18 ocasiones y cometió su último atraco en el año 2000 cuando tenía 80 años.\",\"release_date\":\"2018-09-27\"},{\"vote_count\":703,\"id\":384018,\"video\":false,\"vote_average\":6.5,\"title\":\"Fast & Furious: Hobbs & Shaw\",\"popularity\":287.96,\"poster_path\":\"\\/dc8vs0BEooy8jjspR1pOGeZEBSE.jpg\",\"original_language\":\"en\",\"original_title\":\"Fast & Furious Presents: Hobbs & Shaw\",\"genre_ids\":[28],\"backdrop_path\":\"\\/hpgda6P9GutvdkDX5MUJ92QG9aj.jpg\",\"adult\":false,\"overview\":\"Luke Hobbs (Dwayne Johnson) y Deckard Shaw (Jason Statham) vuelven a la carga para vivir una nueva aventura en la que ellos dos serán los protagonistas absolutos. Su rivalidad y la gran química entre estos dos personajes del universo Fast & Furious serán la clave pelisonline.co de esta nueva historia, en la que deberán trabajar juntos si quieren pararle los pies al villano Brixton (Idris Elba). Además, a la pareja de protagonistas se les unirá un nuevo personaje, Hattie (Vanessa Kirby), la hermana de Shaw.\",\"release_date\":\"2019-08-01\"},{\"vote_count\":1884,\"id\":420818,\"video\":false,\"vote_average\":7.1,\"title\":\"El Rey León\",\"popularity\":237.168,\"poster_path\":\"\\/3A8ca8WOBacCRujSKJ2tCVKsieQ.jpg\",\"original_language\":\"en\",\"original_title\":\"The Lion King\",\"genre_ids\":[12,16,10751,18,28],\"backdrop_path\":\"\\/1TUg5pO1VZ4B0Q1amk3OlXvlpXV.jpg\",\"adult\":false,\"overview\":\"Un remake del clásico animado de Disney de 1994 'El rey león' que estará dirigido por Jon Favreu. Simba (Donald Glover) es el hijo del rey de los leones, Mufasa, y heredero de todo el reino. Pero cuando su padre es brutalmente asesinado por su tío Scar, decidirá huir, dejando vía libre para que su tío tome el puesto de su padre como líder pelisonline.co de la manada. En su camino, Simba se encuentra con el suricato pelisonline.co Timón y el jabalí Pumba, que le enseñarán a vivir la vida sin preocupaciones. Pero el joven león se verá obligado a decidir entre su vida libre de problemas o su destino como rey .\",\"release_date\":\"2019-07-12\"},{\"vote_count\":1055,\"id\":373571,\"video\":false,\"vote_average\":6.2,\"title\":\"Godzilla: rey de los monstruos\",\"popularity\":176.33,\"poster_path\":\"\\/yQ59NPwzHE2XlYwU2VwHF9Wb0IJ.jpg\",\"original_language\":\"en\",\"original_title\":\"Godzilla: King of the Monsters\",\"genre_ids\":[878,28],\"backdrop_path\":\"\\/cNt14e43I2DDW6Xd9zFhrP8eOcA.jpg\",\"adult\":false,\"overview\":\"Los criptozoólogos de la agencia Monarch se enfrentan a un grupo de enormes monstruos: Godzilla, Mothra, Rodan y el enemigo de la humanidad, King Ghidorah. Estas ancianas criaturas harán todo lo posible por sobrevivir, poniendo en riesgo la existencia del ser humano en el planeta.\",\"release_date\":\"2019-05-29\"},{\"vote_count\":1946,\"id\":458156,\"video\":false,\"vote_average\":7.1,\"title\":\"John Wick: Capítulo 3 - Parabellum\",\"popularity\":155.583,\"poster_path\":\"\\/aKw7oqYdfn4pkKYvp18Gd1YhK6m.jpg\",\"original_language\":\"en\",\"original_title\":\"John Wick: Chapter 3 – Parabellum\",\"genre_ids\":[80,28,53],\"backdrop_path\":\"\\/stemLQMLDrlpfIlZ5OjllOPT8QX.jpg\",\"adult\":false,\"overview\":\"John Wick (Keanu Reeves) regresa a la acción, solo que esta vez con una recompensa de 14 millones de dólares sobre su cabeza y con un ejército de mercenarios intentando darle caza. Tras asesinar a uno de los miembros del gremio de asesinos al que pertenecía, Wick es expulsado de la organización, pasando a convertirse en el centro de atención de multitud de asesinos a sueldo que esperan detrás de cada esquina para tratar de deshacerse de él.\",\"release_date\":\"2019-05-15\"},{\"vote_count\":8819,\"id\":299534,\"video\":false,\"vote_average\":8.4,\"title\":\"Vengadores: Endgame\",\"popularity\":155.207,\"poster_path\":\"\\/br6krBFpaYmCSglLBWRuhui7tPc.jpg\",\"original_language\":\"en\",\"original_title\":\"Avengers: Endgame\",\"genre_ids\":[12,878,28],\"backdrop_path\":\"\\/7RyHsO4yDXtBv1zUU3mTpHeQ0d5.jpg\",\"adult\":false,\"overview\":\"Después de los eventos devastadores de 'Vengadores: Infinity War', el universo está en ruinas debido a las acciones de Thanos. Con la ayuda de los aliados que quedaron, los Vengadores deberán reunirse una vez más para intentar deshacer sus acciones y restaurar el orden en el universo de una vez por todas, sin importar cuáles sean las consecuencias... Cuarta y última entrega de la saga \\\"Vengadores\\\".\",\"release_date\":\"2019-04-24\"},{\"vote_count\":42,\"id\":521777,\"video\":false,\"vote_average\":7.1,\"title\":\"Chicos buenos\",\"popularity\":152.206,\"poster_path\":\"\\/xRc750hjqFVNJEYvoHTfH26nSwo.jpg\",\"original_language\":\"en\",\"original_title\":\"Good Boys\",\"genre_ids\":[35],\"backdrop_path\":\"\\/6Xsz9KHQmCcIcj3CoWQq5eLtVoT.jpg\",\"adult\":false,\"overview\":\"Después de ser invitados a su primera \\\"fiesta del beso\\\", tres buenos amigos destrozan por casualidad un dron que tenían prohibido tocar. Para reemplazarlo, se ausentan de clase y toman una serie de decisiones erróneas, involucrándose en un caso relacionado con droga, policía y, sobre todo, con muchas lágrimas.\",\"release_date\":\"2019-07-18\"},{\"vote_count\":909,\"id\":504608,\"video\":false,\"vote_average\":7.5,\"title\":\"Rocketman\",\"popularity\":129.079,\"poster_path\":\"\\/lvt3qfH3QlzydjmGNMGQ2tx2O7C.jpg\",\"original_language\":\"en\",\"original_title\":\"Rocketman\",\"genre_ids\":[10402,18],\"backdrop_path\":\"\\/oAr5bgf49vxga9etWoQpAeRMvhp.jpg\",\"adult\":false,\"overview\":\"'Rocketman' es la historia de Elton John, desde sus años como niño prodigio del piano en la Royal Academy of Music hasta llegar a ser una superestrella de fama mundial gracias a su influyente y duradera asociación con su colaborador y letrista Bernie Taupin.\",\"release_date\":\"2019-05-22\"},{\"vote_count\":43,\"id\":566555,\"video\":false,\"vote_average\":5.1,\"title\":\"Detective Conan: El Puño de Zafiro Azul\",\"popularity\":127.502,\"poster_path\":\"\\/86Y6qM8zTn3PFVfCm9J98Ph7JEB.jpg\",\"original_language\":\"ja\",\"original_title\":\"名探偵コナン 紺青の拳（フィスト）\",\"genre_ids\":[16,28,18,9648,35],\"backdrop_path\":\"\\/wf6VDSi4aFCZfuD8sX8JAKLfJ5m.jpg\",\"adult\":false,\"overview\":\"Conan Edogawa viaja a Singapur, concretamente a la famosa Marina Bay Sands, escenario en el que se ha producido un cruento asesinato. La investigación de Conan parece girar en torno a una gema legendaria conocida como \\\"el Zafiro Azul\\\", que lleva sumergido en el océano desde finales del siglo XIX. Makoto Kyôgoku, un maestro de karate, ayudará al joven detective a detener a Kaitō Kid, quien quiere robar la tan preciada joya.\",\"release_date\":\"2019-04-12\"},{\"vote_count\":7901,\"id\":920,\"video\":false,\"vote_average\":6.7,\"title\":\"Cars\",\"popularity\":125.089,\"poster_path\":\"\\/u4G8EkiIBZYx0wEg2xDlXZigTOZ.jpg\",\"original_language\":\"en\",\"original_title\":\"Cars\",\"genre_ids\":[16,12,35,10751],\"backdrop_path\":\"\\/a1MlbLBk5Sy6YvMbSuKfwGlDVlb.jpg\",\"adult\":false,\"overview\":\"El aspirante a campeón de carreras Rayo McQueen está sobre la vía rápida al éxito, la fama y todo lo que él había soñado, hasta que por error toma un desvío inesperado en la polvorienta y solitaria Ruta 66. Su actitud arrogante se desvanece cuando llega a una pequeña comunidad olvidada que le enseña las cosas importantes de la vida que había olvidado.\",\"release_date\":\"2006-06-08\"},{\"vote_count\":2756,\"id\":420817,\"video\":false,\"vote_average\":7.1,\"title\":\"Aladdín\",\"popularity\":115.612,\"poster_path\":\"\\/trnyoKkkvvjZvRvCMrNDtSf25nH.jpg\",\"original_language\":\"en\",\"original_title\":\"Aladdin\",\"genre_ids\":[12,14,10749,35,10751],\"backdrop_path\":\"\\/v4yVTbbl8dE1UP2dWu5CLyaXOku.jpg\",\"adult\":false,\"overview\":\"Aladdin es un adorable pero desafortunado ladronzuelo enamorado de la hija del Sultán, la princesa Jasmine. Para intentar conquistarla, acepta el desafío de Jafar, que consiste en entrar a una cueva en mitad del desierto para dar con una lámpara mágica que le concederá todos sus deseos. Allí es donde Aladdín conocerá al Genio, dando inicio a una aventura como nunca antes había imaginado.\",\"release_date\":\"2019-05-22\"},{\"vote_count\":406,\"id\":412117,\"video\":false,\"vote_average\":6.6,\"title\":\"Mascotas 2\",\"popularity\":112.567,\"poster_path\":\"\\/9uwvUzb6SxTdci5tmDL1jEvkiff.jpg\",\"original_language\":\"en\",\"original_title\":\"The Secret Life of Pets 2\",\"genre_ids\":[12,16,35,10751],\"backdrop_path\":\"\\/etaok7q2E5tV36oXe7GQzhUQ4fX.jpg\",\"adult\":false,\"overview\":\"Secuela de la película de animación Mascotas, que contaba la historia de Max, un perro que adora a su dueña Katie y la vida que llevan juntos, pero que tiene que aprender a lidiar con la nueva mascota de la casa, un mestizo torpe y descuidado llamado Duke.  Dirigida por Chris Renaud (Gru. Mi villano favorito) y escrita por Brian Lynch (El gato con botas, Los minions) y Ken Daurio (Gru, mi villano favorito, Mascotas), esta cinta de animación narrará de nuevo qué hacen exactamente nuestros animales de compañía cuando salimos por la puerta de casa.\",\"release_date\":\"2019-05-24\"},{\"vote_count\":446,\"id\":466272,\"video\":false,\"vote_average\":7.6,\"title\":\"Érase una vez en Hollywood\",\"popularity\":106.584,\"poster_path\":\"\\/sv5Mfg3gnyrnCWq8oEW47FFCC4K.jpg\",\"original_language\":\"en\",\"original_title\":\"Once Upon a Time in Hollywood\",\"genre_ids\":[18,35,28,80,37],\"backdrop_path\":\"\\/b82nVVhYNRgtsTFV1jkdDwe6LJZ.jpg\",\"adult\":false,\"overview\":\"La película se centra en el panorama cambiante de Hollywood a finales de los años 60, cuando la industria empezaba a olvidarse de los pilares clásicos. La estrella de un western televisivo, Rick Dalton (DiCaprio), intenta amoldarse a pelisonline.co estos cambios al mismo tiempo que su doble (Pitt). Sin embargo, la vida de Dalton parece que está ligada a sus raíces de Hollywood, puesto que es vecino de la actriz y modelo Sharon Tate (Robbie), que acaba siendo víctima de la familia Manson en la matanza de agosto de 1969.\",\"release_date\":\"2019-07-25\"},{\"vote_count\":3077,\"id\":429617,\"video\":false,\"vote_average\":7.8,\"title\":\"Spider-Man: Lejos de Casa\",\"popularity\":101.573,\"poster_path\":\"\\/iKVR1ba3W1wCm9bVCcpnNvxQUWX.jpg\",\"original_language\":\"en\",\"original_title\":\"Spider-Man: Far from Home\",\"genre_ids\":[28,12,878],\"backdrop_path\":\"\\/dihW2yTsvQlust7mSuAqJDtqW7k.jpg\",\"adult\":false,\"overview\":\"Peter Parker decide irse junto a Michelle Jones, Ned y el resto de sus amigos a pasar unas vacaciones a Europa después de los eventos ocurridos en Vengadores: EndGame. Sin embargo, el plan de Parker por dejar de lado sus superpoderes durante unas semanas se ven truncados cuándo es reclutado por Nick Fury para unirse a Mysterio (un humano que proviene de la Tierra 833, una dimensión del Multiverso, que tuvo su primera aparición en Doctor Strange) para luchar contra los Elementales (cuatro entes inmortales que vienen de la misma dimensión y que dominan los cuatro elementos de la Naturaleza, el fuego, el agua, el aire y la tierra) . En ese momento, Parker vuelve a ponerse el traje de Spider-Man para cumplir con  su labor.\",\"release_date\":\"2019-06-28\"},{\"vote_count\":60,\"id\":454640,\"video\":false,\"vote_average\":5.6,\"title\":\"Angry Birds 2: La película\",\"popularity\":93.027,\"poster_path\":\"\\/stYLlUBaub28HN8SzJRyC1t2vXU.jpg\",\"original_language\":\"en\",\"original_title\":\"The Angry Birds Movie 2\",\"genre_ids\":[16,35,28,12,10751],\"backdrop_path\":\"\\/k7sE3loFwuU2mqf7FbZBeE3rjBa.jpg\",\"adult\":false,\"overview\":\"Vuelven a la carga Red, el pájaro de color rojo con problemas de mal genio, y sus amigos Chuck, el pájaro amarillo hiperactivo, y Bomb, el pájaro negro muy volátil. En esta segunda parte, los pájaros protagonistas y los intrigantes cerdos de color verde llevarán su conflicto a un nuevo nivel. Y es que, aparecerá una nueva y malvada villana: Zeta, un pájaro que vive en una isla helada. Cuando Zeta lance una bola de hielo sobre la isla en la que se encuentran Red y compañía, nuestros protagonistas tendrán que hacer frente a esta nueva amenaza.\",\"release_date\":\"2019-08-02\"},{\"vote_count\":608,\"id\":531309,\"video\":false,\"vote_average\":5.8,\"title\":\"El hijo\",\"popularity\":84.742,\"poster_path\":\"\\/bQmRemkGS3SMAexgZND6ey02lYW.jpg\",\"original_language\":\"en\",\"original_title\":\"Brightburn\",\"genre_ids\":[27,878,53],\"backdrop_path\":\"\\/uHEI6v8ApQusjbaRZ8o7WcLYeWb.jpg\",\"adult\":false,\"overview\":\"Todo lo que los Breyer han querido siempre es algo teóricamente simple: un hijo. A pesar de sus intentos, ese deseo no parece hacerse realidad. Hasta que, una noche, algo cae en la granja en la que viven: una nave que lleva a un niño dentro. Conforme este va creciendo, descubre que tiene unas habilidades especiales que no le permiten encajar, unas habilidades que acaban sacando de él lo peor de sí mismo.\",\"release_date\":\"2019-05-09\"},{\"vote_count\":440,\"id\":449562,\"video\":false,\"vote_average\":6.1,\"title\":\"Timadoras compulsivas\",\"popularity\":83.875,\"poster_path\":\"\\/tej9i6MHgpyrNPodgYLDMkwAjek.jpg\",\"original_language\":\"en\",\"original_title\":\"The Hustle\",\"genre_ids\":[35],\"backdrop_path\":\"\\/s6awXOxTKYQLSktiIJfI3969dZH.jpg\",\"adult\":false,\"overview\":\"Dos artistas del engaño, una de clase alta y la otra de los barrios bajos, deciden unirse para timar a los hombres... Remake de la comedia de 1988 'Dirty Rotten Scoundrels'.\",\"release_date\":\"2019-05-09\"},{\"vote_count\":51,\"id\":499701,\"video\":false,\"vote_average\":5.9,\"title\":\"Dora y la ciudad perdida\",\"popularity\":80.939,\"poster_path\":\"\\/jVKN22n5xS9wc4zaW6KRfARgaau.jpg\",\"original_language\":\"en\",\"original_title\":\"Dora and the Lost City of Gold\",\"genre_ids\":[12,35,10751],\"backdrop_path\":\"\\/843PwG97xLcz7TUW8tKDNrOc2sj.jpg\",\"adult\":false,\"overview\":\"Tras haber pasado la mayor parte de su vida explorando la jungla junto a sus padres, nada podría haber preparado a Dora para la aventura más peligrosa a la que jamás se ha enfrentado: ¡el instituto!. Exploradora hasta el final, Dora no tarda en ponerse al frente de un equipo formado por Botas (su mejor amigo, un mono), Diego, un misterioso habitante de la jungla y un desorganizado grupo de adolescentes en una aventura en la que deberán salvar a sus padres y resolver el misterio oculto tras una ciudad perdida de oro.\",\"release_date\":\"2019-08-08\"},{\"vote_count\":14859,\"id\":299536,\"video\":false,\"vote_average\":8.3,\"title\":\"Vengadores: Infinity War\",\"popularity\":78.877,\"poster_path\":\"\\/q6Q81fP4qPvfQTH2Anlgy12jzO2.jpg\",\"original_language\":\"en\",\"original_title\":\"Avengers: Infinity War\",\"genre_ids\":[12,28,878],\"backdrop_path\":\"\\/bOGkgRGdhrBYJSLpXaxhXVstddV.jpg\",\"adult\":false,\"overview\":\"El todopoderoso Thanos ha despertado con la promesa de arrasar con todo a su paso, portando el Guantelete del Infinito, que le confiere un poder incalculable. Los únicos capaces de pararle los pies son los Vengadores y el resto de superhéroes de la galaxia, que deberán estar dispuestos a sacrificarlo todo por un bien mayor. Capitán América e Ironman deberán limar sus diferencias, Black Panther apoyará con sus tropas desde Wakanda, Thor y los Guardianes de la Galaxia e incluso Spider-Man se unirán antes de que los planes de devastación y ruina pongan fin al universo. ¿Serán capaces de frenar el avance del titán del caos?\",\"release_date\":\"2018-04-25\"},{\"vote_count\":6888,\"id\":299537,\"video\":false,\"vote_average\":7,\"title\":\"Capitana Marvel\",\"popularity\":77.683,\"poster_path\":\"\\/d3p5JuwN7dG0TvrN5h4ZY4tMOEX.jpg\",\"original_language\":\"en\",\"original_title\":\"Captain Marvel\",\"genre_ids\":[28,12,878],\"backdrop_path\":\"\\/w2PMyoyLU22YvrGK3smVM9fW1jj.jpg\",\"adult\":false,\"overview\":\"La historia sigue a Carol Danvers mientras se convierte en uno de los héroes más poderosos del universo, cuando la Tierra se encuentra atrapada en medio de una guerra galáctica entre dos razas alienígenas. Situada en los años 90, 'Capitana Marvel' es una historia nueva de un período de tiempo nunca antes visto en la historia del Universo Cinematográfico de Marvel.\",\"release_date\":\"2019-03-06\"}]}")
            MockResponse().setResponseCode(200).setBody(readJsonFile("popularmovies.json"))


            //MockResponse().fromJson(ApplicationProvider.getApplicationContext(), "popularmovies.json")
        )
    }

    fun readJsonFile(file: String): String {
        val bufferedReader = BufferedReader(InputStreamReader(getApplicationContext<MoviesApp>().assets.open(file)))
        val sb = StringBuilder()
        var line: String? = null

        do {
            line = bufferedReader.readLine()
            line?.apply { sb.append(this) }
        } while (line != null)

        return sb.toString()
    }

    @Test
    fun clickMovieNavigatesToDetail() {
        activityTestRule.launchActivity(null)

        onView(withId(es.afmsoft.moviesapp.R.id.moviesGrid)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                3,
                click()
            )
        )

        /*
        onView(withId(R.id.movieDetailToolbar))
            .check(matches(hasDescendant(withText("Godzilla"))))
            */
        //onView(withId(es.afmsoft.moviesapp.R.id.movieDetailCollapsing))
        //    .check(matches(hasDescendant(withText ("test"))))
        onView(withId(es.afmsoft.moviesapp.R.id.movieDetailCollapsing)).check(matches(withCollapsibleToolbarTitle(Is("Godzilla: rey de los monstruos"))))
    }

    fun withCollapsibleToolbarTitle(textMatcher: Matcher<String>): Matcher<Any> {
        return object : BoundedMatcher<Any, CollapsingToolbarLayout>(CollapsingToolbarLayout::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("with toolbar title: ")
                textMatcher.describeTo(description)
            }

            override fun matchesSafely(toolbarLayout: CollapsingToolbarLayout): Boolean {
                return textMatcher.matches(toolbarLayout.title)
            }
        }
    }
}
