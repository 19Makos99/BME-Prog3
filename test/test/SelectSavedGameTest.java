package test;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import controller.SelectSavedGame;
import model.gamemode.BaseGameMode;

public class SelectSavedGameTest {

	@Test
	public void buttonSavesCountTest() throws IOException {
		File f = new File("Saves");
		if (!f.exists())
			f.mkdir();
		
		if (f.listFiles().length == 0) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
			File save = new File("Saves/" + format.format(new Date()) + ".save");
			FileOutputStream fileOutputStream = new FileOutputStream(save);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(new BaseGameMode());
			fileOutputStream.close();
		}
		
		SelectSavedGame selectSavedGame = new SelectSavedGame(new WindowMock());
		
		assertEquals(selectSavedGame.buttons.size(), selectSavedGame.saves.size());
	}
}
