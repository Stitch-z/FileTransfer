package frame;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;

import function.SaveRecords;



/**
 * 该类实现表情包窗体
 * 
 * @author 360°顺滑
 * 
 * @date 2020/05/03
 *
 */
public class EmojiFrame {

	//展示面板
	private JTextPane showPane;
	
	//表情包按钮
	private JButton[] buttons = new JButton[55];

	//表情包图片
	private ImageIcon[] icons = new ImageIcon[55];

	//表情包对话框
	private JDialog emojiJDialog;
	
	//账号
	private String userName;

	public EmojiFrame(JTextPane showPane,String userName) {
		this.showPane = showPane;
		this.userName = userName;
	}
	
	//表情包窗体
	public void init() {

		//用对话框来装表情包
		emojiJDialog = new JDialog();
		emojiJDialog.setTitle("表情包");
		emojiJDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		emojiJDialog.setLayout(new GridLayout(6, 9));
		emojiJDialog.setBounds(490, 263, 500, 400);
		emojiJDialog.setBackground(Color.WHITE);
		ImageIcon image = new ImageIcon("src/pictures/emoji.png");
		emojiJDialog.setIconImage(image.getImage());

		//表情包用按钮来实现，主要是可以添加监听事件，点击后可以实现发送
		for (int i = 1; i <= 54; i++) {

			String path = "src/pictures/emoji_" + i + ".png";
			icons[i] = new ImageIcon(path);
			buttons[i] = new JButton(icons[i]);
			buttons[i].setBackground(Color.WHITE);
			buttons[i].setBorder(null);
			buttons[i].setBorderPainted(false);
			buttons[i].setSize(20, 20);
			buttons[i].setFocusPainted(false);

			emojiJDialog.add(buttons[i]);

		}
		
		emojiJDialog.setVisible(true);
		
		//添加监听事件
		addListen();

	}
	
	
	//监听事件
	public void addListen() {
		
		//为每一个按钮添加监听事件
		for(int i=1;i<=54;i++) {
			
			String path = "src/pictures/emoji_" + i + ".png";
			
			buttons[i].addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					
					//获取图片
					ImageIcon imageIcon = new ImageIcon(path);			
					
					// 获取日期
					Date date = new Date();
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

					String input = dateFormat.format(date) + "\n";
					
					Document document  = showPane.getDocument();
					
					try {
						//写入日期
						document.insertString(document.getLength(), input, new SimpleAttributeSet());
						
						//插入图片
						showPane.insertIcon(imageIcon);
						
						//换行
						document.insertString(document.getLength(), "\n\n", new SimpleAttributeSet());
						
					} catch (BadLocationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					// 保存路径到对应的账号信息里，因为用的是绝对路径，所以要根据实际来修改
					String saveText = input + "D:\\eclipse jee\\FileTransfer\\" + path + "\n\n";
					SaveRecords records = new SaveRecords(userName, saveText);
					records.saveRecords();
					
					
					emojiJDialog.setVisible(false);
				}
			});
		}
	}
}
