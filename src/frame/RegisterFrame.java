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

import function.Register;

/**
 *
 * 文件传输助手注册界面
 * 
 * @author 360°顺滑
 * 
 * @date: 2020/04/27 ~ 2020/04/28
 *
 */
public class RegisterFrame {

	public JFrame registerJFrame;
	public JLabel userNameLabel;
	public JTextField userNameTextField;
	public JLabel passwordLabel;
	public JPasswordField passwordField;
	public JLabel passwordAgainLabel;
	public JPasswordField passwordAgainField;
	public JButton goBackButton;
	public JButton registerButton;


	public void init() {

		// 创建窗体
		registerJFrame = new JFrame("文件传输助手");
//		registerJFrame.setTitle();
		registerJFrame.setSize(540, 400);
		registerJFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		ImageIcon image = new ImageIcon("src/pictures/logo.png");
		registerJFrame.setIconImage(image.getImage());
		registerJFrame.setLocationRelativeTo(null);
		registerJFrame.setResizable(false);

		// 创建内容面板
		Container container = registerJFrame.getContentPane();
		container.setLayout(null);

		// 创建“账号”标签
		userNameLabel = new JLabel("账号：");
		userNameLabel.setFont(new Font("行楷", Font.BOLD, 25));
		userNameLabel.setBounds(97, 25, 100, 100);
		container.add(userNameLabel);

		// 创建输入账号文本框
		userNameTextField = new JTextField();
		userNameTextField.setFont(new Font("黑体", Font.PLAIN, 23));
		userNameTextField.setBounds(170, 61, 280, 33);
		container.add(userNameTextField);

		// 创建“密码”标签
		passwordLabel = new JLabel("密码：");
		passwordLabel.setFont(new Font("行楷", Font.BOLD, 25));
		passwordLabel.setBounds(97, 90, 100, 100);
		container.add(passwordLabel);

		// 创建输入密码文本框
		passwordField = new JPasswordField();
		passwordField.setBounds(170, 125, 280, 33);
		passwordField.setFont(new Font("Arial", Font.BOLD, 23));
		container.add(passwordField);

		// 创建“确认密码”标签
		passwordAgainLabel = new JLabel("确认密码：");
		passwordAgainLabel.setFont(new Font("行楷", Font.BOLD, 25));
		passwordAgainLabel.setBounds(45, 150, 130, 100);
		container.add(passwordAgainLabel);

		// 创建确认密码文本框
		passwordAgainField = new JPasswordField();
		passwordAgainField.setBounds(170, 185, 280, 33);
		passwordAgainField.setFont(new Font("Arial", Font.BOLD, 23));
		container.add(passwordAgainField);

		// 创建返回按钮
		goBackButton = new JButton("返回");
		goBackButton.setBounds(200, 260, 70, 40);
		goBackButton.setFont(new Font("微软雅黑", 1, 18));
		goBackButton.setBackground(Color.WHITE);
		goBackButton.setFocusPainted(false);
		goBackButton.setBorderPainted(false);
		container.add(goBackButton);

		// 创建注册按钮
		registerButton = new JButton("注册");
		registerButton.setBounds(330, 260, 70, 40);
		registerButton.setFont(new Font("微软雅黑", 1, 18));
		registerButton.setBackground(Color.WHITE);
		registerButton.setFocusPainted(false);
		registerButton.setBorderPainted(false);
		container.add(registerButton);

		// 显示窗体
		registerJFrame.setVisible(true);

		addListen();

	}

