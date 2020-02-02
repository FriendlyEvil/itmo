#include "bits_seq.h"
#include <cstring>

bits_sequence::bits_sequence() : num(0) {}

bits_sequence::bits_sequence(uint8_t *data, size_t size) : bits((size - 1) / size_of + 1), num(size) {
    memcpy(bits.data(), data, bits.size() * sizeof(uint64_t));
}

bits_sequence::bits_sequence(std::vector<uint64_t> &data) : bits(data), num(data.size() * size_of) {}

void bits_sequence::add(bool bit) {
    if ((num % size_of) == 0) {
        bits.push_back(0);
    }
    if (bit) {
        bits[num / size_of] |= (1LL << (num % size_of));
    } else {
        bits[num / size_of] &= ~(1LL << (num % size_of));
    }
    num++;
}

void bits_sequence::remove_last() {
    if ((--num % size_of) == 0) {
        bits.pop_back();
    }
}

void bits_sequence::remove(size_t n) {
    while (n > size_of) {
        bits.pop_back();
        n -= size_of;
    }
    if (n >= num % size_of && num % size_of != 0) {
        bits.pop_back();
    }
    num -= n;
}

bool bits_sequence::get_bit(size_t ind) const {
    return (bool) ((bits[ind / size_of] >> (ind % size_of)) & 1);
}

uint32_t bits_sequence::size() const {
    return num;
}

std::vector<uint64_t> bits_sequence::data() const {
    return bits;
}

void bits_sequence::merge(const bits_sequence &add_bits) {
    for (size_t i = 0; i < add_bits.size(); ++i)
        add(add_bits.get_bit(i));
}