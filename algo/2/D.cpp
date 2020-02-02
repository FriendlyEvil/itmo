 От Formul, соревнование: Лабораторная работа 2-2. Графы, кратчайшие пути, задача: (D) Кратчайший путь длины $K$, Полное решение, #

#include <iostream>
#include <vector>
#include <algorithm>
#include <set>

using std::vector;
using std::set;
using std::pair;

const int64_t INF = 8e18;

struct graph {
    std::vector<vector<std::pair<int, int>>> gv;


    graph(int n) {
        gv = std::vector<vector<std::pair<int, int>>>(n);
    }

    void add_verb(int start, int end, int num) {
//        gv[start].push_back({end, num});
        gv[end].push_back({start, num});
    }
};

graph *g;
int n, m, s, h;
vector<vector<int64_t>> d;

void read() {
    int a, b;
    int64_t c;
    for (int i = 0; i < m; i++) {
        std::cin >> a >> b >> c;
        g->add_verb(a - 1, b - 1, c);
    }
}

void fill() {
    d = vector<vector<int64_t>>(h + 1, vector<int64_t>(n, INF + 1e17));
}

void floyd(int s = 0) {
    d[0][s] = 0;
    for (int i = 0; i < h; i++) {
        for (int j = 0; j < n; j++) {
            for (auto k : g->gv[j]) {
                d[i + 1][j] = std::min(d[i + 1][j], d[i][k.first] + k.second);
            }
        }
    }
}


int main() {
    std::cin >> n >> m >> h >> s;
    s--;
    g = new graph(n);

    read();
    fill();

    floyd(s);

    for (int i = 0; i < n; i++) {
        if (d[h][i] >= INF)
            std::cout << -1;
        else
            std::cout << d[h][i];
        std::cout << "\n";
    }
}

