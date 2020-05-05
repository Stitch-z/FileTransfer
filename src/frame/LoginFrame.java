package frame;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import function.Login;

/**
 * 文件传输助手登陆界面
 * 
 * @author：360°顺滑
 * 
 * @date：2020/04/27
 * 
 */

public class LoginFrame {

	public static JFrame loginJFrame;
	public static JLabel userNameLabel;
	public static JTextField userNameTextField;
	public static JLabel passwordLabel;
	public static JPasswordField passwordField;
	public static JButton loginButton;
	public static JButton registerButton;

	public static void main(String[] args) {

		// 创建窗体
		loginJFrame = new JFrame("文件传输助手");
		loginJFrame.setSize(500, 300);
		loginJFrame.setLocationRelativeTo(null);
		loginJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ImageIcon image = new ImageIcon("src/pictures/logo.png");
		loginJFrame.setIconImage(image.getImage());
		loginJFrame.setResizable(false);

		// 创建内容面板
		Container container = loginJFrame.getContentPane();
		container.setLayout(null);

		// 创建“账号”标签
		userNameLabel = new JLabel("账号：");
		userNameLabel.setFont(new Font("行楷", Font.BOLD, 25));
		userNameLabel.setBounds(60, 25, 100, 100);
		container.add(userNameLabel);

		// 创建输入账号文本框
		userNameTextField = new JTextField();
		userNameTextField.setFont(new Font("黑体", Font.PLAIN, 23));
		userNameTextField.setBounds(133, 61, 280, 33);
		container.add(userNameTextField);

		// 创建“密码”标签
		passwordLabel = new JLabel("密码：");
		passwordLabel.setFont(new Font("行楷", Font.BOLD, 25));
		passwordLabel.setBounds(60, 90, 100, 100);
		container.add(passwordLabel);

		// 创建输入密码文本框
		passwordField = new JPasswordField();
		passwordField.setBounds(133, 127, 280, 33);
		passwordField.setFont(new Font("Arial", Font.BOLD, 23));
		container.add(passwordField);

		// 创建登录按钮
		loginButton = new JButton("登录");
		loginButton.setBounds(170, 185, 70, 40);
		loginButton.setFont(new Font("微软雅黑", 1, 18));
		loginButton.setBackground(Color.WHITE);
		loginButton.setFocusPainted(false);
		loginButton.setBorderPainted(false);

		container.add(loginButton);

		// 创建注册按钮
		registerButton = new JButton("注册");
		registerButton.setBounds(282, 185, 70, 40);
		registerButton.setFont(new Font("微软雅黑", 1, 18));
		registerButton.setBackground(Color.WHITE);
		registerButton.setFocusPainted(false);
		registerButton.setBorderPainted(false);
		container.add(registerButton);

		// 显示窗体
		loginJFrame.setVisible(true);

		addListen();
	}

	// 为按钮添加监听器
	public static void addListen() {

		// 为登录按钮添加监听事件
		loginButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				// 创建Login对象并把LoginFrame的文本框内容作为参数传过去
				Login login = new Login(userNameTextField, passwordField);

				// 判断是否符合登录成功的条件
				if (login.isEmptyUserName()) {
					emptyUserName(loginJFrame);
				} else {
					if (login.isEmptyPassword()) {
						emptyPasswordJDialog(loginJFrame);
					} else {
						if (login.queryInformation()) {
							loginJFrame.dispose();
							MainFrame mainFrame = new MainFrame(userNameTextField.getText());
							mainFrame.init();
						} else {
							failedLoginJDialog(loginJFrame);
						}
					}
				}
			}
		});

		// 为注册按钮添加监听事件
		registerButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				// 隐藏当前登录窗口
				loginJFrame.setVisible(false);

				// 打开注册窗口
				new RegisterFrame().init();
			}
		});

	}

	/*
	 * 由于各个标签长度不同，所以为了界面美观，就写了三个弹出对话框而不是一个！
	 * 
	 */

	// 未输入账号时弹出提示对话框
	public static void emptyUserName(JFrame jFrame) {
		JDialog jDialog = new JDialog(jFrame, "提示");
		jDialog.setLayout(null);
		jDialog.setSize(300, 200);
		jDialog.setLocationRelativeTo(null);
		ImageIcon image = new ImageIcon("src/pictures/warn.png");
		jDialog.setIconImage(image.getImage());

		JLabel jLabel = new JLabel("未输入账号！");
		jLabel.setFont(new Font("行楷", 0, 21));
		jLabel.setBounds(82, 0, 200, 100);
		jDialog.add(jLabel);

		JButton button = new JButton("确定");
		button.setBounds(105, 80, 70, 40);
		button.setFont(new Font("微软雅黑", 1, 18));
		button.setBackground(Color.WHITE);
		button.setFocusPainted(false);
		button.setBorderPainted(false);
		jDialog.add(button);

		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				jDialog.dispose();
			}
		});

		jDialog.setVisible(true);
	}

	// 未输入密码时弹出提示对话框
	public static void emptyPasswordJDialog(JFrame jFrame) {
		JDialog jDialog = new JDialog(jFrame, "提示");
		jDialog.setLayout(null);
		jDialog.setSize(300, 200);
		jDialog.setLocationRelativeTo(null);
		ImageIcon image = new ImageIcon("src/pictures/warn.png");
		jDialog.setIconImage(image.getImage());

		JLabel jLabel = new JLabel("未输入密码！");
		jLabel.setFont(new Font("行楷", 0, 21));
		jLabel.setBounds(82, 0, 200, 100);
		jDialog.add(jLabel);

		JButton button = new JButton("确定");
		button.setBounds(105, 80, 70, 40);
		button.setFont(new Font("微软雅黑", 1, 18));
		button.setBackground(Color.WHITE);
		button.setFocusPainted(false);
		button.setBorderPainted(false);
		jDialog.add(button);

		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				jDialog.dispose();
			}
		});

		jDialog.setVisible(true);
	}

	// 账号或密码输入错误！
	public static void failedLoginJDialog(JFrame jFrame) {

		JDialog jDialog = new JDialog(jFrame, "提示");
		jDialog.setLayout(null);
		jDialog.setSize(300, 200);
		jDialog.setLocationRelativeTo(null);
		ImageIcon image = new ImageIcon("src/pictures/warn.png");
		jDialog.setIconImage(image.getImage());

		JLabel jLabel = new JLabel("账号或密码输入错误！");
		jLabel.setFont(new Font("行楷", 0, 20));
		jLabel.setBounds(47, 0, 200, 100);
		jDialog.add(jLabel);

		JButton button = new JButton("确定");
		button.setBounds(105, 80, 70, 40);
		button.setFont(new Font("微软雅黑", 1, 18));
		button.setBackground(Color.WHITE);
		button.setFocusPainted(false);
		button.setBorderPainted(false);
		jDialog.add(button);

		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				jDialog.dispose();
			}
		});

		jDialog.setVisible(true);

	}
}
