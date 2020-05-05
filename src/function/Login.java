package function;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * 文件传输助手登录功能
 * 
 * @author：360°顺滑
 * 
 * @date: 2020/04/29
 * 
 */

public class Login {

	JTextField userNameTextField;
	JPasswordField passwordField;

	public Login(JTextField userNameTextField, JPasswordField passwordField) {
		this.userNameTextField = userNameTextField;
		this.passwordField = passwordField;
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
		if ("".equals(new String(passwordField.getPassword())))
			return true;
		else
			return false;
	}
	
	
	// 查询是否存在该账号密码
	public boolean queryInformation() {

		File file = new File("src/txt/userInformation.txt");
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;

		boolean vis = false;
		try {

			fileReader = new FileReader(file);
			bufferedReader = new BufferedReader(fileReader);

			Pattern userNamePattern = Pattern.compile("用户名：.+");
			Pattern passwordPattern = Pattern.compile("密码：.+");

			String str1 = null;
			while ((str1 = bufferedReader.readLine()) != null) {
				
				Matcher userNameMatcher = userNamePattern.matcher(str1);
				
				if(userNameMatcher.find()) {
					
					if (("用户名：" + userNameTextField.getText()).equals(userNameMatcher.group())) {
						
						String str2 = bufferedReader.readLine();
						Matcher passwordMatcher = passwordPattern.matcher(str2);
						
						if(passwordMatcher.find()) {
							if (("密码：" + new String(passwordField.getPassword())).equals(passwordMatcher.group())) {
								vis = true;
								break;
							}
						}
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
			try {
				bufferedReader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				fileReader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (vis)
			return true;
		else
			return false;
		
	}

}
