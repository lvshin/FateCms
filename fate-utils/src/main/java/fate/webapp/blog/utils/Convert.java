package fate.webapp.blog.utils;

import java.text.DecimalFormat;

/**
 * 字节数转容易理解的大小
 * @author Fate
 *
 */
public class Convert {

	private Convert() {
        super();
        // TODO Auto-generated constructor stub
    }

    public static String sizeConvert(long size){
		DecimalFormat df = new DecimalFormat("#.##");
		if(size<1024)
			return size+"字节";
		else if(size<1024*1024)
			return df.format(size*1.0/1024)+"K";
		else if(size<1024*1024*1024)
			return df.format(size*1.0/1024/1024)+"M";
		else 
			return df.format(size*1.0/1024/1024/1024)+"G";
	}
}
