#include <iostream>
#include <vector>
#include <algorithm>
#include <set>
#include <math.h>

using std::vector;
using std::set;

const float INF = 100000;

struct graph {
    std::vector<vector<float >> gv;


    graph(int n) {
//        gv = vector<vector<float >>(n, vector<float>(n, INF));
    }

    void add_verb(int start, int end, float num) {
        gv[start][end] = num;
        gv[end][start] = num;
    }
};

float *min;
float *sel;
bool *visited;
int *x;
int *y;
graph *g;
vector<float> ans;
int n, m;


void read() {
    int a, b;
    for (int i = 0; i < n; i++) {
        std::cin >> a >> b;
        x[i] = a;
        y[i] = b;
    }
}

float dist(int start, int end) {
    return sqrt((x[start] - x[end]) * (x[start] - x[end]) +
                (y[start] - y[end]) * (y[start] - y[end]));
}

void make_graph() {
    for (int i = 0; i < n; i++) {
        for (int j = i + 1; j < n; j++) {
            g->add_verb(i, j, dist(i, j));
        }
    }
}

void prim() {
    for (int i = 0; i < n; i++) {
        int v = -1;
        for (int j = 0; j < n; j++) {
            if (!visited[j] && (v == -1 || min[j] < min[v])) {
                v = j;
            }
        }

        visited[v] = true;
        if (sel[v] != -1)
            ans[v] = sel[v];

        for (int j = 0; j < n; j++) {
//            if (g->gv[v][j] < min[j]) {
            if (dist(v, j) < min[j]) {
                min[j] = dist(v, j);
                sel[j] = min[j];
            }
        }
    }
}

int main() {
    std::cin >> n;

    ans = vector<float>(n, 0);

    visited = new bool[n];
    std::fill(visited, visited + n, false);
    min = new float[n];
    std::fill(min, min + n, INF);
    sel = new float[n];
    std::fill(sel, sel + n, -1);

    min[0] = 0;

    x = new int[n];
    y = new int[n];

    g = new graph(n);

    read();
//    make_graph();
    prim();

    float answer = 0;

    for (float i : ans)
        answer += i;

    std::cout << answer;

    delete[] visited;
    delete[] min;
    delete[] sel;
    delete[] x;
    delete[] y;
}


