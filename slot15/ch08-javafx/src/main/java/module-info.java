module com.fu.ch08 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.media;

    // Tiện ích dùng chung
    exports com.fu.ch08.common;

    // B1: Làm quen Controls & Xử lý sự kiện (Đặt đồ uống)
    exports com.fu.ch08.b1;

    // TODO: Bỏ comment khi thực hiện các bài tiếp theo:
    // exports com.fu.ch08.b2;
    // exports com.fu.ch08.b3;
    // opens com.fu.ch08.b4 to javafx.fxml;
    // exports com.fu.ch08.b4;
    // opens com.fu.ch08.b5 to javafx.fxml;
    // exports com.fu.ch08.b5;
    // opens com.fu.ch08.b6 to javafx.fxml;
    // exports com.fu.ch08.b6;
    // opens com.fu.ch08.t1 to javafx.fxml, javafx.base;
    // exports com.fu.ch08.t1;
    // exports com.fu.ch08.t2;
    // exports com.fu.ch08.t3;
    // opens com.fu.ch08.t4 to javafx.fxml, javafx.base;
    // exports com.fu.ch08.t4;
    // exports com.fu.ch08.t5;
    // exports com.fu.ch08.t6;
}