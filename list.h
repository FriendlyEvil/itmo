#ifndef EXAM_LIST_H
#define EXAM_LIST_H

#include <iterator>
#include <iostream>
#include <cassert>

template<typename T>
class list {
    struct node1 {
        node1 *next, *prev;

        node1() : next(nullptr), prev(nullptr) {}

        node1(node1 *prev, node1 *next) : next(next), prev(prev) {}
    } neutral_node;

    struct node : node1 {
        T value;

        node(const T &data) : node1(), value(data) {}

        node(const T &data, node1 *prev, node1 *next) : node1(prev, next), value(data) {}

        ~node() = default;
    };

    node1 *neutral = &neutral_node;
public:
    template<typename S>
    struct iterator {
        node1 *temp;

        iterator(node1 *data) : temp(data) {}

        template<typename C>
        iterator(const iterator<C> &data) {
            temp = data.temp;
        }

        S &operator*() {
            return ((node *) temp)->value;
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

        bool operator==(iterator second) const{
            return temp == second.temp;
        }

        bool operator!=(iterator second) const{
            return temp != second.temp;
        }
    };

    typedef iterator<T> iterator;
    typedef iterator<const T> const_iterator;
    typedef std::reverse_iterator<iterator<T>> reverse_iterator;
    typedef std::reverse_iterator<const_iterator> const_reverse_iterator;

    list() {
        neutral_node.next = neutral;
        neutral_node.prev = neutral;
    }

    list(const list &other) : list() {
        node1 *oth = other.neutral->next;
        while (oth != other.neutral) {
            push_back(((node *) oth)->value);
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
        node *dlt = ((node *) neutral->prev);
        dlt->prev->next = neutral;
        neutral->prev = dlt->prev;
        delete dlt;
    }

    T &back() {
        return ((node *) neutral->prev)->value;
    }

    void push_front(const T &data) {
        node1 *new_node_ptr = new node(data, neutral, neutral->next);
//        node *new_node_ptr = &new_node;
        neutral->next->prev = new_node_ptr;
        neutral->next = new_node_ptr;
    }

    void pop_front() {
        node *dlt = ((node *) neutral->next);
        dlt->next->prev = neutral;
        neutral->next = dlt->next;
        delete dlt;
    }

    T &front() {
        return (node *) ((node *) neutral->next)->value;
    }

    iterator begin() {
        return iterator(neutral->next);
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

    iterator end() {
        return iterator(neutral);
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

    iterator insert(const_iterator pos, const T &data) {
        node *new_node_ptr = new node(data, pos.temp->prev, pos.temp);
//        node *new_node_ptr = &new_node;
        pos.temp->prev->next = new_node_ptr;
        pos.temp->prev = new_node_ptr;
        return iterator(new_node_ptr);
    }

    iterator erase(const_iterator pos) {
        iterator new_iter = iterator(pos.temp->next);
        pos.temp->prev->next = pos.temp->next;
        pos.temp->next->prev = pos.temp->prev;
        //assert(new_iter == ++pos);
        delete (node *) pos.temp;
        return new_iter;
    }

    iterator erase(const_iterator first, const_iterator last) {
        iterator iter = iterator(first.temp);
        while (last != iter) {
            iter = erase(iter);
        }
//        iter = erase(iter);
        return iter;
    }

    void splice(const_iterator pos, list &oth, const_iterator first, const_iterator last) {
        node1 *back = last.temp->prev;
        first.temp->prev->next = last.temp;
        last.temp->prev = first.temp->prev;

        node1 *cur = pos.temp->prev;
        pos.temp->prev = back;
        back->next = pos.temp;

        cur->next = first.temp;
        first.temp->prev = cur;
    }

};

#endif //EXAM_LIST_H
