#include "trigram.h"

#include <iostream>

#include <QThread>
#include <QDirIterator>
#include <QTreeWidget>
#include <QCryptographicHash>
#include <QDialog>
#include <QMessageBox>
#include <QCheckBox>
#include <QTextStream>

//#include <finder/consts.h>

trigram::trigram(QObject *parent) :
    QObject(parent) {}

trigram::trigram(QList<QString> &dir_list, QHash<QString, QHash<QString, QSet<int64_t>>> *map)
    : find_list(dir_list), map(map) {
    thread_count = 0;
}

void trigram::calculate_trigrams() {
    find_items();
}

void trigram::find_items() {
    QList<QTreeWidgetItem*> items;

    QList<std::pair<QString, QString>> list;
    for (auto &dir : find_list) {
        QDirIterator it(dir,QDir::NoDotAndDotDot|QDir::Hidden|QDir::Files , QDirIterator::Subdirectories);
        while(it.hasNext()) {
            QString file_name = it.next();
            list.push_back({dir, file_name});
            if (QThread::currentThread()->isInterruptionRequested()) {
                emit search_result();
                emit end_search(QDialog::Accepted);
            }
        }
        if (QThread::currentThread()->isInterruptionRequested()) {
            emit search_result();
            emit end_search(QDialog::Accepted);
        }
    }

    emit update_status_bar_range(list.size());


    thread_count = std::min(thread_max, list.size());
    for (int i = 0; i < thread_count; i++) {
        create_tread(list, i);
    }
}

void trigram::create_tread(QList<std::pair<QString, QString>> const &list, int n) {
    QList<std::pair<QString, QString>> find;
    for(int i = (list.size() / thread_count) * n; i < (list.size() / thread_count) * (n + 1); ++i) {
        find.push_back(list[i]);
    }
    trigram_finder* finder = new trigram_finder(QThread::currentThread(), find);
    QThread* thread = new QThread();

    finder->moveToThread(thread);
    connect(thread, &QThread::started, finder, &trigram_finder::process);
    connect(finder, &trigram_finder::update_result, this, &trigram::update_result);
    connect(finder, &trigram_finder::update_progress, this, &trigram::update_progess);
    connect(finder, &trigram_finder::end_search, this, &trigram::end_find);
    connect(finder, &trigram_finder::end_search, thread, &QThread::quit);
    connect(finder, &trigram_finder::end_search, finder, &trigram_finder::deleteLater);
    connect(thread, &QThread::finished, thread, &QThread::deleteLater);

    thread->start();
}

void trigram::update_progess() {
    emit update_status_bar(0);
}

void trigram::end_find() {
    if (--thread_count == 0) {
        emit search_result();
        emit end_search(QDialog::Accepted);
    }
}

void trigram::update_result(QHash<QString, QHash<QString, QSet<int64_t>>> res) {
    for (QString i : find_list) {
        for (auto it = res[i].begin(); it != res[i].end(); ++it) {
            (*map)[i].insert(it.key(), it.value());
        }
    }
}
