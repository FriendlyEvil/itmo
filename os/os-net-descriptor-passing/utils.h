#ifndef OS_NET_UTILS_H
#define OS_NET_UTILS_H


#include <string>

std::pair<std::string, ssize_t > socket_read(int sock, bool throw_flag = false);

void socket_write(int sock, std::string &str, bool throw_flag = false);

void send_descriptor(int sock, int fd);

int get_descriptor(int sock);

#endif //OS_NET_UTILS_H
