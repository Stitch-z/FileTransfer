package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import function.ClearRecords;
import function.FindRecords;
import function.RecordsEcho;



/**
 * 该类实现历史记录窗口
 * 
 * @author 360°顺滑
 *
 * @date 2020/05/02
 *
 */
public class HistoricRecordsFrame {

	private String userName;
	private JTextPane textShowPane;
	
	private JFrame historicRecordsFrame;
	private JButton findRecordsButton;
	private JButton clearRecordsButton;
	private JTextField searchTextField;
	private JTextPane recordsShowPane;
	private Box clearBox;

	public HistoricRecordsFrame(String userName,JTextPane textShowPane) {
		this.userName = userName;
		this.textShowPane = textShowPane;
	}
	
	
	public void init() {

		// 搜索文本框
		searchTextField = new JTextField();
		searchTextField.setFont(new Font("宋体", 0, 25));

		// 查找记录按钮
		findRecordsButton = new JButton("查找记录");
		findRecordsButton.setFont(new Font("行楷", Font.PLAIN, 20));
		findRecordsButton.setBackground(Color.WHITE);
		findRecordsButton.setBorderPainted(false);
		findRecordsButton.setFocusPainted(false);

		// 将搜索文本框和搜索按钮放入Box容器
		Box searchBox = Box.createHorizontalBox();
		searchBox.setPreferredSize(new Dimension(900, 47));
		searchBox.setBackground(Color.white);
		searchBox.add(Box.createHorizontalStrut(35));
		searchBox.add(searchTextField);
		searchBox.add(Box.createHorizontalStrut(20));
		searchBox.add(findRecordsButton);
		searchBox.add(Box.createHorizontalStrut(25));

		// 显示文本窗格
		recordsShowPane = new JTextPane();
		recordsShowPane.setSize(900, 600);
		recordsShowPane.setBackground(Color.WHITE);
		recordsShowPane.setEditable(false);
		recordsShowPane.setBorder(null);
		recordsShowPane.setFont(new Font("宋体", 0, 25));
		// 显示文本窗格添加滚动条
		JScrollPane scrollShowPane = new JScrollPane(recordsShowPane);

		// 清空记录按钮
		clearRecordsButton = new JButton("清空记录");
		clearRecordsButton.setFont(new Font("行楷", Font.PLAIN, 20));
		clearRecordsButton.setBackground(Color.WHITE);
		clearRecordsButton.setBorderPainted(false);
		clearRecordsButton.setFocusable(false);

		// Box容器并添加清空记录按钮
		clearBox = Box.createHorizontalBox();
		clearBox.setPreferredSize(new Dimension(1000, 60));
		clearBox.setBackground(Color.white);
		clearBox.add(Box.createVerticalStrut(5));
		clearBox.add(clearRecordsButton);
		clearBox.add(Box.createVerticalStrut(5));

		// 设置主窗体
		historicRecordsFrame = new JFrame("历史记录");
		historicRecordsFrame.setSize(900, 700);
		historicRecordsFrame.setLocationRelativeTo(null);
		historicRecordsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		// 改变窗体logo
		ImageIcon image = new ImageIcon("src/pictures/historicRecordsLogo.png");
		historicRecordsFrame.setIconImage(image.getImage());
		historicRecordsFrame.setLayout(new BorderLayout());
		// 添加窗体以上两个主要容器
		historicRecordsFrame.add(searchBox, BorderLayout.NORTH);
		historicRecordsFrame.add(scrollShowPane, BorderLayout.CENTER);
		historicRecordsFrame.add(clearBox, BorderLayout.SOUTH);
		historicRecordsFrame.setVisible(true);
		
		addListen();
		
		RecordsEcho recordsEcho = new RecordsEcho(userName, recordsShowPane);
		recordsEcho.read();
		
		
		
	}
	
	
	//添加按钮监听事件
	public void addListen() {
		
		//清空历史记录监听事件
		clearRecordsButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new ClearRecords(userName,textShowPane,recordsShowPane).clear();
			}
		});
		
		//查找记录监听事件
		findRecordsButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				FindRecords findRecords = new FindRecords(recordsShowPane,userName,searchTextField.getText());
				findRecords.find();
			}
		});
	}
}
