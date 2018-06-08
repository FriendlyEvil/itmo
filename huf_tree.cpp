#include "huf_tree.h"

freq_tree::freq_tree(const std::vector<uint64_t> &all_freqs) : cur_node(root) {
    typedef std::pair<uint64_t, std::shared_ptr<node>> pair;
    uint64_t inf = (1LL << 62);

    std::vector<pair> freqs;
    for (size_t i = 0; i < all_freqs.size(); ++i) {
        if (all_freqs[i] != 0) {
            freqs.push_back(pair(all_freqs[i], std::make_shared<node>(node((uint8_t) i))));
        }
    }
    std::sort(freqs.begin(), freqs.end(), [](const pair &a, const pair &b) { return a.first < b.first; });

//    if (freqs.size() == 0)
//        return;

    size_t ind_freq = 0, ind_sum = 0, n = freqs.size();
    freqs.push_back(pair(inf, nullptr));
    freqs.push_back(pair(inf, nullptr));
    std::vector<pair> sum_freqs;

    while (ind_freq < n || ind_sum < sum_freqs.size() - 1) {
        uint64_t fr1 = freqs[ind_freq].first, fr2 = freqs[ind_freq + 1].first,
                sum1 = inf, sum2 = inf;
        if (ind_sum < sum_freqs.size())
            sum1 = sum_freqs[ind_sum].first;
        if (ind_sum + 1 < sum_freqs.size())
            sum2 = sum_freqs[ind_sum + 1].first;

        uint64_t res_freq;
        std::shared_ptr<node> new_node;
        if (fr1 + fr2 <= sum1 + sum2 && fr2 <= sum1) {
            new_node = std::make_shared<node>(node(freqs[ind_freq].second, freqs[ind_freq + 1].second));
            res_freq = fr1 + fr2;
            ind_freq += 2;
        } else if (fr1 + sum1 <= fr1 + fr2 && fr1 <= sum2) {
            new_node = std::make_shared<node>(node(freqs[ind_freq++].second, sum_freqs[ind_sum++].second));
            res_freq = fr1 + sum1;
        } else {
            new_node = std::make_shared<node>(node(sum_freqs[ind_sum].second, sum_freqs[ind_sum + 1].second));
            res_freq = sum1 + sum2;
            ind_sum += 2;
        }
        sum_freqs.push_back({res_freq, new_node});
    }
    root = sum_freqs[sum_freqs.size() - 1].second;
}

freq_tree::freq_tree(uint8_t *tree_bits, size_t size_tree, size_t size_alphabet) {
    bits_sequence bs(tree_bits, size_tree);
    std::vector<uint8_t> alphabet(size_alphabet);

    size_t begin_alphabet = ((size_tree - 1) / (sizeof(uint64_t) * 8) + 1) * sizeof(uint64_t);
    for (size_t i = 0; i < size_alphabet; ++i) {
        alphabet[i] = tree_bits[begin_alphabet + i];
    }

    size_t a = 0, b = 0;
    root = create_tree_rec(bs, alphabet, a, b);
    cur_node = root;
}

void freq_tree::create_code_rec(bits_sequence &bits, std::shared_ptr<node> cur_node, std::shared_ptr<node> parent) {
    if (cur_node->left != nullptr) {
        bits.add(0);
        create_code_rec(bits, cur_node->left, cur_node);
        create_code_rec(bits, cur_node->right, cur_node);
    }
    if (cur_node != parent->right)
        bits.add(1);
}

void freq_tree::create_code_alphabet_rec(std::vector<uint8_t> &alphabet, std::shared_ptr<node> cur_node) {
    if (cur_node->left != nullptr) {
        create_code_alphabet_rec(alphabet, cur_node->left);
        create_code_alphabet_rec(alphabet, cur_node->right);
    } else {
        alphabet.push_back(cur_node->symbol);
    }
}

void freq_tree::create_code(std::vector<uint8_t> &alphabet, bits_sequence &bits) {
    create_code_rec(bits, root, root);
    create_code_alphabet_rec(alphabet, root);
}


void freq_tree::create_alphabet_rec(std::unordered_map<uint8_t, bits_sequence> &alphabet, std::shared_ptr<node> cur_node, bits_sequence &cur_seq) {
    if (cur_node->left != nullptr) {
        cur_seq.add(0);
        create_alphabet_rec(alphabet, cur_node->left, cur_seq);
        cur_seq.remove_last();
        cur_seq.add(1);
        create_alphabet_rec(alphabet, cur_node->right, cur_seq);
        cur_seq.remove_last();
    } else {
        alphabet[cur_node->symbol] = cur_seq;
    }
}

std::unordered_map<uint8_t, bits_sequence> freq_tree::create_alphabet() {
    std::unordered_map<uint8_t, bits_sequence> map;
    bits_sequence seq;
    create_alphabet_rec(map, root, seq);
    return map;
}

std::shared_ptr<freq_tree::node> freq_tree::create_tree_rec(const bits_sequence &tree, const std::vector<uint8_t> &alphabet, size_t &ind_tree, size_t &ind_alpha) {
    if (tree.get_bit(ind_tree++)) {
        return std::make_shared<node>(alphabet[ind_alpha++]);
    }
    std::shared_ptr<node> new_node = std::make_shared<node>(node(0));
    new_node->left = create_tree_rec(tree, alphabet, ind_tree, ind_alpha);
    new_node->right = create_tree_rec(tree, alphabet, ind_tree, ind_alpha);
    return new_node;
}

std::shared_ptr<freq_tree::node> freq_tree::get_rec(std::shared_ptr<freq_tree::node> cur_node, size_t &cur_ind, const bits_sequence &seq, uint8_t &res_sym) {
    if (cur_node->left == nullptr) {
        res_sym = cur_node->symbol;
        return nullptr;
    } else if (cur_ind == seq.size())
        return cur_node;

    if (seq.get_bit(cur_ind++) == 0)
        return get_rec(cur_node->left, cur_ind, seq, res_sym);
    else
        return get_rec(cur_node->right, cur_ind, seq, res_sym);
}

bool freq_tree::get(const bits_sequence &seq, size_t &cur_ind, uint8_t &res_sym) {
    cur_node = get_rec(cur_node, cur_ind, seq, res_sym);
    if (cur_node == nullptr) {
        cur_node = root;
        return true;
    }
    return false;
}
