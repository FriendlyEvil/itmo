#include <iostream>
#include <fstream>
#include <vector>
#include <deque>
#include <algorithm>
#include <string>

const std::string file_in = "fullham.in";
const std::string file_out = "fullham.out";

std::fstream in (file_in);
std::ofstream out (file_out);

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
    for (int i = 1 ; i < n; i++) {
            in >> str;
        for (int j = 0; j < i; j++) {
            verb[i][j] = str[j] - '0';
            verb[j][i] = verb[i][j];
        }
    }
}

int main() {
    read();
    std::deque<int> queue;

    for (int i = 0; i < n; i++) {
        queue.push_back(i);
    }
    for (int i = 0; i < n * (n - 1); i++) {
        int k = 1;
        if (!verb[queue[0]][queue[1]]) {
            k = 2;
            while (!verb[queue[0]][queue[k]] || !verb[queue[1]][queue[k + 1]]) {
                k++;
            }
        }
        std::reverse(queue.begin() + 1, queue.begin() + k + 1);
        queue.push_back(queue.front());
        queue.pop_front();
    }

    for (int i : queue)
        out << i + 1 << " ";

}