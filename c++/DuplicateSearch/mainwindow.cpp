#include "dialog.h"
#include "file_scan.h"
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


main_window::main_window(QWidget *parent)
    : QMainWindow(parent)
    , ui(new Ui::MainWindow)
{
    ui->setupUi(this);
    setGeometry(QStyle::alignedRect(Qt::LeftToRight, Qt::AlignCenter, size(), qApp->desktop()->availableGeometry()));

    connect(ui->scan_button, &QAbstractButton::clicked, this, &main_window::scan_directory_list);
    connect(ui->add_button, &QAbstractButton::clicked, this, &main_window::add_directory);
    connect(ui->open_button, &QAbstractButton::clicked, this, &main_window::open);
    connect(ui->delete_button, &QAbstractButton::clicked, this, &main_window::delete_select_file);
    connect(ui->delete_button, &QAbstractButton::clicked, this, &main_window::delete_directory_from_list);
    connect(ui->path, &QLineEdit::textEdited, this, &main_window::change_path);
    connect(ui->tableWidget, SIGNAL(itemSelectionChanged()), this, SLOT(check_delete_button()));

    ui->delete_button->setEnabled(false);
    ui->scan_button->setEnabled(false);
    change_path();
}

main_window::~main_window()
{}

void main_window::open() {
    QString dir;
    dir = QFileDialog::getExistingDirectory(this, "Select Directory for Scanning",
                                            QString(), QFileDialog::ShowDirsOnly | QFileDialog::DontResolveSymlinks);

    ui->path->setText(dir);
    change_path();
}

void main_window::scan_directory_list()
{
    ui->scan_button->setEnabled(false);
    ui->delete_button->setEnabled(false);
    ui->treeWidget->clear();

    QList<QString> list;
    for (int i = 0; i < ui->tableWidget->rowCount(); i++)
        list.push_back(ui->tableWidget->item(i, 0)->text());

    while (ui->tableWidget->rowCount() > 0) {
        ui->tableWidget->removeRow(0);
    }

    auto *thread = new QThread();
    auto *scan = new file_scan(list);
    auto *dialog = new class dialog(thread, this);

    scan->moveToThread(thread);
    connect(thread, SIGNAL(started()), scan, SLOT(find_duplicates()));
    connect(scan, SIGNAL(update_status_bar(int)), dialog, SLOT(update_status_bar(int)));
    connect(scan, SIGNAL(update_status_bar_range(int)), dialog, SLOT(update_status_bar_range(int)));
    connect(scan, SIGNAL(update_status(QString const&)), dialog, SLOT(update_message(QString const&)));

    qRegisterMetaType<QList<QTreeWidgetItem*>>("QList<QTreeWidgetItem*>");

    connect(scan, SIGNAL(search_result(QList<QTreeWidgetItem*>)), this, SLOT(show_results(QList<QTreeWidgetItem*>)));

    connect(scan, SIGNAL(end_search(int)), thread, SLOT(quit()));
    connect(scan, SIGNAL(end_search(int)), dialog, SLOT(done(int)));
    connect(scan, SIGNAL(end_search(int)), scan, SLOT(deleteLater()));
    connect(thread, SIGNAL(finished()), thread, SLOT(deleteLater()));
    thread->start();
    if (dialog->exec() == QDialog::Rejected) {
        dialog->stop();
    }
    change_path();
}

void main_window::check_delete_button() {
    ui->delete_button->setEnabled(ui->tableWidget->selectedItems().length() != 0);
}

void main_window::show_results(QList<QTreeWidgetItem*> items) {
    if (items.size() == 0) {
        QTreeWidgetItem* item = new QTreeWidgetItem();
        item->setText(0, "Not found element");
        ui->treeWidget->insertTopLevelItem(0, item);
    } else {
        connect(ui->treeWidget, SIGNAL(itemChanged(QTreeWidgetItem*, int)),
                this, SLOT(mark_file(QTreeWidgetItem*)));

        std::reverse(items.begin(), items.end());

        ui->treeWidget->insertTopLevelItems(0, items);
    }
}

void main_window::delete_directory_from_list() {
    for (auto &el : ui->tableWidget->selectedItems()) {
        ui->tableWidget->removeRow(el->row());
    }
    change_path();
    if (ui->tableWidget->rowCount() == 0)
        ui->scan_button->setEnabled(false);
}

void main_window::mark_file(QTreeWidgetItem* check_box) {
    QString file_name = check_box->text(0);

    if (check_box->checkState(0) == Qt::Checked) {
        remove_files.emplace(file_name, check_box);
    } else if (remove_files.find({file_name, check_box}) != remove_files.end()) {
        remove_files.erase(remove_files.find({file_name, check_box}));
    }

    ui->delete_button->setEnabled(remove_files.size());
}

void main_window::delete_select_file() {
    if (remove_files.size() == 0)
        return;

    static bool skip_ckeck_message = false;

    if (!skip_ckeck_message) {
        QMessageBox* message = new QMessageBox(QMessageBox::Warning, QString("Notification"),
                QString("Are you sure you want to delete ") + QString::number(remove_files.size()) + " files",
                QMessageBox::StandardButtons({QMessageBox::Ok, QMessageBox::Cancel}), this);
        QCheckBox* check = new QCheckBox("don't show again", message);
        message->setCheckBox(check);
        if (message->exec() == QMessageBox::Cancel)
            return;
        skip_ckeck_message = (message->checkBox()->checkState() == Qt::Checked);
    }

    ui->delete_button->setEnabled(false);
    static bool skip_error = false;

    for (auto item : remove_files) {
        QFile file(item.first);

        if (file.permissions().testFlag(QFileDevice::WriteUser) /*|| file.remove()*/) {
            QTreeWidgetItem* parent_item = item.second->parent();
            parent_item->removeChild(item.second);
            if (parent_item->childCount() == 0) {
                ui->treeWidget->invisibleRootItem()->removeChild(parent_item);
            }
            remove_files.erase({item.first, item.second});
        } else {
            if (skip_error)
                continue;
            QMessageBox* m = new QMessageBox(QMessageBox::Warning, QString("Error"),
                    QString("failed to delete file: \n" + item.first), QMessageBox::StandardButtons(QMessageBox::Ignore),this);
            QCheckBox* check = new QCheckBox("don't show this error again", m);
            m->setCheckBox(check);
            if (m->exec() == QMessageBox::Ignore) {
                skip_error = (m->checkBox()->checkState() == Qt::Checked);
            }
            ui->delete_button->setEnabled(true);
        }
    }
}

void main_window::add_directory() {
    ui->add_button->setEnabled(false);
    remove_subfolder(QFileInfo(ui->path->text()));

    int row = ui->tableWidget->rowCount();
    ui->tableWidget->insertRow(row);
    ui->tableWidget->setItem(row, 0, new QTableWidgetItem(QFileInfo(ui->path->text()).absoluteFilePath()));

    ui->scan_button->setEnabled(true);
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
    for (int i = 0; i < ui->tableWidget->rowCount(); i++) {
        QString file = QString(ui->tableWidget->item(i, 0)->text());
        if (file_path.startsWith(file))
            return false;
    }
    return true;
}

void main_window::remove_subfolder(QFileInfo path) {
    QString file_path = path.absolutePath();
    for (int i = 0; i < ui->tableWidget->rowCount(); i++) {
        QString file = ui->tableWidget->item(i, 0)->text();
        if (file.startsWith(file_path)) {
            ui->tableWidget->removeRow(i--);
            change_path();
        }
    }
}
