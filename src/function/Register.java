package function;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * 文件传输助手注册功能
 * 
 * @author：360°顺滑
 * 
 * @date：2020/04/28 ~ 2020/04/29
 * 
 */

public class Register {

	
	JTextField userNameTextField;
	JPasswordField passwordField;
	JPasswordField passwordAgainField;

	//将RegisterFrame参数传入进来
	public Register(JTextField userNameTextField, JPasswordField passwordField, JPasswordField passwordAgainField) {
		this.userNameTextField = userNameTextField;
		this.passwordField = passwordField;
		this.passwordAgainField = passwordAgainField;
	}

	
	//判断账号是否为空方法
	public boolean isEmptyUserName() {
		if (userNameTextField.getText().equals(""))
			return true;
		else
			return false;
	}

	
	//判断密码是否为空方法
	public boolean isEmptyPassword() {
		//操作密码框文本要先将其转换为字符串
		if ("".equals(new String(passwordField.getPassword())) || "".equals(new String(passwordAgainField.getPassword())))
			return true;
		else
			return false;
	}

	
	//判断密码和输入密码是否一致方法
	public boolean isSamePassWord() {
		
		//操作密码框文本要先将其转换为字符串
		if (new String(passwordField.getPassword()).equals(new String(passwordAgainField.getPassword())))
			return true;
		else
			return false;
	}

	
	//判断账号是否已存在方法
	public boolean isExistAccount() {
		File file = new File("src/txt/userInformation.txt");

		FileReader fileReader = null;
		BufferedReader bufferedReader = null;

		boolean vis = false;

		try {
			fileReader = new FileReader(file);
			bufferedReader = new BufferedReader(fileReader);

			//正则表达式
			Pattern pattern = Pattern.compile("用户名：.+");

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				Matcher matcher = pattern.matcher(str);
				if (matcher.find()) {
					if (("用户名：" + userNameTextField.getText()).equals(matcher.group())) {
						vis = true;
						break;
					}
				}

			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			if (fileReader != null) {
				try {
					fileReader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		if (!vis) {
			return false;
		} else {
			return true;
		}

	}
	

	//保存信息到本地
	public void saveInformation() {
		File file = new File("src/txt/userInformation.txt");

		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;

		try {
			fileWriter = new FileWriter(file, true);
			bufferedWriter = new BufferedWriter(fileWriter);

			bufferedWriter.write("用户名：" + userNameTextField.getText());
			bufferedWriter.newLine();
			bufferedWriter.write("密码：" + new String(passwordField.getPassword()));
			bufferedWriter.newLine();
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
