#include "dialog.h"
#include <ui_dialog.h>

dialog::dialog(QThread* thread, QWidget *parent) :
    thread(thread), QDialog(parent), ui(new Ui::Dialog){

    ui->setupUi(this);
    connect(ui->pushButton, SIGNAL(clicked()), this, SLOT(stop()));
}

dialog::~dialog(){}

void dialog::stop() {
    thread->requestInterruption();
}

void dialog::update_status_bar(int value) {
//    ui->progressBar->setValue(value);
    ui->progressBar->setValue(ui->progressBar->value() + 1);
}

void dialog::update_status_bar_range(int value){
    ui->progressBar->setMaximum(value);
}

void dialog::update_message(QString const& message) {
    ui->label->setText(message);
}


