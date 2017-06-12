package certificate;



import javax.net.ssl.HttpsURLConnection;
import javax.swing.*;
import javax.swing.*;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

import sun.misc.BASE64Decoder;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.Principal;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509CRL;
import java.security.cert.X509CRLEntry;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class jiemian extends JFrame implements ActionListener {
 private Panel pan,pan1,pan2,pan3;
 private JTextField aField;
 private static  JTextArea bField;
 private JButton b;

 static int bolean =1;



	
	//��֤֤��ʱ����Ч���㷨
	public static boolean time_analysis(String Notbefore,String Notend) throws ParseException{
	      //���и�ʽת����ת��UTC��ʽ
			String start = Notbefore.replace("Z", " UTC");//ע���ǿո�+UTC
	    	String end=Notend.replace("Z", " UTC");
	        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");//ע���ʽ���ı��ʽ
	        Date kaishi = format.parse(start);
	        Date jieshu = format.parse(end);
	      //��ȡ��ǰʱ��
	        Date now =new Date();  
	        bField.append("ϵͳ��ǰʱ��"+now+"\n");
	       // System.out.println("ϵͳ��ǰʱ��"+now);
		
	        //����֤��һ��Ϊ1~2�꣬����Ҫ��֤�Ƿ�ʱ
	          if((jieshu.getTime()/1000)- (kaishi.getTime()/1000) > 63072000){
	        	  bField.append("--------֤����Ч�ڹ�������Ч��----------"+"\n");
	        	  //System.out.println("--------֤����Ч�ڹ�������Ч��----------"+"\n");
	        	  return false;
	          }else if(now.getTime() > kaishi.getTime() && now.getTime() < jieshu.getTime()){
	        	  bField.append("--------֤������Ч���ڣ�������ʹ�ã�---------"+"\n");
	        	//System.out.println("--------֤������Ч���ڣ�������ʹ�ã�---------"+"\n");
	        	return true;
	          }else
	        	  bField.append("--------֤�黹δ��Ч�����ѹ��ڣ�----"+"\n");
	    	 // System.out.println("--------֤�黹δ��Ч�����ѹ��ڣ�----"+"\n");
	          return false;
	}
	

		//��֤RSAǩ���㷨
		
		/**
	     * Verify
	     *
	     * @param data //ǩ������
	     * @param sign //ǩ�����ֵ
     * @param publickey ��Կ
	     * @return
	     * @throws Exception
	     */
		
	     public static boolean Verify(String data, String sign, PublicKey publickey ) throws Exception{
	    	   // BigInteger mbig = new BigInteger(mod, 16);
				//BigInteger ebig = new BigInteger(exp, 16);
	    	// RSAPublicKeySpec spec = new RSAPublicKeySpec(mbig, ebig);
	        // KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	       //  java.security.PublicKey verifyKey = keyFactory.generatePublic(spec);
	         Signature verifier = Signature.getInstance("SHA256withRSA");	
	         verifier.initVerify(publickey);  	
	         verifier.update(hexString2ByteArray(data));
	         System.out.println("hexString2ByteArray(data)"+hexString2ByteArray(data));
	         System.out.println("hexString2ByteArray(sign)"+hexString2ByteArray(sign));
	         return verifier.verify(hexString2ByteArray(sign));
	     }

	     public static byte[] hexString2ByteArray(String hexStr){
	         if (hexStr == null)
	             return null;
	         if (hexStr.length() % 2 != 0)
	             return null;
	         byte data[] = new byte[hexStr.length() / 2];
	         for (int i = 0; i < hexStr.length() / 2; i++){
	             char hc = hexStr.charAt(2 * i);
	             char lc = hexStr.charAt(2 * i + 1);
	             byte hb = hexChar2Byte(hc);
	             byte lb = hexChar2Byte(lc);
	             if (hb < 0 || lb < 0)
	                 return null;
	             int n = hb << 4;
	             data[i] = (byte)(n + lb);
	         }
	        return data;
	    }

	    public static byte hexChar2Byte(char c){
	        if (c >= '0' && c <= '9')
	            return (byte)(c - 48);
	        if (c >= 'a' && c <= 'f')
	            return (byte)((c - 97) + 10);
	        if (c >= 'A' && c <= 'F')
	            return (byte)((c - 65) + 10);
	        else
	            return -1;
	    }

	/*
		// ͨ����Կbyte[]����Կ��ԭ��������RSA�㷨 
public static  PublicKey getPublicKey(String modulus, String publicExponent) throws NoSuchAlgorithmException, InvalidKeySpecException { 
    
	    BigInteger bigIntModulus = new BigInteger(modulus,16); 
	    System.out.println("getpublickey�е�ģ��ת����"+bigIntModulus);
    BigInteger bigIntPrivateExponent = new BigInteger(publicExponent,16); 
    System.out.println("getpublickey�е�ָ��ת����"+bigIntPrivateExponent);
    RSAPublicKeySpec keySpec = new RSAPublicKeySpec(bigIntModulus, bigIntPrivateExponent); 
    KeyFactory keyFactory = KeyFactory.getInstance("RSA"); 
    PublicKey publicKey = keyFactory.generatePublic(keySpec); 
    System.out.println("ת��֮��Ĺ�Կ"+publicKey);
     return publicKey; 

				 } 
				 */
/**
* У������ǩ��
*/

private static byte[] decryptBASE64(String key) {
	// TODO Auto-generated method stub
	byte[] bytes = null;
	try {
		bytes = (new BASE64Decoder()).decodeBuffer(key);
	} catch (IOException e) {
		e.printStackTrace();
	}
	return bytes;
}
	
/**
* decode by Base64 
**/  
public static byte[] decodeBase64(String input) throws Exception{  
  Class clazz=Class.forName("com.sun.org.apache.xerces.internal.impl.dv.util.Base64");  
  Method mainMethod= clazz.getMethod("decode", String.class);  
  mainMethod.setAccessible(true);  
  Object retObj=mainMethod.invoke(null, input);  

  return (byte[])retObj;  
}  
/**
*��֤crl�е�ǩ�� 
**/
public static void VerifyCRLSignature(String stURL,BigInteger certSN,RSAPublicKey publickey) throws  IOException {

URL url = new URL(stURL);

// Open connection
HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
 bField.append("\n"+"��������֤crl���ǩ��"+"\n"+"��ʼ����CRL...."+"\n");

try {
    // Get .crtFile
    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
    
    CertificateFactory cf = CertificateFactory.getInstance("X.509");
    bField.append("CRL���سɹ�"+"\n"+"\n");

    X509CRL crl = (X509CRL) cf.generateCRL(in);
    byte[] signature = crl.getSignature();
    bField.append("��ȡCRLǩ����Ϣ:"+signature+"\n");
    bField.append("��֤CRLǩ��:"+"\n"+"\n");

    
     try{ crl.verify(publickey);
     bField.append("crlǩ����֤�ɹ�"+"\n");
  
     } catch (Exception e) {
    	 bField.append("crlǩ����֤ʧ��"+"\n");
        }
}
     catch (Exception e) {
    	 bField.append("uri"+"ʧ�ܣ������³���"+"\n");
}
finally {
        urlConnection.disconnect();
}
}
/**
*��֤crl��ʱ����Ч���Լ���֤֤���Ƿ��� 
**/
public static boolean GetCRL(String stURL,BigInteger certSN) throws  IOException {

URL url = new URL(stURL);

// Open connection
HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
 //System.out.println("����url����....");
try {
    // Get .crtFile
    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
   // System.out.println("���������.....");
    CertificateFactory cf = CertificateFactory.getInstance("X.509");
   // System.out.println("���֤�鹤��.....");
    X509CRL crl = (X509CRL) cf.generateCRL(in);
   // System.out.println("���֤�鳷���б��й���Ϣ:"+crl);
    //System.out.println("All revoked: "+crl.getRevokedCertificates().toString());

    // Test what certificate? Serial number   
    //System.out.println("���к�ʮ���Ʊ�ʾ: "+certSN.toString()+"\n");
    // See if revoked  
    X509CRLEntry isRevoked = crl.getRevokedCertificate(certSN);
    Date thisupdate = crl.getThisUpdate();
    bField.append( "��������֤CRL�����ڣ�"+"\n");
    bField.append("CRL��Чʱ��"+thisupdate+"\n");
    Date nextupdate = crl.getNextUpdate();
    bField.append("CRL�´θ���ʱ��"+nextupdate +"\n");
 
    Date now =new Date();
    bField.append("ϵͳʱ��"+now +"\n");
    bField.append("��֤CRL�Ƿ���Ч:"+"\n");
    
    if(thisupdate.getTime()< now.getTime()&& now.getTime()<nextupdate.getTime()){
    	bField.append("CRL��ǰ����Ч����"+"\n");
    	bField.append("��������֤CRL���Ƿ��е�ǰ֤�����к�"+"\n");
    	bField.append("Revoking������״̬��ѯ��:"+"\n");

    if (isRevoked != null) {
    	bField.append("�Ѿ���������"+"\n"+"=================="+"\n"+"�������к�Ϊ�� "+isRevoked.toString());
        return true;
    } else {
    	bField.append("û�б�������"+"\n"+"==================");
       return false;
    }
    }else
    	bField.append("--------CRL��δ��Ч-----------");

    return true;

} catch (Exception e) {
	bField.append("uri"+"ʧ�ܣ������³���");

}
finally {
        urlConnection.disconnect();
}
return false;
}
/**
* ��ȡ�ϼ�֤��Ĺ�Կ
* **/
public static RSAPublicKey getlastpublickey(String public_url) throws  IOException {
	 URL url = new URL(public_url);

	    // Open connection
	    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
	    bField.append("ͨ��֤�������ذ䷢�߹�Կ"+"\n");
	     //System.out.println("ͨ��֤�������ذ䷢�߹�Կ");
	
     try{
    	 InputStream in = new BufferedInputStream(urlConnection.getInputStream());
       
         CertificateFactory cf = CertificateFactory.getInstance("X.509");
        
         //System.out.println("���֤�鹤��...."+"\n");
         Certificate x509 =  cf.generateCertificate(in);
       //  System.out.println("֤��"+x509);
         RSAPublicKey pub = (RSAPublicKey) x509.getPublicKey();
       //  String modulus = pub.getModulus().toString(16);
         bField.append("��Կ���سɹ�"+"\n");
        // System.out.println("��Կ���سɹ�");
          //System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<�Ѿ���ȡ����Կ>>>>>>>>>>>>>>>>>>>>>"+"\n");
         bField.append("��Կ:"+pub);
         // System.out.println("��Կ:"+pub);
         return pub;
        // System.out.println("modulus:"+pub.getModulus().toString(16));
         //System.out.println("exponent:"+pub.getPublicExponent().toString(16));
        //System.out.println("��ȡ��Կ"+x509.getPublicKey());
     } catch (Exception e) {
    	 bField.append("uri"+"ʧ�ܣ������³���");
        // System.out.println("uri"+"ʧ�ܣ������³���");
     }
     finally {
             urlConnection.disconnect();
     }
		return null;


}


private static final char[] DIGITS
= {'0', '1', '2', '3', '4', '5', '6', '7',
    '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

public static final String toHex(byte[] data) {
final StringBuffer sb = new StringBuffer(data.length * 2);
for (int i = 0; i < data.length; i++) {
sb.append(DIGITS[(data[i] >>> 4) & 0x0F]);
sb.append(DIGITS[data[i] & 0x0F]);
}
return sb.toString();
}

//��ȡ֤����
public static boolean testConnectionTo(String aURL) throws Exception {
	URL destinationURL = new URL(aURL);
	HttpsURLConnection conn = (HttpsURLConnection) destinationURL.openConnection();
	conn.connect();
	Certificate[] certs = conn.getServerCertificates();
	ArrayList<BigInteger> list=new ArrayList<BigInteger>();
	Principal principalLast=null;
	bField.append("������֤����������֤"+"\n");
	bField.append("��ȡ֤����"+"\n");
	bField.append("��ȥ��֤�����֤����������"+certs.length+"\n");
	//System.out.println("������֤����������֤"+"\n"+"=========================");
	//System.out.println("��ȡ֤����"+"\n"+"=========================");
	//System.out.println("��ȥ��֤�����֤����������"+certs.length);
	for(int i=0;i<certs.length;i++){
		X509Certificate x509Certificate=(X509Certificate) certs[i];
		bField.append("��ʾ�䷢��ӵ������Ϣ"+"\n");
		//System.out.println("��ʾ�䷢��ӵ������Ϣ"+"\n"+"=========================");
		//��ȡ�����߱�ʶ
		Principal principalIssuer=x509Certificate.getIssuerDN();
		bField.append("issuer:"+principalIssuer+"\n");
		//System.out.println("issuer:"+principalIssuer);
		//��ȡ֤��������ʶ
		Principal principalSubject=x509Certificate.getSubjectDN();
		bField.append("subject:"+principalSubject+"\n");
		//System.out.println("subject:"+principalSubject);
		bField.append("----------------------"+"\n");
		//System.out.println("----------------------");
		//if(principalIssuer.equals(principalSubject)){
		//	System.out.println("this is ROOT CA");}
		//����֤������к�
		list.add(x509Certificate.getSerialNumber());
		if(principalLast!=null){
		//��֤֤��İ䲼������һ��֤���������
			bField.append("��ʼ��֤�������¶�����֤"+"\n");
			bField.append("��֤��ǰ֤��İ䲼������һ��֤���������"+"\n");
			//System.out.println("��ʼ��֤�������¶�����֤"+"\n"+"=========================");
			//System.out.println("��֤��ǰ֤��İ䲼������һ��֤���������"+"\n"+"====================");
		if(principalSubject.equals(principalLast)){
			bField.append("��֤CA��ǩ��"+"\n");
			//System.out.println("��֤CA��ǩ��"+"\n"+"====================");
		try{
		//��ȡ�ϸ�֤��Ĺ�Կ
		PublicKey publickey=certs[i].getPublicKey();
		bField.append("��ȡ�䷢��CA�Ĺ�Կ��"+publickey +"\n");
		//System.out.println("��ȡ�䷢��CA�Ĺ�Կ��"+publickey +"\n"+"====================");
	 byte[]sign=((X509Certificate) certs[i-1]).getSignature();
	 bField.append("��ȡ��ǰ֤���ǩ����"+sign +"\n");
	//System.out.println("��ȡ��ǰ֤���ǩ����"+sign +"\n"+"====================");
		//��֤ǩ��
		certs[i-1].verify(publickey);   	
		bField.append("֤��ǩ����֤��success��");
		//System.out.println("֤��ǩ����֤��success��");
		 int bolean = 1;
		}catch(Exception e){
			bField.append("error");
			//System.out.println("error");
			int bolean = 0;
			 if(bolean==0){
				   return false;
			  }
		}
		
		}//if(principalIssuer.equals(principalLast))
		 
			
		
		}//if(principalLast!=null)
		principalLast=principalIssuer;
		
	

		
}//for
	return true;
}//fangfa
  

//��ȡ����֤���ļ��㷨
		public static List<String> readTxtFile(String filePath){
			List<String> json = new ArrayList<String>();
	        try {
		        String encoding="GBK";
		        File file=new File(filePath);
		        if(file.isFile() && file.exists()){ //�ж��ļ��Ƿ����
		            InputStreamReader read = new InputStreamReader(
		            new FileInputStream(file),encoding);//���ǵ������ʽ
		            BufferedReader bufferedReader = new BufferedReader(read);
		            String lineTxt = null;
		            while((lineTxt = bufferedReader.readLine()) != null){
//		                System.out.println(lineTxt);
		            	json.add(lineTxt);
		            }
	            read.close();
			    }else{
			        System.out.println("�Ҳ���ָ�����ļ�");
			    }
	        } catch (Exception e) {
	            System.out.println("��ȡ�ļ����ݳ���");
	            e.printStackTrace();
	        } finally {
				return json;
			}
	        
	     
	    }
 

 jiemian(){
	 
 this.setBounds(0,0,500,480);
 pan=new Panel();
 pan1=new Panel();
 pan2=new Panel();
 pan3=new Panel();
 this.setTitle("֤����Ч�Է���");
 

 b=new JButton("���ļ�");
 b.addActionListener(this);
 aField=new JTextField(32);
 bField=new JTextArea(20,40);
 JScrollPane js=new JScrollPane(bField);
 pan1.add(aField);

 pan2.add(b);
 pan3.add(js);
 pan.add(pan1);
 pan.add(pan2);
 pan.add(pan3);
 add(pan);
 
 this.setAlwaysOnTop(true);
 this.setVisible(true);
 }
 
 public static void main(String args[]){
 new jiemian();
 }

@Override
public void actionPerformed(ActionEvent e) {
	
	if(e.getSource()==b){

		 String[] aString=null;
		String filename2=fldr.fldr();
		//String path=fldr.fldr();
	
		  aField.setText(filename2);
		  List<String> falselist = new ArrayList<String>();
		  List<String> notvalitidylist = new ArrayList<String>();
			 List<String> jsonlist = null;
		
			jsonlist = readTxtFile(filename2);
			String sizejson =String.valueOf(jsonlist.size());
	    bField.append("֤������Ŀ"+sizejson+"\n");
	    for(int i = 0; i < jsonlist.size(); i++){
			String str = jsonlist.get(i);
			HashMap jsonob = JSON.parseObject(str,HashMap.class);
	    //huoqu raw
			String raw = jsonob.get("raw").toString();
			//System.out.println(raw);
			Object par = jsonob.get("parsed");
			HashMap jsonob1 = JSON.parseObject(par.toString(),HashMap.class);
	     //��ȡ���к�
			String serial = jsonob1.get("serial_number").toString();
			String vers = jsonob1.get("version").toString();
			//System.out.println("xuliehao:"+serial);
			
			//��ȡǩ���㷨
			String sign_algo = jsonob1.get("signature_algorithm").toString();
			
		//��ȡ����֤�����Ч��
			Object val = jsonob1.get("validity"); 
			HashMap jsonob2 = JSON.parseObject(val.toString(),HashMap.class);
			String start_time = jsonob2.get("start").toString();
			String end_time = jsonob2.get("end").toString();	
			
	
        //��ȡ�䷢��
			Object issuer1 = jsonob1.get("issuer");
			HashMap jsonob3 = JSON.parseObject(issuer1.toString(),HashMap.class);
			String issuer = jsonob3.get("common_name").toString();
			//System.out.println("banfa"+issuer);
		
		//��ȡӵ����
			Object subject1 = jsonob1.get("subject");
			HashMap jsonob4 = JSON.parseObject(subject1.toString(),HashMap.class);
			String subject = jsonob4.get("common_name").toString();
			   String subje = subject.replaceAll("\\[\"|\"\\]", "");//ȥ��ʼ�˺��ն˵�"["��"]"�� 
			 
			   String bt = "https://";
			   StringBuffer sb = new StringBuffer(subje);
			  String newsubje =sb.insert(0,bt).toString();
			   
		//��ȡǩ��ֵ
			Object sign = jsonob1.get("signature"); 
			HashMap jsonobtwo = JSON.parseObject(sign.toString(),HashMap.class);
			//Object sign_algo= jsonobtwo.get("signature_algorithm");
			//HashMap jsonobfour = JSON.parseObject(sign_algo.toString(),HashMap.class);
	        String sign2 = jsonobtwo.get("value").toString();
		
	        
	     //��ȡCRL
	        Object kuozhan = jsonob1.get("extensions");
	      //  System.out.println("kuozhan:"+kuozhan);
	        
	        HashMap kuozhanxiang = JSON.parseObject(kuozhan.toString(),HashMap.class);
	        String crl_url = kuozhanxiang.get("crl_distribution_points").toString();
	        String b = crl_url.replaceAll("\\[\"|\"\\]", "");//ȥ��ʼ�˺��ն˵�"["��"]"�� 
	        //System.out.println(crl_url);
	        
	        //��ȡissuer_urls
	        Object authority_info_access = kuozhanxiang.get("authority_info_access");
	        HashMap json_authority = JSON.parseObject(authority_info_access.toString(),HashMap.class);
	        String issuer_urls = json_authority.get("issuer_urls").toString();
	        String issuer_urls_new = issuer_urls.replaceAll("\\[\"|\"\\]", "");//ȥ��ʼ�˺��ն˵�"["��"]"��
	        //
	        String issurl = issuer_urls_new.replace("http://","https://");
	        
	        Object key_info = jsonob1.get("subject_key_info");
				HashMap jsonob444 = JSON.parseObject(key_info.toString(),HashMap.class);
				String key_algo = jsonob444.get("key_algorithm").toString();
				Object rsa_public_key = jsonob444.get("rsa_public_key");
				HashMap jsonob4444 = JSON.parseObject(rsa_public_key.toString(),HashMap.class);
				//��ȡ��Կ��ָ��
			    String exponent = jsonob4444.get("exponent").toString();
			    //��ȡ��Կ��ģ��
			    String modules = jsonob4444.get("modulus").toString();
			    //���ɹ�Կ
			     
			     
			  // !!!!�ϴζ������������ -----------------
				//Verify(raw, sign2, modules, exponent);

		        //System.out.println("jiemahou:"+certSN);
			    
			  //�����ʾ��
			    int j =i+1;
			    bField.append("��i��֤��i=="+String.valueOf(j)+ "\n");
			    bField.append("�汾��"+vers+ "\n");
			    bField.append("���к�:"+serial+ "\n");
			    bField.append("ӵ���ߣ�"+subject+ "\n");
			    bField.append("�䷢��:"+issuer+ "\n");
			    bField.append("��Կ�㷨:"+key_algo);
			    bField.append("ָ��:"+exponent+ "\n");
			    bField.append("ģ��:"+modules+ "\n");
			    bField.append("ǩ���㷨:"+sign_algo+ "\n");
			    bField.append("ǩ��:"+sign2+ "\n");
			    bField.append("CRL:"+b+ "\n");
			    bField.append("-------------"+"\n");
			    bField.append("��һ��ʱ����Ч����֤"+"\n");
			    bField.append("��ʼʱ��:"+start_time + "\n");
			    bField.append("��ֹʱ��:"+end_time + "\n");
			
				try {
					if(time_analysis(start_time,end_time)==false){	
						falselist.add(serial);
						notvalitidylist.add(str);
						  bField.append("!!!!ʱ����Ч����֤����Ч!!!");
						  bField.append("֤����Ч����֤--��Ч�б�"+falselist);
						
					   }
					   
					   else if(testConnectionTo(newsubje)==false){
						   falselist.add(serial);
						   notvalitidylist.add(str);
						   bField.append("��������֤����Ч!!!");
							  bField.append("֤����Ч����֤--��Ч�б�"+falselist);
					   }else{
						   
					 

     //  System.out.println("ȥ�������ź�˫���ź��crl_url:"+b);
     
					// BigInteger cer2 = new BigDecimal(serial).toBigInteger().toString(16);
					//System.out.println("cer2:"+cer2);
   
  
					//  String publick = "3082010a02820101008b5e0156b9ec6b";
     //  BigInteger certSN = new BigInteger(publick,16);	        
					 //  System.out.println(certSN);
   // System.out.println("�����кŻ��ɴ�����:"+n+"\n");
   
     //System.out.println("issurl:"+issurl);
						   
     
					 
					   
					   

					        BigInteger n = new BigInteger(serial);
					  RSAPublicKey  pubk = getlastpublickey(issurl);					 
                              VerifyCRLSignature(b,n,pubk);
					 if(GetCRL(b,n)==true){
						falselist.add(serial); 
						notvalitidylist.add(str);
						bField.append("֤����Ч����֤--��Ч�б�"+falselist);
					 }

					   }
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}//ʱ����֤�ϸ�Ļ�
		        }
	
	   //д��
	  //  System.out.println(notvalitidylist+"\n");
	   String actual =JSON.toJSONString(notvalitidylist);
	//   System.out.println(actual);
	  //  Gson gson = new Gson();
	   // String jsonStr =gson.toJson(notvalitidylist);
	    try {
	       FileOutputStream fos = new FileOutputStream(new File("c:\\daochu.json"));//�������д����Ҫ�ŵĵ�ַ
	       Writer writer = new OutputStreamWriter(fos);
	       writer.write(actual);
	       writer.flush();
	       writer.close();
	      } catch (Exception e1) {
	       e1.printStackTrace();
	      }

	     
		

		
	}	
}
 }

