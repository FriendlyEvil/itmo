#include <iostream>
#include <vector>
#include <algorithm>
#include <set>

using std::vector;
using std::set;
using std::pair;

const int64_t  INF = 8e18;

struct edge {
    int from, to;
    int64_t w;

    edge(int from, int to, int64_t  w) : from(from), to(to), w(w) {}
};

struct graph {
    vector<edge> e;
    vector<vector<int>> v;

    graph(int n) {
        v = vector<vector<int>>(n);
    }

    void add(int from, int to, int64_t  w) {
        v[from].push_back(to);
        e.push_back(edge(from, to, w));
    }
};

graph *g;
int n, m, s;
vector<bool> visited;
vector<bool> visited1;

vector<int64_t> d;
vector<int64_t> ans;

void read() {
    int a, b;
    int64_t c;
    for (int i = 0; i < m; i++) {
        std::cin >> a >> b >> c;
        g->add(a - 1, b - 1, c);
    }
}

void fill() {
    d = vector<int64_t >(n, INF + 1e17);
    visited = vector<bool>(n, false);
    visited1 = vector<bool>(n, false);
    ans = vector<int64_t >(n);
}

void dfs(int v) {
    visited[v] = true;
    for (int i : g->v[v]) {
        if (!visited[i]) {
            dfs(i);
        }
    }
}

void dfs1(int v) {
    visited1[v] = true;
    for (int i : g->v[v]) {
        if (!visited1[i]) {
            dfs1(i);
        }
    }
}

void floyd(int s = 0) {
    d[s] = 0;
    for (int i = 0; i < n; i++) {
        for (edge e : g->e) {
            if (visited[e.to] && d[e.from] < INF && d[e.to] > d[e.from] + e.w)
                d[e.to] = std::max(d[e.from] + e.w, -INF);
        }
    }

    for (int i = 0; i < n; i++) {
        ans[i] = d[i];
    }

    for (edge e : g->e)
        if (visited[e.to] && ans[e.from] < INF && ans[e.to] > ans[e.from] + e.w)
            ans[e.to] = ans[e.from] + e.w;

}


int main() {
    std::cin >> n >> m >> s;
    s--;
    g = new graph(n);

    read();
    fill();

    dfs(s);
    floyd(s);

    for (int i = 0; i < n; i++) {
        if (visited[i] && ans[i] != d[i])
            dfs1(i);
    }

    for (int i =0; i< n; i++) {
        if (d[i] >= INF)
            std::cout << "*";
        else if(visited1[i])
            std::cout << "-";
        else
            std::cout << d[i];
        std::cout << "\n";
    }
}
