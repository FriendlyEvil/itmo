#include "mainwindow.h"
#include "ui_mainwindow.h"

#include <QCommonStyle>
#include <QDesktopWidget>
#include <QDir>
#include <QFileDialog>
#include <QFileInfo>
#include <QMessageBox>
#include <QColor>
#include <QCryptographicHash>
#include <QDirIterator>
#include <QThread>
#include <QCheckBox>
#include <QMouseEvent>
#include <QInputDialog>
#include <QColor>
#include <QDesktopServices>

#include <iostream>
#include <list>
#include <thread>

#include "dialog/dialog.h"
#include "finder/tringram/trigram.h"

#include <finder/finder.h>

main_window::main_window(QWidget *parent)
    : QMainWindow(parent)
    , ui(new Ui::MainWindow)
{
    ui->setupUi(this);
//    setGeometry(QStyle::alignedRect(Qt::LeftToRight, Qt::AlignCenter, size(), qApp->desktop()->availableGeometry()));

    connect(ui->scan_button, &QAbstractButton::clicked, this, &main_window::scan_directory_list);
    connect(ui->add_button, &QAbstractButton::clicked, this, &main_window::add_directory);
    connect(ui->open_button, &QAbstractButton::clicked, this, &main_window::open);
    connect(ui->delete_button, &QAbstractButton::clicked, this, &main_window::delete_directory_from_list);
    connect(ui->path, &QLineEdit::textEdited, this, &main_window::change_path);
    connect(ui->tableWidget_2, SIGNAL(itemSelectionChanged()), this, SLOT(check_delete_button()));

    connect(ui->find_button, &QAbstractButton::clicked, this, &main_window::find_string);

    connect(ui->treeWidget, SIGNAL(itemDoubleClicked(QTreeWidgetItem * , int)), this, SLOT(open_file(QTreeWidgetItem *)));
    qRegisterMetaType<QHash<QString,QHash<QString,QSet<int64_t>>>>("QHash<QString,QHash<QString,QSet<int64_t>>>");
    qRegisterMetaType<QList<QString>>("QList<QString>");
    ui->delete_button->setEnabled(false);
    ui->scan_button->setEnabled(false);
    ui->find_button->setEnabled(false);
    change_path();
    map = new QHash<QString, QHash<QString, QSet<int64_t>>>();
}

void main_window::open_file(QTreeWidgetItem* item) {
    if (item->text(0) != not_found)
        QDesktopServices::openUrl(QUrl(item->text(0)));
}

main_window::~main_window()
{delete map;}

void main_window::open() {
    QString dir;
    dir = QFileDialog::getExistingDirectory(this, "Select Directory for Scanning",
                                            QString(), QFileDialog::ShowDirsOnly | QFileDialog::DontResolveSymlinks);

    ui->path->setText(dir);
    change_path();
}

void main_window::find_string() {
    bool bOk;
    QString str = QInputDialog::getText(this, "Input", "Srting:", QLineEdit::Normal, "hello",&bOk);
    if (bOk) {
//        ui->treeWidget->clear();
//        finder find(map, str);
//        connect(&find, &finder::update_result, this, &main_window::show_results);
//        find.find();

        QThread *thread = new QThread();
        finder *find = new finder(map, str);
        dialog *dial = new dialog(thread, this);

        find->moveToThread(thread);
        connect(thread, &QThread::started, find, &finder::find);
        connect(find, &finder::update_status_bar, dial, &dialog::update_status_bar);
        connect(find, &finder::update_status_bar_range, dial, &dialog::update_status_bar_range);
        connect(find, &finder::update_message, dial, &dialog::update_message);
        connect(find, &finder::update_result, this, &main_window::show_results);
        connect(find, &finder::end_search, thread, &QThread::quit);
        connect(find, &finder::end_search, dial, &dialog::done);
        connect(find, &finder::end_search, find, &finder::deleteLater);
        connect(thread, &QThread::finished, thread, &QThread::deleteLater);

        thread->start();
        if (dial->exec() == QDialog::Rejected) {
            dial->stop();
        }
        delete dial;
    }
}

