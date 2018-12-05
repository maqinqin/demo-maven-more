package com.git.cloud.cloudservice.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.alibaba.fastjson.JSON;
import com.git.cloud.cloudservice.dao.ICloudServiceAttrDao;
import com.git.cloud.cloudservice.dao.ICloudServiceAttrSelDao;
import com.git.cloud.cloudservice.dao.ICloudServiceDao;
import com.git.cloud.cloudservice.dao.ICloudServiceFlowRefDao;
import com.git.cloud.cloudservice.dao.ImageDao;
import com.git.cloud.cloudservice.model.po.CloudFile;
import com.git.cloud.cloudservice.model.po.CloudFileTemplate;
import com.git.cloud.cloudservice.model.po.CloudServiceAttrPo;
import com.git.cloud.cloudservice.model.po.CloudServiceAttrSelPo;
import com.git.cloud.cloudservice.model.po.CloudServiceFlowRefPo;
import com.git.cloud.cloudservice.model.po.CloudServicePo;
import com.git.cloud.cloudservice.service.ICloudFileService;
import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.foundation.util.UUIDGenerator;
import com.git.cloud.resmgt.common.model.po.CmPasswordPo;
import com.gitcloud.tankflow.service.IBpmDesignerService;

import net.sf.json.JSONObject;



/**
 * 进行cloud_service的文件导入导出
 * @author gaosida
 *
 */

public class CloudFileServiceImpl  implements ICloudFileService{
	
	private ICloudServiceDao cloudServiceDao;
	
	private ImageDao imageDao;
	
	private ICloudServiceAttrDao cloudServiceAttrDao;
	
	private ICloudServiceAttrSelDao cloudServiceAttrSelDao;
	
	private ICloudServiceFlowRefDao cloudServiceFlowRefDao;
	
	private IBpmDesignerService designerServiceImpl;

