module de.kozdemir.convertWordsToMp3 {
    requires javafx.controls;
    requires javafx.fxml;
	requires org.apache.commons.io;

    opens de.kozdemir.convertWordsToMp3 to javafx.fxml;
    exports de.kozdemir.convertWordsToMp3;
}
