package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;

import function.DropTargetFile;
import function.FileSend;
import function.RecordsEcho;
import function.TextSend;

/**
 * 文件传输助手主界面
 * 
 * @author 360°顺滑
 * 
 * @date:2020/04/29 ~ 2020/04/30
 *
 */

public class MainFrame {

	String userName;

	public MainFrame() {
	};

	public MainFrame(String userName) {
		this.userName = userName;
	}

	private JButton fileButton;
	private JButton historicRecordsButton;
	private JButton sendButton;
	private JTextPane showPane;
	private JTextPane inputPane;
	private JButton expressionButton;
	private JScrollPane scrollShowPane;
	private Box buttonBox;
	private Box inputBox;
	private Box sendBox;
	private Box totalBox;
	private ImageIcon image;
	static JFrame mainFrame;

	public void init() {

		// 显示文本窗格
		showPane = new JTextPane();
		showPane.setSize(600, 400);
		showPane.setBackground(Color.WHITE);
		showPane.setEditable(false);
		showPane.setBorder(null);
		showPane.setFont(new Font("宋体", 0, 25));
		// 显示文本窗格添加滚动条
		scrollShowPane = new JScrollPane(showPane);

		// 表情包按鈕并添加图标
		Icon expressionIcon = new ImageIcon("src/pictures/expression.png");
		expressionButton = new JButton(expressionIcon);
		expressionButton.setBackground(Color.WHITE);
		expressionButton.setFocusPainted(false);
		expressionButton.setBorderPainted(false);

		// 文件按钮并添加图标
		Icon fileIcon = new ImageIcon("src/pictures/file.png");
		fileButton = new JButton(fileIcon);
		fileButton.setBackground(Color.WHITE);
		fileButton.setFocusPainted(false);
		fileButton.setBorderPainted(false);

		// 历史记录按钮并添加图标
		Icon historicRecordsIcon = new ImageIcon("src/pictures/historicRecords.png");
		historicRecordsButton = new JButton(historicRecordsIcon);
		historicRecordsButton.setBackground(Color.WHITE);
		historicRecordsButton.setFocusPainted(false);
		historicRecordsButton.setBorderPainted(false);

		// 按钮Box容器添加三个按钮
		buttonBox = Box.createHorizontalBox();
		buttonBox.setPreferredSize(new Dimension(1000, 50));
		buttonBox.add(Box.createHorizontalStrut(10));
		buttonBox.add(expressionButton);
		buttonBox.add(Box.createHorizontalStrut(10));
		buttonBox.add(fileButton);
		buttonBox.add(Box.createHorizontalStrut(10));
		buttonBox.add(historicRecordsButton);
		// 添加 “历史记录”按钮到右边框的距离 到buttonBox容器中
		buttonBox.add(Box.createHorizontalGlue());

		// 输入文本窗格
		inputPane = new JTextPane();
		inputPane.setSize(600, 300);
		inputPane.setFont(new Font("宋体", 0, 24));
		inputPane.setBackground(Color.WHITE);
		JScrollPane scrollInputPane = new JScrollPane(inputPane);

		// 输入区域的Box容器
		inputBox = Box.createHorizontalBox();
		inputBox.setPreferredSize(new Dimension(1000, 150));
		inputBox.add(scrollInputPane);

		// 发送按钮
		sendButton = new JButton("发送(S)");
		sendButton.setFont(new Font("行楷", Font.PLAIN, 20));
		sendButton.setBackground(Color.WHITE);
		sendButton.setFocusPainted(false);
		sendButton.setBorderPainted(false);

		// 发送Box容器并添加发送按钮
		sendBox = Box.createHorizontalBox();
		sendBox.setPreferredSize(new Dimension(1000, 50));
		sendBox.setBackground(Color.white);
		sendBox.add(Box.createHorizontalStrut(710));
		sendBox.add(Box.createVerticalStrut(5));
		sendBox.add(sendButton);
		sendBox.add(Box.createVerticalStrut(5));

		// 总的Box容器添加以上3个Box
		totalBox = Box.createVerticalBox();
		totalBox.setPreferredSize(new Dimension(1000, 250));
		totalBox.setSize(1000, 400);
		totalBox.add(buttonBox);
		totalBox.add(inputBox);
		totalBox.add(Box.createVerticalStrut(3));
		totalBox.add(sendBox);
		totalBox.add(Box.createVerticalStrut(3));

		// 设置主窗体
		mainFrame = new JFrame("文件传输助手");
		mainFrame.setSize(950, 800);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// 改变窗体logo
		image = new ImageIcon("src/pictures/logo.png");
		mainFrame.setIconImage(image.getImage());
		mainFrame.setLayout(new BorderLayout());
		// 添加窗体以上两个主要容器
		mainFrame.add(scrollShowPane, BorderLayout.CENTER);
		mainFrame.add(totalBox, BorderLayout.SOUTH);
		mainFrame.setVisible(true);

		// 添加监听器
		addListen();

		// 信息记录回显到展示面板
		RecordsEcho echo = new RecordsEcho(userName, showPane);
		echo.read();

	}

