#include "file_scan.h"

#include <QThread>
#include <QDirIterator>
#include <QTreeWidget>
#include <QCryptographicHash>
#include <QDialog>
#include <QMessageBox>
#include <QCheckBox>

file_scan::file_scan(QList<QString> &find_list, QObject *parent) :
    QObject(parent), find_list(find_list) {}

bool file_scan::check_interruption_request() {
    if (QThread::currentThread()->isInterruptionRequested())
        return true;
    return false;
}

void file_scan::find_duplicates() {
    auto items = find();
    emit search_result(items);
    emit end_search(QDialog::Accepted);
}

QList<QTreeWidgetItem*> file_scan::find() {
    QList<QTreeWidgetItem*> items;
    QMap<qint64, QList<QString>> map;

    for (auto &dir : find_list) {
        emit update_status("find files in directory:\n" + dir);
        QDirIterator it(dir,QDir::NoDotAndDotDot|QDir::Hidden|QDir::Files|QDir::NoSymLinks , QDirIterator::Subdirectories);
        while(it.hasNext()) {
            QString file_name = it.next();
            qint64 size = QFileInfo(file_name).size();
            auto iter = map.find(size);
            if (iter != map.end()) {
                iter->push_back(file_name);
            } else {
                QList<QString> new_list;
                new_list.push_back(file_name);
                map.insert(size, new_list);
            }
            if (check_interruption_request())
                return items;
        }
    }

    int group_number = 0;
    int num = 0;
    emit update_status_bar_range(map.size());

    for (auto el : map) {
        if (el.size() > 1) {
            QMap<QString, QList<QString>> hash_map;
            QString message = "calculate hash of a group of " +
                    QString::number(el.size()) + " files of " +
                    QString::number(QFileInfo(el[0]).size()) + " size\n";

            for (QString file_name : el) {
                emit update_status(message + "current file: " + file_name);
                QString hash = hash_sha256(file_name);
                if (hash == "") {
                    continue;
                }
                auto it = hash_map.find(hash);
                if (it != hash_map.end()) {
                    it->push_back(file_name);
                } else {
                    QList<QString> new_list;
                    new_list.push_back(file_name);
                    hash_map.insert(hash, new_list);
                }
                if (check_interruption_request())
                    return items;
            }

            for (auto files : hash_map) {
                if (files.size() > 1) {
                    QTreeWidgetItem* parent = new QTreeWidgetItem();
                    QString str = "Group " + QString::number(++group_number) +
                            "(file count = " + QString::number(files.size()) +
                            " file size =" + QString::number(QFileInfo(files[0]).size())+ ")";
                    parent->setText(0, str);
                    for (QString i : files) {
                        QTreeWidgetItem* item = new QTreeWidgetItem();
                        item->setText(0, i);
                        item->setFlags(item->flags() | Qt::ItemIsUserCheckable | Qt::ItemIsSelectable);
                        item->setCheckState(0, Qt::Unchecked);

                        parent->insertChild(0, item);
                    }
                    items.push_back(parent);
                }
            }
        }
        if (check_interruption_request())
            return items;
        emit update_status_bar(++num);
    }
    return items;
}

QString file_scan::hash_sha256(QString const& str){
    QFile file(str);
    if (file.open(QIODevice::ReadOnly)) {
        QCryptographicHash crypt (QCryptographicHash::Sha3_256);
        if (crypt.addData(&file))
            return QString(crypt.result().toHex());
    }
    return "";
}
