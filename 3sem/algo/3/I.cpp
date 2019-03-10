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
    vector<int> size_str;
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
    bool flag;

    axo_tree(bool flag) : flag(flag) {
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
        verbs[cur].size_str.push_back(str.length());
    }

    vector<int> find(string &str) {
        vector<int> ans(last, -1);
        int cur = 0;
        int fin = 0;
        verbs[0].visited = true;
        for (char ch : str) {
            cur = getLink(cur, ch);
            for (int i = cur; i > 0 && !verbs[i].visited ; i = getUp(i)) {
                verbs[i].visited = true;
                for (int j = 0; j < verbs[i].term.size(); ++j) {
                    if (flag)
                        ans[verbs[i].term[j]] = fin - verbs[i].size_str[j] + 1;
                    else
                        ans[verbs[i].term[j]] = - (fin + 1) + str.length();
                }
            }
            verbs[cur].visited = true;
            fin++;
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
    string name = "search6";
    ifstream in(name + ".in");
    ofstream out(name + ".out");
    string s, p, t;
    axo_tree tree1(true);
    axo_tree tree2(false);
    in >> n;
    for (int i = 0; i < n; ++i) {
        in >> s;
        tree1.add(s);
        reverse(s.begin(), s.end());
        tree2.add(s);
    }
    in >> s;
    auto first = tree1.find(s);
    reverse(s.begin(), s.end());
    auto second = tree2.find(s);
    for (int i = 0;i < n; i++)
        out << first[i] << " " << second[i] << "\n";
    out.close();

}