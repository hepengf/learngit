package com.congoal.cert.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.congoal.cert.pojo.UnCertAward;
import com.congoal.cert.pojo.UnCertBulk;
import com.congoal.cert.pojo.UnCertCRL;
import com.congoal.cert.service.CertAdministrationService;
import com.congoal.cert.utils.BasicAction;
import com.congoal.cert.utils.CertCommonUtils;

@SuppressWarnings("all")
@Scope("prototype")
@Controller("fileDownloadAction")
public class FileDownloadAction extends BasicAction {

	private static Log logger = LogFactory.getLog(FileDownloadAction.class);

	@Autowired
	private CertAdministrationService certAdministrationService;

	// 初始的通过param指定的文件名属性
	private String fileName;

	//文件完整路径
	private String path;

	public void setPath(String path) {
		this.path = path;
	}
	
	private ByteArrayOutputStream bos;
	
	// 指定要被下载的文件路径，struts.xml中配置
	private String inputPath;

	public void setInputPath(String value) {

		inputPath = value;

	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}
	
	public void setBos(ByteArrayOutputStream bos) {
		this.bos = bos;
	}
	
	public OutputStream getBos() {
		return bos;
	}

	/**
	 * 密钥库下载
	 * @return
	 */
	public String CertDownload() {
		HttpServletRequest request = this.getRequest();
		String id = request.getParameter("id");
		if (StringUtils.isEmpty(id) || StringUtils.isBlank(id)) {
			return ERROR;
		}
		String hql = "from UnCertAward where id=" + id;
		List<UnCertAward> alists = (List<UnCertAward>) this.certAdministrationService
				.findAll(hql);
		
		if (null != alists && 0 < alists.size()) {
			UnCertAward ua = alists.get(0);
			this.path = ua.getPath();// 证书下载完整路径
			logger.info("证书下载路径：" + path);
			this.setFileName(ua.getFname());//文件名
		}
		return SUCCESS;
	}
	/**
	 * 
	 * @return
	 */
	public String rootDownload() {
		this.path = CertCommonUtils.ROOTCA_ONLINECERTSPATH
		        +CertCommonUtils.ROOT_CERT_NAME;
		this.setFileName(CertCommonUtils.ROOT_CERT_NAME);
		return SUCCESS;
	}
	
	public InputStream getInputStream() throws Exception {
//		return ServletActionContext.getServletContext().getResourceAsStream(
//				this.path);//只能读工程下目录
		
		return new FileInputStream(this.path);
	}
	
	/**
	 * cer证书文件下载
	 * @return
	 * @throws Exception 
	 */
	public String cerDownload() throws Exception {
		HttpServletRequest request = this.getRequest();
		String id = request.getParameter("id");
		String bulk = request.getParameter("bulk");
		if (StringUtils.isEmpty(id) || StringUtils.isBlank(id)) {
			return ERROR;
		}
		
		if (StringUtils.isEmpty(bulk) || StringUtils.isBlank(bulk)) {
			bulk = "false";
		}
		logger.info("id "+id);
		//多文件下载
		if (bulk.equals("true")) {
			String hql = "from UnCertBulk where id="+id;
			List<UnCertBulk> bulks = (List<UnCertBulk>) this.certAdministrationService.findAll(hql);
			if (null!=bulks && bulks.size()>0) {
				UnCertBulk app = bulks.get(0);
				this.path = app.getDownpath()+"";
				logger.info("批量下载   path: "+path);
				this.setFileName(app.getDownFname()+"");
			}else{
				logger.error("服务器没有相应证书存在，下载失败！");
			}
		}else {
				String hql = "select path,fname from UnCertAward where id="+id;
				
				List<Object[]> results = (List<Object[]>) this.certAdministrationService.findAll(hql);
				
				
				if(results != null && !results.isEmpty())
				{
					Object[] result = results.get(0);
					String filePath=result[0]+"";
					String fileName=result[1]+"";
					if(filePath.endsWith(".keystore") && fileName.endsWith(".keystore"))
					{						
						filePath = filePath.replace("keystore", "cer");// 证书下载完整路径
						fileName = fileName.replace("keystore", "cer");
					}
					this.path=filePath;
					logger.info("证书下载路径：" + path);
					this.setFileName(fileName);//文件名
				}
				else
				{
					logger.error("该密钥库未导出证书!");
					return null;
				}
		}
		return SUCCESS;
	}

	/**
	 * crl文件下载
	 * @return
	 */
	public String crlDownload()
	{
		HttpServletRequest request = this.getRequest();
		String id = request.getParameter("id");
		if (StringUtils.isEmpty(id) || StringUtils.isBlank(id)) {
			return ERROR;
		}
		String hql = "from UnCertCRL where id=" + id;
		List<UnCertCRL> alists = (List<UnCertCRL>) this.certAdministrationService.findAll(hql);
		
		if (null != alists && 0 < alists.size()) {
			UnCertCRL ua = alists.get(0);
			this.path = ua.getPath();// 证书下载完整路径
			logger.info("CRL文件下载路径：" + path);
			this.setFileName(ua.getFname());//文件名
		}
		return SUCCESS;
	}
	
}
