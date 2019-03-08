package bug_third;

import java.io.*;

import java.net.*;
import java.util.*;

import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

public class Bug_third {
	
	//等待爬取的url
    private static List<String> allwaiturl = new ArrayList<>();
    //爬取过的url
    private static Set<String> alloverurl = new HashSet<>();
    
    private static String filePath = "E:\\images";
    

/*    //记录所有url的深度进行爬取判断
    private static Map<String,Integer> allurldepth=new HashMap<>();
    //爬取得深度
    private static int maxdepth=3;*/

	
	
	 public static void downImages(String url) throws IOException{
		 	
		 	
		 	//判断当前url是否爬取过
	        if(!(alloverurl.contains(url))){
	        	Document doc = Jsoup.connect(url).get();
	    		Elements fliterpage = doc.getElementsByTag("img");	
				Elements nexturls = doc.getElementsByAttribute("target");
				
				for(Element next:nexturls) {
					String nexturl = next.attr("href");
				    //输出该网页存在的链接
				    //System.out.println(nexturl);
				    //将url地址放到队列中  判断
					if(nexturl.startsWith("https://www.mzitu.com/")){
						allwaiturl.add(nexturl);
					}
				}
				
	        

				//  妹子图里面的attr获取的是data-original


				for(Element element:fliterpage) {
					String imageUrl = element.attr("data-original");
					System.out.println(imageUrl);
					String NEXTURL = imageUrl;
					// 截取图片的名称		 
				    //String fileName = imageUrl.substring(imageUrl.lastIndexOf("/")).replace("/", "");
				    //System.out.println(fileName);
				    //String fileName = imageUrl;
				    //创建文件的目录结构
				    File files = new File(filePath);
				    if(!files.exists()){// 判断文件夹是否存在，如果不存在就创建一个文件夹
				        files.mkdirs();
				    }
				    try {
				        URL urll = new URL(imageUrl);
				        HttpURLConnection connection = (HttpURLConnection) urll.openConnection();
				        
				        //防止报403错误。@unuesd
				        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36"); 
				        // 用referer 反防盗链！！ 
				        connection.setRequestProperty("Referer", NEXTURL);
				        connection.setRequestMethod("GET");
						connection.setConnectTimeout(10000);
						connection.setReadTimeout(10000);
				        if (!"".equals(imageUrl) && (imageUrl.startsWith("http://") && (imageUrl.endsWith(".jpg"))|| imageUrl.startsWith("https://"))) {
					        InputStream is = connection.getInputStream();
					        // 创建文件 这个"\\"一定不要忘记！！
					        File file = new File(filePath + "\\" + System.currentTimeMillis() + ".jpg");
					        FileOutputStream out = new FileOutputStream(file);
					        System.out.println("正在下载的图片的地址：" + NEXTURL);
					        int i = 0;
					        // read()返回下一个数据字节；如果已到达文件末尾，则返回 -1。 
					        while((i = is.read()) != -1){
					            //将指定的一个字节写入文件的输出流中，所以是一次写入一个字节
					        	out.write(i);
					        }
					        is.close();
					        out.close();
				    }

				    } catch (Exception e) {
				        e.printStackTrace();
				        System.out.println("下载失败！");
				    }
				}
				//将当前url归列到alloverurl中
				alloverurl.add(url);
				System.out.println(url+"网页爬取完成，已爬取网页数量："+alloverurl.size()+"，剩余爬取网页数量："+allwaiturl.size());

	        }
			url = allwaiturl.get(0);
			allwaiturl.remove(0);	
	        downImages(url); 
	    }
	 
		 
	public static void main(String[] args) throws Exception {
		// TODO 自动生成的方法存根
		downImages("https://www.mzitu.com/");
		System.out.println("-------------------------下载完毕！----------------------------");
		

	}

}
