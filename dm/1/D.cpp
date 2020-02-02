#include <iostream>
#include <fstream>
#include <vector>
#include <deque>
#include <algorithm>
#include <string>

const std::string file = "guyaury";
const std::string file_in = file + ".in";
const std::string file_out = file + ".out";

std::fstream in(file_in);
std::ofstream out(file_out);

int n;

std::vector<std::vector<bool>> verb;

void fill() {
    verb = std::vector<std::vector<bool>>(n, std::vector<bool>(n, false));
}

void read() {
//    std::rand

    in >> n;
    fill();

    std::string str;
    for (int i = 1; i < n; i++) {
        in >> str;
        for (int j = 0; j < i; j++) {
            verb[i][j] = str[j] - '0';
            verb[j][i] = !verb[i][j];
        }
    }
}

bool comp(int& first, int& second) {
    if (verb[first][second])
        return true;
    return false;
}

int main() {
    read();
    std::deque<int> queue;

    for (int i = 0; i < n; i++) {
        queue.push_back(i);
    }

    start:

    std::sort(queue.begin(), queue.end(), &comp);

    if (!verb[queue.back()][queue[0]]) {
        goto start;
    }


    for (int i : queue)
        out << i + 1 << " ";

}