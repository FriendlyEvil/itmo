#include "server.h"
#include "utils.h"
#include <sys/socket.h>
#include <cstdio>
#include <unistd.h>
#include <stdexcept>
#include <sys/un.h>
#include <cstring>
#include <sys/stat.h>
#include <fcntl.h>

server::server(std::string &path) : path(path), sock(-1) {}

server::~server() {
    close(sock);
    unlink(path.c_str());
}

void server::start() {
    sock = socket(AF_UNIX, SOCK_STREAM, 0);
    if (sock == -1) {
        throw std::runtime_error("Error creating socket");
    }

    sockaddr_un addr;
    addr.sun_family = AF_UNIX;
    std::strcpy(addr.sun_path, path.c_str());

    if (bind(sock, reinterpret_cast<sockaddr *>(&addr), sizeof(addr)) == -1) {
        throw std::runtime_error("Binding error");
    }

    if (listen(sock, 3) == -1) {
        throw std::runtime_error("Listening error");
    }

    while (true) {
        int temp_sock = accept(sock, nullptr, nullptr);
        if (temp_sock == -1) {
            perror("Accepting error");
            close(temp_sock);
            continue;
        }

        std::string name;
        int in_fds[2];
        int out_fds[2];
        if (pipe(in_fds) == -1) {
            close(temp_sock);
            throw std::runtime_error("Error creating pipe");
        }
        if (pipe(out_fds) == -1) {
            close(temp_sock);
            throw std::runtime_error("Error creating pipe");
        }

        send_descriptor(temp_sock, in_fds[1]);
        send_descriptor(temp_sock, out_fds[0]);

        close(temp_sock);
        int infd = out_fds[1];
        int outfd = in_fds[0];

        auto read = socket_read(outfd);
        if (read.first == "close") {
            break;
        }
        socket_write(infd, read.first);

        close(infd);
        close(outfd);

    }
}