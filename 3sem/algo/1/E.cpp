#include <iostream>
#include <vector>
#include <algorithm>
#include <set>

using std::vector;
using std::set;

struct graph {
    std::vector<vector<std::pair<int, int>>> gv;

    graph(int n) {
        gv = std::vector<vector<std::pair<int, int>>>(n);
    }

    void add_verb(int start, int end, int num) {
        gv[start].push_back({end, num});
        gv[end].push_back({start, num});
    }
};

int *ret;
int *enter;
bool *visited;
graph *g;
vector<int> ans;
int n, m;
int tim = 0;
int max_color = 0;

void add_ans(int u, int par, int col) {
    visited[u] = true;
    for (std::pair<int, int> v : g->gv[u]) {
        if (!visited[v.first]) {
            if (ret[v.first] >= enter[u]) {
                ans[v.second - 1] = ++max_color;
                add_ans(v.first, u, max_color);
            } else {
                ans[v.second - 1] = col;
                add_ans(v.first, u, col);
            }
        } else if (enter[v.first] < enter[u]) {
            ans[v.second - 1] = col;
        }
    }
}


void dfs(int u, int par) {
    tim++;
    enter[u] = tim;
    ret[u] = tim;
    for (std::pair<int, int> v : g->gv[u]) {
        if (v.first == par) {
            continue;
        }
        if (enter[v.first] != 0) {
            ret[u] = std::min(ret[u], enter[v.first]);
        } else {
            dfs(v.first, u);
            ret[u] = std::min(ret[v.first], ret[u]);
        }
    }
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

    ret = new int[n];
    std::fill(ret, ret + n, 0);
    enter = new int[n];
    std::fill(enter, enter + n, 0);

    visited = new bool[n];
    std::fill(visited, visited + n, false);

    ans = vector<int>(m, 0);


    g = new graph(n);

    read();

    for (int i = 0; i < n; i++) {
        if (enter[i] == 0)
            dfs(i, -1);
    }


    for (int i = 0; i < n; i++) {
        if (!visited[i]) {
            add_ans(i, max_color, -1);
        }
    }

    int min = ans[0];

    for (int i = 0;i < m; i++) {
        if (ans[i] < min)
            min = ans[i];
    }

    min -= 1;

    std::cout << max_color - min << "\n";
    for (int j : ans)
        std::cout << j - min << " ";
}