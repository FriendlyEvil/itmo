#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QMainWindow>
#include <memory>
#include <map>
#include <set>
#include <vector>
#include <QFileInfo>
#include <QTreeWidget>

namespace Ui {
class MainWindow;
}

class main_window : public QMainWindow
{
    Q_OBJECT

public:
    explicit main_window(QWidget *parent = 0);
    ~main_window();

private slots:
    void scan_directory_list();
    void open();
    void add_directory();
    void mark_file(QTreeWidgetItem* check_box);
    void show_results(QList<QTreeWidgetItem*> items);
    void check_delete_button();
    void delete_directory_from_list();

private:
    void change_path();
    void delete_select_file();
    bool check_path(QFileInfo path);
    void remove_subfolder(QFileInfo path);

private:
    std::unique_ptr<Ui::MainWindow> ui;
    std::set<std::pair<QString, QTreeWidgetItem *>> remove_files;
};

#endif // MAINWINDOW_H
