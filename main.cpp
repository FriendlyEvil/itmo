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

struct A {
    A(int a) : a(a) {}

    void f() {
        std::cout << "test class member\n";
    }

private:
    int a;
};

void test_class_member() {
    function<void(A)> v(&A::f);
    v(A(7));
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

struct PrintNum {
    PrintNum() = default;

    PrintNum(PrintNum const &a) {
        std::cout << "const &" << std::endl;
    }

    PrintNum &operator=(const PrintNum &other) {
        std::cout << "const operator = " << std::endl;
        return *this;
    }

    PrintNum &operator=(PrintNum &other) {
        std::cout << "operator = " << std::endl;
        return *this;
    }

    PrintNum(PrintNum &&other) {
        std::cout << "&& constructor" << std::endl;
    }

    ~PrintNum() {
//        std::cout << "destr" << std::endl;
    }

    void operator()(int i) const {
        std::cout << i << " " << "one" << '\n';
    }
};

int main() {
    PrintNum m;
    function<void(int)> fun1;
    fun1 = (std::move(m));

    return 0;
    test_empty_function();
    test_sum(5, 7);
    test_class_member();
    test_lymbda();
    test_move();
    test_bool();
    test_copy();
    test_bind();
}
