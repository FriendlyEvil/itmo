#include <iostream>
#include "functional"
#include "function.h"
#include <cassert>

void f() {
    std::cout << "empty function\n";
}

void test_empty_function() {
    function<void()> v(f);
    f();
}

int sum(int a, int b) {
    return a + b;
}

void test_sum(int a, int b) {
    function<int(int, int)> v (sum);
    std::cout << "sum test:\n";
    std::cout << a << " + " << b << " = " << v(a, b) << "\n";
}

void test_lymbda() {
    function<void(int)> v ([](int a) { std::cout << "Lymbda : " << a << "\n"; });
    v(5);
}

template<typename R, typename... Args>
void mv(function<R(Args...)> &&f, Args... args) {
    f(args...);
}

void test_move() {
    function<void(int)> v = [](int a) { std::cout << "Test move " << a << "\n"; };
    mv<void, int>(std::move(v), 19);
}

void test_bool() {
    std::cout << "test bool\n";
    function<void()> v;
    assert(static_cast<bool>(v) == false);
    function<void(int, bool)> v1 = nullptr;
    assert(static_cast<bool>(v1) == false);
    function<void()> v2 = [](){};
    assert(static_cast<bool>(v2) == true);
}

void test_copy() {
    function<void()> v = [](){std::cout << "test copy\n";};
    auto v1(v);
    v1();
    v();
}

void print(int a) {
    std::cout << a;
}

void test_bind() {
    std::cout << "Test bind: \n";
    function<void()> v = std::bind(print, 51);
    v();
    std::cout << "\n";
}

int main() {
    test_empty_function();
    test_sum(5, 7);
    test_lymbda();
    test_move();
    test_bool();
    test_copy();
    test_bind();
}
