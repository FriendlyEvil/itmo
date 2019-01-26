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
    function() noexcept: callable(nullptr), is_small(false) {};

    function(std::nullptr_t) noexcept : callable(nullptr), is_small(false) {};

    function(const function &other) {
        auto *tmp = reinterpret_cast<const function_base *>(other.buf);
        is_small = other.is_small;
        if (is_small) {
            tmp->do_small_copy(buf);
        } else {
            tmp->do_big_copy(buf);
        }
    };

    function(function &&other) noexcept {
        is_small = false;
        new(buf) std::unique_ptr<function_base>(nullptr);
        swap(other);
    };

    template<typename F>
    function(F f) {
        if constexpr (sizeof(f) < MAX) {
            is_small = true;
            new(buf) function_holder<F>(std::move(f));
        } else {
            is_small = false;
            new(buf) std::unique_ptr<function_holder<F>>(new function_holder<F>(std::move(f)));
        }
    };

    function &operator=(const function &other) {
        function tmp(other);
        swap(tmp);
        return *this;
    }

    function &operator=(function &&other) noexcept {
        function tmp(std::move(other));
        swap(tmp);
        return *this;
    }

    void swap(function &other) noexcept {
        function tmp(std::move(other));
        other = std::move(*this);
        *this = std::move(tmp);
    }

    explicit operator bool() const noexcept {
        if (!is_small)
            return static_cast<bool>(callable);
        return true;
    }

    R operator()(Args... args)  const {
        if (is_small) {
            auto m = reinterpret_cast<const function_base*>(buf);
            return m->call(std::forward<Args>(args)...);
        }
        return callable->call(std::forward<Args>(args)...);
    }
    ~function() {
        if (is_small) {
            reinterpret_cast<function_base*>(buf)->~function_base();
        } else {
            callable.reset();
        }
    }


private:
    class function_base {
    public:
        function_base() {};

        function_base(const function_base &) = delete;

        void operator=(const function_base &) = delete;

        virtual ~function_base() {};

        virtual R call(Args... args) const = 0;

        virtual void do_small_copy(void *buf) const = 0;

        virtual void do_big_copy(void *buf) const = 0;
    };


    template<typename F>
    class function_holder : public function_base {
    public:
        function_holder(F func) : function_base(), func(std::move(func)) {}

        R call(Args... args) const {
            return func(std::forward<Args>(args)...);
        }

        void do_small_copy(void *buf) const {
            new(buf) function_holder<F>(func);
        }

        void do_big_copy(void *buf) const {
            new(buf) std::unique_ptr<function_holder<F>>(new function_holder<F>(func));
        }

        ~function_holder() = default;

    private:
        F func;
    };

private:
    static const int MAX = 64;
    union {
        std::unique_ptr<function_base> callable;
        char buf[MAX];
    };

    bool is_small;
};


#endif //EXAM_FUNCTION_H
