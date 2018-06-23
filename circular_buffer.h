#ifndef CIRCULAR_BUFFER_CIRCULAR_BUFFER_H
#define CIRCULAR_BUFFER_CIRCULAR_BUFFER_H


#include <algorithm>
#include <iterator>

template<typename T>
class circular_buffer {
    size_t left, size_, capacity;
    T *data;

    void ensure_capacity(size_t size) {
        if (size >= capacity) {
            circular_buffer<T> oth(capacity * 2 + 1);
            for (size_t i = 0, temp = left; i < size_; ++i, temp = (++temp) % capacity) {
                oth.push_back(data[temp]);
            }
            swap(*this, oth);
        }
    }


public:

    circular_buffer() : left(0), size_(0), capacity(0), data(nullptr) {}

    circular_buffer(size_t capacity) : left(0), size_(0), capacity(capacity),
                                       data(reinterpret_cast<T *>(new char[sizeof(T) * capacity])) {}


    circular_buffer(const circular_buffer &other) : left(other.left), size_(other.size_),
                                                    capacity(other.capacity), data(nullptr) {
        if (capacity == 0)
            return;
        data = reinterpret_cast<T *>(new char[sizeof(T) * capacity]);
        for (size_t i = 0, temp = left; i < size_; ++i, temp = (++temp) % capacity) {
            push_back(other.data[temp]);
        }
    }

    ~circular_buffer() {
        clear();
        delete[] data;
    }

    circular_buffer &operator=(const circular_buffer &other) {
        circular_buffer<T> oth(other);
        swap(*this, oth);
        return *this;
    }

    size_t size() {
        return size_;
    }

    bool empty() {
        return size_ == 0;
    }

    void clear() {
        while (!empty()) {
            pop_back();
        }
    }

    void push_back(const T &dat) {
        ensure_capacity(size_ + 1);
        new(&data[(left + size_) % capacity]) T(dat);
        ++size_;
    }

    void pop_back() {
        data[(left + size_ - 1) % capacity].~T();
        --size_;
    }

    void push_front(const T &dat) {
        ensure_capacity(size_ + 1);
        left = (left - 1 + capacity) % capacity;
        new(&data[left]) T(dat);
        ++size_;
    }

    void pop_front() {
        data[left].~T();
        ++left;
        left %= capacity;
        --size_;
    }

    T &front() {
        return data[left];
    }

    T &front() const {
        return data[left];
    }

    T &back() {
        return data[(left + size_ - 1) % capacity];
    }

    T &back() const {
        return data[(left + size_ - 1) % capacity];
    }

    T &operator[](size_t i) {
        return data[(left + i) % capacity];
    }

    T &operator[](size_t i) const {
        return data[(left + i) % capacity];
    }

    template<typename S>
    class iterator1 {
        friend class circular_buffer;

        typedef std::ptrdiff_t difference_type;
        typedef S value_type;
        typedef S *pointer;
        typedef S &reference;
        typedef std::bidirectional_iterator_tag iterator_category;

        T *data;
        size_t ind, left, capacity;

        iterator1(T *data, size_t ind, size_t left, size_t capacity) : data(data), ind(ind),
                                                                       left(left), capacity(capacity) {}

    public:
        template<typename C>
        iterator1(const iterator1<C> &other) {
            data = other.data;
            ind = other.ind;
            left = other.left;
            capacity = other.capacity;
        }

        reference operator*() const {
            return *(data + ((left + ind) % capacity));
        }

        pointer operator->() const {
            return (data + ((left + ind) % capacity));
        }

        iterator1 &operator++() {
            ++ind;
            return *this;
        }

        iterator1 &operator--() {
            --ind;
            return *this;
        }

        iterator1 operator++(int) {
            iterator1 cur = *this;
            ++ind;
            return cur;
        }

        iterator1 operator--(int) {
            iterator1 cur = *this;
            --ind;
            return cur;
        }

        friend iterator1 operator+(iterator1 const &oth, difference_type add) {
            return iterator1(oth.data, oth.ind + add, oth.left, oth.capacity);
        }

        friend iterator1 operator+(difference_type add, iterator1 const &oth) {
            return iterator1(oth.data, oth.ind + add, oth.left, oth.capacity);
        }

        friend iterator1 operator-(iterator1 const &oth, difference_type add) {
            return iterator1(oth.data, oth.ind - add, oth.left, oth.capacity);
        }

        friend iterator1 operator-(difference_type add, iterator1 const &oth) {
            return iterator1(oth.data, oth.ind - add, oth.left, oth.capacity);
        }

        friend iterator1 operator+=(iterator1 &oth, difference_type add) {
            oth = oth + add;
            return oth;
        }

        friend iterator1 operator-=(iterator1 &oth, difference_type add) {
            oth = oth - add;
            return oth;
        }

        friend difference_type operator-(iterator1 const &first, iterator1 const &second) {
            return first.ind - second.ind;
        }

        bool operator==(iterator1 second) const {
            return data == second.data && ind == second.ind
                   && left == second.left && capacity == second.capacity;
        }

        bool operator!=(iterator1 second) const {
            return !(*this == second);
        }
    };


    typedef iterator1<T> iterator;
    typedef iterator1<const T> const_iterator;
    typedef std::reverse_iterator<iterator> reverse_iterator;
    typedef std::reverse_iterator<const_iterator> const_reverse_iterator;

    iterator begin() {
        return iterator(data, 0, left, capacity);
    }

    const_iterator begin() const {
        return const_iterator(data, 0, left, capacity);
    }

    reverse_iterator rbegin() {
        return reverse_iterator(end());
    }

    const_reverse_iterator rbegin() const {
        return const_reverse_iterator(end());
    }

    iterator end() {
        return iterator(data, size_, left, capacity);
    }

    const_iterator end() const {
        return const_iterator(data, size_, left, capacity);
    }

    reverse_iterator rend() {
        return reverse_iterator(begin());
    }

    const_reverse_iterator rend() const {
        return const_reverse_iterator(begin());
    }

    iterator erase(const_iterator pos) {
        size_t temp = pos.ind;
        iterator cur = iterator(data, temp, left, capacity);
        if (2 * temp <= size_) {
            while (cur != begin()) {
                *cur = *(--cur);
            }
            pop_front();
        } else {
            while (cur != --end()) {
                *cur = *(++cur);
            }
            pop_back();
        }
        return iterator(data, temp, left, capacity);
    }

    iterator insert(const_iterator pos, const T &dat) {
        if (2 * pos.ind <= size_) {
            push_front(dat);
            iterator temp = iterator(data, pos.ind, left, capacity), cur = begin();
            while (cur != temp) {
                *(cur) = *(++cur);
            }
            *cur = dat;
        } else {
            push_back(dat);
            iterator temp = iterator(data, pos.ind, left, capacity), cur = --end();
            while (cur != temp) {
                *cur = *(--cur);
            }
            *cur = dat;
        }
        return iterator(data, pos.ind, left, capacity);

    }

    void swap(circular_buffer<T> &first, circular_buffer<T> &second);
};

template<typename T>
void circular_buffer<T>::swap(circular_buffer<T> &first, circular_buffer<T> &second) {
    std::swap(first.left, second.left);
    std::swap(first.size_, second.size_);
    std::swap(first.capacity, second.capacity);
    std::swap(first.data, second.data);
}


#endif //CIRCULAR_BUFFER_CIRCULAR_BUFFER_H
