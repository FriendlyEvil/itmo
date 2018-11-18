#include <iostream>
#include <vector>
#include <algorithm>
#include <stack>
#include <map>

using std::vector;
using std::map;

struct graph {
    std::vector<vector<std::pair<int, int>>> gv;
    std::map<std::pair<int, int>, int> cr;

    graph(int n) {
        gv = std::vector<vector<std::pair<int, int>>>(n);
    }

    void add_verb(int start, int end, int num) {
        gv[start].push_back({end, num});
        gv[end].push_back({start, num});
        if (cr.find({start, end}) != cr.end()) {
            int nm = cr[{start, end}];
            cr[{start, end}] = nm + 1;
            cr[{end, start}] = nm + 1;
        } else {
            cr[{start, end}] = 1;
            cr[{end, start}] = 1;
        };
    }

    bool check(int start, int end) {
        return cr[{start, end}] == 1;
    }
};

int *ret;
int *enter;
graph *g;
vector<int> ans;
int n, m;
int time = 0;
int maxColor = 0;
std::stack<int> stack;


void add_ans(int v) {
    maxColor++;
    int last = -1;
    vector<int> vec;
    while (last != v && !stack.empty()) {
        ans[stack.top()] = maxColor;
        last = stack.top();
        stack.pop();
    }
}

void dfs(int u, int par) {
    time++;
    stack.push(u);
    enter[u] = time;
    ret[u] = time;
    for (std::pair<int, int> v : g->gv[u]) {
        if (v.first == par) {
            continue;
        }
        if (enter[v.first] != 0) {
            ret[u] = std::min(ret[u], enter[v.first]);
        } else {
            dfs(v.first, u);
            ret[u] = std::min(ret[v.first], ret[u]);
            if (ret[v.first] > enter[u]
                && g->check(v.first, u)
                    )
                add_ans(v.first);
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
    ans = vector<int>(n, 0);

    g = new graph(n);

    read();

    for (int i = 0; i < n; i++) {
        if (enter[i] == 0) {
            dfs(i, -1);
            add_ans(-2);
        }
    }


    std::cout << maxColor << "\n";
    for (int j : ans)
        std::cout << j << " ";
}