void main_window::scan_directory_list()
{
    ui->scan_button->setEnabled(false);
    ui->delete_button->setEnabled(false);

    QList<QString> list;
    for (int i = 0; i < ui->tableWidget_2->rowCount(); i++)
        if (ui->tableWidget_2->item(i, 0)->backgroundColor() != good_scan_background)
            list.push_back(ui->tableWidget_2->item(i, 0)->text());


    QThread *thread = new QThread();
    trigram *tri = new trigram(list, map);
    dialog *dial = new dialog(thread, this);

    tri->moveToThread(thread);
    connect(thread, &QThread::started, tri, &trigram::calculate_trigrams);
    connect(tri, &trigram::update_status_bar, dial, &dialog::update_status_bar);
    connect(tri, &trigram::update_status_bar_range, dial, &dialog::update_status_bar_range);
    connect(tri, &trigram::search_result, this, &main_window::update_result);
    connect(tri, &trigram::end_search, thread, &QThread::quit);
    connect(tri, &trigram::end_search, dial, &dialog::done);
    connect(tri, &trigram::end_search, tri, &trigram::deleteLater);
    connect(thread, &QThread::finished, thread, &QThread::deleteLater);

    thread->start();
    if (dial->exec() == QDialog::Rejected) {
        dial->stop();
    }
    ui->find_button->setEnabled(true);
    delete dial;
    change_path();
}

void main_window::update_result() {
    ui->scan_button->setEnabled(false);
    ui->find_button->setEnabled(false);
    for (int i = 0; i < ui->tableWidget_2->rowCount(); i++) {
        if (map->find(ui->tableWidget_2->item(i, 0)->text()) != map->end()) {
            ui->tableWidget_2->item(i, 0)->setBackgroundColor(good_scan_background);
            ui->find_button->setEnabled(true);
        } else {
            ui->scan_button->setEnabled(true);
        }
    }
}

void main_window::check_delete_button() {
    ui->delete_button->setEnabled(ui->tableWidget_2->selectedItems().length() != 0);
}

void main_window::show_results(QList<QString> list) {
    if (list.size() == 0) {
        QTreeWidgetItem* item = new QTreeWidgetItem();
        item->setText(0, not_found);

        ui->treeWidget->insertTopLevelItem(0, item);
    } else {
        for (auto i : list) {
            auto p = new QTreeWidgetItem();
            p->setText(0, i);
            ui->treeWidget->addTopLevelItem(p);
        }
    }
    ui->label->setText(QString::number(list.size()) + " file found");
}

void main_window::delete_directory_from_list() {
    for (auto &el : ui->tableWidget_2->selectedItems()) {
        if (el->backgroundColor() == good_scan_background)
            map->erase(map->find(el->text()));
        ui->tableWidget_2->removeRow(el->row());
    }
    change_path();
    ui->scan_button->setEnabled(false);
    ui->find_button->setEnabled(false);
    for (int i = 0; i < ui->tableWidget_2->rowCount(); i++) {
        if (ui->tableWidget_2->item(i, 0)->backgroundColor() == good_scan_background)
            ui->find_button->setEnabled(true);
        else
            ui->scan_button->setEnabled(true);
    }
}

void main_window::add_directory() {
    ui->add_button->setEnabled(false);
    remove_subfolder(QFileInfo(ui->path->text()));

    int row = ui->tableWidget_2->rowCount();
    ui->tableWidget_2->insertRow(row);
    ui->tableWidget_2->setItem(row, 0, new QTableWidgetItem(QFileInfo(ui->path->text()).absoluteFilePath()));

    ui->scan_button->setEnabled(true);
    ui->find_button->setEnabled(false);
}


void main_window::change_path() {
    QFileInfo file_info = QFileInfo(ui->path->text());
    if (file_info.isDir() && check_path(file_info))
        ui->add_button->setEnabled(true);
    else
        ui->add_button->setEnabled(false);
}

bool main_window::check_path(QFileInfo path) {
    QString file_path = path.absolutePath();
    for (int i = 0; i < ui->tableWidget_2->rowCount(); i++) {
        QString file = QString(ui->tableWidget_2->item(i, 0)->text());
        if (file_path.startsWith(file))
            return false;
    }
    return true;
}

void main_window::remove_subfolder(QFileInfo path) {
    QString file_path = path.absolutePath();
    for (int i = 0; i < ui->tableWidget_2->rowCount(); i++) {
        QString file = ui->tableWidget_2->item(i, 0)->text();
        if (file.startsWith(file_path)) {
            if (ui->tableWidget_2->item(i, 0)->backgroundColor() == good_scan_background)
                map->erase(map->find(ui->tableWidget_2->item(i, 0)->text()));
            ui->tableWidget_2->removeRow(i--);
            change_path();
        }
    }
}
