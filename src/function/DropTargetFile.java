package function;

import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.File;
import java.util.List;

import javax.swing.JTextPane;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;

/**
 * 该类实现拖拽文件，复制粘贴功能
 * 
 * 由于该功能第一次实现，所以部分代码是搬砖过来的
 * 
 * @author 360°顺滑
 *
 * @date 2020/05/03
 */
public class DropTargetFile implements DropTargetListener {

	/** 用于显示拖拽数据的面板 */
    private JTextPane inputPane;

    public DropTargetFile(JTextPane inputPane) {
        this.inputPane = inputPane;
    }

    public void dragEnter(DropTargetDragEvent dtde) {
    	
    }

    public void dragOver(DropTargetDragEvent dtde) {

    }

    public void dragExit(DropTargetEvent dte) {

    }

    public void dropActionChanged(DropTargetDragEvent dtde) {

    }

    public void drop(DropTargetDropEvent dtde) {
    	
        boolean isAccept = false;

        try {
            /*
                                * 判断拖拽目标是否支持文件列表数据（即拖拽的是否是文件或文件夹, 支持同时拖拽多个）
             */
            if (dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                // 接收拖拽目标数据
                dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
                isAccept = true;

                // 以文件集合的形式获取数据
                @SuppressWarnings("unchecked")
				List<File> files = (List<File>) dtde.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);

                // 把文件路径输出到文本区域
                if (files != null && files.size() > 0) {
                    StringBuilder filePaths = new StringBuilder();
                    for (File file : files) {
                        filePaths.append(file.getAbsolutePath() + "\n");
                    }
                    
                    Document document = inputPane.getDocument();
                    
                    document.insertString(document.getLength(), filePaths.toString(), new SimpleAttributeSet());
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        // 如果此次拖拽的数据是被接受的, 则必须设置拖拽完成（否则可能会看到拖拽目标返回原位置, 造成视觉上以为是不支持拖拽的错误效果）
        if (isAccept) {
            dtde.dropComplete(true);
        }
    }

	
}
