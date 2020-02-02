#include <iostream>
#include <vector>
#include <algorithm>
#include <set>

using std::vector;
using std::set;
using std::pair;

const int INF = 1e9;

struct graph {
    vector<vector<int>> gv;


    graph(int n) {
        gv = vector<vector<int>>(n, vector<int>(n, 0));
    }

    void add_verb(int start, int end, int num) {
        gv[start][end] = num;
    }
};

graph *g;
vector<int> d;
vector<int> p;
int n;
vector<int> ans;

void read() {
    int a;
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            std::cin >> a;
            if (a == 100000)
                g->add_verb(i, j, INF);
            else
                g->add_verb(i, j, a);
        }
    }
}

void fill() {
    p = vector<int>(n, -1);
    d = vector<int>(n, INF);
}

void floyd(int s = 0) {
    d[s] = 0;
    bool flag = false;
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; ++j) {
            for (int k = 0; k < n; ++k) {
                if (g->gv[j][k] != INF) {
                    if (d[k] > d[j] + g->gv[j][k]) {
                        d[k] = d[j] + g->gv[j][k];
                        p[k] = j;
                        flag = true;
                    }
                }
            }
        }
    }

    if (!flag)
        return;

    for (int j = 0; j < n; ++j) {
        for (int k = 0; k < n; ++k) {
            if (g->gv[j][k] != INF) {
                if (d[k] > d[j] + g->gv[j][k]) {
                    for (int i = 0; i < n; i++) {
                        k = p[k];
                    }
                    j = k;
                    while (j != p[k]) {
                        ans.push_back(p[k]);
                        k = p[k];
                    }
                    ans.push_back(j);
                    return;
                }
            }
        }
    }
}


int main() {
    std::cin >> n;
    g = new graph(n);

    read();
    fill();

    floyd();

    if (ans.size() == 0)
        std::cout << "NO";
    else {
        std::cout << "YES\n";
        std::cout << ans.size() << "\n";
        for (int i = ans.size() - 1; i >= 0; i--)
            std::cout << ans[i] + 1 << " ";
    }

    delete g;
}
