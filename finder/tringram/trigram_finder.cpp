#include "trigram_finder.h"

#include <iostream>

#include <QThread>
#include <QDirIterator>
#include <QTreeWidget>
#include <QCryptographicHash>
#include <QDialog>
#include <QMessageBox>
#include <QCheckBox>
#include <QTextStream>

#include <finder/consts.h>

trigram_finder::trigram_finder(QThread* parent_thread, QList<std::pair<QString, QString>> const &list, QObject *parent) :
    QObject(parent), parent_thread(parent_thread) {
    find_list = list;
}

bool trigram_finder::check_interruption_request() {
    if (parent_thread->isInterruptionRequested())
        return true;
    return false;
}

void trigram_finder::process() {
    for (auto i : find_list) {
        calculate_trigram(i);
        if (check_interruption_request())
            break;
        emit update_progress();
    }
    emit update_result(&map);
}

void trigram_finder::calculate_trigram(std::pair<QString, QString> const &file_info) {
    QString directory = file_info.first;
    QString file_name = file_info.second;
    QFile file(file_name);
    if (file.open(QIODevice::ReadOnly)) {
        QTextStream stream(&file);
        QString str;
        QSet<int64_t>* set = &map[directory][file_name];
        while (!stream.atEnd()) {
            str = str.append(stream.read(consts::buf_size));
            if (!trigram_finder::find_trigrams(set, str)) {
                map[directory].remove(file_name);
                if (check_interruption_request())
                    return;
                break;
            }
            if (check_interruption_request())
                return;
            str = str.mid(str.length() - consts::trigram_size + 1, consts::trigram_size - 1);
        }
        file.close();
    }
}


bool trigram_finder::find_trigrams(QSet<int64_t> *set, QString const &str) {
    if (str.size() < consts::trigram_size)
        return  false;
    auto data = str.data();
    int64_t tri = (int64_t(data[1].unicode()) << 32) + (int64_t(data[0].unicode()) << 16);
    for (int i = consts::trigram_size - 1; i < str.length(); i++) {
        tri = (tri >> 16) + (int64_t(data[i].unicode()) << 32);
        set->insert(tri);
        if (set->size() > consts::max_size_trigram)
            return false;
    }
    return true;
}
