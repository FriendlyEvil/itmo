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
    string s, p, t;
//    cin >> p >> t;
//    s = p + "#" + t;
    cin >> s;

    vector<int> z(s.length());
    z[0] = 0;
    int l = 0, r = 0;
    for (int i = 1; i < s.length(); ++i) {
        z[i] = max(0, min((r - i), z[i - l]));
        while (i + z[i] < s.length() && s[z[i]] == s[i + z[i]])
            z[i]++;
        if (i + z[i] > r) {
            l = i;
            r = i + z[i];
        }
    }

    bool f = true;

    for (int i = 1; i < s.length(); ++i) {
        bool flag = false;
        for (int j = i; j < s.length(); j += i) {
            if (z[j] < i) {
                flag = true;
                break;
            }
        }
        if (!flag) {
            cout << i;
            f = false;
            break;
        }
    }

    if (f)
        cout << s.length();

}