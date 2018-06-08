#ifndef HUFFMAN_FREQ_TREE_H
#define HUFFMAN_FREQ_TREE_H

#include <vector>
#include <algorithm>
#include <unordered_map>
#include "bits_seq.h"
#include <memory>

class freq_tree {
public:
    struct node {
        uint8_t symbol;
        std::shared_ptr<node> left, right;

        node(uint8_t symbol) : symbol(symbol), left(nullptr), right(nullptr) {}

        node(std::shared_ptr<node> left, std::shared_ptr<node> right) : symbol(0), left(left), right(right) {}
    };

    std::shared_ptr<node> root;
    std::shared_ptr<node> cur_node;
private:
    void create_code_rec(bits_sequence &bits, std::shared_ptr<node> cur_node, std::shared_ptr<node> parent);

    void create_code_alphabet_rec(std::vector<uint8_t> &alphabet, std::shared_ptr<node> cur_node);

    void create_alphabet_rec(std::unordered_map<uint8_t, bits_sequence> &alphabet, std::shared_ptr<node> cur_node,
                             bits_sequence &cur_seq);

    std::shared_ptr<node> get_rec(std::shared_ptr<node> cur_node, size_t &cur_ind, const bits_sequence &seq,
                                  uint8_t &res_sym);

    std::shared_ptr<node>
    create_tree_rec(const bits_sequence &tree, const std::vector<uint8_t> &alphabet, size_t &ind_tree,
                    size_t &ind_alpha);

public:
    freq_tree(const std::vector<uint64_t> &all_freqs);

    freq_tree(uint8_t *tree_bits, size_t size_tree, size_t size_alphabet);

    void create_code(std::vector<uint8_t> &alphabet, bits_sequence &bits);

    std::unordered_map<uint8_t, bits_sequence> create_alphabet();

    bool get(const bits_sequence &seq, size_t &cur_ind, uint8_t &res_sym);
};


#endif //HUFFMAN_FREQ_TREE_H
