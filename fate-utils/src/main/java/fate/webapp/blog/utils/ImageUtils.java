package fate.webapp.blog.utils;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import com.mortennobel.imagescaling.ResampleOp;

/**
 * 图片处理工具类：<br>
 * 功能：缩放图像、切割图像、图像类型转换、彩色转黑白、文字水印、图片水印等
 * 
 * @author Administrator
 */
public class ImageUtils {

	/**
	 * 几种常见的图片格式
	 */
	public static String IMAGE_TYPE_GIF = "gif";// 图形交换格式
	public static String IMAGE_TYPE_JPG = "jpg";// 联合照片专家组
	public static String IMAGE_TYPE_JPEG = "jpeg";// 联合照片专家组
	public static String IMAGE_TYPE_BMP = "bmp";// 英文Bitmap（位图）的简写，它是Windows操作系统中的标准图像文件格式
	public static String IMAGE_TYPE_PNG = "png";// 可移植网络图形
	public static String IMAGE_TYPE_PSD = "psd";// Photoshop的专用格式Photoshop

	// /**
	// * 程序入口：用于测试
	// * @param args
	// */
	// public static void main(String[] args) {
	// // 1-缩放图像：
	// // 方法一：按比例缩放
	// ImageUtils.scale("e:/abc.jpg", "e:/abc_scale.jpg", 2, true);//测试OK
	// // 方法二：按高度和宽度缩放
	// ImageUtils.scale2("e:/abc.jpg", "e:/abc_scale2.jpg", 500, 300,
	// true);//测试OK
	//
	//
	// // 2-切割图像：
	// // 方法一：按指定起点坐标和宽高切割
	// ImageUtils.cut("e:/abc.jpg", "e:/abc_cut.jpg", 0, 0, 400, 400 );//测试OK
	// // 方法二：指定切片的行数和列数
	// ImageUtils.cut2("e:/abc.jpg", "e:/", 2, 2 );//测试OK
	// // 方法三：指定切片的宽度和高度
	// ImageUtils.cut3("e:/abc.jpg", "e:/", 300, 300 );//测试OK
	//
	//
	// // 3-图像类型转换：
	// ImageUtils.convert("e:/abc.jpg", "GIF", "e:/abc_convert.gif");//测试OK
	//
	//
	// // 4-彩色转黑白：
	// ImageUtils.gray("e:/abc.jpg", "e:/abc_gray.jpg");//测试OK
	//
	//
	// // 5-给图片添加文字水印：
	// // 方法一：
	// ImageUtils.pressText("我是水印文字","e:/abc.jpg","e:/abc_pressText.jpg","宋体",Font.BOLD,Color.white,80,
	// 0, 0, 0.5f);//测试OK
	// // 方法二：
	// ImageUtils.pressText2("我也是水印文字", "e:/abc.jpg","e:/abc_pressText2.jpg",
	// "黑体", 36, Color.white, 80, 0, 0, 0.5f);//测试OK
	//
	// // 6-给图片添加图片水印：
	// ImageUtils.pressImage("e:/abc2.jpg",
	// "e:/abc.jpg","e:/abc_pressImage.jpg", 0, 0, 0.5f);//测试OK
	// }

