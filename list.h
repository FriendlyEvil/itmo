#ifndef EXAM_LIST_H
#define EXAM_LIST_H

#include <iterator>
#include <iostream>
#include <cassert>

template<typename T>
class list {
    struct node {
        node *next, *prev;
        T value;

        node() : next(nullptr), prev(nullptr) {}

        node(const T &data) : next(nullptr), prev(nullptr), value(data) {}

        node(const T &data, node *prev, node *next) : next(next), prev(prev), value(data) {}

        ~node() = default;
    } neutral_node;

    node *neutral = &neutral_node;
public:
    template<typename S>
    struct iterator {
        node *temp;

        iterator(node *data) : temp(data) {}

        template<typename C>
        iterator(const iterator<C> &data) {
            if (std::is_same<S, const C>::value) {
                temp = data.temp;
            } else {
                std::cerr << "Incorrect cast";
                exit(1);
            }
        }

        S &operator*() {
            return temp->value;
        }

        iterator &operator++() {
            temp = temp->next;
            return *this;
        }

        iterator &operator--() {
            temp = temp->prev;
            return *this;
        }

        iterator operator++(int) {
            node *cur = temp;
            temp = temp->next;
            return iterator(cur);
        }

        iterator operator--(int) {
            node *cur = temp;
            temp = temp->prev;
            return iterator(cur);
        }

        bool operator==(iterator second) {
            return temp == second.temp;
        }

        bool operator!=(iterator second) {
            return temp != second.temp;
        }
    };

    typedef iterator<const T> const_iterator;
    typedef std::reverse_iterator<iterator<T>> reverse_iterator;
    typedef std::reverse_iterator<const_iterator> const_reverse_iterator;

    list() {
        neutral_node.next = neutral;
        neutral_node.prev = neutral;
    }

    list(const list &other) : list() {
        node *oth = other.neutral->next;
        while (oth != other.neutral) {
            push_back(oth->value);
            oth = oth->next;
        }
    }

    ~list() {
        clear();
    }

    list &operator=(const list &other) {
        list oth(other);
        swap(oth);
        return *this;
    }

    void swap(list &other) {
        std::swap(neutral, other.neutral);
    }

    void clear() {
        while (!empty()) {
            pop_back();
        }
    }

    bool empty() const {
        return neutral->prev == neutral;
    }

    void push_back(const T &data) {
        node *new_node_ptr = new node(data, neutral->prev, neutral);
//        node *new_node_ptr = &new_node;
        neutral->prev->next = new_node_ptr;
        neutral->prev = new_node_ptr;
    }

    void pop_back() {
        node *dlt = neutral->prev;
        dlt->prev->next = neutral;
        neutral->prev = dlt->prev;
        delete dlt;
    }

    T &back() {
        return neutral->prev->value;
    }

    void push_front(const T &data) {
        node *new_node_ptr = new node(data, neutral, neutral->next);
//        node *new_node_ptr = &new_node;
        neutral->next->prev = new_node_ptr;
        neutral->next = new_node_ptr;
    }

    void pop_front() {
        node *dlt = neutral->next;
        dlt->next->prev = neutral;
        neutral->next = dlt->next;
        delete dlt;
    }

    T &front() {
        return (node *) neutral->next->value;
    }

    iterator<T> begin() {
        return iterator<T>(neutral->next);
    }

    const_iterator begin() const {
        return const_iterator(neutral->next);
    }

    reverse_iterator rbegin() {
        reverse_iterator(end());
    }

    const_reverse_iterator rbegin() const {
        const_reverse_iterator(end());
    }

    iterator<T> end() {
        return iterator<T>(neutral);
    }

    const_iterator end() const {
        return const_iterator(neutral);
    }

    reverse_iterator rend() {
        reverse_iterator(begin());
    }

    const_reverse_iterator rend() const {
        const_reverse_iterator(begin());
    }

    iterator<T> insert(const_iterator pos, const T &data) {
        node *new_node_ptr = new node(data, pos.temp->prev, pos.temp);
//        node *new_node_ptr = &new_node;
        pos.temp->prev->next = new_node_ptr;
        pos.temp->prev = new_node_ptr;
        return iterator<T>(new_node_ptr);
    }

    iterator<T> erase(const_iterator pos) {
        iterator<T> new_iter = iterator<T>(pos.temp->next);
        pos.temp->prev->next = pos.temp->next;
        pos.temp->next->prev = pos.temp->prev;
        //assert(new_iter == ++pos);
        delete pos.temp;
        return new_iter;
    }

    iterator<T> erase(const_iterator first, const_iterator last) {
        iterator<T> iter = iterator<T>(first.temp);
        while (last != iter) {
            iter = erase(iter);
        }
//        iter = erase(iter);
        return iter;
    }

    void splice(const_iterator pos, list &oth, const_iterator first, const_iterator last) {
        node *back = last.temp->prev;
        first.temp->prev->next = last.temp;
        last.temp->prev = first.temp->prev;

        node *cur = pos.temp->prev;
        pos.temp->prev = back;
        back->next = pos.temp;

        cur->next = first.temp;
        first.temp->prev = cur;
    }

};

#endif //EXAM_LIST_H
