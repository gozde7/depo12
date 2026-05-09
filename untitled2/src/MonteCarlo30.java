/**
 * Final Değerlendirme Ödevi - 4
 * Öğrenci No Son İki Hane : 30
 * Algoritma Tipi           : Monte Carlo
 * Veri Boyutu              : n = 10^5
 * Koşul                    : x mod 7 == 0
 * Seed                     : 30
 */

import java.util.Random;

public class MonteCarlo30 {

    // ─────────────────────────────────────────────
    // PARAMETRELER
    // ─────────────────────────────────────────────
    static final int    STUDENT_SEED = 30;
    static final int    N            = 100_000;   // 10^5
    static final int    K_ITERATIONS = 50;        // Monte Carlo iterasyon sayısı
    static final int    RUNS         = 100;       // Kaç kez çalıştırılacak

    // ─────────────────────────────────────────────
    // 1. VERİ SETİ OLUŞTURMA
    // ─────────────────────────────────────────────
    static int[] generateDataset(int n, long seed) {
        Random rng = new Random(seed);
        int[] data = new int[n];
        for (int i = 0; i < n; i++) {
            data[i] = rng.nextInt(1_000_000) + 1; // [1, 10^6]
        }
        return data;
    }

    // ─────────────────────────────────────────────
    // 2. KOŞUL: x mod 7 == 0
    // ─────────────────────────────────────────────
    static boolean condition(int x) {
        return x % 7 == 0;
    }

    // ─────────────────────────────────────────────
    // 3. MONTE CARLO ALGORİTMASI
    //    k rastgele örnekten herhangi biri koşulu
    //    sağlıyorsa true döner.
    // ─────────────────────────────────────────────
    static boolean monteCarlo(int[] data, int k, Random rng) {
        for (int i = 0; i < k; i++) {
            int idx = rng.nextInt(data.length);
            if (condition(data[idx])) {
                return true;
            }
        }
        return false;
    }

    // ─────────────────────────────────────────────
    // 4. TEORİK HATA OLASILĞI
    //    p       = koşulu sağlayan eleman sayısı / n
    //    P(hata) = (1 - p)^k
    // ─────────────────────────────────────────────
    static double theoreticalError(int[] data, int k) {
        int count = 0;
        for (int x : data) {
            if (condition(x)) count++;
        }
        double p = (double) count / data.length;
        return Math.pow(1 - p, k);
    }

    static double successProbability(int[] data) {
        int count = 0;
        for (int x : data) {
            if (condition(x)) count++;
        }
        return (double) count / data.length;
    }

    static int countTargets(int[] data) {
        int count = 0;
        for (int x : data) {
            if (condition(x)) count++;
        }
        return count;
    }

    // ─────────────────────────────────────────────
    // 5. 100 ÇALIŞTIRMA & İSTATİSTİK
    // ─────────────────────────────────────────────
    static void runExperiment(int[] data, int k, int runs) {
        Random rng = new Random(STUDENT_SEED + 1L);

        double[] times = new double[runs];
        int errors = 0;

        for (int i = 0; i < runs; i++) {
            long start = System.nanoTime();
            boolean found = monteCarlo(data, k, rng);
            long end = System.nanoTime();

            times[i] = (end - start) / 1_000_000.0; // ms cinsinden
            if (!found) errors++;
        }

        // İstatistik hesapla
        double avgTime = 0;
        for (double t : times) avgTime += t;
        avgTime /= runs;

        double variance = 0;
        for (double t : times) variance += (t - avgTime) * (t - avgTime);
        variance /= runs;
        double stdTime = Math.sqrt(variance);

        double empiricalError = (double) errors / runs;
        double p              = successProbability(data);
        double theoreticalErr = theoreticalError(data, k);
        int    targetCount    = countTargets(data);

        printResults(p, theoreticalErr, empiricalError, avgTime, stdTime,
                errors, runs, k, targetCount);
        kSensitivityTable(p);
    }

