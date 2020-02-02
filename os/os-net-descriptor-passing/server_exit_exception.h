
#include <stdexcept>

class server_exit_exception : public std::runtime_error {
public:
    server_exit_exception() : runtime_error("Good exit") {}
};