package certificate;


	

	import java.io.File;

	import javax.swing.JFileChooser;
	import javax.swing.filechooser.FileSystemView;

	public class fldr {

	public static String fldr(){
	int result = 0;
	File file = null;
	String path = null;
	JFileChooser fileChooser = new JFileChooser();
	FileSystemView fsv = FileSystemView.getFileSystemView();  //ע���ˣ�������Ҫ��һ��
    fileChooser.setCurrentDirectory(fsv.getHomeDirectory());
	fileChooser.setDialogTitle("��ѡ��Ҫ�ϴ����ļ�...");
	fileChooser.setApproveButtonText("ȷ��");
	fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
	result = fileChooser.showOpenDialog(fileChooser);
	if (JFileChooser.APPROVE_OPTION == result) {
	    	   path=fileChooser.getSelectedFile().getPath();
	    	   
	   }
	return path;
	}}
	
	
	

