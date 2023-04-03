package stock;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/SaleInfo")
public class SaleInfo extends HttpServlet
{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
    {
    	String name=req.getParameter("name");
    	String mbl=req.getParameter("mbl");
    	String vnumber=req.getParameter("vno");
    	String vname=req.getParameter("vname");
    	String km=req.getParameter("km");
    	int kilo=Integer.parseInt(km);
    	String date=req.getParameter("date");
    	String invoice=req.getParameter("inv");
    	long invo=Long.parseLong(invoice);
    	String size=req.getParameter("size");
    	String brand=req.getParameter("brand");
    	String model=req.getParameter("model");
    	String quantity=req.getParameter("qnt");
    	int quant=Integer.parseInt(quantity);
    	
    	String url="jdbc:mysql://localhost:3306/shop?user=root&password=12345";
    	String insert ="insert into sales values(?,?,?,?,?,?,?,?,?,?,?)";
    	String insert2 ="update stock set Quantity=Quantity-? where Tyresize=? and Brand=? and Model=?";
    	
    	try 
    	{
    		Class.forName("com.mysql.jdbc.Driver");
			Connection cn = DriverManager.getConnection(url);
			PreparedStatement ps = cn.prepareStatement(insert);
			PreparedStatement ps2 = cn.prepareStatement(insert2);
			PrintWriter pw = resp.getWriter();
			
			ps.setString(1, date);
			ps.setLong(2, invo);
			ps.setString(3, name);
			ps.setString(4, mbl);
			ps.setString(5, vnumber);
			ps.setString(6, vname);
			ps.setInt(7, kilo);
			ps.setString(8, size);
			ps.setString(9, brand);
			ps.setString(10, model);
			ps.setInt(11, quant);
			
			ps2.setString(2, size);
			ps2.setString(3, brand);
			ps2.setString(4, model);
			ps2.setInt(1, quant);
			
			int num = ps.executeUpdate();
			int num2 = ps2.executeUpdate();
			
			if(num!=0 && num2!=0)
			{
				RequestDispatcher rd=req.getRequestDispatcher("sale.html");
				rd.include(req, resp);
				pw.println("sale Successful");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
}
