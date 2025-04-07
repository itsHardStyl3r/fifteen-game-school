import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns
from pathlib import Path

# Wczytaj dane
df = pd.read_csv("summary.txt", sep=r"\s+", header=None, engine="python")
df.columns = [
    "głębokość", "numer", "algorytm", "strategia",
    "długość", "odwiedzone", "przetworzone", "maks_głębokość", "czas"
]

# Zamiana przecinków na kropki i konwersja na float
df["czas"] = df["czas"].astype(str).str.replace(",", ".").astype(float)

# Konwersja kolumn do liczb
int_cols = ["głębokość", "numer", "długość", "odwiedzone", "przetworzone", "maks_głębokość"]
for col in int_cols:
    df[col] = pd.to_numeric(df[col], errors="coerce")

# Mapowanie strategii
algorytm_map = {"bfs": "BFS", "dfs": "DFS", "astr": "A*"}
df["algorytm_kategoria"] = df["algorytm"].map(algorytm_map)

# Kryteria
kryteria = {
    "długość": "Długość rozwiązania",
    "odwiedzone": "Stany odwiedzone",
    "przetworzone": "Stany przetworzone",
    "maks_głębokość": "Maksymalna głębokość",
    "czas": "Czas [ms]"
}

Path("plots").mkdir(parents=True, exist_ok=True)

# Funkcja generująca oddzielne wykresy SVG
def generuj_wykresy_svg(kryterium, tytul):
    # 1. BFS / DFS / A*
    data1 = df.groupby(["głębokość", "algorytm_kategoria"])[kryterium].mean().reset_index()
    plt.figure(figsize=(8, 6))
    sns.barplot(data=data1, x="głębokość", y=kryterium, hue="algorytm_kategoria")
    # plt.title(f"{tytul} – BFS / DFS / A*")
    plt.ylabel(tytul)
    plt.savefig(f"plots/{kryterium}_bfs_dfs_astar.svg", format="svg")
    plt.close()

    # 2. A* – heurystyki
    data2 = df[df["algorytm"] == "astr"].groupby(["głębokość", "strategia"])[kryterium].mean().reset_index()
    if not data2.empty:
        plt.figure(figsize=(8, 6))
        sns.barplot(data=data2, x="głębokość", y=kryterium, hue="strategia")
        # plt.title(f"{tytul} – A* heurystyki")
        plt.ylabel(tytul)
        plt.savefig(f"plots/{kryterium}_astar_vs_heurystyka.svg", format="svg")
        plt.close()

    # 3. BFS – porządki
    data3 = df[df["algorytm"] == "bfs"].groupby(["głębokość", "strategia"])[kryterium].mean().reset_index()
    if not data3.empty:
        plt.figure(figsize=(8, 6))
        sns.barplot(data=data3, x="głębokość", y=kryterium, hue="strategia")
        # plt.title(f"{tytul} – BFS porządki")
        plt.ylabel(tytul)
        plt.savefig(f"plots/{kryterium}_bfs_vs_porządki.svg", format="svg")
        plt.close()

    # 4. DFS – porządki
    data4 = df[df["algorytm"] == "dfs"].groupby(["głębokość", "strategia"])[kryterium].mean().reset_index()
    if not data4.empty:
        plt.figure(figsize=(8, 6))
        sns.barplot(data=data4, x="głębokość", y=kryterium, hue="strategia")
        # plt.title(f"{tytul} – DFS porządki")
        plt.ylabel(tytul)
        plt.savefig(f"plots/{kryterium}_dfs_vs_porządki.svg", format="svg")
        plt.close()

# Generuj wszystko
for klucz, nazwa in kryteria.items():
    generuj_wykresy_svg(klucz, nazwa)
