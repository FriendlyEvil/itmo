#include <algorithm>
#include <cassert>
#include <cstdlib>
#include <vector>
#include <utility>
#include "circular_buffer.h"
#include <ctime>
#include <iostream>

#include "gtest/gtest.h"


//int main() {
//
//}

template<typename T>
bool check(T &a, T &b) {
    return a == b;
}

template<typename T>
void print(circular_buffer<T> &my) {
    std::cout << "\n";
    for (T i : my) {
        std::cout << i << " ";
    }
    std::cout << "\n";
}

template<typename T>
bool check(std::vector<T> &v1, circular_buffer<T> &v2) {
    int j = 0;
    for (T i : v2) {
        if (v1[j] != i)
            return false;
        ++j;
    }
    return true;
}

//TEST(correctness, main) {
//    circular_buffer<int> s(7);
//    for (int i = 0; i < 10; i++) {
//        s.push_back(i);
//    }
//    print(s);
//}

TEST(correctness, start_empty) {
    circular_buffer<int> s;
    EXPECT_EQ(s.empty(), true);
}


TEST(correctness, push_back_one) {
    circular_buffer<int> s(1);
    s.push_back(1);
    std::vector<int> v = {1};
    EXPECT_EQ(check<int>(s[0], v[0]), true);
}

TEST(correctness, push_front_one) {
    circular_buffer<int> s(1);
    s.push_front(1);
    std::vector<int> v = {1};
    EXPECT_EQ(check<int>(s[0], v[0]), true);
}

TEST(correctness, push_back_one_circl) {
    circular_buffer<int> s(1);
    s.push_back(2);
    s.push_back(1);
    std::vector<int> v = {1};
    EXPECT_EQ(check<int>(s[1], v[0]), true);
}

TEST(correctness, push_front_one_circl) {
    circular_buffer<int> s(1);
    s.push_front(2);
    s.push_front(1);
    std::vector<int> v = {1};
    EXPECT_EQ(check<int>(s[0], v[0]), true);
}

TEST(correctness, merge_push_one_circl) {
    circular_buffer<int> s(1);
    s.push_front(2);
    s.push_back(1);
    std::vector<int> v = {1};
    EXPECT_EQ(check<int>(s[1], v[0]), true);
}


TEST(correctness, many_push_back_one_circl) {
    circular_buffer<int> s(8);
    std::vector<int> v;
    for (int i = 0; i < 8; i++) {
        s.push_back(i);
        v.push_back(i);
    }
    EXPECT_EQ(check(v, s), true);
}

TEST(correctness, many_push_front_one_circl) {
    circular_buffer<int> s(8);
    std::vector<int> v;
    for (int i = 0; i < 8; i++) {
        s.push_front(i);
        v.push_back(7 - i);
    }
    EXPECT_EQ(check(v, s), true);
}

TEST(correctness, clear) {
    circular_buffer<int> s(8);
    std::vector<int> v;
    for (int i = 0; i < 8; i++) {
        s.push_back(i);
    }
    s.clear();
    EXPECT_EQ(s.empty(), true);
    for (int i = 0; i < 8; i++) {
        s.push_front(i);
    }
    s.clear();
    EXPECT_EQ(s.empty(), true);
}

TEST(correctness, pop_back) {
    circular_buffer<int> s(8);
    std::vector<int> v;
    for (int i = 0; i < 8; i++) {
        s.push_back(i);
    }
    s.clear();
    EXPECT_EQ(s.empty(), true);
    for (int i = 0; i < 8; i++) {
        s.push_front(i);
    }
    s.clear();
    EXPECT_EQ(s.empty(), true);
}

TEST(correctness, erase) {
    circular_buffer<int> s;
    s.push_back(1);
    s.erase(s.begin());
    EXPECT_EQ(s.empty(), true);
}

TEST(correctness, insert) {
    circular_buffer<int> s;
    s.push_back(1);
    s.push_back(2);
    s.push_back(3);
    s.push_back(4);
    s.insert(s.begin() + 2, 5);
//    print(s);
    std::vector<int> v = {1, 2, 5, 3, 4};
    EXPECT_EQ(check(v, s), true);
}

TEST(correctness, insert1) {
    circular_buffer<int> s;
    s.push_back(1);
    s.push_back(2);
    s.push_back(3);
    s.push_back(4);
    s.insert(s.begin() + 3, 5);
//    print(s);
    std::vector<int> v = {1, 2, 3, 5, 4};
    EXPECT_EQ(check(v, s), true);
}

TEST(correctness, insert_erase) {
    circular_buffer<int> s;
    s.push_back(1);
    s.push_back(2);
    s.push_back(3);
    s.push_back(4);
    s.erase(s.insert(s.begin() + 3, 5));
//    print(s);
    std::vector<int> v = {1, 2, 3, 4};
    EXPECT_EQ(check(v, s), true);
}

TEST(correctness, insert_erase1) {
    circular_buffer<int> s;
    s.push_back(1);
    s.push_back(2);
    s.push_back(3);
    s.push_back(4);
    s.insert(s.erase(s.insert(s.begin() + 3, 5)), 5);
//    print(s);
    std::vector<int> v = {1, 2, 3, 5, 4};
    EXPECT_EQ(check(v, s), true);
}























