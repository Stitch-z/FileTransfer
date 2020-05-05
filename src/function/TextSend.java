package function;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;

import frame.MainFrame;

/**
 * 实现发送消息并保存到信息记录
 * 
 * @author 360°顺滑
 * 
 * @date 2020/05/01
 *
 */
public class TextSend {

	JFrame mainFrame;
	JTextPane textShowPane;
	JTextPane textInputPane;
	String userName;

	public TextSend(JTextPane textShowPane, JTextPane textInputPane, String userName) {
		this.textShowPane = textShowPane;
		this.textInputPane = textInputPane;
		this.userName = userName;
	}

	public void sendText() {

		//如果输入框不为空
		if (!("".equals(textInputPane.getText()))) {

			// 获取日期并设置日期格式
			Date date = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			SimpleAttributeSet attributeSet = new SimpleAttributeSet();

			// 获取日期
			String inputText = dateFormat.format(date) + "\n";

			//正则表达式
			Pattern pattern = Pattern.compile(".+[\\.].+");
			Matcher matcher = pattern.matcher(textInputPane.getText());

			// 判断是否为文件
			boolean isFile = false;
			// 判断是否为第一个文件
			boolean isFirst = true;
			while (matcher.find()) {
				isFile = true;

				// 获得文件名
				int index = matcher.group().lastIndexOf("\\");
				String fileName = matcher.group().substring(index + 1);

				// 图片的情况
				if (matcher.group().endsWith(".png") || matcher.group().endsWith(".jpg")
						|| matcher.group().endsWith(".jpeg") || matcher.group().endsWith("gif")) {

					Document document = textShowPane.getDocument();

					try {

						if (isFirst) {
							isFirst = false;
							document.insertString(document.getLength(), inputText, new SimpleAttributeSet());
							new RecordsEcho(userName, textShowPane).writeImage(matcher.group(), fileName);
							document.insertString(document.getLength(), "\n", new SimpleAttributeSet());

						}

						else {
							new RecordsEcho(userName, textShowPane).writeImage(matcher.group(), fileName);
							document.insertString(document.getLength(), "\n", new SimpleAttributeSet());

						}

					} catch (BadLocationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else {// 文件的情况

					Document document = textShowPane.getDocument();

					try {

						if (isFirst) {
							isFirst = false;
							document.insertString(document.getLength(), inputText, new SimpleAttributeSet());
							new RecordsEcho(userName, textShowPane).writeFile(matcher.group(), fileName);
							document.insertString(document.getLength(), "\n", new SimpleAttributeSet());

						}

						else {
							new RecordsEcho(userName, textShowPane).writeFile(matcher.group(), fileName);
							document.insertString(document.getLength(), "\n", new SimpleAttributeSet());

						}

					} catch (BadLocationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			}

			if (!isFile) {

				// 实现发送文本太长自动换行
				String str = "";

				for (int i = 0; i < textInputPane.getText().length(); i++) {

					if (i != 0 && i % 30 == 0)
						str += "\n";

					str += textInputPane.getText().charAt(i);

				}

				Document document = textShowPane.getDocument();

				try {
					document.insertString(document.getLength(), inputText + str + "\n\n", attributeSet);
				} catch (BadLocationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			// 把信息保存到对应的用户本地历史记录txt文件
			SaveRecords records = new SaveRecords(userName, inputText + textInputPane.getText() + "\n\n");
			records.saveRecords();

			textInputPane.setText("");

		} else {
			new MainFrame();
			MainFrame.warnJDialog("不能发送空白信息！");
		}
	}

}
