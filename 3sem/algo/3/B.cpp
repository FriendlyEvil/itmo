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
    cout << 0 << " ";
    for (int i = 1; i < s.length(); ++i) {
        int k = p[i - 1];
        while (k > 0 && s[i] != s[k])
            k = p[k - 1];
        if (s[i] == s[k])
            k++;
        p[i] = k;
        cout << k << " ";
    }


}