fun main() {

    println("======================== Test 1 ========================")
    println("Checking value selection by index:")

    var map = SpecialHashMap<String, Int>()
    map["value1"] = 1
    map["value2"] = 2
    map["value3"] = 3
    map["1"] = 10
    map["2"] = 20
    map["3"] = 30
    map["1, 5"] = 100
    map["5, 5"] = 200
    map["10, 5"] = 300
    println(map.iloc[0])  // >>> 10
    println(map.iloc[2])  // >>> 300
    println(map.iloc[5])  // >>> 200
    println(map.iloc[8])  // >>> 3

    println("======================== Test 2 ========================")
    println("Checking work with digital keys:")

    var hm2 = SpecialHashMap<Int, Int>()
    hm2[2] = 10
    hm2[6] = 20
    hm2[1] = 30
    hm2[15] = 40
    hm2[-8] = 50
    println(hm2.iloc[0])  // >>> 50
    println(hm2.iloc[1])  // >>> 30
    println(hm2.iloc[2])  // >>> 10
    println(hm2.iloc[3])  // >>> 20
    println(hm2.iloc[4])  // >>> 40

    println("======================== Test 3 ========================")
    println("Checking the selection of key:value pairs by condition:")

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
    map2["(10, 5, 5)"] = 600

    println(map2.ploc[">=1"]) // >>> {1=10, 2=20, 3=30}
    println(map2.ploc["<3"]) // >>> {1=10, 2=20}
    println(map2.ploc[">0,>0"]) // >>> {(1, 5)=100, (5, 5)=200, (10, 5)=300}
    println(map2.ploc[">=10,  >0"]) // >>> {(10, 5)=300}
    println(map2.ploc["<5, >=5,  >=3"]) // >>> {(1, 5, 3)=400}


}