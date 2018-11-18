#include <iostream>
#include <vector>
#include <algorithm>
#include <set>

using std::vector;
using std::set;
using std::pair;

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
graph *mat;
int n;

void read() {
    int a;
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            std::cin >> a;
            g->add_verb(i, j, a);
            mat->add_verb(i, j, a);
        }
    }
}

void fill() {
}

void floyd() {
    for (int i = 0; i < n; ++i) {
        for (int j = 0; j < n; ++j) {
            for (int k = 0; k < n; ++k) {
                mat->gv[j][k] = std::min(mat->gv[j][k], mat->gv[j][i] + mat->gv[i][k]);
            }
        }
    }
}

int main() {
    std::cin >> n;
    g = new graph(n);
    mat = new graph(n);

    read();
    fill();

    floyd();


    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; ++j) {
            std::cout << mat->gv[i][j] << " ";
        }
        std::cout << std::endl;
    }

}
