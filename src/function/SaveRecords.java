package function;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


/**
 * 该类实现保存信息记录
 * 
 * 
 * @author 360°顺滑
 *
 * @date 2020/05/01
 *
 */
public class SaveRecords {
	
	String userName;
	String input;
	
	public SaveRecords(String userName,String input) {
		this.userName = userName;
		this.input = input;
	}
	
	public void saveRecords() {
		
		String path = "src/txt/" + userName + ".txt";
		
		File file = new File(path);

		// 文件不存在就创建一个
		if (!file.exists()) {

			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		try {
			fileWriter = new FileWriter(file,true);
			bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(input);
			bufferedWriter.flush();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (bufferedWriter != null) {
				try {
					bufferedWriter.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (fileWriter != null) {
				try {
					fileWriter.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
