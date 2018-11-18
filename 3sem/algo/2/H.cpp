#include <iostream>
#include <fstream>
#include <vector>
#include <algorithm>
#include <set>
#include <queue>

using std::vector;
using std::set;
using std::pair;
using std::queue;



const int64_t INF = 10001;

std::fstream in("dwarf.in");
std::ofstream out("dwarf.out");

struct edge {
    int to, w;

    edge(int to, int w) : to(to), w(w) {}
};

struct graph {
    vector<vector<edge>> e;
    vector<int> w;

    graph(int n) {
        e = vector<vector<edge>>(n);
    }

    void add(int from, int to, int w) {
        e[from].push_back(edge(to, w));
        e[to].push_back(edge(from, w));
    }

    void add_w(int i) {
        w.push_back(i);
    }
};

graph *g;
int n, m;
vector<int> visited;
queue<int> q;

void read() {
    int a, b;
    int c;
    for (int i = 0; i < n; i++) {
        in >> a;
        g->add_w(a);
    }
    for (int i = 0; i < m; i++) {
        in >> a >> b >> c;
        g->add(c - 1, b - 1, a - 1);
        if (!visited[b - 1]) {
            visited[b - 1] = 1;
            q.push(b - 1);
        }
        if (!visited[c - 1]) {
            visited[c - 1] = 1;
            q.push(c - 1);
        }
    }
}

void fill() {
    visited = vector<int>(n, 0);
}


int main() {
    in >> n >> m;
    g = new graph(n);

    fill();
    read();

    while (!q.empty()) {
        int i = q.front();
        q.pop();
        visited[i] = 0;
        for (int j = 0; j < g->e[i].size(); j++) {
            if (g->w[g->e[i][j].w] > g->w[i] + g->w[g->e[i][j].to]) {
                g->w[g->e[i][j].w] = g->w[i] + g->w[g->e[i][j].to];
                if (!visited[g->e[i][j].w]) {
                    q.push(g->e[i][j].w);
                    visited[g->e[i][j].w] = 1;
                }
            }
        }
    }
    out << g->w[0];
}
