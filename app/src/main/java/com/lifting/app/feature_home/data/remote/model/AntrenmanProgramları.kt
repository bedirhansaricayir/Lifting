package com.lifting.app.feature_home.data.remote.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class AntrenmanProgramlari(
    @SerializedName("antrenmanlar") var antrenmanlar: Antrenmanlar? = Antrenmanlar()
)

data class Antrenmanlar(
    @SerializedName("dusuk_zorluk") var dusukZorluk: ArrayList<DusukZorluk> = arrayListOf(),
    @SerializedName("orta_zorluk") var ortaZorluk: ArrayList<OrtaZorluk> = arrayListOf(),
    @SerializedName("yuksek_zorluk") var yuksekZorluk: ArrayList<YuksekZorluk> = arrayListOf()
)

@Parcelize
data class DusukZorluk(
    @SerializedName("program_adi") var programAdi: String? = null,
    @SerializedName("gun_sayisi") var gunSayisi: Int? = null,
    @SerializedName("uygulanis") var uygulanis: ArrayList<Uygulanis> = arrayListOf()
): Parcelable

@Parcelize
data class OrtaZorluk(
    @SerializedName("program_adi") var programAdi: String? = null,
    @SerializedName("gun_sayisi") var gunSayisi: Int? = null,
    @SerializedName("uygulanis") var uygulanis: ArrayList<Uygulanis> = arrayListOf()
) : Parcelable

@Parcelize
data class YuksekZorluk(
    @SerializedName("program_adi") var programAdi: String? = null,
    @SerializedName("gun_sayisi") var gunSayisi: Int? = null,
    @SerializedName("uygulanis") var uygulanis: ArrayList<Uygulanis> = arrayListOf()
) : Parcelable

@Parcelize
data class Uygulanis(
    @SerializedName("gun") var gun: String? = null,
    @SerializedName("hareketler") var hareketler: ArrayList<Hareketler> = arrayListOf()
): Parcelable

@Parcelize
data class Hareketler(

    @SerializedName("hareket_adi") var hareketAdi: String? = null,
    @SerializedName("set_sayisi") var setSayisi: Int? = null,
    @SerializedName("tekrar_sayisi") var tekrarSayisi: Int? = null,
    @SerializedName("hareket_form") var hareketForm: String? = null

):Parcelable