package com.fitness.app.data.remote

import com.google.gson.annotations.SerializedName

/**
 * Api servisine istek atılıp cevap geldiğinde karşılanacak verilerin modellenmesi
*/
data class AntrenmanProgramlari(
    @SerializedName("antrenmanlar") var antrenmanlar: Antrenmanlar? = Antrenmanlar()
)

data class Antrenmanlar(
    @SerializedName("dusuk_zorluk") var dusukZorluk: ArrayList<DusukZorluk> = arrayListOf()
)


data class DusukZorluk(
    @SerializedName("program_adi") var programAdi: String? = null,
    @SerializedName("gun_sayisi") var gunSayisi: Int? = null,
    @SerializedName("uygulanis") var uygulanis: ArrayList<Uygulanis> = arrayListOf()
)

data class Uygulanis(
    @SerializedName("gun") var gun: String? = null,
    @SerializedName("hareketler") var hareketler: ArrayList<Hareketler> = arrayListOf()
)

data class Hareketler(

    @SerializedName("hareket_adi") var hareketAdi: String? = null,
    @SerializedName("set_sayisi") var setSayisi: Int? = null,
    @SerializedName("tekrar_sayisi") var tekrarSayisi: Int? = null

)