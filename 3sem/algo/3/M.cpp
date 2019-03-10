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
    int len, suf;
    int count = -1;
    string res;
    map<char, int> char_map;

    verb(int len, int suf) : len(len), suf(suf) {}

    verb(int len) : len(len) {}
};

struct suffix_tree {
    vector<verb> verbs;
    int last = 0;

    suffix_tree() {
        verbs.push_back(verb(0, -1));
    }

    void add(char c) {
        int new_last = static_cast<int>(verbs.size());
        verbs.push_back(verb(verbs[last].len + 1));
        int p;
        for (p = last; p != -1 && verbs[p].char_map.find(c) ==
                                  verbs[p].char_map.end();
             p = verbs[p].suf) {
            verbs[p].char_map[c] = static_cast<int>(verbs.size() - 1);
        }
        if (p == -1)
            verbs.back().suf = 0;
        else {
            int tran = verbs[p].char_map[c];
            if (verbs[p].len == verbs[tran].len - 1)
                verbs.back().suf = tran;
            else {
                verbs.push_back(verb(verbs[p].len + 1, verbs[tran].suf));
                verbs.back().char_map = verbs[tran].char_map;
                while (p != -1 && verbs[p].char_map[c] == tran) {
                    verbs[p].char_map[c] = static_cast<int>(verbs.size() - 1);
                    p = verbs[p].suf;
                }
                verbs[tran].suf = verbs[verbs.size() - 2].suf = static_cast<int>(verbs.size() - 1);
            }
        }
        last = new_last;
    }
};

pair<int, string> lcs(suffix_tree &tree, int v, int count) {
    int ress = 0;
    int full = (1 << count) - 1;
    string max;

    for (auto i : tree.verbs[v].char_map) {
        if (i.first < count) {
            ress |= (1 << i.first);
        } else {
            int w;
            string s;
            if (tree.verbs[i.second].count == -1) {
                auto temp = lcs(tree, i.second, count);
                w = temp.first;
                s = temp.second;
            } else {
                w = (tree.verbs[i.second].count);
                s = tree.verbs[i.second].res;
            }
            if (w == full) {
                if (s.size() >= max.size()) {
                    max = s;
                    max.push_back(i.first);
                }
            } else
                ress |= w;
        }
    }

    if (max.size() > 0) {
        tree.verbs[v].count = full;
        tree.verbs[v].res = max;
        return {full, max};
    }
    tree.verbs[v].count = ress;
    return {ress, ""};
}

//int64_t count_str(suffix_tree &tree, int v) {
//    if (tree.verbs[v].count != -1)
//        return tree.verbs[v].count;
//    int64_t res = 1;
//    for (auto i : tree.verbs[v].char_map)
//        res += count_str(tree, i.second);
//    tree.verbs[v].count = res;
//    return res;
//}



int main() {
    string name = "common";
    ifstream in(name + ".in");
    ofstream out(name + ".out");
    string s, p, t;
//    cin >> p >> t;
//    s = p + "#" + t;
//    cin >> n;
    suffix_tree tree;
//    for (int i = 0; i < n; ++i) {
    in >> s;
    for (auto j : s)
        tree.add(j);
    tree.add(0);
    in >> s;
//    for (auto j : s)
//        tree.add(j);
//    tree.add(1);
////        tree.add(i);
////    }
//    t = lcs(tree, 0, 2).second;
//    reverse(t.begin(), t.end());
//    out << t;
    for (int i = 0; i < s.size(); ++i) {

    }

}