package fate.webapp.blog.api.open;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 生成验证码存入session
 * 现在验证码已替换为极验验证
 * @author Fate
 *
 */
@Controller
@RequestMapping("/op")
public class CheckCodeCtl {

	//默认去掉o
	private static final char[] chars = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
			'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
			'X', 'Y', 'Z' };
	private static final int SIZE = 4;
	private static final int LINES = 4;
	private static final int WIDTH = 80;
	private static final int HEIGHT = 35;
	private static final int FONT_SIZE = 18;

	/**
	 * 返回图片验证码
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/checkCode")
	public void code(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		/* 1 设置服务器返回文件的格式为jpg格式的图片 */
		// 常见的还有：text/html和text/xml
		response.setContentType("image/jpeg");

		/* 2 画图 */
		// BufferedImage:内存映象对象

		StringBuffer sb = new StringBuffer();
		BufferedImage image = new BufferedImage(WIDTH, HEIGHT,
				BufferedImage.TYPE_INT_RGB);
		Graphics graphic = image.getGraphics();
		graphic.setColor(Color.white);
		graphic.fillRect(0, 0, WIDTH, HEIGHT);
		Random ran = new Random();
		// 画随机字符
		for (int i = 1; i <= SIZE; i++) {
			int r = ran.nextInt(chars.length);
			graphic.setColor(getRandomColor());
			graphic.setFont(new Font(null, Font.BOLD + Font.ITALIC, FONT_SIZE));
			graphic.drawString(chars[r] + "", (i - 1) * WIDTH / SIZE + 5,
					HEIGHT / 2 + 5);
			sb.append(chars[r]);// 将字符保存，存入Session
		}
		// 画干扰线
		for (int i = 1; i <= LINES; i++) {
			graphic.setColor(getRandomColor());
			graphic.drawLine(ran.nextInt(WIDTH), ran.nextInt(HEIGHT),
					ran.nextInt(WIDTH), ran.nextInt(HEIGHT));
		}
		HttpSession session = request.getSession();
		session.setAttribute("checkCode", sb.toString());// 用于验证
		/* 3 压缩图片并输出 */
		// 获得字节输出流，因为要输出的是图像压缩之后
		// 的字节数组，所以，不能用PrintWriter。
		OutputStream os = response.getOutputStream();
		// 将图片压缩，输出
		ImageIO.write(image, "jpeg", os);
		os.close();
	}

	public static Color getRandomColor() {
		Random ran = new Random();
		Color color = new Color(ran.nextInt(200), ran.nextInt(200),
				ran.nextInt(200));
		return color;
	}
}
