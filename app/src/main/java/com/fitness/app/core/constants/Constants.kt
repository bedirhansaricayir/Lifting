package com.fitness.app.core.constants

class Constants {
    companion object{
        //Sağlık Ekranı
        const val ERKEK = "Erkek"
        const val KADIN = "KADIN"
        const val SEDANTER = "Sedanter"
        const val HAFIF = "Hafif aktif"
        const val ORTA = "Orta aktif"
        const val COK = "Çok aktif"
        const val SEDANTER_TEXT = "Tüm gün masa başında oturuyorum"
        const val HAFIF_TEXT = "Ara sıra ve kısa süreli egzersiz yapıyorum"
        const val ORTA_TEXT = "Her gün en az bir saat egzersiz yapıyorum"
        const val COK_TEXT = "Haftada 4-5 gün en az 2 saat egzersiz yapıyorum"

        //Aktivite Seviyesi
        const val HIC = "Daha önce antrenman yapmadım"
        const val IKI_UC_GUN = "Haftada 2-3 Gün yapıyorum"
        const val BES_GUN = "Haftada 5+ Gün yapıyorum"

        //Hedef
        const val YAG_YAK = "Yağ yakmak"
        const val KAS_KAZAN = "Kas kazanmak"
        const val FORM_KORU = "Form korumak"

        const val HIC_YAG_YAK = "Gün 1: Vücut Ağırlığı ile Kuvvet\n" +
                "\n" +
                "Squat: 3 set x 10 tekrar\n" +
                "Lunges: 3 set x 10 tekrar (her bacak için)\n" +
                "Push-up: 3 set x 10 tekrar\n" +
                "Plank: 3 set x 30 saniye\n" +
                "Superman: 3 set x 10 tekrar\n+" +
                "Gün 2: Yürüyüş ve Esneme\n" +
                "\n" +
                "15 dakika hızlı tempolu yürüyüş\n" +
                "10 dakika dinlenme aralıklarıyla 5-10 dakika koşu veya hızlı tempolu yürüyüş\n" +
                "10 dakika yavaş tempolu yürüyüş ve soğuma\n" +
                "10-15 dakika esneme ve esneklik egzersizleri\n +" +
                "Gün 3: Vücut Ağırlığı ile Kuvvet\n" +
                "\n" +
                "Squat: 3 set x 10 tekrar\n" +
                "Lunges: 3 set x 10 tekrar (her bacak için)\n" +
                "Push-up: 3 set x 10 tekrar\n" +
                "Plank: 3 set x 30 saniye\n" +
                "Superman: 3 set x 10 tekrar\n" +
                "Gün 3: Kardiyo ve Esneme\n" +
                "\n" +
                "20 dakika düşük yoğunluklu kardiyo (örn. yürüyüş, bisiklet, eliptik)\n" +
                "10-15 dakika esneme ve esneklik egzersizleri"
        const val HIC_KAS_KAZAN = "Gün 1: Vücut Ağırlığı ile Kuvvet Antrenmanı\n" +
                "\n" +
                "Squat: 3 set x 10 tekrar\n" +
                "Push-up: 3 set x 10 tekrar\n" +
                "Glute Bridge: 3 set x 10 tekrar\n" +
                "Plank: 3 set x 30 saniye\n" +
                "\n" +
                "Gün 2: Vücut Ağırlığı ile Kuvvet Antrenmanı\n" +
                "\n" +
                "Lunges: 3 set x 10 tekrar (her bacak için)\n" +
                "Dumbbell Row: 3 set x 10 tekrar (her kol için)\n" +
                "Dumbbell Shoulder Press: 3 set x 10 tekrar\n" +
                "Superman: 3 set x 10 tekrar\n" +
                "\n" +
                "Gün 3: Vücut Ağırlığı ile Kuvvet Antrenmanı\n" +
                "\n" +
                "Bulgarian Split Squat: 3 set x 10 tekrar (her bacak için)\n" +
                "Push-up (diz üzerinde): 3 set x 10 tekrar\n" +
                "Plank to Push-up: 3 set x 10 tekrar\n" +
                "Bodyweight Rows (barfiks çekme): 3 set x 10 tekrar"
        const val HIC_FORM_KORU = "Gün 1: Kuvvet Antrenmanı\n" +
                "\n" +
                "Squat: 2 set x 12 tekrar\n" +
                "Push-up: 2 set x 12 tekrar\n" +
                "Glute Bridge: 2 set x 12 tekrar\n" +
                "Dumbbell Shoulder Press: 2 set x 12 tekrar\n" +
                "Superman: 2 set x 12 tekrar\n" +
                "Gün 2: Kardiyo\n" +
                "\n" +
                "Yürüyüş: 30 dakika hızlı tempolu yürüyüş veya yürüyüş-jog kombinasyonu\n" +
                "Gün 3: Kuvvet Antrenmanı\n" +
                "\n" +
                "Lunges: 2 set x 12 tekrar (her bacak için)\n" +
                "Dumbbell Row: 2 set x 12 tekrar (her kol için)\n" +
                "Bench Press (dumbbell veya barbell): 2 set x 12 tekrar\n" +
                "Plank: 2 set x 30 saniye\n" +
                "Mountain Climbers: 2 set x 12 tekrar (her bacak için)"

        const val IKI_UC_GUN_YAG_YAK = "Gün 1: Kardiyo ve Kuvvet Antrenmanı\n" +
                "\n" +
                "Kardiyo: 20 dakika yüksek yoğunluklu interval antrenmanı (örn. koşu, bisiklet, kürek çekme)\n" +
                "Squat: 3 set x 12 tekrar\n" +
                "Lunges: 3 set x 12 tekrar (her bacak için)\n" +
                "Dumbbell Bench Press: 3 set x 12 tekrar\n" +
                "Lat Pulldown: 3 set x 12 tekrar\n" +
                "Plank: 3 set x 30 saniye\n" +
                "\n" +
                "Gün 2: Kardiyo ve Kuvvet Antrenmanı\n" +
                "\n" +
                "Kardiyo: 20 dakika yüksek yoğunluklu interval antrenmanı\n" +
                "Deadlift: 3 set x 10 tekrar\n" +
                "Dumbbell Shoulder Press: 3 set x 10 tekrar\n" +
                "Bent-Over Barbell Row: 3 set x 10 tekrar\n" +
                "Walking Lunges: 3 set x 10 adım (her bacak için)\n" +
                "Russian Twist: 3 set x 12 tekrar\n" +
                "\n" +
                "Gün 3: Kardiyo ve Kuvvet Antrenmanı\n" +
                "\n" +
                "Kardiyo: 20 dakika yüksek yoğunluklu interval antrenmanı\n" +
                "Barbell Squat: 3 set x 12 tekrar\n" +
                "Romanian Deadlift: 3 set x 12 tekrar\n" +
                "Push-ups: 3 set x 12 tekrar\n" +
                "Dumbbell Bent-Over Row: 3 set x 12 tekrar\n" +
                "Mountain Climbers: 3 set x 12 tekrar (her bacak için)"
        const val IKI_UC_GUN_KAS_KAZAN = "Gün 1: Bütün Vücut Antrenmanı\n" +
                "\n" +
                "Barbell Bench Press: 3 set x 8-12 tekrar\n" +
                "Bent-Over Barbell Row: 3 set x 8-12 tekrar\n" +
                "Squat: 3 set x 8-12 tekrar\n" +
                "Shoulder Press: 3 set x 8-12 tekrar\n" +
                "Dumbbell Bicep Curl: 3 set x 8-12 tekrar\n" +
                "Tricep Pushdown: 3 set x 8-12 tekrar\n" +
                "Crunches: 3 set x 12-15 tekrar\n" +
                "\n" +
                "Gün 2: Bütün Vücut Antrenmanı\n" +
                "\n" +
                "Deadlift: 3 set x 8-12 tekrar\n" +
                "Lat Pulldown: 3 set x 8-12 tekrar\n" +
                "Leg Press: 3 set x 8-12 tekrar\n" +
                "Dumbbell Shoulder Press: 3 set x 8-12 tekrar\n" +
                "Barbell Curl: 3 set x 8-12 tekrar\n" +
                "Skull Crusher: 3 set x 8-12 tekrar\n" +
                "Leg Raises: 3 set x 12-15 tekrar\n" +
                "\n" +
                "Gün 3: Bütün Vücut Antrenmanı\n" +
                "\n" +
                "Incline Barbell Bench Press: 3 set x 8-12 tekrar\n" +
                "Seated Cable Row: 3 set x 8-12 tekrar\n" +
                "Lunges: 3 set x 8-12 tekrar\n" +
                "Dumbbell Lateral Raise: 3 set x 8-12 tekrar\n" +
                "Hammer Curl: 3 set x 8-12 tekrar\n" +
                "Tricep Dips: 3 set x 8-12 tekrar\n" +
                "Plank: 3 set x 30-60 saniye"
        const val IKI_UC_GUN_FORM_KORU = "Gün 1: Kardiyo ve Kuvvet Antrenmanı\n" +
                "\n" +
                "Kardiyo: 20 dakika yüksek yoğunluklu interval antrenmanı (örn. koşu, bisiklet, kürek çekme)\n" +
                "Squat: 3 set x 12 tekrar\n" +
                "Lunges: 3 set x 12 tekrar (her bacak için)\n" +
                "Dumbbell Bench Press: 3 set x 12 tekrar\n" +
                "Lat Pulldown: 3 set x 12 tekrar\n" +
                "Plank: 3 set x 30 saniye\n" +
                "\n" +
                "Gün 2: Kardiyo ve Kuvvet Antrenmanı\n" +
                "\n" +
                "Deadlift: 3 set x 10 tekrar\n" +
                "Dumbbell Shoulder Press: 3 set x 10 tekrar\n" +
                "Bent-Over Barbell Row: 3 set x 10 tekrar\n" +
                "Walking Lunges: 3 set x 10 adım (her bacak için)\n" +
                "Russian Twist: 3 set x 12 tekrar\n" +
                "\n" +
                "Gün 3: Kardiyo ve Kuvvet Antrenmanı\n" +
                "\n" +
                "Kardiyo: 20 dakika yüksek yoğunluklu interval antrenmanı\n" +
                "Barbell Squat: 3 set x 12 tekrar\n" +
                "Romanian Deadlift: 3 set x 12 tekrar\n" +
                "Push-ups: 3 set x 12 tekrar\n" +
                "Dumbbell Bent-Over Row: 3 set x 12 tekrar\n" +
                "Mountain Climbers: 3 set x 12 tekrar (her bacak için)"

        const val BES_GUN_YAG_YAK = "Gün 1: Kardiyo ve Kuvvet Antrenmanı\n" +
                "\n" +
                "Kardiyo: 30 dakika yüksek yoğunluklu interval antrenmanı (örn. koşu, bisiklet, kürek çekme)\n" +
                "Barbell Squat: 3 set x 10 tekrar\n" +
                "Dumbbell Bench Press: 3 set x 10 tekrar\n" +
                "Bent-Over Barbell Row: 3 set x 10 tekrar\n" +
                "Dumbbell Shoulder Press: 3 set x 10 tekrar\n" +
                "Plank: 3 set x 30 saniye\n" +
                "Gün 2: Kardiyo\n" +
                "\n" +
                "Kardiyo: 45 dakika düşük yoğunluklu sürekli kardiyo (örn. hızlı tempolu yürüyüş, bisiklet, yüzme)\n" +
                "Gün 3: Kardiyo ve Kuvvet Antrenmanı\n" +
                "\n" +
                "Kardiyo: 30 dakika yüksek yoğunluklu interval antrenmanı\n" +
                "Deadlift: 3 set x 10 tekrar\n" +
                "Dumbbell Lunges: 3 set x 10 tekrar (her bacak için)\n" +
                "Push-ups: 3 set x 10 tekrar\n" +
                "Lat Pulldown: 3 set x 10 tekrar\n" +
                "Russian Twist: 3 set x 12 tekrar\n" +
                "Gün 4: Kardiyo\n" +
                "\n" +
                "Kardiyo: 45 dakika düşük yoğunluklu sürekli kardiyo\n" +
                "Gün 5: Kardiyo ve Kuvvet Antrenmanı\n" +
                "\n" +
                "Kardiyo: 30 dakika yüksek yoğunluklu interval antrenmanı\n" +
                "Barbell Squat: 3 set x 12 tekrar\n" +
                "Romanian Deadlift: 3 set x 12 tekrar\n" +
                "Dumbbell Bench Press: 3 set x 12 tekrar\n" +
                "Bent-Over Dumbbell Row: 3 set x 12 tekrar\n" +
                "Mountain Climbers: 3 set x 12 tekrar (her bacak için)"
        const val BES_GUN_KAS_KAZAN = "Gün 1: Göğüs ve Triceps\n" +
                "\n" +
                "Barbell Bench Press: 4 set x 8-10 tekrar\n" +
                "Dumbbell Incline Bench Press: 3 set x 10-12 tekrar\n" +
                "Dumbbell Flyes: 3 set x 10-12 tekrar\n" +
                "Tricep Pushdown: 4 set x 10-12 tekrar\n" +
                "Skull Crushers: 3 set x 10-12 tekrar\n" +
                "Gün 2: Bacak ve Kalça\n" +
                "\n" +
                "Barbell Squat: 4 set x 8-10 tekrar\n" +
                "Romanian Deadlift: 3 set x 10-12 tekrar\n" +
                "Leg Press: 3 set x 10-12 tekrar\n" +
                "Walking Lunges: 3 set x 12 adım (her bacak için)\n" +
                "Glute Bridge: 3 set x 12-15 tekrar\n" +
                "Gün 3: Omuz ve Sırt\n" +
                "\n" +
                "Barbell Shoulder Press: 4 set x 8-10 tekrar\n" +
                "Dumbbell Lateral Raise: 3 set x 10-12 tekrar\n" +
                "Bent-Over Barbell Row: 4 set x 8-10 tekrar\n" +
                "Lat Pulldown: 3 set x 10-12 tekrar\n" +
                "Seated Cable Row: 3 set x 10-12 tekrar\n" +
                "Gün 4: Biceps ve Triceps\n" +
                "\n" +
                "Barbell Curl: 3 set x 10-12 tekrar\n" +
                "Hammer Curl: 3 set x 10-12 tekrar\n" +
                "Concentration Curl: 3 set x 10-12 tekrar\n" +
                "Tricep Dips: 3 set x 10-12 tekrar\n" +
                "Overhead Tricep Extension: 3 set x 10-12 tekrar\n" +
                "Gün 5: Göğüs ve Sırt\n" +
                "\n" +
                "Dumbbell Bench Press: 4 set x 8-10 tekrar\n" +
                "Pull-ups: 3 set x Maksimum tekrar\n" +
                "Incline Dumbbell Press: 3 set x 10-12 tekrar\n" +
                "Barbell Rows: 4 set x 8-10 tekrar\n" +
                "Cable Flyes: 3 set x 10-12 tekrar"
        const val BES_GUN_FORM_KORU = "Gün 1: Hafif Kuvvet Antrenmanı\n" +
                "\n" +
                "Dumbbell Bench Press: 3 set x 12 tekrar\n" +
                "Lat Pulldown: 3 set x 12 tekrar\n" +
                "Dumbbell Shoulder Press: 3 set x 12 tekrar\n" +
                "Leg Press: 3 set x 12 tekrar\n" +
                "Plank: 3 set x 30 saniye\n" +
                "Gün 2: Kardiyo ve Esneme\n" +
                "\n" +
                "Kardiyo: 30 dakika düşük yoğunluklu kardiyo (örn. yürüyüş, bisiklet, yüzme)\n" +
                "Esneme: 10-15 dakika esneme ve esneklik egzersizleri\n" +
                "Gün 3: Hafif Kuvvet Antrenmanı\n" +
                "\n" +
                "Barbell Squat: 3 set x 12 tekrar\n" +
                "Dumbbell Bent-Over Row: 3 set x 12 tekrar\n" +
                "Dumbbell Lateral Raise: 3 set x 12 tekrar\n" +
                "Hamstring Curl: 3 set x 12 tekrar\n" +
                "Russian Twist: 3 set x 12 tekrar\n" +
                "Gün 4: Kardiyo ve Esneme\n" +
                "\n" +
                "Kardiyo: 30 dakika düşük yoğunluklu kardiyo\n" +
                "Esneme: 10-15 dakika esneme ve esneklik egzersizleri\n" +
                "Gün 5: Hafif Kuvvet Antrenmanı\n" +
                "\n" +
                "Dumbbell Chest Press: 3 set x 12 tekrar\n" +
                "Seated Cable Row: 3 set x 12 tekrar\n" +
                "Dumbbell Shoulder Press: 3 set x 12 tekrar\n" +
                "Leg Press: 3 set x 12 tekrar\n" +
                "Plank: 3 set x 30 saniye"



        //DataStore
        const val ONBOARDING = "on_boarding_pref"
        const val ONBOARDING_COMPLETED = "on_boarding_completed"


        //ApiClient
        const val BASE = "https://gist.github.com/"
        const val END_POINT = "bedirhansaricayir/51bed929bca74d888f3dd5e20a11f80e/raw/b3cc5cae248e6667a45be53064f3c9f88589c94f/program_data.json"

        //MainScreen routes
        const val HOME_SCREEN = "home_screen"
        const val HOME_SCREEN_TITLE = "Programlar"

        const val TRACKER_SCREEN = "tracker_screen"
        const val TRACKER_SCREEN_TITLE = "Analiz"

        const val HEALTH_SCREEN = "health_screen"
        const val HEALTH_SCREEN_TITLE = "Sağlık"

        //AuthScreen routes
        const val SIGN_IN = "login_screen"
        const val SIGN_UP = "register_screen"
        const val FORGOT_PASS = "forgot_screen"
        const val ONBOARDING_SCREEN = "onboarding_screen"


        //DetailScreen routes
        const val DETAIL_SCREEN = "detail_screen"

    }
}