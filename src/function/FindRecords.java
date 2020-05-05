package function;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;

/**
 * 该类实现查找历史记录
 * 
 * 这段代码说实话，写得有点糟，本来后期要优化，但是有点难搞，就这样吧，将就着看！
 * 
 * 
 * @author 360°顺滑
 *
 * @date 2020/05/02
 */
public class FindRecords {

	private JTextPane recordsShowPane;
	private String userName;
	private String keywords;

	public FindRecords(JTextPane recordsShowPane, String userName, String keywords) {
		this.recordsShowPane = recordsShowPane;
		this.userName = userName;
		this.keywords = keywords;
	}

	// 该方法包括查找文本、图片、文件，因为要交叉使用，所以写在一起了
	public void find() {

		// 如果查找的不为空
		if (!(keywords.equals(""))) {

			// 查找相关账号历史记录
			File file = new File("src/txt/" + userName + ".txt");

			FileReader fileReader = null;
			BufferedReader bufferedReader = null;

			try {

				fileReader = new FileReader(file);
				bufferedReader = new BufferedReader(fileReader);

				String str = "";
				String str1 = null;

				// 先把所有文本找到
				while ((str1 = bufferedReader.readLine()) != null) {

					if (str1.equals(""))
						str += "\n";
					else
						str = str + str1 + "\n";

				}

				// 正则表达式匹配要找的内容
				Pattern pattern = Pattern.compile(".+\n.*" + keywords + ".*\n\n");
				Matcher matcher = pattern.matcher(str);

				// 标记有没有找到
				boolean isExist = false;

				// 标记是否第一次找到
				boolean oneFind = false;

				// 如果找到了
				while (matcher.find()) {

					isExist = true;
					// 正则表达式匹配是否为文件图片路径
					Pattern pattern1 = Pattern.compile(".+[\\.].+");
					Matcher matcher1 = pattern1.matcher(matcher.group());

					// 如果是文件或者图片
					if (matcher1.find()) {

						// 截取日期
						int index3 = matcher.group().indexOf("\n");
						String date = matcher.group().substring(0, index3);

						// 获得文件名
						int index1 = matcher.group().lastIndexOf("\\");
						int index2 = matcher.group().lastIndexOf("\n\n");
						String fileName = matcher.group().substring(index1 + 1, index2);

						// 图片的情况
						if (fileName.endsWith(".png") || fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")
								|| fileName.endsWith("gif")) {

							Pattern pattern2 = Pattern.compile("[emoji_].+[\\.].+");
							Matcher matcher2 = pattern2.matcher(fileName);

							// 如果是表情包则不需要添加名称
							if (matcher2.find()) {

								if (!oneFind) {

									// 写入日期
									recordsShowPane.setText(date + "\n");

									// 插入表情包和名称
									RecordsEcho echo = new RecordsEcho(fileName, recordsShowPane);
									echo.writeEmoji(matcher1.group());

									Document document = recordsShowPane.getDocument();
									
									try {
										document.insertString(document.getLength(), "\n", new SimpleAttributeSet());
									} catch (BadLocationException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									
									
									oneFind = true;

								} else { // 不是第一次就直接写入

									Document document = recordsShowPane.getDocument();

									try {

										document.insertString(document.getLength(), date + "\n",
												new SimpleAttributeSet());

										RecordsEcho echo = new RecordsEcho(fileName, recordsShowPane);
										echo.writeEmoji(matcher1.group());
										
										document.insertString(document.getLength(), "\n", new SimpleAttributeSet());



									} catch (BadLocationException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

								}

							}

							// 不是表情包的图片
							else {

								// 如果是第一次找到，要先清空再写入
								if (!oneFind) {
									// 写入日期
									recordsShowPane.setText(date + "\n");

									// 插入图片和名称
									RecordsEcho echo = new RecordsEcho(fileName, recordsShowPane);
									echo.writeImage(matcher1.group(), fileName);

									// 换行
									Document document = recordsShowPane.getDocument();

									try {
										document.insertString(document.getLength(), "\n", new SimpleAttributeSet());
									} catch (BadLocationException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

									// 标记不是第一次了
									oneFind = true;

								} else { // 不是第一次找到就直接写入

									Document document = recordsShowPane.getDocument();

									try {
										document.insertString(document.getLength(), date + "\n",
												new SimpleAttributeSet());

										RecordsEcho echo = new RecordsEcho(fileName, recordsShowPane);
										echo.writeImage(matcher1.group(), fileName);

										document.insertString(document.getLength(), "\n", new SimpleAttributeSet());

									} catch (BadLocationException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}

							}

						} else { // 文件的情况

							// 第一次找到
							if (!oneFind) {

								// 清空并写入日期
								recordsShowPane.setText(date + "\n");

								// 插入文件图片以及名称
								RecordsEcho echo = new RecordsEcho(fileName, recordsShowPane);
								echo.writeFile(matcher1.group(), fileName);

								// 换行
								Document document = recordsShowPane.getDocument();

								try {
									document.insertString(document.getLength(), "\n", new SimpleAttributeSet());
								} catch (BadLocationException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

								oneFind = true;

							} else { // 不是第一次找到
								Document document = recordsShowPane.getDocument();

								try {
									document.insertString(document.getLength(), date + "\n", new SimpleAttributeSet());

									RecordsEcho echo = new RecordsEcho(fileName, recordsShowPane);
									echo.writeFile(matcher1.group(), fileName);

									document.insertString(document.getLength(), "\n", new SimpleAttributeSet());

								} catch (BadLocationException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}

					} else { // 查找的是文本
						
						String s = "";
						
						for(int i = 0; i < matcher.group().length(); i++) {
							
							//因为查找到的字符串包含日期，所以要从20开始
							if(i>20 && (i-20) % 30 == 0)
								s += "\n";
							
							s += matcher.group().charAt(i);
							
							
							
						}
						
						if (!oneFind) {
								
							recordsShowPane.setText(s);
							oneFind = true;
							
							
						} else {
							Document document = recordsShowPane.getDocument();

							try {
								document.insertString(document.getLength(), s, new SimpleAttributeSet());
							} catch (BadLocationException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}

				}

				// 找不到的情况
				if (!isExist)
					recordsShowPane.setText("\n\n\n                              无相关记录！");

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

		else { // 查找为空的情况
			recordsShowPane.setText("\n\n\n                              无相关记录！");
		}

	}

}
