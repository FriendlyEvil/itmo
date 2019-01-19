//
// Created by mi on 19.01.2019.
//

#ifndef EXAM_FUNCTION_H
#define EXAM_FUNCTION_H

#include <memory>
#include <iostream>

template<class>
class function;

template<typename R, typename... Args>
class function<R(Args...)> {

public:
    function() noexcept: callable() {};

    function(std::nullptr_t) noexcept : callable(nullptr) {};

    function(const function &other) : callable(other.callable->clone()) {};

    function(function &&other) noexcept {
        swap(other);
    };

    template<typename F>
    function(F f)
    {
        if (sizeof(f) < MAX) {
//            std::cout << "Small\n";
            callable = std::make_unique<function_holder_small<F>>(f);
        } else {
//            std::cout << "Big\n";
            callable = std::make_unique<function_holder_big<F>>(f);
        }
    };

    template<typename F, typename ClassF>
    function(F ClassF::* func) : callable(new class_holder<F, ClassF>(func)) {};

    function &operator=(const function &other) {
        function func(other);
        swap(func);
        return *this;
    }

    function &operator=(function &&other) noexcept {
        swap(other);
        return *this;
    }

    void swap(function &other) noexcept {
        std::swap(callable, other.callable);
    }

    explicit operator bool() const noexcept {
        return static_cast<bool>(callable);
    }

    R operator()(Args... args) {
        return callable->call(args...);
    }


private:
    class function_base {
    public:
        function_base() {};

        function_base(const function_base &) = delete;

        void operator=(const function_base &) = delete;

        virtual ~function_base() {};

        virtual R call(Args... args) = 0;

        virtual std::unique_ptr<function_base> clone() = 0;
    };


    template<typename F>
    class function_holder_small : public function_base {
    public:
        function_holder_small(F func) : function_base(), func(func) {}

        R call(Args... args) {
            return func(args...);
        }

        std::unique_ptr<function_base> clone() {
            return std::unique_ptr<function_base>(new function_holder_small(func));
        }

    private:
        F func;
    };

    template<typename F>
    class function_holder_big : public function_base {
    public:

        function_holder_big(F func) : function_base(), func(new F(func)) {}

        R call(Args... args) {
            return (*func)(args...);
        }

        std::unique_ptr<function_base> clone() {
            return std::unique_ptr<function_base>(new function_holder_big(*func));
        }

    private:
        std::unique_ptr<F> func;
    };

    template<typename F, typename ClassF, typename ... ArgsF>
    class class_holder : public function_base {
    public:
        class_holder(F ClassF::* func) : function_base(), func(func) {}

        R call(ClassF object, ArgsF ... args) {
            return (object.*func)(args...);
        }

        std::unique_ptr<function_base> clone() {
            return std::unique_ptr<function_base>(new class_holder(func));
        }

    private:
        F ClassF::* func;
    };

private:
    typedef std::unique_ptr<function_base> callable_t;
    callable_t callable;
    static const int MAX = 10;
};


#endif //EXAM_FUNCTION_H
