# Final Değerlendirme Ödevi - 4
## Monte Carlo Yaklaşımı ile Rastgele Algoritma Analizi

Bu proje, büyük veri kümeleri üzerinde çalışan randomize algoritmaların performansını incelemek amacıyla hazırlanmıştır.  
Projede Monte Carlo yaklaşımı kullanılarak belirli bir koşulu sağlayan elemanların olasılıksal olarak tespit edilmesi amaçlanmıştır.

---

# Proje Bilgileri

| Özellik | Değer |
|---|---|
| Öğrenci No Son İki Hanesi | 30 |
| Algoritma Türü | Monte Carlo |
| Veri Boyutu | 10^5 |
| Programlama Dili | Java |
| Kullanılan Seed | 30 |
| Koşul | x mod 7 == 0 |

---

# Problem Tanımı

Rastgele oluşturulmuş büyük bir veri kümesi içerisinde:

```math
x mod 7 = 0
```

koşulunu sağlayan elemanların bulunması hedeflenmiştir.

Veri kümesi:

- 100000 elemandan oluşmaktadır
- Elemanlar 1 ile 1000000 arasında rastgele üretilmiştir
- Rastgelelik Java `Random` sınıfı ile sağlanmıştır

---

# Monte Carlo Yaklaşımı

Monte Carlo algoritmaları:

- Belirli sayıda iterasyon çalışır
- Rastgele örnekleme yapar
- Hızlı çalışabilir
- Küçük hata olasılığı içerir

Bu projede algoritma:

1. Veri kümesinden rastgele indeks seçer
2. Seçilen elemanı kontrol eder
3. Eğer:
   ```math
   x mod 7 = 0
   ```
   ise başarı döndürür
4. En fazla `k` iterasyon çalışır

---

# Kullanılan Parametreler

| Parametre | Değer |
|---|---|
| Veri Boyutu | 100000 |
| İterasyon Sayısı (k) | 50 |
| Çalıştırma Sayısı | 100 |
| Seed | 30 |

---

# Teorik Analiz

Bir elemanın başarılı olma olasılığı:

```math
p = başarılı eleman sayısı / n
```

Monte Carlo hata olasılığı:

```math
P(error) = (1 - p)^k
```

Bu projede yaklaşık olarak:

```math
p ≈ 1/7
```

olmaktadır.

k değeri arttıkça hata olasılığı azalmaktadır.

---

# Program Yapısı

Program aşağıdaki bölümlerden oluşmaktadır:

## 1. Veri Seti Oluşturma

```java
generateDataset()
```

Rastgele veri kümesini oluşturur.

---

## 2. Koşul Kontrolü

```java
condition()
```

Sayının 7’ye tam bölünüp bölünmediğini kontrol eder.

---

## 3. Monte Carlo Algoritması

```java
monteCarlo()
```

Rastgele örnekleme yaparak hedef elemanı arar.

---

## 4. Teorik Hata Hesabı

```java
theoreticalError()
```

Teorik hata olasılığını hesaplar.

---

## 5. Deneysel Çalışma

```java
runExperiment()
```

Algoritmayı 100 kez çalıştırarak:

- Ortalama süre
- Hata oranı
- Standart sapma

değerlerini hesaplar.

---

# Zaman Karmaşıklığı

Monte Carlo yaklaşımının yaklaşık zaman karmaşıklığı:

```math
O(k)
```

Burada:

- `k` iterasyon sayısını temsil eder.

Bu nedenle veri boyutu artsa bile çalışma süresi büyük ölçüde etkilenmez.

---

# Deneysel Sonuçlar

Deneyler sonucunda:

- Ortalama çalışma süresinin düşük olduğu
- Standart sapmanın düşük olduğu
- Deneysel hata oranının teorik hata oranına yakın olduğu

gözlemlenmiştir.

Bu durum Monte Carlo yaklaşımının teorik beklentiyle uyumlu çalıştığını göstermektedir.

---

# Avantajlar

- Hızlı çalışır
- Büyük veri kümelerinde verimlidir
- Kolay uygulanabilir
- Düşük bellek kullanımı sağlar

---

# Dezavantajlar

- Kesin doğruluk garanti edilmez
- Hata olasılığı içerir
- Rastgeleliğe bağlı sonuç farklılıkları oluşabilir

---

# Kullanım

## Derleme

```bash
javac MonteCarlo30.java
```

## Çalıştırma

```bash
java MonteCarlo30
```

---

# Çıktılar

Program çalıştırıldığında:

- Ortalama çalışma süresi
- Standart sapma
- Teorik hata oranı
- Deneysel hata oranı
- k değerine göre hata tablosu

ekrana yazdırılır.

---

# Sonuç

Bu projede Monte Carlo yaklaşımı başarıyla uygulanmıştır.

Teorik analiz ile deneysel sonuçlar karşılaştırılmış ve büyük ölçüde uyumlu olduğu gözlemlenmiştir.

Monte Carlo algoritmalarının özellikle büyük veri kümelerinde hızlı ve etkili çalıştığı sonucuna ulaşılmıştır.