	public static void resize(File originalFile, File resizedFile,
			int newWidth, int newHeight, float quality) {

		try {
			BufferedImage inputBufImage = ImageIO.read(originalFile);
			ResampleOp resampleOp = new ResampleOp(newWidth, newHeight);// 转换
			BufferedImage rescaledTomato = resampleOp.filter(inputBufImage,
					null);
			ImageIO.write(rescaledTomato, IMAGE_TYPE_PNG, resizedFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 图像切割(按指定起点坐标和宽高切割)
	 * 
	 * @param srcImageFile
	 *            源图像地址
	 * @param result
	 *            切片后的图像地址
	 * @param x
	 *            目标切片起点坐标X
	 * @param y
	 *            目标切片起点坐标Y
	 * @param width
	 *            目标切片宽度
	 * @param height
	 *            目标切片高度
	 * @throws IOException
	 */
	public final static void cut(File srcImageFile, File result, int x, int y,
			int width, int height) {
		ImageInputStream iis = null;
		try {

			iis = ImageIO.createImageInputStream(srcImageFile);

			Iterator<ImageReader> iterator = ImageIO.getImageReaders(iis);

			ImageReader reader = (ImageReader) iterator.next();

			reader.setInput(iis, true);
			ImageReadParam param = reader.getDefaultReadParam();
			Rectangle rectangle = new Rectangle(x, y, width, height);
			param.setSourceRegion(rectangle);
			BufferedImage bi = reader.read(0, param);
			ImageIO.write(bi, IMAGE_TYPE_PNG, result);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (iis != null) {
				try {
					iis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 图像切割（指定切片的宽度和高度）
	 * 
	 * @param srcImageFile
	 *            源图像地址
	 * @param descDir
	 *            切片目标文件夹
	 * @param destWidth
	 *            目标切片宽度。默认200
	 * @param destHeight
	 *            目标切片高度。默认150
	 */
	public final static void cut(String srcImageFile, String descDir,
			int destWidth, int destHeight) {
		try {
			if (destWidth <= 0)
				destWidth = 200; // 切片宽度
			if (destHeight <= 0)
				destHeight = 150; // 切片高度
			// 读取源图像
			BufferedImage bi = ImageIO.read(new File(srcImageFile));
			int srcWidth = bi.getHeight(); // 源图宽度
			int srcHeight = bi.getWidth(); // 源图高度
			if (srcWidth > destWidth && srcHeight > destHeight) {
				Image img;
				ImageFilter cropFilter;
				Image image = bi.getScaledInstance(srcWidth, srcHeight,
						Image.SCALE_DEFAULT);
				int cols = 0; // 切片横向数量
				int rows = 0; // 切片纵向数量
				// 计算切片的横向和纵向数量
				if (srcWidth % destWidth == 0) {
					cols = srcWidth / destWidth;
				} else {
					cols = (int) Math.floor(srcWidth / destWidth) + 1;
				}
				if (srcHeight % destHeight == 0) {
					rows = srcHeight / destHeight;
				} else {
					rows = (int) Math.floor(srcHeight / destHeight) + 1;
				}
				// 循环建立切片
				// 改进的想法:是否可用多线程加快切割速度
				for (int i = 0; i < rows; i++) {
					for (int j = 0; j < cols; j++) {
						// 四个参数分别为图像起点坐标和宽高
						// 即: CropImageFilter(int x,int y,int width,int height)
						cropFilter = new CropImageFilter(j * destWidth, i
								* destHeight, destWidth, destHeight);
						img = Toolkit.getDefaultToolkit().createImage(
								new FilteredImageSource(image.getSource(),
										cropFilter));
						BufferedImage tag = new BufferedImage(destWidth,
								destHeight, BufferedImage.TYPE_INT_RGB);
						Graphics g = tag.getGraphics();
						g.drawImage(img, 0, 0, null); // 绘制缩小后的图
						g.dispose();
						// 输出为文件
						ImageIO.write(tag, "JPEG", new File(descDir + "_r" + i
								+ "_c" + j + ".jpg"));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 图像类型转换：GIF->JPG、GIF->PNG、PNG->JPG、PNG->GIF(X)、BMP->PNG
	 * 
	 * @param srcImageFile
	 *            源图像地址
	 * @param formatName
	 *            包含格式非正式名称的 String：如JPG、JPEG、GIF等
	 * @param destImageFile
	 *            目标图像地址
	 */
	public final static void convert(String srcImageFile, String formatName,
			String destImageFile) {
		try {
			File f = new File(srcImageFile);
			f.canRead();
			f.canWrite();
			BufferedImage src = ImageIO.read(f);
			ImageIO.write(src, formatName, new File(destImageFile));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 彩色转为黑白
	 * 
	 * @param srcImageFile
	 *            源图像地址
	 * @param destImageFile
	 *            目标图像地址
	 */
	public final static void gray(String srcImageFile, String destImageFile) {
		try {
			BufferedImage src = ImageIO.read(new File(srcImageFile));
			ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
			ColorConvertOp op = new ColorConvertOp(cs, null);
			src = op.filter(src, null);
			ImageIO.write(src, "JPEG", new File(destImageFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 添加文字水印
	 * 
	 * @param targetImg
	 *            目标图片路径，如：C://myPictrue//1.jpg
	 * @param pressText
	 *            水印文字， 如：中国证券网
	 * @param fontName
	 *            字体名称， 如：宋体
	 * @param fontStyle
	 *            字体样式，如：粗体和斜体(Font.BOLD|Font.ITALIC)
	 * @param fontSize
	 *            字体大小，单位为像素
	 * @param color
	 *            字体颜色
	 * @param x
	 *            水印文字距离目标图片左侧的偏移量，如果x<0, 则在正中间
	 * @param y
	 *            水印文字距离目标图片上侧的偏移量，如果y<0, 则在正中间
	 * @param alpha
	 *            透明度(0.0 -- 1.0, 0.0为完全透明，1.0为完全不透明)
	 */
	public static void pressText(String targetImg, String pressText,
			String fontName, int fontStyle, int fontSize, Color color, int x,
			int y, float alpha) {
		try {
			File file = new File(targetImg);

			Image image = ImageIO.read(file);
			int width = image.getWidth(null);
			int height = image.getHeight(null);
			BufferedImage bufferedImage = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D g = bufferedImage.createGraphics();
			g.drawImage(image, 0, 0, width, height, null);
			g.setFont(new Font(fontName, fontStyle, fontSize));
			g.setColor(color);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
					alpha));

			int width_1 = fontSize * getLength(pressText);
			int height_1 = fontSize;
			int widthDiff = width - width_1;
			int heightDiff = height - height_1;
			if (x < 0) {
				x = widthDiff / 2;
			} else if (x > widthDiff) {
				x = widthDiff;
			}
			if (y < 0) {
				y = heightDiff / 2;
			} else if (y > heightDiff) {
				y = heightDiff;
			}

			g.drawString(pressText, x, y + height_1);
			g.dispose();
			ImageIO.write(bufferedImage, IMAGE_TYPE_JPG, file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 添加图片水印
	 * 
	 * @param targetImg
	 *            目标图片路径，如：C://myPictrue//1.jpg
	 * @param waterImg
	 *            水印图片路径，如：C://myPictrue//logo.png
	 * @param x
	 *            水印图片距离目标图片左侧的偏移量，如果x<0, 则在正中间
	 * @param y
	 *            水印图片距离目标图片上侧的偏移量，如果y<0, 则在正中间
	 * @param alpha
	 *            透明度(0.0 -- 1.0, 0.0为完全透明，1.0为完全不透明)
	 */
	public final static void pressImage(String targetImg, String waterImg,
			int x, int y, float alpha) {
		try {
			File file = new File(targetImg);
			Image image = ImageIO.read(file);
			int width = image.getWidth(null);
			int height = image.getHeight(null);
			BufferedImage bufferedImage = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D g = bufferedImage.createGraphics();
			g.drawImage(image, 0, 0, width, height, null);

			Image waterImage = ImageIO.read(new File(waterImg)); // 水印文件
			int width_1 = waterImage.getWidth(null);
			int height_1 = waterImage.getHeight(null);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
					alpha));

			int widthDiff = width - width_1;
			int heightDiff = height - height_1;
			if (x < 0) {
				x = widthDiff / 2;
			} else if (x > widthDiff) {
				x = widthDiff;
			}
			if (y < 0) {
				y = heightDiff / 2;
			} else if (y > heightDiff) {
				y = heightDiff;
			}
			g.drawImage(waterImage, x, y, width_1, height_1, null); // 水印文件结束
			g.dispose();
			ImageIO.write(bufferedImage, IMAGE_TYPE_JPG, file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取字符长度，一个汉字作为 1 个字符, 一个英文字母作为 0.5 个字符
	 * 
	 * @param text
	 * @return 字符长度，如：text="中国",返回 2；text="test",返回 2；text="中国ABC",返回 4.
	 */
	public static int getLength(String text) {
		int textLength = text.length();
		int length = textLength;
		for (int i = 0; i < textLength; i++) {
			if (String.valueOf(text.charAt(i)).getBytes().length > 1) {
				length++;
			}
		}
		return (length % 2 == 0) ? length / 2 : length / 2 + 1;
	}
}
