#include <iostream>
#include <arpa/inet.h>
#include "client.h"

int main(int argc, char const *argv[]) {
    std::string path = "/tmp/socket";
    std::string message;
    if (argc > 1) {
        message = argv[1];
    } else {
        message = "default";
    }

    try {
        client cl(path);
        std::cout << cl.echo(message) << "\n";
    } catch (std::runtime_error &e) {
        perror(e.what());
        return 1;
    }
}