#include <iostream>
#include "list.h"

void out(list<int> *l) {
    for (int i : *l) {
        std::cout << i;
    }
    std::cout << "\n";
}

int main() {
//    out(l);
    list<int> l = list<int>();
    l.push_back(1);
    l.push_back(2);
    l.push_back(3);
    l.push_front(4);
    l.push_front(5);
    l.push_front(6);
//    out(l);
    l.pop_back();
//    out(l);
    l.pop_front();
//    out(l);
    list<int> r = list<int>(l);
    out(&r);
    l.push_back(7);
    l.push_back(8);
    out(&l);
    l.splice(l.begin(), r, r.begin(), r.end());
    out(&l);
    l.erase(--l.end());
    out(&l);
    l.erase(++l.begin(), --l.end());
    out(&l);
    std::cout << "end";
}