#include <iostream>
#include <fstream>
#include <vector>
#include <set>
#include <queue>
#include <algorithm>
#include <map>
#include <list>

using namespace std;

int n, m, a, b, c, d;

struct  verb {
    int up = -1;
    int suf = -1;
    int parent;
    char char_parent;
    map<char, int> char_map;
    map<char, int> son;
    vector<int> term;
    int visited = 0;

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
    vector<int> term;

    axo_tree() {
        verbs.push_back(verb(-1, 0));
    }

    void add(string &str) {
        int cur = 0;
        for (char ch : str) {
            if (get(verbs[cur], ch) != -1) {
                cur = get(verbs[cur], ch);
            } else {
                verbs[cur].son[ch] = verbs.size();
                verbs[cur].char_map[ch] = verbs.size();
                verbs.push_back(verb(cur, ch));
                cur = verbs.size() - 1;
            }
        }
        verbs[cur].term.push_back(last++);
        term.push_back(cur);
    }

    vector<int> find(string &str) {
        vector<int> ans(last, 0);
        int cur = 0;
        verbs[0].visited = true;
        for (char ch : str) {
            cur = getLink(cur, ch);
            verbs[cur].visited += 1;
        }

        list<int> r = {0};
        for (auto it = r.begin(); it != r.end(); ++it)
            for (auto j : verbs[*it].son)
                r.push_back(j.second);
        reverse(r.begin(), r.end());
        for (auto i : r)
            verbs[getSuffLink(i)].visited += verbs[i].visited;

        for (int i = 0; i < last; ++i) {
            ans[i] = verbs[term[i]].visited;
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
    string name = "search5";
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
        out << i << " ";
    out.close();

//    cin >> n;
//    for (int i = 0; i < n; ++i) {
//        cin >> s;
//        tree.add(s);
//    }
//    cin >> s;
//    for (auto i : tree.find(s))
//        cout << i << " ";
////    out.close();

}