package com.git.cloud.taglib.dic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.tags.RequestContextAwareTag;


/**
 * @author wxg
 * 
 */

public class DictionaryTag extends RequestContextAwareTag  {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private IDictionaryTagDao dictionaryTagDao;
	private static final String _START_PAGE = "/pages/taglib/dic/start.jsp";
	private static final String _END_PAGE = "/pages/taglib/dic/end.jsp";
	
	private String name;

	// 字典项的CODE值
	private String value;

	// 字典KIND
	private String kind;

	private boolean multiSelect;

	private boolean readOnly;

	private boolean onlyShow;

	private String showType;

	private String attr;
	
	private String id;

	// 值对象来源
	private String source;

	// 静态字典 staticDic类似如"0:***;1:***;2:***"
	private String staticDic;

	// 直接使用sql构造字典，sql类似如"select ×× as CODE,×× as DETAIL,×× as REMARK from ××"
	private String sql;

	// 默认值
	private String defaultValue;

	// 排序
	private String order;

	// 是否可对select进行编辑

	private boolean canEdit;
	
	private boolean newLine;
	//点击事件
	private String click;
	//默认级联方法名
	private String cascade;
	
	public boolean isNewLine() {
		return newLine;
	}

	public void setNewLine(boolean newLine) {
		this.newLine = newLine;
	}

	public boolean isCanEdit() {
		return canEdit;
	}

	public void setCanEdit(boolean canEdit) {
		this.canEdit = canEdit;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public String getShowType() {
		return showType;
	}

	public void setShowType(String showType) {
		this.showType = showType;
	}

	public String getAttr() {
		return attr;
	}

	public void setAttr(String attr) {
		this.attr = attr;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getStaticDic() {
		return staticDic;
	}

	public void setStaticDic(String staticDic) {
		this.staticDic = staticDic;
	}

	@SuppressWarnings("unchecked")
	public int doStartTagInternal() throws JspException {
		ServletRequest request = pageContext.getRequest();
//		String[] locations = {
//				"/config/spring/applicationContext-common-ds.xml",
//				"/config/spring/applicationContext-taglib.xml",
//				"/config/spring/applicationContext-common.xml"
//			 };
//		ClassPathXmlApplicationContext ctx =  new ClassPathXmlApplicationContext(locations);
		dictionaryTagDao = (IDictionaryTagDao) this.getRequestContext().getWebApplicationContext().getBean("dictionaryTagDao");
		try {
			if (this.value != null) {
				if (this.source != null) {
					Object obj = request.getAttribute(this.source);
					if (obj == null) {
						obj = pageContext.getAttribute(this.source);
					}
					if (obj != null) {
						if (obj instanceof Map) {
							Map map = (Map) obj;
							this.value = (String) map.get(this.value);
						} else {
							this.value = (String) PropertyUtils.getNestedProperty(obj, this.value);
						}
					}
				}
				if (this.value == null) {
					this.value = this.defaultValue;
				}
				if (this.value == null) {
					this.value = "";
				}
			}
			
			List list = new ArrayList();

			if (this.kind != null) {
				List<DictionaryTag> resultList = dictionaryTagDao.getDicList(this);
//				if ("desc".equals(this.getOrder())) {
//					resultList = SortUtils.sortByProperty(resultList, "value", "desc");
//				}else{
//					resultList = SortUtils.sortByProperty(resultList, "value", "asc");
//				}
				for(DictionaryTag obj:resultList){
					Map dicMap = new HashMap();
					dicMap.put("CODE", obj.getValue());
					dicMap.put("DETAIL", obj.getName());
					list.add(dicMap);
				}
			} else if (this.sql != null) {
				List<DictionaryTag> resultList = dictionaryTagDao.getDicListBySql(this.getSql());
				if (this.order != null){
					if ("desc".equals(this.getOrder())) {
						resultList = SortUtils.sortByProperty(resultList, "value", "desc");
					}else{
						resultList = SortUtils.sortByProperty(resultList, "value", "asc");
					}					
				}
				for(DictionaryTag obj:resultList){
					Map dicMap = new HashMap();
					dicMap.put("CODE", obj.getValue());
					dicMap.put("DETAIL", obj.getName());
					list.add(dicMap);
				}
			} else if (this.staticDic != null) {
				// 静态字典构造
				list = new ArrayList();
				String[] dic = this.staticDic.split(";");
				for (int i = 0; i < dic.length; i++) {
					Map dicMap = new HashMap();
					dicMap.put("CODE", dic[i].substring(0, dic[i].indexOf(":")));
					dicMap.put("DETAIL", dic[i].substring(dic[i].indexOf(":") + 1));
					list.add(dicMap);
				}
			}
			request.setAttribute("dt_dic", this);
			request.setAttribute("dt_list", list);
			pageContext.include(_START_PAGE);
		} catch (Exception e) {
			logger.error("异常exception",e);
		}
		return EVAL_PAGE;
	}

	public int doEndTag() throws JspException {
		try {
			pageContext.include(_END_PAGE);
		} catch (Exception e) {
			logger.error("异常exception",e);
		}
		return EVAL_PAGE;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public boolean isMultiSelect() {
		return multiSelect;
	}

	public void setMultiSelect(boolean multiSelect) {
		this.multiSelect = multiSelect;
	}

	public boolean isOnlyShow() {
		return onlyShow;
	}

	public void setOnlyShow(boolean onlyShow) {
		this.onlyShow = onlyShow;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}
	
	public String getStartPage() {
			return _START_PAGE;
	}

	public String getEndPage() {
		return _END_PAGE;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClick() {
		return click;
	}

	public void setClick(String click) {
		this.click = click;
	}

	public IDictionaryTagDao getDictionaryTagDao() {
		return dictionaryTagDao;
	}

	public void setDictionaryTagDao(IDictionaryTagDao dictionaryTagDao) {
		this.dictionaryTagDao = dictionaryTagDao;
	}

	public String getCascade() {
		return cascade;
	}

	public void setCascade(String cascade) {
		this.cascade = cascade;
	}

}
