#include <iostream>
#include <fstream>
#include <vector>
#include <set>
#include <queue>
#include <algorithm>
#include <map>

using namespace std;

int n, m, a, b, c, d;

struct  verb {
    int up = -1;
    int suf = -1;
    int parent;
    char char_parent;
    map<char, int> char_map;
    vector<int> term;
    bool visited = false;

    verb(int par, char p) : parent(par), char_parent(p) {}
};

int get(verb &v, char c) {
    auto it = v.char_map.find(c);
    if (it != v.char_map.end())
        return it->second;
    return -1;
}

struct axo_tree {
    vector<verb> verbs;
    int last = 0;

    axo_tree() {
        verbs.push_back(verb(-1, 0));
    }

    void add(string &str) {
        int cur = 0;
        for (char ch : str) {
            if (get(verbs[cur], ch) != -1) {
                cur = get(verbs[cur], ch);
            } else {
                verbs[cur].char_map[ch] = verbs.size();
                verbs.push_back(verb(cur, ch));
                cur = verbs.size() - 1;
            }
        }
        verbs[cur].term.push_back(last++);
    }

    vector<bool> find(string &str) {
        vector<bool> ans(last, false);
        int cur = 0;
        verbs[0].visited = true;
        for (char ch : str) {
            cur = getLink(cur, ch);
            for (int i = cur; i > 0 && !verbs[i].visited ; i = getUp(i)) {
                verbs[i].visited = true;
                for (int j : verbs[i].term)
                    ans[j] = true;
            }
            verbs[cur].visited = true;
        }
        return ans;
    }

    int getSuffLink(int v) {
        if (verbs[v].suf == -1) {
            if (verbs[v].parent == -1 || verbs[v].parent == 0)
                verbs[v].suf = 0;
            else
                verbs[v].suf = getLink(getSuffLink(verbs[v].parent), verbs[v].char_parent);
        }
        return verbs[v].suf;
    }

    int getLink(int v, char c) {
        if (get(verbs[v], c) == -1) {
            if (get(verbs[v], c) == 0)
                verbs[v].char_map[c] = 0;
            else
                verbs[v].char_map[c] = getLink(getSuffLink(v), c);
        }
        return verbs[v].char_map[c];
    }

    int getUp(int v) {
        if (verbs[v].up == -1) {
            if (getSuffLink(v) == 0) {
                verbs[v].up = 0;
            } else if (verbs[getSuffLink(v)].term.size() > 0) {
                verbs[v].up = getSuffLink(v);
            } else {
                verbs[v].up = getUp(getSuffLink(v));
            }
        }
        return  verbs[v].up;
    }
};

int main() {
    string name = "search4";
    ifstream in(name + ".in");
    ofstream out(name + ".out");
    string s, p, t;
    axo_tree tree;
    in >> n;
    for (int i = 0; i < n; ++i) {
        in >> s;
        tree.add(s);
    }
    in >> s;
    for (auto i : tree.find(s))
        out << (i ? "YES\n" : "NO\n");
    out.close();

}