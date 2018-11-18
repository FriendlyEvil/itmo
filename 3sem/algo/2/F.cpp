#include <iostream>
#include <vector>
#include <algorithm>
#include <set>

using std::vector;
using std::set;
using std::pair;

const long long INF = INT64_MAX;

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

bool *visited;
graph *g;
vector<long long> ans1;
vector<long long> ans2;
int n, m;

void read() {
    int a, b, c;
    for (int i = 0; i < m; i++) {
        std::cin >> a >> b >> c;
        g->add_verb(a - 1, b - 1, c);
    }
}

void fill() {
    visited = new bool[n];
    std::fill(visited, visited + n, false);
    ans1 = vector<long long>(n, INF);
    ans2 = vector<long long>(n, INF);
}

void dekstra(int s, vector<long long>& ans) {
    set<pair<long long, int>> st;
    ans[s] = 0;
    for (int i = 0; i < n; i++) {
        st.emplace(std::make_pair(ans[i], i));
    }
    for (int i = 0; i < n; ++i) {
        int v = st.begin()->second;
        if (ans[v] == INF)
            break;
        visited[v] = true;
        st.erase(st.begin());
        for (std::pair<int, int> &k : g->gv[v]) {
            if (ans[v] + k.second < ans[k.first]) {
                st.erase(std::make_pair(ans[k.first], k.first));
                ans[k.first] = ans[v] + k.second;
                if (!visited[k.first])
                    st.emplace(std::make_pair(ans[k.first], k.first));
            }
        }
    }
}

int main() {
    std::ios_base::sync_with_stdio(false);
    std::cin.tie(NULL);

    std::cin >> n >> m;
    fill();
    g = new graph(n);
    read();
    int a, b, c;
    std::cin >> a >> b >> c;
    a--; b--; c--;

    dekstra(a, ans1);
    std::fill(visited, visited + n, false);
    dekstra(b, ans2);

    long long ab = ans1[b];
    long long ac = ans1[c];
    long long bc = ans2[c];
    if (ab >= INF || ac >= INF || bc >= INF) {
        std::cout << -1;
    } else {
        long long ans = std::min(std::min(ab + bc, ac + bc), ab + ac);
        std::cout << ans;
    }

}
