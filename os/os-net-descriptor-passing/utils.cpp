#include <sys/socket.h>
#include <stdexcept>
#include <unistd.h>
#include <cstring>
#include "utils.h"

const size_t BUFFER_SIZE = 1 << 16;

std::pair<std::string, ssize_t> socket_read(int sock, bool throw_flag) {
    char buf[BUFFER_SIZE];
    ssize_t reading = 0;

    while (reading != BUFFER_SIZE) {
        ssize_t cur_size = read(sock, buf + reading, BUFFER_SIZE - reading);
        if (cur_size == -1) {
            if (throw_flag) {
                throw std::runtime_error("Reading error");
            } else {
                return {"", -1};
            }
        }
        reading += cur_size;
        if (buf[reading - 1] == '\0')
            break;
    }

    return {buf, reading};
}

void socket_write(int sock, std::string &str, bool throw_flag) {
    const char *message = str.c_str();
    size_t str_len = sizeof(message);

    ssize_t sending = 0;

    do {
        ssize_t cur_size = write(sock, message + sending, str_len - sending);
        if (cur_size == -1) {
            if (throw_flag) {
                throw std::runtime_error("Sending error");
            } else {
                perror("Sending error");
            }
        }
        sending += cur_size;
    } while (sending != str_len);
}

msghdr get_message(char *buf, size_t buf_size, char *buf1, size_t buf1_size) {
    msghdr message{};

    iovec io{};
    io.iov_base = buf1;
    io.iov_len = buf1_size;

    message.msg_iovlen = 1;
    message.msg_control = buf;
    message.msg_iov = &io;
    message.msg_controllen = buf_size;

    return message;
}

void send_descriptor(int sock, int fd) {
    char buf[CMSG_SPACE(sizeof(fd))];

    msghdr message = get_message(buf, sizeof(buf), const_cast<char *>(""), 1);

    cmsghdr *cmsg = CMSG_FIRSTHDR(&message);
    cmsg->cmsg_level = SOL_SOCKET;
    cmsg->cmsg_type = SCM_RIGHTS;
    cmsg->cmsg_len = CMSG_LEN(sizeof(fd));

    memmove(CMSG_DATA(cmsg), &fd, sizeof(fd));

    message.msg_controllen = cmsg->cmsg_len;

    if (sendmsg(sock, &message, 0) == -1) {
        throw std::runtime_error("Sending descriptor error");
    }
}

int get_descriptor(int sock) {
    cmsghdr *structcmsghdr;
    char buf[CMSG_SPACE(sizeof(int))];
    char buf1[BUFFER_SIZE];

    msghdr message = get_message(buf, sizeof(buf), buf1, sizeof(buf1));

    if (recvmsg(sock, &message, 0) == -1) {
        throw std::runtime_error("Reading descriptor error");
    }
    
    structcmsghdr = CMSG_FIRSTHDR(&message);
    int received_fd;
    memcpy(&received_fd, (int *) CMSG_DATA(structcmsghdr), sizeof(int));
    return received_fd;
}