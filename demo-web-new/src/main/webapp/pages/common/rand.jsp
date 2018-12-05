<%@ page contentType="image/jpeg" import="java.io.OutputStream,java.awt.*,java.awt.image.*,java.util.*,javax.imageio.*" %>
<%!
Color getRandColor(int fc,int bc){
        Random random = new Random();
        if(fc>255) fc=255;
        if(bc>255) bc=255;
        int r=fc+random.nextInt(bc-fc);
        int g=fc+random.nextInt(bc-fc);
        int b=fc+random.nextInt(bc-fc);
        return new Color(r,g,b);
        }
%><%
response.setHeader("Pragma","No-cache");
response.setHeader("Cache-Control","no-cache");
response.setDateHeader("Expires", 0);

int width=60, height=26;
BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
Graphics g = image.getGraphics();
Random random = new Random();
g.setColor(getRandColor(200,250));
g.fillRect(0, 0, width, height);
g.setFont(new Font("Arial",Font.ITALIC,20));
g.setColor(getRandColor(160,200));
for (int i=0;i<155;i++)
{
 int x = random.nextInt(width);
 int y = random.nextInt(height);
        int xl = random.nextInt(12);
        int yl = random.nextInt(12);
 g.drawLine(x,y,x+xl,y+yl);
}
String sRand="";
for (int i=0;i<4;i++){
    String rand=String.valueOf(random.nextInt(10));
    sRand+=rand;
    g.setColor(new Color(0,0,0));
    g.drawString(rand,14*i+3,21);
}
session.setAttribute("rand",sRand);
System.out.println("verification code"+session.getAttribute("rand"));
g.dispose();
OutputStream outputStream = response.getOutputStream();
ImageIO.write(image, "JPEG", outputStream);
outputStream.flush();  
outputStream.close();  
outputStream = null;
response.flushBuffer();  
out.clear();  
out = pageContext.pushBody(); 
%>