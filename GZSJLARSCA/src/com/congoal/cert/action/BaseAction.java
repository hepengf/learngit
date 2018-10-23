package com.congoal.cert.action;

import com.congoal.cert.pojo.User;
import com.congoal.cert.service.ModuleService;
import com.congoal.cert.utils.Pager;
import com.opensymphony.xwork2.ActionContext;

import org.apache.struts2.ServletActionContext;
import org.logicalcobwebs.proxool.admin.Admin;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author hepengfei
 * @date May 19, 2010
 * @Description action基类
 */

@Controller
@Scope("prototype")
public class BaseAction {

	protected String resultStr;
    public String getResultStr() {
		return resultStr;
	}

	public void setResultStr(String resultStr) {
		this.resultStr = resultStr;
	}

	protected Result result;//返回JSP结果
    protected String message;//返回JSP详细信息
    @SuppressWarnings("rawtypes")
    protected List list;
    protected User user;
    @Resource
    protected Pager pager;
    @Resource
    private ModuleService moduleService;
    /**
     * 通过在外部注入所需对象 *
     */
    @Resource
    protected DataSource dataSource;

    protected static final String SUCCESS = "success";
    protected static final String ERROR = "error";
    protected static final String INPUT = "input";
    protected static final String LOGIN = "login";
    protected static final String ADD = "add";
    protected static final String EDIT = "edit";
    protected static final String LIST = "list";

    protected HttpServletRequest request = ServletActionContext.getRequest();

    @PostConstruct
    public void inits() {
        user = (User) ActionContext.getContext().getSession().get("user");
    }

    public String add() {
        return ADD;
    }

    public String edit() {
        return EDIT;
    }

    public Pager getPager() {
        return pager;
    }

    public void setPager(Pager pager) {
        this.pager = pager;
    }

    @SuppressWarnings({"rawtypes"})
    public List getList() {
        return list;
    }

    public String getMessage() {
        return message;
    }

    public String getBasePath() {
        return ServletActionContext.getRequest().getContextPath();
    }

//    public void setMid(Integer id) {
//        ActionContext.getContext().getSession().put("currentPosition", moduleService.getCurrentPosition(id));
//    }

    public String getResult() {
        return result.toString();
    }

    public static enum Result {
        SUCCESS, FAIL, RCSUCCESS
    }

    public Date getNow() {
        return new Date();
    }

    protected void outMessage(String message) {
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html; charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.print(message.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
    }

    /**
     * 提供getter，方便页面取值
     *
     * @return
     */
    public User getOperator() {
        return user;
    }

    public void setOperator(User operator) {
        this.user = user;
    }

    protected HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	protected HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	protected Map getSession() {
		return ActionContext.getContext().getSession();
	}

	protected ActionContext getContext() {
		return ActionContext.getContext();
	}

	protected User getUser() {
		return (User) this.getRequest().getSession().getAttribute("user");
	}
}
