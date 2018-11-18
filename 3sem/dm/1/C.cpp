#include <iostream>
#include <fstream>
#include <vector>
#include <deque>
#include <algorithm>
#include <string>

int n;

void read() {
    std::cin >> n;
}

bool ask(int first, int second) {
    std::cout << 1 << " " << first + 1 << " " << second + 1 << std::endl;
    std::cout.flush();
    std::string str;
    std::cin >> str;
    return str == "YES";
}

bool comp(const int &first, const int &second) {
    return ask(first, second);
}

int main() {
    read();
    std::deque<int> queue;

    for (int i = 0; i < n; i++) {
        queue.push_back(i);
    }


    std::stable_sort(queue.begin(), queue.end(), &comp);


    std::cout << 0 << " ";
    for (int i : queue)
        std::cout << i + 1 << " ";
    std::cout.flush();

}