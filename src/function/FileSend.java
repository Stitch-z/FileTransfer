package function;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;

/**
 * 实现打开文件按钮发送图片文件表情包
 * 
 * @author 360°顺滑
 *
 * @date 2020/05/01
 */
public class FileSend {

	String userName;
	String path;
	String fileName;
	JTextPane textShowPane;
	JTextPane textInputPane;

	public FileSend() {};
	
	public FileSend(String userName, JTextPane textShowPane, JTextPane textInputPane) {

		this.userName = userName;
		this.textShowPane = textShowPane;
		this.textInputPane = textInputPane;
	}

	
	// 弹出选择框并判断发送的是文件还是图片
	public void send() {
		// 点击文件按钮可以打开文件选择框
		JFileChooser fileChooser = new JFileChooser();

		int result = fileChooser.showOpenDialog(null);

		if (result == JFileChooser.APPROVE_OPTION) {

			// 被选择文件路径
			path = fileChooser.getSelectedFile().getAbsolutePath();
			// 被选择文件名称
			fileName = fileChooser.getSelectedFile().getName();

			// 选择的是图片
			if (path.endsWith(".png") || path.endsWith(".jpg") || path.endsWith(".gif") || path.endsWith(".jpeg")) {
				sendImage(path, fileName);
			} else {
				sendFile(path, fileName);
			}

		}
		
		
	}

	
	// 发送图片
	public void sendImage(String path, String fileName) {

		// 获取图片
		ImageIcon imageIcon = new ImageIcon(path);

		// 如果图片比整个界面大则调整大小
		int width, height;
		if (imageIcon.getIconWidth() > 950 || imageIcon.getIconHeight() > 400) {
			width = 600;
			height = 250;
		} else {
			width = imageIcon.getIconWidth();
			height = imageIcon.getIconHeight();
		}

		// 设置图片大小
		imageIcon.setImage(imageIcon.getImage().getScaledInstance(width, height, 0));

		// 获取日期
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM--dd HH:mm:ss");

		String input = dateFormat.format(date) + "\n";

		// 为图片名称添加按钮，用于打开图片
		JButton button = new JButton(fileName);
		button.setFont(new Font("宋体", Font.PLAIN, 20));
		button.setBackground(Color.WHITE);
		button.setBorderPainted(false);
		button.setFocusPainted(false);

		// 获取整个展示面板的内容，方便图片文件的插入
		Document document = textShowPane.getDocument();
		try {
			// 插入日期
			document.insertString(document.getLength(), input, new SimpleAttributeSet());

			// 插入图片
			textShowPane.insertIcon(imageIcon);

			// 换行
			document.insertString(document.getLength(), "\n", new SimpleAttributeSet());

			// 插入按钮，也就是图片名称
			textShowPane.insertComponent(button);

			document.insertString(document.getLength(), "\n\n", new SimpleAttributeSet());

		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 为按钮添加点击事件
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {

					// 实现打开文件功能
					File file = new File(path);
					Desktop.getDesktop().open(file);
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			}
		});

		// 输入框重新清空
		textInputPane.setText("");

		// 保存路径到对应的账号信息里
		String saveText = input + path + "\n\n";
		SaveRecords records = new SaveRecords(userName, saveText);
		records.saveRecords();
	}
	

	// 发送文件
	public void sendFile(String path, String fileName) {
		// 获取固定文件图标
		Icon fileImage = new ImageIcon("src/pictures/document.png");

		// 获取日期
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String input = dateFormat.format(date) + "\n";

		// 为名称添加按钮
		JButton button = new JButton(fileName);
		button.setFont(new Font("宋体", Font.PLAIN, 20));
		button.setBackground(Color.WHITE);
		button.setBorderPainted(false);
		button.setFocusPainted(false);

		// 获取面板内容
		Document document = textShowPane.getDocument();

		try {
			document.insertString(document.getLength(), input, new SimpleAttributeSet());

			textShowPane.insertIcon(fileImage);

			textShowPane.insertComponent(button);

			document.insertString(document.getLength(), "\n\n", new SimpleAttributeSet());
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//为名称按钮添加监听事件，实现打开功能
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					// 实现打开文件功能
					File file = new File(path);
					Desktop.getDesktop().open(file);
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			}
		});

		textInputPane.setText("");

		// 保存路径到对应的账号信息里
		String saveText = input + path + "\n\n";
		SaveRecords records = new SaveRecords(userName, saveText);
		records.saveRecords();
	}
	
	//发送表情包功能
	public void sendEmoji(String path) {

		// 获取图片
		ImageIcon imageIcon = new ImageIcon(path);


		// 获取日期
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM--dd HH:mm:ss");

		String input = dateFormat.format(date) + "\n";

		// 获取整个展示面板的内容，方便图片文件的插入
		Document document = textShowPane.getDocument();
		try {
			// 插入日期
			document.insertString(document.getLength(), input, new SimpleAttributeSet());

			// 插入图片
			textShowPane.insertIcon(imageIcon);


			document.insertString(document.getLength(), "\n\n", new SimpleAttributeSet());

		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		// 输入框重新清空
		textInputPane.setText("");

	}
	

}
