#ifndef HUFFMAN_BITS_SEQ_H
#define HUFFMAN_BITS_SEQ_H

#include <cstdint>
#include <vector>
#include <cstddef>

class bits_sequence {
private:
    std::vector<uint64_t> bits;
    uint32_t num;

public:
    static const size_t size_of = sizeof(uint64_t) * 8;

    bits_sequence();

    bits_sequence(uint8_t *data, size_t size);

    bits_sequence(std::vector<uint64_t> &data);

    void add(bool bit);

    void remove_last();

    void remove(size_t n);

    bool get_bit(size_t ind) const;

    uint32_t size() const;

    std::vector<uint64_t> data() const;

    void merge(const bits_sequence &add_bits);
};

#endif //HUFFMAN_BITS_SEQ_H
