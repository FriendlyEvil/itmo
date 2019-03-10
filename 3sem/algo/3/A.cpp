#include <iostream>
#include <fstream>
#include <vector>
#include <set>
#include <queue>
#include <algorithm>

using namespace std;

int n, m, a, b, c, d;
const int p = 31;
vector<long long> has;
vector<long long> pp;

long long get_hash(int i, int j) {
    if (i == 0)
        return has[j] * pp[n - 1];
    return (has[j] - has[i - 1]) * pp[n - i - 1];
}

int main() {
//    string name = "cycles";
//    ifstream in(name + ".in");
//    ofstream out(name + ".out");
    string s;
    cin >> s;
    n = s.length();
    long long h = 0, pow = 1;
    pp.push_back(1);
    for (int i = 0; i < s.length(); ++i) {
        h += (s[i] - 'a' + 1) * pow;
        pow *= p;
        pp.push_back(pow);
        has.push_back(h);
    }
    
    cin >> m;

    for (int i = 0; i < m; ++i) {
        cin >> a >> b >> c >> d;
        if (get_hash(a - 1, b - 1) == get_hash(c - 1, d - 1))
            cout << "Yes\n";
        else
            cout << "No\n";
    }

}