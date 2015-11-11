package net.qingsoft.crm.product.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jspsmart.upload.File;
import com.jspsmart.upload.Files;
import com.jspsmart.upload.Request;
import com.jspsmart.upload.SmartUpload;


import net.qingsoft.crm.product.dao.ProductDaoFactory;
import net.qingsoft.crm.product.dao.ProductInfoDao;
import net.qingsoft.crm.util.BaseServlet;
import net.qingsoft.crm.vo.CategoryInfo;

public class ProductInfoServlet extends BaseServlet{
	
	private ProductInfoDao productInfoDao = ProductDaoFactory.createProductInfoDao();

	public void pageCount(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		
		int pgSize = 5;
		
		int count=productInfoDao.countAccount();
		count=(count+pgSize-1)/pgSize;
		request.setAttribute("count",count);
	}

	public void viewAll(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
	
		String pgNo=request.getParameter("pageNo");
		
		int pgSize = 5;
		int pgStart=0;
		if(pgNo==null){
			pgStart=1;
		}
		else{
			pgStart=Integer.parseInt(pgNo);
		}
		ArrayList<CategoryInfo> list =productInfoDao.viewAllInfo(pgStart, pgSize);//获得所有客户资料list
		request.setAttribute("list",list); 
	}
	
	public void addProduct(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		String brand =null;
		String name = null;
		String color = null;
		int amount = 0;
		String path = null;
		
		SmartUpload su = new SmartUpload();
		
		try {
			
			su.initialize(this.getServletConfig(),request,response);
			
			su.setAllowedFilesList("jpg,gif,jpeg,png");
			
			su.setTotalMaxFileSize(1024*1024);
			
			su.upload();
			
			Request req = su.getRequest();
			
			brand = req.getParameter("addBrand");
			name = req.getParameter("addName");
			color = req.getParameter("addColor");
			amount = Integer.parseInt(req.getParameter("addAmount"));
			
			System.out.println(brand);
			
			Files files = su.getFiles();
			
			for(int i = 0; i<files.getCount();i++){
				File file = files.getFile(i);
				
				String fileExt = file.getFileExt();
				
				String s = UUID.randomUUID().toString();
				
				path = s+"."+fileExt;
				
				file.saveAs("/images/productImages/"+path);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		productInfoDao.addProduct(brand,name,color,amount,path);
		
		response.sendRedirect("/crm/product/product.jsp");
	}
	
	public void isExist(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		
		String brand = request.getParameter("addBrand");
		
		String name = request.getParameter("addName");
		
		String color = request.getParameter("addColor");
		
		boolean bool = productInfoDao.isExist(brand,name,color);
		
		PrintWriter out = response.getWriter();
		
		out.write(bool+"");
	}
	
	public void updateAmount(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		int id = Integer.parseInt(request.getParameter("id"));
		int amount = Integer.parseInt(request.getParameter("amount"));
		
		int num = productInfoDao.updateAmount(id,amount);
		
		PrintWriter pw=response.getWriter();
		
		if(num==0){
			pw.write("false");
		}else{
			pw.write("true");
		}
	}
	
	public void findImg(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		int id = Integer.parseInt(request.getParameter("id"));
		
		String path = productInfoDao.findImg(id);
		PrintWriter pw = response.getWriter();
		pw.write(path+"");
	}
	
	public void deleteProduct(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		int id = Integer.parseInt(request.getParameter("id"));
		
		int num = productInfoDao.deleteProduct(id);
		PrintWriter pw=response.getWriter();
		
		if(num==0){
			pw.write("false");
		}else{
			pw.write("true");
		}
		
	}

}
