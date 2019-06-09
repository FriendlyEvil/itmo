#include <iostream>
#include "server.h"

int main() {
    std::string path = "/tmp/socket";

    try {
        server sr(path);
        sr.start();
    } catch (std::runtime_error& e) {
        perror(e.what());
        return 1;
    }
}