module de.kozdemir.convertWordsToMp3 {
    requires javafx.controls;
    requires javafx.fxml;
	requires org.apache.commons.io;
	requires javafx.graphics;

    opens de.kozdemir.convertWordsToMp3 to javafx.fxml;
    exports de.kozdemir.convertWordsToMp3;
}
