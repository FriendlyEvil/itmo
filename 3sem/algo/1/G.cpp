#include <iostream>
#include <fstream>
#include <vector>
#include <map>

using namespace std;

const int cnst = 2001;

std::map<string, int> map_g;
std::map<int, string> map_g_rev;

vector<int> g[cnst];
vector<int> g_rev[cnst];
vector<bool> used(cnst);
vector<int> stack;
int component[cnst];
int comp = 0;

void dfs(int v) {
    if (!used[v]) {
        used[v] = true;
        for (int i = 0; i < g[v].size(); i++) {
            dfs(g[v][i]);
        }
        stack.push_back(v);
    }
}

void add_ans(int v) {
    if (!used[v]) {
        used[v] = true;
        component[v] = comp;
        for (int i = 0; i < g_rev[v].size(); i++) {
            add_ans(g_rev[v][i]);
        }
    }
}

int main() {
    int n, m;

    cin >> n >> m;

    string name;
    for (int i = 0; i < n; i++) {
        cin >> name;
        map_g.insert(std::make_pair(name, 2 * i));
        map_g_rev.insert(std::make_pair(2 * i, name));
    }

    for (int i = 0; i < m; i++) {
        string parse;
        cin >> parse;
        bool flag1 = (parse[0] == '+');
        int number1 = map_g[parse.substr(1, parse.size())];

        cin >> parse;
        cin >> parse;

        bool flag2 = (parse[0] == '+');
        int number2 = map_g[parse.substr(1, parse.size())];

        g[number1 + flag1].push_back(number2 + flag2);
        g[number2 + !flag2].push_back(number1 + !flag1);
        g_rev[number2 + flag2].push_back(number1 + flag1);
        g_rev[number1 + !flag1].push_back(number2 + !flag2);
    }


    for (int i = 0; i < 2 * n; i++) {
        if (!used[i]) {
            dfs(i);
        }
    }

    used.assign(static_cast<unsigned long>(2 * n), false);

    for (int i = static_cast<int>(stack.size() - 1); i >= 0; i--) {
        if (!used[stack[i]]) {
            ++comp;
            add_ans(stack[i]);
        }
    }

    for (int i = 0; i < n; i += 2) {
        if (component[i] == component[i + 1]) {
            cout << -1;
            exit(0);
        }
    }

    vector<string> buf;
    int count = 0;
    for (int i = 0; i < 2 * n; i += 2) {
        if (component[i] < component[i + 1]) {
            count++;
            buf.emplace_back(map_g_rev[i]);
        }
    }

    cout << count << endl;
    for (int i = 0; i < buf.size(); i++) {
        cout << buf[i] << '\n';
    }


}