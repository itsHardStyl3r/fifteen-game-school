package me.hardstyl3r;

import org.jfree.chart.*;
import org.jfree.data.category.DefaultCategoryDataset;

import java.io.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class Graph {

    static class Entry {
        int glebokosc;
        String algorytm;
        String strategia;
        int dlugoscRozwiazania;
        int odwiedzone;
        int przetworzone;
        int maksGlebokosc;
        double czasMs;

        public Entry(String[] fields) {
            this.glebokosc = Integer.parseInt(fields[0]);
            this.algorytm = fields[2];
            this.strategia = fields[3];
            this.dlugoscRozwiazania = Integer.parseInt(fields[4]);
            this.odwiedzone = Integer.parseInt(fields[5]);
            this.przetworzone = Integer.parseInt(fields[6]);
            this.maksGlebokosc = Integer.parseInt(fields[7]);
            this.czasMs = Double.parseDouble(fields[8].replace(',', '.'));
        }
    }

    public static void main(String[] args) throws Exception {
        List<Entry> dane = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("summary.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                dane.add(new Entry(line.replace(",", ".").split("\\s+")));
            }
        }
        new File("plots").mkdirs();

        String[] kryteria = {"dlugosc", "odwiedzone", "przetworzone", "glebokosc_maks", "czas"};
        String[] tytuly = {"Długość rozwiązania", "Liczba odwiedzonych stanów",
                "Liczba przetworzonych stanów", "Maksymalna głębokość rekursji",
                "Czas trwania (ms)"};

        for (int i = 0; i < kryteria.length; i++) {
            saveChartsForCriterion(tytuly[i], dane, kryteria[i]);
        }
    }

    private static void saveChartsForCriterion(String title, List<Entry> dane, String criterion) {
        DefaultCategoryDataset dataset1 = new DefaultCategoryDataset();
        DefaultCategoryDataset dataset2 = new DefaultCategoryDataset();
        DefaultCategoryDataset dataset3 = new DefaultCategoryDataset();
        DefaultCategoryDataset dataset4 = new DefaultCategoryDataset();

        Map<Integer, List<Entry>> grouped = dane.stream()
                .filter(e -> e.dlugoscRozwiazania != -1)
                .collect(Collectors.groupingBy(e -> e.glebokosc));

        for (var entry : grouped.entrySet()) {
            int g = entry.getKey();
            List<Entry> list = entry.getValue();

            // Średnie: ogólnie
            dataset1.addValue(avg(list, criterion, "bfs", null), "BFS", String.valueOf(g));
            dataset1.addValue(avg(list, criterion, "dfs", null), "DFS", String.valueOf(g));
            dataset1.addValue(avg(list, criterion, "astr", null), "A*", String.valueOf(g));

            // Średnie BFS - porządki
            for (String strat : List.of("DRLU", "DRUL", "LUDR", "LURD", "RDLU", "RDUL", "ULDR", "ULRD"))
                dataset2.addValue(avg(list, criterion, "bfs", strat), strat, String.valueOf(g));

            // Średnie DFS - porządki
            for (String strat : List.of("DRLU", "DRUL", "LUDR", "LURD", "RDLU", "RDUL", "ULDR", "ULRD"))
                dataset3.addValue(avg(list, criterion, "dfs", strat), strat, String.valueOf(g));

            // Średnie A* - heurystyki
            for (String strat : List.of("hamm", "manh"))
                dataset4.addValue(avg(list, criterion, "astr", strat), strat, String.valueOf(g));
        }

        saveChart(title, "Ogółem", dataset1, criterion + "_ogolem");
        saveChart(title + " (A*)", "A*", dataset4, criterion + "_astr");
        saveChart(title + " (BFS)", "BFS", dataset2, criterion + "_bfs");
        saveChart(title + " (DFS)", "DFS", dataset3, criterion + "_dfs");
    }

    private static void saveChart(String title, String subtitle, DefaultCategoryDataset dataset, String filename) {
        try {
            JFreeChart chart = ChartFactory.createBarChart(
                    title,
                    "Głębokość",
                    subtitle,
                    dataset
            );

            ChartUtils.saveChartAsPNG(
                    new File("plots/" + filename + ".png"),
                    chart,
                    1200,
                    600
            );
        } catch (IOException e) {
            System.err.println("Błąd podczas zapisywania wykresu: " + e.getMessage());
        }
    }

    private static double avg(List<Entry> list, String criterion, String alg, String strat) {
        return list.stream()
                .filter(e -> e.algorytm.equalsIgnoreCase(alg))
                .filter(e -> strat == null || e.strategia.equalsIgnoreCase(strat))
                .mapToDouble(e -> switch (criterion) {
                    case "dlugosc" -> e.dlugoscRozwiazania;
                    case "odwiedzone" -> e.odwiedzone;
                    case "przetworzone" -> e.przetworzone;
                    case "glebokosc_maks" -> e.maksGlebokosc;
                    case "czas" -> e.czasMs;
                    default -> 0;
                }).average().orElse(0);
    }
}
