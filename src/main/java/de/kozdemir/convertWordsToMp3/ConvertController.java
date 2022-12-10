package de.kozdemir.convertWordsToMp3;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.io.IOUtils;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class ConvertController {

	@FXML
	private TextArea txtTextArea;
	@FXML
	private TextField txtDirectoryName;

	@FXML
	private ComboBox<Locale> cmbQuelleLanguage;

	@FXML
	private ComboBox<Locale> cmbTargetLanguage;

	@FXML
	private AnchorPane anchorPaneId;

	@FXML
	public void clearForm() {
		txtTextArea.clear();
	}

	private static String directoryName;

	@FXML
	public void browse() {

		final DirectoryChooser dc = new DirectoryChooser();

		Stage stage = (Stage) anchorPaneId.getScene().getWindow();

		File file = dc.showDialog(stage);

		if (file != null) {

			txtDirectoryName.setText(file.getAbsolutePath());
			directoryName = file.getAbsolutePath().toString().replace("\\", "\\".concat("\\")).concat("\\")
					.concat("\\");
			System.out.println("Patch: " + directoryName);
		}

	}

	@FXML
	public void convert() {

//		klasorOlustur(new File("Audios"));


		if (txtTextArea.getText().trim().equals("")) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Please paste the words you want to translate into the relevant field.");
//				alert.setContentText(alert.toString());
			alert.showAndWait();

		} else if (directoryName != null) {
			
			List<String> words = new ArrayList<>();

			for (String s : txtTextArea.getText().split("\n")) {
				words.add(s.trim().toLowerCase());
			}
			for (String w : words) {
				String dosyaadi = w.replace('?', ' ').replace(',', ' ').replace('.', ' ').replace('!', ' ');
				String translate = w.replace(' ', '+').replace("ü", "ue").replace("ä", "ae").replace("ß", "ss")
						.replace("ö", "oe");

				if (dosyaadi.length() > 50) {
					dosyaadi = dosyaadi.substring(0, 49) + "...";
				}

				URL url = null;
				try {
					url = new URL(
							"https://translate.google.com/translate_tts?ie=UTF-8&tl=de-DE&client=tw-ob&q=" + translate);

					System.out.println(url);

					HttpURLConnection httpcon = null;

					httpcon = (HttpURLConnection) url.openConnection();
					httpcon.addRequestProperty("User-Agent", "anything");
					IOUtils.copy(httpcon.getInputStream(), new FileOutputStream(directoryName + dosyaadi + ".mp3"));

				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("select the folder where you want to save the files");
//			alert.setContentText(alert.toString());
			alert.showAndWait();
		}

	}

	public static String klasorOlustur(File file) {

		if (file.exists()) // Klasör varmı diye kontrol ettiriyoruz.
		{
			return "Bu klasör ile aynı isimli bir klasör var.";
		} else {
			try {
				if (file.mkdir()) // Buradaki mkdir klasörü belirtir.
				{
					return "Onaylandı";
				} else {
					return "Hata oluştu";
				}
			} catch (Exception e) {
				return "Hata oluştu: " + e;
			}
		}
	}

//	public void donustur(String dosyaadi, String translate) {
//		System.out.println(dosyaadi + " : " + translate);
//		URL url = null;
//		try {
//			url = new URL("https://translate.google.com/translate_tts?ie=UTF-8&tl=de-DE&client=tw-ob&q=" + translate);
//
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		}
//
//		System.out.println(url);
//		HttpURLConnection httpcon = null;
//		try {
//			httpcon = (HttpURLConnection) url.openConnection();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		httpcon.addRequestProperty("User-Agent", "anything");
//		try {
//			IOUtils.copy(httpcon.getInputStream(), new FileOutputStream("Audios\\" + dosyaadi + ".mp3"));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//	}

}
