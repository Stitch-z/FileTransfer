package function;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;

/**
 * 
 * 该类实现历史记录回显
 * 
 * @author 360°顺滑
 * 
 * @date 2020/05/02
 *
 */
public class RecordsEcho {

	private String userName;
	private JTextPane showPane;

	public RecordsEcho(String userName, JTextPane showPane) {
		this.userName = userName;
		this.showPane = showPane;
	}

	// 将用户的信息记录回显到展示面板
	public void read() {

		// 对应账号的信息记录文本
		File file = new File("src/txt/" + userName + ".txt");

		// 文件不存在就创建一个
		if (!file.exists()) {

			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		FileReader fileReader = null;
		BufferedReader bufferedReader = null;

		try {

			fileReader = new FileReader(file);
			bufferedReader = new BufferedReader(fileReader);

			// 正则表达式
			Pattern pattern = Pattern.compile(".+[\\.].+");

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {

				Matcher matcher = pattern.matcher(str);

				// 如果是文件或图片
				if (matcher.find()) {

					// 获得文件名
					int index = str.lastIndexOf("\\");
					String fileName = str.substring(index + 1);

					// 图片的情况
					if (str.endsWith(".png") || str.endsWith(".jpg") || str.endsWith(".jpeg") || str.endsWith("gif")) {

						Pattern pattern1 = Pattern.compile("[emoji_].+[\\.].+");

						Matcher matcher1 = pattern1.matcher(fileName);

						// 如果是表情包
						if (matcher1.find()) {
							writeEmoji(str);
						} else {
							writeImage(str, fileName);
						}
					} else {
						// 文件的情况
						writeFile(str, fileName);
					}

				} else {
					// 如果是文本则直接写入
					writeText(str);
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

	}

	// 把文本显示在展示面板
	public void writeText(String str) {

		String s = "";

		for (int i = 0; i < str.length(); i++) {

			if (i != 0 && i % 30 == 0)
				s += "\n";

			s += str.charAt(i);

		}

		Document document = showPane.getDocument();

		try {
			document.insertString(document.getLength(), s + "\n", new SimpleAttributeSet());
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void writeEmoji(String path) {

		// 获取图片
		ImageIcon imageIcon = new ImageIcon(path);

		// 获取整个展示面板的内容，方便图片文件的插入
		Document document = showPane.getDocument();

		try {

			// 插入图片
			showPane.insertIcon(imageIcon);

			// 换行
			document.insertString(document.getLength(), "\n", new SimpleAttributeSet());

		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 把图片显示在展示面板
	public void writeImage(String path, String fileName) {
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


		// 为图片名称添加按钮，用于打开图片
		JButton button = new JButton(fileName);
		button.setFont(new Font("宋体", Font.PLAIN, 20));
		button.setBackground(Color.WHITE);
		button.setBorderPainted(false);
		button.setFocusPainted(false);

		// 获取整个展示面板的内容，方便图片文件的插入
		Document document = showPane.getDocument();
		try {

			// 插入图片
			showPane.insertIcon(imageIcon);

			// 换行
			document.insertString(document.getLength(), "\n", new SimpleAttributeSet());

			// 插入按钮，也就是图片名称
			showPane.insertComponent(button);

			document.insertString(document.getLength(), "\n", new SimpleAttributeSet());

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

	}

	// 把文件显示在展示面板中
	public void writeFile(String path, String fileName) {

		// 获取固定文件图标
		Icon fileImage = new ImageIcon("src/pictures/document.png");

		// 为名称添加按钮
		JButton button = new JButton(fileName);
		button.setFont(new Font("宋体", Font.PLAIN, 20));
		button.setBackground(Color.WHITE);
		button.setBorderPainted(false);
		button.setFocusPainted(false);

		// 获取面板内容
		Document document = showPane.getDocument();

		try {

			showPane.insertIcon(fileImage);

			showPane.insertComponent(button);

			document.insertString(document.getLength(), "\n", new SimpleAttributeSet());
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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

	}

}
