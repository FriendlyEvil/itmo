#include <iostream>
#include <vector>

using std::vector;

struct graph {
    std::vector<vector<int>> gv;

    graph(int n) {
        gv = std::vector<vector<int>>(n);
    }

    void add_verb(int start, int end) {
        gv[start].push_back(end);
    }

    bool check_cicle() {
        int *c = new int[gv.size()];
        std::fill(c, c + gv.size(), 0);
        for (int i = 0; i < gv.size(); ++i)
            if (c[i] == 0)
                if (!dfs(i, c))
                    return false;
        return true;
    }

    bool dfs(int u, int *c) {
        c[u] = 1;
        for (int i : gv[u]) {
            if (c[i] == 0) {
                if (!dfs(i, c))
                    return false;
            } else if (c[i] == 1) {
                return false;
            }
        }
        c[u] = 2;
        return true;
    }
};

bool *visited;
graph *g;
vector<int> ans;
int n, m;


void dfs(int u) {
    visited[u] = true;
    for (int v : g->gv[u])
        if (!visited[v])
            dfs(v);
    ans.push_back(u);
}

void top_sort() {
    for (int i = 0; i < n; i++) {
        if (!visited[i]) {
            dfs(i);
        }
    }
}

bool check() {
    return g->check_cicle();
}

void read() {
    int a, b;
    for (int i = 0; i < m; i++) {
        std::cin >> a >> b;
        g->add_verb(a - 1, b - 1);
    }
}

int main() {
    std::cin >> n >> m;
    visited = new bool[n];
    std::fill(visited, visited + n, false);
    g = new graph(n);

    read();
    if (check()) {
        top_sort();
        for (int i = n - 1; i >= 0; i--) {
            std::cout << ans[i] + 1 << " ";
        }
    } else {
        std::cout << -1;
    }
}