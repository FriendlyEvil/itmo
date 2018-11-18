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
graph *g;
vector<int> ans;
set<int> ans_set;
int n, m;
int time = 0;

void add_ans(int v) {
    if (ans_set.find(v) == ans_set.end()) {
        ans_set.insert(v);
        ans.push_back(v);
    }
}


void dfs(int u, int par) {
    time++;
    enter[u] = time;
    ret[u] = time;
    int a = 0;
    for (std::pair<int, int> v : g->gv[u]) {
        if (v.first == par) {
            continue;
        }
        if (enter[v.first] != 0) {
            ret[u] = std::min(ret[u], enter[v.first]);
        } else {
            dfs(v.first, u);
            ret[u] = std::min(ret[v.first], ret[u]);
            if (ret[v.first] >= enter[u] && par != -1)
                add_ans(u);
            a++;
        }
    }
    if (par == -1 && a > 1)
        add_ans(u);
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

    g = new graph(n);

    read();

    for (int i = 0; i < n; i++) {
        if (enter[i] == 0)
            dfs(i, -1);
    }

    std::sort(ans.begin(), ans.end());

    std::cout << ans.size() << "\n";
    for (int j : ans)
        std::cout << j + 1 << " ";
}