	// 为按钮添加监听事件
	public void addListen() {

		// 为注册按钮添加监听事件
		registerButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				// 创建register对象，同时将RegisterFrame的文本框内容作为参数传过去
				Register register = new Register(userNameTextField, passwordField, passwordAgainField);

				// 判断输入账号是否为空
				if (register.isEmptyUserName()) {

					emptyUserName(registerJFrame);

				} else {

					// 判断输入密码是否为空
					if (register.isEmptyPassword()) {
						emptyPasswordJDialog(registerJFrame);
					}

					else {
						// 判断密码和确认密码是否一致
						if (register.isSamePassWord()) {

							// 判断账号是否已存在
							if (!register.isExistAccount()) {

								// 注册成功！！！

								register.saveInformation();
								registerJFrame.dispose();
								userNameTextField.setText("");
								passwordField.setText("");
								passwordAgainField.setText("");

								new LoginFrame();
								LoginFrame.loginJFrame.setVisible(true);

								successRegisterJDialog(registerJFrame);

							} else
								existAccountJDialog(registerJFrame);
						} else {
							differentPasswordJDialog(registerJFrame);
							passwordField.setText("");
							passwordAgainField.setText("");
						}
					}
				}

			}
		});

		// 为返回按钮添加监听事件
		goBackButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// 销毁注册窗口
				registerJFrame.dispose();

				// 重新显示登录窗口
				new LoginFrame();
				LoginFrame.loginJFrame.setVisible(true);
			}
		});

	}

	/*
	 * 由于各个标签长度不同，所以为了界面美观，就写了三个弹出对话框而不是一个！
	 * 
	 */

	// 未输入账号时弹出提示对话框
	public void emptyUserName(JFrame jFrame) {
		JDialog jDialog = new JDialog(jFrame, "提示");
		jDialog.setLayout(null);
		jDialog.setSize(300, 200);
		jDialog.setLocationRelativeTo(null);
		ImageIcon image = new ImageIcon("src/pictures/warn.png");
		jDialog.setIconImage(image.getImage());

		JLabel jLabel = new JLabel("未输入用户名！");
		jLabel.setFont(new Font("行楷", 0, 21));
		jLabel.setBounds(73, 0, 200, 100);
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
	public void emptyPasswordJDialog(JFrame jFrame) {
		JDialog jDialog = new JDialog(jFrame, "提示");
		jDialog.setLayout(null);
		jDialog.setSize(300, 200);
		jDialog.setLocationRelativeTo(null);
		ImageIcon image = new ImageIcon("src/pictures/warn.png");
		jDialog.setIconImage(image.getImage());

		JLabel jLabel = new JLabel("未输入密码！");
		jLabel.setFont(new Font("行楷", 0, 21));
		jLabel.setBounds(73, 0, 200, 100);
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

	// 密码和确认密码不一致时弹出提示框
	public void differentPasswordJDialog(JFrame jFrame) {

		JDialog jDialog = new JDialog(jFrame, "提示");
		jDialog.setLayout(null);
		jDialog.setSize(300, 200);
		jDialog.setLocationRelativeTo(null);
		ImageIcon image = new ImageIcon("src/pictures/warn.png");
		jDialog.setIconImage(image.getImage());

		JLabel jLabel = new JLabel("输入密码不一致！");
		jLabel.setFont(new Font("行楷", 0, 21));
		jLabel.setBounds(63, 0, 200, 100);
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

	// 已存在账号弹出提示对话框
	public void existAccountJDialog(JFrame jFrame) {

		JDialog jDialog = new JDialog(jFrame, "提示");
		jDialog.setLayout(null);
		jDialog.setSize(300, 200);
		jDialog.setLocationRelativeTo(null);
		ImageIcon image = new ImageIcon("src/pictures/warn.png");
		jDialog.setIconImage(image.getImage());

		JLabel jLabel = new JLabel("该账号已存在！");
		jLabel.setFont(new Font("行楷", 0, 21));
		jLabel.setBounds(73, 0, 200, 100);
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

	// 成功注册对话框
	public void successRegisterJDialog(JFrame jFrame) {

		JDialog jDialog = new JDialog(jFrame, "提示");
		jDialog.setLayout(null);
		jDialog.setSize(300, 200);
		jDialog.setLocationRelativeTo(null);
		ImageIcon image = new ImageIcon("src/pictures/warn.png");
		jDialog.setIconImage(image.getImage());

		JLabel jLabel = new JLabel("注册成功！");
		jLabel.setFont(new Font("行楷", 0, 21));
		jLabel.setBounds(73, 0, 200, 100);
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
				// 销毁提示对话框
				jDialog.dispose();

			}
		});

		jDialog.setVisible(true);

	}

}
