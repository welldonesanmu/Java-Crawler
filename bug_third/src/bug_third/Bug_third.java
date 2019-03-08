package bug_third;

import java.io.*;

import java.net.*;
import java.util.*;

import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

public class Bug_third {
	
	//�ȴ���ȡ��url
    private static List<String> allwaiturl = new ArrayList<>();
    //��ȡ����url
    private static Set<String> alloverurl = new HashSet<>();
    
    private static String filePath = "E:\\images";
    

/*    //��¼����url����Ƚ�����ȡ�ж�
    private static Map<String,Integer> allurldepth=new HashMap<>();
    //��ȡ�����
    private static int maxdepth=3;*/

	
	
	 public static void downImages(String url) throws IOException{
		 	
		 	
		 	//�жϵ�ǰurl�Ƿ���ȡ��
	        if(!(alloverurl.contains(url))){
	        	Document doc = Jsoup.connect(url).get();
	    		Elements fliterpage = doc.getElementsByTag("img");	
				Elements nexturls = doc.getElementsByAttribute("target");
				
				for(Element next:nexturls) {
					String nexturl = next.attr("href");
				    //�������ҳ���ڵ�����
				    //System.out.println(nexturl);
				    //��url��ַ�ŵ�������  �ж�
					if(nexturl.startsWith("https://www.mzitu.com/")){
						allwaiturl.add(nexturl);
					}
				}
				
	        

				//  ����ͼ�����attr��ȡ����data-original


				for(Element element:fliterpage) {
					String imageUrl = element.attr("data-original");
					System.out.println(imageUrl);
					String NEXTURL = imageUrl;
					// ��ȡͼƬ������		 
				    //String fileName = imageUrl.substring(imageUrl.lastIndexOf("/")).replace("/", "");
				    //System.out.println(fileName);
				    //String fileName = imageUrl;
				    //�����ļ���Ŀ¼�ṹ
				    File files = new File(filePath);
				    if(!files.exists()){// �ж��ļ����Ƿ���ڣ���������ھʹ���һ���ļ���
				        files.mkdirs();
				    }
				    try {
				        URL urll = new URL(imageUrl);
				        HttpURLConnection connection = (HttpURLConnection) urll.openConnection();
				        
				        //��ֹ��403����@unuesd
				        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36"); 
				        // ��referer ������������ 
				        connection.setRequestProperty("Referer", NEXTURL);
				        connection.setRequestMethod("GET");
						connection.setConnectTimeout(10000);
						connection.setReadTimeout(10000);
				        if (!"".equals(imageUrl) && (imageUrl.startsWith("http://") && (imageUrl.endsWith(".jpg"))|| imageUrl.startsWith("https://"))) {
					        InputStream is = connection.getInputStream();
					        // �����ļ� ���"\\"һ����Ҫ���ǣ���
					        File file = new File(filePath + "\\" + System.currentTimeMillis() + ".jpg");
					        FileOutputStream out = new FileOutputStream(file);
					        System.out.println("�������ص�ͼƬ�ĵ�ַ��" + NEXTURL);
					        int i = 0;
					        // read()������һ�������ֽڣ�����ѵ����ļ�ĩβ���򷵻� -1�� 
					        while((i = is.read()) != -1){
					            //��ָ����һ���ֽ�д���ļ���������У�������һ��д��һ���ֽ�
					        	out.write(i);
					        }
					        is.close();
					        out.close();
				    }

				    } catch (Exception e) {
				        e.printStackTrace();
				        System.out.println("����ʧ�ܣ�");
				    }
				}
				//����ǰurl���е�alloverurl��
				alloverurl.add(url);
				System.out.println(url+"��ҳ��ȡ��ɣ�����ȡ��ҳ������"+alloverurl.size()+"��ʣ����ȡ��ҳ������"+allwaiturl.size());

	        }
			url = allwaiturl.get(0);
			allwaiturl.remove(0);	
	        downImages(url); 
	    }
	 
		 
	public static void main(String[] args) throws Exception {
		// TODO �Զ����ɵķ������
		downImages("https://www.mzitu.com/");
		System.out.println("-------------------------������ϣ�----------------------------");
		

	}

}
