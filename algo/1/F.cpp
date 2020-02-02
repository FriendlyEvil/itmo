#include <iostream>
#include <vector>
#include <algorithm>
#include <set>

using std::vector;
using std::set;

struct graph {
    std::vector<vector<std::pair<int, int>>> gv;
    std::vector<vector<std::pair<int, int>>> gv_rev;


    graph(int n) {
        gv = std::vector<vector<std::pair<int, int>>>(n);
        gv_rev = std::vector<vector<std::pair<int, int>>>(n);
    }

    void add_verb(int start, int end, int num) {
        gv[start].push_back({end, num});
        gv_rev[end].push_back({start, num});
    }
};

int *color;
bool *visited;
graph *g;
vector<int> ans;
int n, m;
int max_color = 0;

void add_ans(int u, int col) {
    visited[u] = true;
    color[u] = col;
    for (std::pair<int, int> v : g->gv_rev[u]) {
        if (!visited[v.first]) {
            add_ans(v.first, col);
        }
    }
}


void dfs(int u) {
    visited[u] = true;
    for (std::pair<int, int> v : g->gv[u]) {
        if (!visited[v.first])
            dfs(v.first);
    }
    ans.push_back(u);
}

void read() {
    int a, b;
    for (int i = 0; i < m; i++) {
        std::cin >> a >> b;
        g->add_verb(a - 1, b - 1, i + 1);
    }
}

int main() {
    std::cin >> n >> m;

    visited = new bool[n];
    std::fill(visited, visited + n, false);
    color = new int[n];
    std::fill(color, color + n, 0);


    g = new graph(n);

    read();

    for (int i = 0; i < n; i++) {
        if (!visited[i])
            dfs(i);
    }
    std::fill(visited, visited + n, false);

    for (int i = ans.size() - 1; i >= 0; i--) {
        if (!visited[ans[i]]) {
            add_ans(ans[i], max_color);
            max_color++;
        }
    }

    std::set<int> verb[max_color + 1];

    for (int i = 0; i < n; i++) {
        for (std::pair<int, int> v : g->gv[i]) {
            if (color[v.first] != color[i])
                verb[color[i]].insert(color[v.first]);
        }
    }

    int ans = 0;
    for (int i =0 ; i <= max_color; i++)
        ans += verb[i].size();

    std::cout << ans << "\n";
}


