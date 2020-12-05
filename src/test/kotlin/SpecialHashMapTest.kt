import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

internal class SpecialHashMapTest {


    @ParameterizedTest
    @MethodSource("getIlocValues")
    fun getIloc(index: Int, expected: Int) {
        val map = SpecialHashMap<String, Int>()
        map["value1"] = 1
        map["value2"] = 2
        map["value3"] = 3
        map["1"] = 10
        map["2"] = 20
        map["3"] = 30
        map["1, 5"] = 100
        map["5, 5"] = 200
        map["10, 5"] = 300
        assertEquals(expected, map.iloc[index])
    }

    @ParameterizedTest
    @MethodSource("getPlocValues")
    fun getPloc(key: String, expected: Map<String,Int>) {
        val map2 = SpecialHashMap<String, Int>()
        map2["value1"] = 1
        map2["value2"] = 2
        map2["value3"] = 3
        map2["1"] = 10
        map2["2"] = 20
        map2["3"] = 30
        map2["(1, 5)"] = 100
        map2["(5, 5)"] = 200
        map2["(10, 5)"] = 300
        map2["(1, 5, 3)"] = 400
        map2["(5, 5, 4)"] = 500
        map2["(5, 5, 2)"] = 700
        map2["(10, 5, 5)"] = 600
        assertEquals(expected, map2.ploc[key])
    }

    companion object {
        @JvmStatic
        fun getIlocValues() = listOf(
                Arguments.of(0, 10),
                Arguments.of(2, 300),
                Arguments.of(5, 200),
                Arguments.of(8, 3)
        )
        @JvmStatic
        fun getPlocValues() = listOf(
                Arguments.of(">=1", mapOf("1" to 10, "2" to 20, "3" to 30)),
                Arguments.of("<3", mapOf("1" to 10, "2" to 20)),
                Arguments.of(">0,>0", mapOf("(1, 5)" to 100, "(5, 5)" to 200, "(10, 5)" to 300)),
                Arguments.of(">=10,  >0", mapOf("(10, 5)" to 300)),
                Arguments.of("<5, >=5,  >=3", mapOf("(1, 5, 3)" to 400)),
                //Key sorting check, (5, 5, 4)=500 comes after (5, 5, 2)=700
                Arguments.of("<=5, >=2,  >=2", mapOf("(1, 5, 3)" to 400, "(5, 5, 2)" to 700, "(5, 5, 4)" to 500))
        )
    }
}