	/**
	 * 导出txt文件
	 * @throws Exception 
	 */
	@Override
	public CloudFile cloudleading(String id) throws Exception{
		CloudFile cloudFile = new CloudFile();
		ArrayList<String> listAttrPoId = new ArrayList<String>();
		ArrayList<String> listFlowId = new ArrayList<String>();
		CloudServicePo cloudService = cloudServiceDao.findById(id);  //根据serviceId查询cloudService信息
		if (cloudService != null) {
			cloudFile.setCloudServiceList(cloudService);;
		}
		String imageId = cloudFile.getCloudServiceList().getImageId(); //根据imageID查询image表信息
		if(imageId != null) {
			cloudFile.setCloudImage(imageDao.findImage(imageId));
		}
		List<CloudServiceAttrPo> listAttr = cloudServiceAttrDao.cloudLeading(id);   //根据serviceId查询arrt表的数组
		if(listAttr != null) {
			cloudFile.setCloudServiceAttrList(listAttr);
			for(CloudServiceAttrPo attr : listAttr) {
				listAttrPoId.add(attr.getAttrId());
			}
		}
		List<CloudServiceAttrSelPo> listAttrSelPo = new ArrayList<CloudServiceAttrSelPo>();   //
		if(listAttrPoId != null){
			listAttrSelPo = cloudServiceAttrSelDao.cloudLeading(listAttrPoId);   //根据attrID查询attrSel表的数组
			if(listAttrSelPo != null) {
				cloudFile.setCloudServiceAttrSelList(listAttrSelPo);
			}
		}
		List<CloudServiceFlowRefPo> listFlowRe = cloudServiceFlowRefDao.cloudLeading(id);  //根据serviceId查询flowref表的数组信息
		if(listFlowRe != null) {
			for(CloudServiceFlowRefPo po : listFlowRe) {
				listFlowId.add(po.getFlowId());
			}
		}
		List<CloudFileTemplate> listTemplate = new ArrayList<CloudFileTemplate>();
		List<CloudServiceFlowRefPo> flowRefPo = new ArrayList<CloudServiceFlowRefPo>();
		for(String modelId : listFlowId) {
			CloudFileTemplate tem = cloudServiceFlowRefDao.templateFile(modelId);   //根据flowId查询bpm_model表的信息，然后根据bpm_model表的template_id查询template表的信息
			if(tem != null){
				listTemplate.add(tem);
			}
			for(CloudServiceFlowRefPo flowRe : listFlowRe ) {      //将template表的template_id加入到flowref中
				if(flowRe.getFlowId().equals(modelId)) {
					flowRe.setTemplateId(tem.getTemplateId());
					flowRefPo.add(flowRe);
				}
			}
			listTemplate.add(tem);
		}
		if(flowRefPo != null) {
			cloudFile.setCloudServiceFlowRefList(flowRefPo);
		}
		if(listTemplate != null) {
			cloudFile.setCloudFileTemplateList(listTemplate);
		}
		List<CmPasswordPo> listCmPassword = cloudServiceDao.cloudFilePassword(id);   //根据service_id查询cm_password表的数组信息
		if(listCmPassword != null) {
			cloudFile.setCmPasswordList(listCmPassword);
		}
		writeToTxt(JSONObject.fromObject(cloudFile).toString()); //进行写入txt操作
		return cloudFile;
	}

	
	/**
	 * 导入txt文件
	 * @param path
	 * @throws Exception
	 */
	@SuppressWarnings("unlikely-arg-type")
	@Override
	public void savecloudWrite() throws Exception {
		JFileChooser fd = new JFileChooser();  
		//fd.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);  
		fd.showOpenDialog(null);  
		File f = fd.getSelectedFile();  
		if(f != null){
			String encoding = "UTF-8";  
	        File file = new File(f.getAbsolutePath());  
	        Long filelength = file.length();  
	        byte[] filecontent = new byte[filelength.intValue()];  
	        try {  
	            FileInputStream in = new FileInputStream(file);  
	            in.read(filecontent);  
	            in.close();
	        } catch (FileNotFoundException e) {  
	            e.printStackTrace();  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }  
	        try {  
	        	String jsonString = new String(filecontent, encoding);
				if(jsonString != null) {
					CloudFile cloudFile = JSON.parseObject(jsonString,CloudFile.class);
					if(cloudFile == null) {
						throw new Exception("");
					}

					if(jsonString.indexOf("[") != -1){                   //过滤jsonarray类型
						jsonString = jsonString.replace("[", "");  
			        }  
			        if(jsonString.indexOf("]") != -1){  
			        	jsonString = jsonString.replace("]", "");  
			        }
					try {
						Date date = new Date();
						SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String dataName = df.format(date);									//取当前时间年月日时分秒
						String imageId = "";       
						if(cloudFile.getCloudImage() != null) {
							if(imageDao.findImage(cloudFile.getCloudImage().getImageId()) == null) { 		//查询导入json文件中的imageId在数据中是否存在
								imageId = cloudFile.getCloudImage().getImageId();		//不在，imageId赋当前值
							} else {
								imageId = UUIDGenerator.getUUID();						//数据库中存在，重新生成UUID，imageId赋新生成值
								cloudFile.getCloudImage().setImageId(imageId);
								cloudFile.getCloudImage().setImageName(cloudFile.getCloudImage().getImageName()+dataName);
							}
							imageDao.insertImage(cloudFile.getCloudImage());		//保存image信息
						}
						String serviceId = "";
						if(cloudFile.getCloudServiceList() != null) {											
							CloudServicePo cloudService = cloudServiceDao.findById(cloudFile.getCloudServiceList().getServiceId());
							if(cloudService == null) {											//查询导入数据中的serviceId在数据库中是否存在
								serviceId = cloudFile.getCloudServiceList().getServiceId();	//不在，serviceId赋予当前值
							} else {														//在，重新生成UUID，serviceId赋予新值
								serviceId = UUIDGenerator.getUUID();
								cloudFile.getCloudServiceList().setServiceId(serviceId);
								cloudFile.getCloudServiceList().setServiceName(cloudFile.getCloudServiceList().getServiceName()+dataName);
							}
							cloudFile.getCloudServiceList().setImageId(imageId);
							cloudServiceDao.save(cloudFile.getCloudServiceList());
						}
						if("".equals(serviceId)) {			//若导入数据没有serviceId，导入结束
							return;
						}
						HashMap<String, String> attrIdMap = new HashMap<String, String> ();
						if(cloudFile.getCloudServiceAttrList() != null) {
							String attrId;
							for(CloudServiceAttrPo po : cloudFile.getCloudServiceAttrList()) {
								attrId = UUIDGenerator.getUUID();				//新生成一个UUID
								attrIdMap.put(po.getAttrId(), attrId);				
								po.setAttrId(attrId);			
								po.setServiceId(serviceId);
								cloudServiceAttrDao.save(po);					//保存新的UUID的数据
							}
						}
						if(cloudFile.getCloudServiceAttrSelList() != null) {
							for(CloudServiceAttrSelPo po : cloudFile.getCloudServiceAttrSelList()) {
								po.setAttrSelId(UUIDGenerator.getUUID());				//新生成一个attrsel表的uuid
								po.setAttrId(attrIdMap.get(po.getAttrId()));			//获取attr中新生成的UUID
								cloudServiceAttrSelDao.save(po);
							}
						}
						if(cloudFile.getCmPasswordList() != null) {
							for(CmPasswordPo po : cloudFile.getCmPasswordList()) {
								po.setId(UUIDGenerator.getUUID());			//新生成一个新的cm_password表的UUID
								po.setResourceId(imageId);					//将imageId值赋予
								cloudServiceDao.savecmPassword(po);
							}
						}
						if(cloudFile.getCloudServiceFlowRefList() != null) {
	
							HashMap<String, CloudFileTemplate> templateMap = new HashMap<String, CloudFileTemplate> ();
							for(CloudFileTemplate template : cloudFile.getCloudFileTemplateList()) {			
								templateMap.put(template.getTemplateId(), template);				//当前的Bpmtemplate
							}
							String templateName = "";
							for(CloudServiceFlowRefPo po : cloudFile.getCloudServiceFlowRefList()) {
								int flag = cloudServiceFlowRefDao.selectBpmModelByFlowId(po.getFlowId());					//获取bpm_model表中是否含有flow_id
								if(flag != 0) {																		//有 正常存储
									po.setServiceFlowId(UUIDGenerator.getUUID());
									po.setServiceId(serviceId);
									cloudServiceFlowRefDao.save(po);
									/*if(cloudFile.getCloudFileTemplateList() != null) {
										for(CloudFileTemplate cloudTemplate : cloudFile.getCloudFileTemplateList()) {
											if(cloudServiceFlowRefDao.selectTmeplateById(cloudTemplate.getTemplateId()) ==null) {
												cloudServiceFlowRefDao.savebpmTemplate(cloudTemplate);
												System.out.println("4-------------------");
											}
										}
									}*/
									
								}else {														//没有
									CloudFileTemplate template = templateMap.get(po.getTemplateId());
									if(cloudServiceFlowRefDao.selectTmeplateById(template.getTemplateId()) == null) {		//查询当前的template_id在bpm_tamplate中是否存在
										cloudServiceFlowRefDao.savebpmTemplate(template);									//不存在，保存template_id
									} else {
										String uuid = UUIDGenerator.getUUID();											//存在，新生成一个UUID
										template.setTemplateId(uuid);
										template.setTemplateName(template.getTemplateName() + dataName);				//重新命名template_name
										cloudServiceFlowRefDao.savebpmTemplate(template);					//保存template
									}
									String string = designerServiceImpl.publishTemplate(template.getTemplateId());						//进行发布
									if(string == null || !"发布成功！".equals(string)) {						//如果发布不成功，进行抛异常
										templateName += "," + template.getTemplateName();
									}
									List<String> listTemPlate  = cloudServiceFlowRefDao.selectTemplateByCreateDate();	//根据创建时间，进行查询model_id
									if(listTemPlate != null) {	
										po.setServiceFlowId(UUIDGenerator.getUUID());
										po.setServiceId(serviceId);
										po.setFlowId(listTemPlate.get(0));
										cloudServiceFlowRefDao.save(po);						//保存flow_sel
									}
									
									/*if(cloudFile.getCloudFileTemplateList().size() != 0) {
										System.out.println(cloudFile.getCloudFileTemplateList()+"6-------------------");
										for(CloudFileTemplate template : cloudFile.getCloudFileTemplateList()) {
											System.out.println("13-------------------");
											if(cloudServiceFlowRefDao.selectTmeplateById(template.getTemplateId()) == null && po.getTemplateId().equals(template.getTemplateId())) {
												System.out.println("7-------------------");
												cloudServiceFlowRefDao.savebpmTemplate(template);
												String string = designerServiceImpl.publishTemplate(template.getTemplateId());
												List<String> listTemPlate  = cloudServiceFlowRefDao.selectTemplateByCreateDate();
												if(listTemPlate != null) {	
													System.out.println("8-------------------");
													po.setFlowId(listTemPlate.get(0));
													if(cloudServiceFlowRefDao.findById(po.getServiceFlowId()) == null) {
														System.out.println("9-------------------");
														cloudServiceFlowRefDao.save(po);
													}
												}
											}else {
												System.out.println("10-------------------");
												String uuid = UUIDGenerator.getUUID();
												template.setTemplateId(uuid);
												cloudServiceFlowRefDao.savebpmTemplate(template);
												String string = designerServiceImpl.publishTemplate(template.getTemplateId());
												List<String> listTemPlate  = cloudServiceFlowRefDao.selectTemplateByCreateDate();
												if(listTemPlate != null) {	
													System.out.println("11-------------------");
													po.setFlowId(listTemPlate.get(0));
													if(cloudServiceFlowRefDao.findById(po.getServiceFlowId()) == null) {
														System.out.println("12-------------------");
														cloudServiceFlowRefDao.save(po);
													}
												}
											}
										}
									}*/
									
//									if(cloudFile.getCloudFileTemplateList() != null) {
//										for(CloudFileTemplate template : cloudFile.getCloudFileTemplateList()) {
//											if(po.getTemplateId().equals(template.getTemplateId())) {
//												CloudFileTemplate cloudFileTemplate = cloudServiceFlowRefDao.selectTmeplateById(template.getTemplateId());
//												if(cloudFileTemplate == null) {
//													cloudServiceFlowRefDao.savebpmTemplate(template);
//													String string = designerServiceImpl.publishTemplate(template.getTemplateId());
//													System.out.println(string);
//												}else {
//													
//												}
//											}
//										}
//									}
								}
//								if(cloudServiceFlowRefDao.findById(po.getServiceFlowId()) == null) {
//									cloudServiceFlowRefDao.save(po);
//								}
							}
							if(!"".equals(templateName)) {					//如果发布过程发生异常，进行抛出
								throw new Exception ("保存成功" + templateName + "流程发布失败");
							}
						}
					}catch(RollbackableBizException e) {
						throw new RollbackableBizException("数据库存入失败",e);
					}catch(Exception e1) {
						throw new Exception(e1.getMessage());
					}
					
					
//					if(cloudFile.getCloudFileTemplateList() != null) {
//						for(CloudFileTemplate po : cloudFile.getCloudFileTemplateList()) {
//							if(cloudServiceFlowRefDao.selectTmeplateById(po.getTemplateId()) ==null) {
//								cloudServiceFlowRefDao.savebpmTemplate(po);
//							}
//						}
//					}
//					}		
				}
				
	        } catch (RollbackableBizException e) {  
	        	System.err.println("The OS does not support " + encoding);  
	        	e.printStackTrace();  
	        } catch (Exception e1) {  
	        	throw new Exception(e1.getMessage());
	        }  

		} 
	}
	
	
	/**
	 * JFileChoose进行保存文件的弹框
	 * @param json
	 */
	public void writeToTxt(String json) {
		 JFileChooser dialog = new JFileChooser();
		 dialog.setDialogTitle("另存为");	
		 dialog.setFileSelectionMode(JFileChooser.FILES_ONLY);
		 dialog.setDialogType(JFileChooser.SAVE_DIALOG);
		 FileFilter filter = new FileNameExtensionFilter("*.txt", "文本文档(*.txt)");
		 dialog.setFileFilter(filter);
		 int result = dialog.showSaveDialog(null);
		 String fileName = null;
		 File file = null;
		 if(result == JFileChooser.APPROVE_OPTION){
			file = dialog.getSelectedFile();
			fileName = file.getAbsolutePath();	//得到文件全名
			File txt = new File(file.getAbsolutePath());
		    if (!txt.exists()) {
		        try {
		            txt.createNewFile();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		    }
			
			byte[] bytes = json.getBytes(); //新加的
			FileOutputStream fos = null;
			try {
			    fos = new FileOutputStream(txt);
			    fos.write(bytes);
			    fos.flush();
			    fos.close();
			} catch (FileNotFoundException e) {
			    e.printStackTrace();
			} catch (IOException e) {
			    e.printStackTrace();
			}
		 }
		 
	}
	
	
	/**
	 * 过滤掉非TXT类型文件
	 * @author DELL
	 *
	 */
	 public class TextFileFilter extends FileFilter {
	    	private ArrayList<String> extensions = new ArrayList<String>();
	    	private ArrayList<String> descriptions = new ArrayList<String>();
	    	
	    	public TextFileFilter(){
	    		super();
	    	}
	    	
	    	public TextFileFilter(String extension, String description) {
	    		super();
	    		this.extensions.add(extension);
	    		this.descriptions.add(description);
	    	}
	    	

	    	public boolean accept(File pathname) {
	    		if (pathname != null) {
	    			if (pathname.isDirectory()) {
	    				return true;
	    			}
	    			String extension = getExtension(pathname);
	    			try {
	    				for(int i=0; i<extensions.size(); i++){
		    				if(extensions.get(i).toLowerCase().endsWith(extension.toLowerCase())){
		    					return true;
		    				}
		    			}
	    			}catch(Exception e){
	    				
	    			}
	    			
	    		}
	    		return false;
	    	}
	    	private String getExtension(File pathname) {
	    		if (pathname != null) {
	    			String filename = pathname.getName();
	    			int i = filename.lastIndexOf('.');
	    			if (i > 0 && i < filename.length() - 1) {
	    				return filename.substring(i).toLowerCase();
	    			}
	    		}
	    		return null;
	    	}
	    	public String getDescription() {
	    		return descriptions.get(descriptions.size()-1);
	    	}
	    }

	public ICloudServiceDao getCloudServiceDao() {
		return cloudServiceDao;
	}


	public void setCloudServiceDao(ICloudServiceDao cloudServiceDao) {
		this.cloudServiceDao = cloudServiceDao;
	}


	public ImageDao getImageDao() {
		return imageDao;
	}


	public void setImageDao(ImageDao imageDao) {
		this.imageDao = imageDao;
	}


	public ICloudServiceAttrDao getCloudServiceAttrDao() {
		return cloudServiceAttrDao;
	}


	public void setCloudServiceAttrDao(ICloudServiceAttrDao cloudServiceAttrDao) {
		this.cloudServiceAttrDao = cloudServiceAttrDao;
	}


	public ICloudServiceAttrSelDao getCloudServiceAttrSelDao() {
		return cloudServiceAttrSelDao;
	}


	public void setCloudServiceAttrSelDao(ICloudServiceAttrSelDao cloudServiceAttrSelDao) {
		this.cloudServiceAttrSelDao = cloudServiceAttrSelDao;
	}


	public ICloudServiceFlowRefDao getCloudServiceFlowRefDao() {
		return cloudServiceFlowRefDao;
	}


	public void setCloudServiceFlowRefDao(ICloudServiceFlowRefDao cloudServiceFlowRefDao) {
		this.cloudServiceFlowRefDao = cloudServiceFlowRefDao;
	}


	public IBpmDesignerService getDesignerServiceImpl() {
		return designerServiceImpl;
	}


	public void setDesignerServiceImpl(IBpmDesignerService designerServiceImpl) {
		this.designerServiceImpl = designerServiceImpl;
	}

	
	

}
