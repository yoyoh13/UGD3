package com.yohanes.ugd3_a_0891.entity

class Kos (var name: String, var tipe: String, var jumlah: Int){
    companion object{
        @JvmField
        var listofKos = arrayOf(
            Kos("Kos wid", "Putra", 3),
            Kos("Kos pak uus", "Campur",2),
            Kos("Kos cari uang", "Putra",1),
            Kos("Kos ab", "Perempuan",4),
            Kos("Kos abc", "Campur",2),
            Kos("Kos def", "Perempuan",3),
            Kos("Kos asik", "Putra",1),
            Kos("Kos merah", "Perempuan",3),
            Kos("Kos kuning", "Perempuan",2),
            Kos("Kos hijau", "Perempuan",2),
        )
    }
}