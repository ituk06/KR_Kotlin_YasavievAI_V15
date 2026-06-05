package org.example

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
    var running = true
    while (running) {
        println("\n--- ГЛАВНОЕ МЕНЮ ---")
        println("1. Ввод/вывод данных")
        println("2. Отобразить записи")
        println("3. Найти запись")
        println("4. Редактировать")
        println("5. Удалить")
        println("6. Статистика")
        println("0. Выход")
        print("Ваш выбор: ")

        when (readlnOrNull()?.toIntOrNull()) {
            1 -> consoleMenuIO()
            2 -> consoleMenuOps()
            3 -> findCargo()
            4 -> editCargo()
            5 -> delCargo()
            6 -> stats()
            0 -> running = false
            else -> println("Неверный ввод")
        }
    }
}

fun consoleMenuIO() {
    println("\n1. Добавить | 2. Сохранить CSV | 3. Загрузить CSV | 4. Назад")
    when (readlnOrNull()?.toIntOrNull()) {
        1 -> addCargo()
        2 -> saveCsv()
        3 -> loadCsv()
    }
}

fun addCargo() {
    print("Имя: "); val n = readln()
    print("Вес: "); val w = readln().toDoubleOrNull() ?: 0.0
    print("Цена: "); val p = readln().toDoubleOrNull() ?: 0.0
    print("Откуда: "); val o = readln()
    print("Куда: "); val d = readln()
    list.add(Cargo(nextId++, n, w, p, o, d))
    println("Добавлено!")
}

fun consoleMenuOps() {
    println("\n1. Показать все | 2. Фильтр и Сортировка | 3. Назад")
    when (readlnOrNull()?.toIntOrNull()) {
        1 -> if (list.isEmpty()) println("Список пуст.") else list.forEach { println(it) }
        2 -> filterSort()
    }
}

fun filterSort() {
    print("Мин. вес для фильтра: ")
    val minW = readln().toDoubleOrNull() ?: 0.0
    val res = list.filter { it.w >= minW }.sortedBy { it.p }
    if (res.isEmpty()) println("Записи не найдены.") else res.forEach { println(it) }
}

fun editCargo() {
    print("ID для правки: ")
    val id = readln().toIntOrNull() ?: return
    val c = list.find { it.id == id }
    if (c != null) {
        print("Новая цена: ")
        c.p = readln().toDoubleOrNull() ?: c.p
        println("Обновлено.")
    } else {
        println("Не найдено.")
    }
}

fun delCargo() {
    print("ID для удаления: ")
    val id = readln().toIntOrNull() ?: return
    if (list.removeIf { it.id == id }) println("Удалено.") else println("Не найдено.")
}

fun stats() {
    if (list.isEmpty()) {
        println("Список пуст.")
        return
    }
    println("Сумма цен: ${list.sumOf { it.p }}, Средняя: ${list.map { it.p }.average()}")
}

fun saveCsv() {
    File("data.csv").bufferedWriter(Charsets.UTF_8).use { out ->
        out.write("\uFEFF")
        list.forEach { out.write("${it.id},${it.name},${it.w},${it.p},${it.orig},${it.dest}\n") }
    }
    println("Сохранено в data.csv")
}

fun loadCsv() {
    val f = File("data.csv")
    if (!f.exists()) {
        println("Файл не найден.")
        return
    }
    list.clear()
    f.forEachLine { line ->
        val p = line.split(",")
        if (p.size >= 6) {
            val idStr = p[0].replace("\uFEFF", "")
            list.add(Cargo(idStr.toInt(), p[1], p[2].toDouble(), p[3].toDouble(), p[4], p[5]))
        }
    }
    nextId = (list.maxOfOrNull { it.id } ?: 0) + 1
    println("Загружено.")
}

fun findCargo() {
    print("Поиск по пункту: ")
    val q = readln()
    val res = list.filter { it.orig.contains(q, true) || it.dest.contains(q, true) }
    if (res.isEmpty()) println("Ничего не найдено.") else res.forEach { println(it) }
}