    // ─────────────────────────────────────────────
    // 6. SONUÇLARI YAZDIR
    // ─────────────────────────────────────────────
    static void printResults(double p, double theoreticalErr,
                             double empiricalError, double avgTime,
                             double stdTime, int errors, int runs,
                             int k, int targetCount) {
        System.out.println("============================================================");
        System.out.println("   MONTE CARLO ALGORİTMASI – SONUÇ RAPORU");
        System.out.println("============================================================");
        System.out.printf("  Öğrenci No Son Hanesi  : 30%n");
        System.out.printf("  Veri Boyutu (n)        : %,d%n", N);
        System.out.printf("  İterasyon (k)          : %d%n", k);
        System.out.printf("  Çalıştırma Sayısı      : %d%n", runs);
        System.out.printf("  Koşul                  : x mod 7 == 0%n");
        System.out.printf("  Seed                   : %d%n", STUDENT_SEED);

        System.out.println();
        System.out.println("─── VERİ İSTATİSTİKLERİ ───────────────────────────");
        System.out.printf("  Koşulu Sağlayan Eleman : %,d%n", targetCount);
        System.out.printf("  Başarı Olasılığı (p)   : %.6f  (~1/7 ≈ %.6f)%n",
                p, 1.0 / 7);

        System.out.println();
        System.out.println("─── TEORİK ANALİZ ──────────────────────────────────");
        System.out.printf("  P(hata | k=%d)          : (1 - %.6f)^%d%n", k, p, k);
        System.out.printf("                         = %.8f%n", theoreticalErr);
        System.out.printf("                         ≈ %.4f%%%n", theoreticalErr * 100);

        System.out.println();
        System.out.println("─── DENEYSEL SONUÇLAR (100 çalıştırma) ────────────");
        System.out.printf("  Ortalama Süre          : %.4f ms%n", avgTime);
        System.out.printf("  Standart Sapma (süre)  : %.4f ms%n", stdTime);
        System.out.printf("  Toplam Hata Sayısı     : %d / %d%n", errors, runs);
        System.out.printf("  Deneysel Hata Oranı    : %.4f  (%.2f%%)%n",
                empiricalError, empiricalError * 100);

        System.out.println();
        System.out.println("─── KARŞILAŞTIRMA ──────────────────────────────────");
        double diff = Math.abs(empiricalError - theoreticalErr);
        System.out.printf("  Teorik  P(hata)        : %.6f%n", theoreticalErr);
        System.out.printf("  Deneysel Hata Oranı    : %.6f%n", empiricalError);
        System.out.printf("  Fark (|teorik-deney|)  : %.6f%n", diff);

        System.out.println();
        System.out.println("─── RASTGELE SAPMAYA ETKİSİ ────────────────────────");
        double cv = (avgTime > 0) ? (stdTime / avgTime) * 100 : 0;
        System.out.printf("  Değişim Katsayısı (CV) : %.2f%%%n", cv);
        System.out.print("  Yorum: Rastgelelik çalışma süresi üzerinde ");
        if (cv < 5)
            System.out.printf("düşük sapma (%.2f%%) oluşturmaktadır.%n", cv);
        else if (cv < 15)
            System.out.printf("orta düzey sapma (%.2f%%) oluşturmaktadır.%n", cv);
        else
            System.out.printf("yüksek sapma (%.2f%%) oluşturmaktadır.%n", cv);

        System.out.println("============================================================");
    }

    // ─────────────────────────────────────────────
    // 7. FARKLI k DEĞERLERİ İÇİN ÖZET TABLO
    // ─────────────────────────────────────────────
    static void kSensitivityTable(double p) {
        System.out.println();
        System.out.printf("  k Değerine Göre Teorik Hata Olasılıkları%n");
        System.out.printf("  (p = %.6f)%n", p);
        System.out.printf("  %6s | %12s | %10s%n", "k", "P(hata)", "P(hata) %");
        System.out.println("  -------+--------------+-----------");
        for (int k : new int[]{1, 5, 10, 20, 50, 100, 200}) {
            double pe = Math.pow(1 - p, k);
            System.out.printf("  %6d | %12.8f | %9.4f%%%n", k, pe, pe * 100);
        }
    }

    // ─────────────────────────────────────────────
    // ANA PROGRAM
    // ─────────────────────────────────────────────
    public static void main(String[] args) {
        System.out.println("Veri seti oluşturuluyor...");
        int[] data = generateDataset(N, STUDENT_SEED);

        System.out.printf("Monte Carlo %d kez çalıştırılıyor (k=%d)...%n",
                RUNS, K_ITERATIONS);
        runExperiment(data, K_ITERATIONS, RUNS);
    }
}