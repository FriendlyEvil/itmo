#include <iostream>
#include <fstream>
#include <vector>
#include <set>
#include <queue>
#include <algorithm>

using namespace std;

int n, m, a, b, d;
const int p = 31;
vector<int> pn, c, cnt, pnn, cn;


int main() {
    string name = "array";
    ifstream in(name + ".in");
    ofstream out(name + ".out");
    string s;
//    , p, t;
//    cin >> p >> t;
//    s = p + "#" + t;
    in >> s;
    s += "#";
    n = s.length();
    pn = vector<int>(n, 0);
    c = vector<int>(n, 0);
    cnt = vector<int>(n, 0);
    pnn = vector<int>(n, 0);
    cn = vector<int>(n, 0);

    for (int i = 0; i < s.length(); ++i) {
        cnt[s[i]]++;
    }
    for (int i = 1; i < p; ++i) {
        cnt[i] += cnt[i - 1];
    }
    for (int i = 0; i < s.length(); ++i) {
        cnt[s[i]]--;
        pn[cnt[s[i]]] = i;
    }
    c[pn[0]] = 0;
    int num = 1;
    for (int j = 0; j < s.length(); ++j) {
        if (s[pn[j]] != s[pn[j - 1]])
            num++;
        c[pn[j]] = num - 1;
    }

    for (int k = 0; (1 << k) < s.length(); ++k) {
        for (int i = 0; i < n; ++i) {
            pnn[i] = pn[i] - (1 << k);
            if (pnn[i] < 0) pnn[i] += s.length();
        }
        cnt = vector<int>(num, 0);
        for (int i = 0; i < n; ++i)
            ++cnt[c[pnn[i]]];
        for (int i = 1; i < num; ++i)
            cnt[i] += cnt[i - 1];
        for (int i = n - 1; i >= 0; --i)
            pn[--cnt[c[pnn[i]]]] = pnn[i];
        cn[pn[0]] = 0;
        num = 1;
        for (int i = 1; i < n; ++i) {
            int l = (pn[i] + (1 << k)) % n;
            int r = (pn[i - 1] + (1 << k)) % n;
            if (c[pn[i]] != c[pn[i - 1]] || c[l] != c[r])
                ++num;
            cn[pn[i]] = num - 1;
        }
        c = cn;
    }

    for (int i = 0; i < s.length() - 1; ++i) {
        out << p[i] << " ";
    }


}