#include <iostream>
#include <fstream>
#include <vector>
#include <set>
#include <queue>
#include <algorithm>

using namespace std;

int n, m, a, b, c, d;
const int p = 31;


int main() {
//    string name = "cycles";
//    ifstream in(name + ".in");
//    ofstream out(name + ".out");
    string s;
    cin >> s;

    vector<int> p(s.length());
    p[0] = 0;
    int l = 0, r = 0;
    for (int i = 1; i < s.length(); ++i) {
        p[i] = max(0, min((r - i), p[i - l]));
        while (i + p[i] < s.length() && s[p[i]] == s[i + p[i]])
            p[i]++;
        if (i + p[i] > r) {
            l = i;
            r = i + p[i];
        }
        cout << p[i] << " ";
    }


}