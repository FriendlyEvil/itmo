#include <iostream>
#include <fstream>
#include <vector>
#include <algorithm>
#include <set>
#include <map>

using std::vector;
using std::set;

struct graph {
    std::vector<vector<int>> gv;


    graph(int n) {
        gv = vector<vector<int>>(n, vector<int>(n, 0));
    }


    void add_verb(std::vector<int> a) {
        gv.push_back(a);
    }
};

bool *visited;
graph *g;
int n, m = 0;
int max = 0;
int l = -1;
int r;

void fill() {
    visited = new bool[n];

    std::fill(visited, visited + n, false);
}

void add_ans(int v) {
    visited[v] = true;
    for (int i = 0; i < n; i++) {
        if (g->gv[i][v] <= m && !visited[i])
            add_ans(i);
    }
}

void dfs(int v) {
    visited[v] = true;
    for (int i = 0; i < n; i++) {
        if (g->gv[v][i] <= m && !visited[i])
            dfs(i);
    }
}


void read() {
    int a;
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            std::cin >> a;
            if (a > max)
                max = a;
            g->gv[i][j] = a;
        }
    }
    r = max;
}

int doi() {
    while (r - l > 1) {
        std::fill(visited, visited + n, false);
        m = (l + r) / 2;
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                if (i != 0) {
                    l = m;
                    return 1;
                }
                dfs(i);
            }
        }
        std::fill(visited, visited + n, false);
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                if (i != 0) {
                    l = m;
                    return 1;
                }
                add_ans(i);
            }
        }

        r = m;

    }
    return 0;
}


int main() {
    std::ios_base::sync_with_stdio(false);

    std::freopen("avia.in", "r", stdin);
    std::freopen("avia.out", "w", stdout);

    std::cin >> n;

    fill();
    g = new graph(n);
    read();

    while (doi()) {}


    std::cout << r;

}
