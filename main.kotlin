import java.io.File

data class Cargo(
    val id: Int,
    var name: String,
    var w: Double,
    var p: Double,
    var orig: String,
    var dest: String
)

val list = mutableListOf<Cargo>()
var nextId = 1

fun main() {
    var run = true
    while (run) {
        println("\nСистема учета грузовых перевозок")
        println("1. Добавить запись")
        println("2. Отобразить все записи")
        println("3. Найти запись")
        println("4. Редактировать запись")
        println("5. Удалить запись")
        println("6. Фильтрация и сортировка")
        println("7. Агрегированные показатели")
        println("8. Сохранить в CSV")
        println("9. Загрузить из CSV")
        println("0. Выход")
        print("Выбор: ")
        
        when (readlnOrNull()?.toIntOrNull()) {
            1 -> add()
            2 -> showAll()
            3 -> find()
            4 -> edit()
            5 -> del()
            6 -> filterSort()
            7 -> stats()
            8 -> save()
            9 -> load()
            0 -> run = false
            else -> println("Ошибка ввода.")
        }
    }
}

fun add() {
    print("Название: ")
    val n = readlnOrNull() ?: ""
    print("Вес: ")
    val w = readlnOrNull()?.toDoubleOrNull() ?: 0.0
    print("Цена: ")
    val p = readlnOrNull()?.toDoubleOrNull() ?: 0.0
    print("Откуда: ")
    val o = readlnOrNull() ?: ""
    print("Куда: ")
    val d = readlnOrNull() ?: ""
    
    list.add(Cargo(nextId++, n, w, p, o, d))
    println("Добавлено.")
}

fun showAll() {
    if (list.isEmpty()) println("Пусто.")
    for (c in list) {
        println("[${c.id}] ${c.name} | Вес: ${c.w} | Цена: ${c.p} | Маршрут: ${c.orig} -> ${c.dest}")
    }
}

fun find() {
    print("Текст для поиска (откуда/куда): ")
    val q = readlnOrNull() ?: ""
    val res = list.filter { it.orig.contains(q, true) || it.dest.contains(q, true) }
    res.forEach { println("[${it.id}] ${it.name} | ${it.orig} -> ${it.dest}") }
}

fun edit() {
    print("ID: ")
    val id = readlnOrNull()?.toIntOrNull() ?: return
    val c = list.find { it.id == id }
    if (c != null) {
        print("Новая цена (было ${c.p}): ")
        val np = readlnOrNull()?.toDoubleOrNull()
        if (np != null) c.p = np
        println("Обновлено.")
    }
}

fun del() {
    print("ID для удаления: ")
    val id = readlnOrNull()?.toIntOrNull() ?: return
    list.removeIf { it.id == id }
    println("Удалено.")
}

fun filterSort() {
    print("Мин. вес: ")
    val minW = readlnOrNull()?.toDoubleOrNull() ?: 0.0
    val res = list.filter { it.w >= minW }.sortedBy { it.p }
    res.forEach { println("[${it.id}] ${it.name} | Вес: ${it.w} | Цена: ${it.p}") }
}

fun stats() {
    if (list.isEmpty()) return
    val avg = list.map { it.p }.average()
    val sum = list.sumOf { it.p }
    val max = list.maxOf { it.w }
    println("Ср. цена: $avg, Сумма цен: $sum, Макс. вес: $max")
}

fun save() {
    val f = File("data.csv")
    f.printWriter().use { out ->
        list.forEach { c ->
            out.println("${c.id},${c.name},${c.w},${c.p},${c.orig},${c.dest}")
        }
    }
    println("Сохранено.")
}

fun load() {
    val f = File("data.csv")
    if (!f.exists()) return
    list.clear()
    var maxId = 0
    f.forEachLine { line ->
        val p = line.split(",")
        if (p.size == 6) {
            val id = p[0].toIntOrNull() ?: 0
            list.add(Cargo(id, p[1], p[2].toDouble(), p[3].toDouble(), p[4], p[5]))
            if (id > maxId) maxId = id
        }
    }
    nextId = maxId + 1
    println("Загружено.")
}
