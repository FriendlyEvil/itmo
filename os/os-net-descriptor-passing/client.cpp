#include "client.h"
#include "utils.h"
#include <string>
#include <cstring>
#include <unistd.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <stdexcept>
#include <sys/un.h>
#include <fcntl.h>

client::client(std::string &path) : path(path), sock(-1) {}

client::~client() {
    close(sock);
    close(in);
    close(out);
}

void client::connect_to_server() {
    sock = socket(AF_UNIX, SOCK_STREAM, 0);
    if (sock == -1) {
        throw std::runtime_error("Error creating socket");
    }
    sockaddr_un addr;
    addr.sun_family = AF_UNIX;
    std::strcpy(addr.sun_path, path.c_str());

    if (connect(sock, reinterpret_cast<sockaddr *>(&addr), sizeof(addr)) == -1) {
        throw std::runtime_error("Connection error");
    }

    in = get_descriptor(sock);
    out = get_descriptor(sock);
}

std::string client::echo(std::string &str) {
    if (sock == -1) {
        connect_to_server();
    }

    socket_write(in, str, true);
    auto read = socket_read(out, true);
    return read.first;
}