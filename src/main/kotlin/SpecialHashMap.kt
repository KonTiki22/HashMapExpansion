class InvalidConditionException(message: String): Exception(message)
class InvalidKeyTypeException(message: String): Exception(message)

class SpecialHashMap<K,V>: HashMap<K,V>() {

    var iloc = ILoc()
    var ploc = PLoc()
    inner class ILoc {
        operator fun get(i: Int): V? {
            var kList = this@SpecialHashMap.keys.toList()
            if(kList[0] is String) {
                kList = kList.sortedBy { K -> K.toString() }
            }
            else if(kList[0] is Int || kList[0] is Double) {
                kList = kList.sortedBy { K -> K.toString().toDouble() }
            }
            return this@SpecialHashMap[kList[i]]
        }
    }
    inner class PLoc {
        operator fun get(cond: String): Map<K,V> {

            val condSplit = Regex("""[^<>=0-9]+""").split(cond)
            val condList = condSplit.map {
                it-> Regex("""([<>=]+)|([0-9]+)""").findAll(it).toList().map { it.value }
            }

            // Condition check, it must consist of two elements: a comparison operator and a number,
            // the comparison operator must contain one or two characters
            fun checkCond(): Boolean {
                for(cond in condList) {
                    if(cond.size != 2 || cond[0] !in listOf("<", ">", "<=", ">=", "<>", "=", "==")) {
                        return false
                    }
                }
                return true
            }

            fun splitKey(key: String): List<Int> {
                return Regex("""[0-9]+""").findAll(key).toList().map { it.value.toInt() }
            }

            //Key compliance with condition check function
            fun checkKey(key: String): Boolean {
                if(!Regex("""^\(?([0-9]+[^0-9]*)*[0-9]+\)?${'$'}""").matches(key)) return false
                val nums = splitKey(key)
                var res = true
                if(nums.size != condList.size) return false
                for(i in nums.indices) {
                    res = res && when(condList[i][0]) {
                        "<" -> nums[i] <  condList[i][1].toInt()
                        ">" -> nums[i] >  condList[i][1].toInt()
                        "<=" -> nums[i] <=  condList[i][1].toInt()
                        ">=" -> nums[i] >=  condList[i][1].toInt()
                        "=","==" -> nums[i] ==  condList[i][1].toInt()
                        "<>" -> nums[i] !=  condList[i][1].toInt()
                        else -> false
                    }
                }
                return res
            }

            val kList = this@SpecialHashMap.keys.toList()
            if(kList[0] !is String) {
                throw InvalidKeyTypeException("Invalid key type for this request.")
            }
            if(!checkCond()) {
                throw InvalidConditionException("Condition contains invalid operators or operands.")
            }
            // A condition for sorting keys by numbers,
            // in first the first numbers in each key are compared, if they are equal, the second are compared, etc.
            val comparatorList = Array(condList.size) {
                index:Int -> { it: K -> splitKey(it.toString())[index] }
            }

            return this@SpecialHashMap.filterKeys {checkKey(it.toString())}.toSortedMap(compareBy<K> (*comparatorList))
        }
    }
}