	// 提示对话框
	public static void warnJDialog(String information) {
		JDialog jDialog = new JDialog(mainFrame, "提示");
		jDialog.setLayout(null);
		jDialog.setSize(300, 200);
		jDialog.setLocation(770, 400);
		ImageIcon image = new ImageIcon("src/pictures/warn.png");
		jDialog.setIconImage(image.getImage());

		JLabel jLabel = new JLabel(information);
		jLabel.setFont(new Font("行楷", 0, 21));
		jLabel.setBounds(48, 0, 200, 100);
		jDialog.add(jLabel);

		JButton button = new JButton("确定");
		button.setBounds(105, 80, 70, 40);
		button.setFont(new Font("微软雅黑", 1, 18));
		button.setBackground(Color.WHITE);
		button.setFocusPainted(false);
		button.setBorderPainted(false);
		jDialog.add(button);

		// 为弹出对话框按钮添加监听事件
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				jDialog.dispose();
			}
		});

		jDialog.setVisible(true);
	}

	// 添加监听事件
	@SuppressWarnings("unused")
	public void addListen() {

		/*
		 * 为输入文本添加目标监听器
		 */

		// 创建拖拽目标监听器
		DropTargetListener listener = new DropTargetFile(inputPane);
		// 在 inputPane上注册拖拽目标监听器
		DropTarget dropTarget = new DropTarget(inputPane, DnDConstants.ACTION_COPY_OR_MOVE, listener, true);

		// 发送按钮监听事件
		sendButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				TextSend textSend = new TextSend(showPane, inputPane, userName);
				textSend.sendText();
			}
		});

		// 输入框添加键盘事件
		inputPane.addKeyListener(new KeyListener() {

			// 发生击键事件时被触发
			@Override
			public void keyTyped(KeyEvent e) {

			}

			// 按键被释放时被触发
			@Override
			public void keyReleased(KeyEvent e) {

			}

			// 按键被按下时被触发
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub

				// 如果按下的是 Ctrl + Enter 组合键 则换行
				if ((e.getKeyCode() == KeyEvent.VK_ENTER) && e.isControlDown()) {

					Document document = inputPane.getDocument();

					try {
						document.insertString(document.getLength(), "\n", new SimpleAttributeSet());
					} catch (BadLocationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					// 否则发送
				} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {

					TextSend textSend = new TextSend(showPane, inputPane, userName);
					textSend.sendText();

				}

			}

		});

		// 表情包按钮监听事件
		expressionButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new EmojiFrame(showPane, userName).init();
			}
		});

		// 文件按钮监听事件
		fileButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				FileSend fileSend = new FileSend(userName, showPane, inputPane);
				fileSend.send();
			}
		});

		// 历史记录按钮监听事件
		historicRecordsButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new HistoricRecordsFrame(userName, showPane).init();
			}
		});
	}

}
