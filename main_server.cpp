#include <iostream>
#include "server.h"
#include "server_exit_exception.h"

int main() {
    std::string path = "/tmp/socket";

    try {
        server sr(path);
        sr.start();
    } catch (server_exit_exception &e) {
        std::cout << "server close";
    } catch (std::runtime_error &e) {
        perror(e.what());
        return 1;
    }
}