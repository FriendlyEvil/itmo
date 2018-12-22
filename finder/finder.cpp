#include "consts.h"
#include "finder.h"
#include <functional>
#include <cstdint>
#include <algorithm>
#include <QFile>
#include <QTextStream>
#include <QThread>
#include "finder/tringram/trigram_finder.h"

finder::finder(QHash<QString, QHash<QString, QSet<int64_t>>> *trigrams, QString const &find_string):
    trigrams(trigrams), find_string(find_string) {}

void finder::find() {
    QList<QString> list;
    QSet<int64_t> tri;
    trigram_finder::find_trigrams(&tri, find_string);
    for (auto i : *trigrams) {
        for (auto set = i.begin(); set != i.end(); ++set) {
            bool flag = true;
            for (int64_t el : tri) {
                if (!set.value().contains(el)) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                if (check_file(set.key())) {
                    list.push_back(set.key());
                }
            }
        }
    }
    emit update_result(list);
}

bool finder::check_file(QString const &filepath) {
    QFile file(filepath);
    if (file.open(QIODevice::ReadOnly | QIODevice::Text)) {
        QTextStream stream(&file);
        QString str;
        while (!stream.atEnd()) {
            str = str.append(stream.read(consts::buf_size));
            for (int i = 0; i < str.size() - find_string.size(); ++i) {
                int j = 0;
                for (; str[i + j] == str[j] && j < find_string.size(); ++j);
                if (j == find_string.size())
                    return true;
            }

            str = str.mid(str.length() - find_string.size() + 1, find_string.size() - 1);
        }
    }
    file.close();
    return false;
}
