package com.epam.hospital;

import com.epam.hospital.controller.ControllerConstants;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class PaginationTag extends TagSupport {
    private String name;
    private int current_page;
    private int count_page;

    public void setName(String name) {
        this.name = name;
    }

    public void setCurrent_page(int current_page) {
        this.current_page = current_page;
    }

    public void setCount_page(int count_page) {
        this.count_page = count_page;
    }

    @Override
    public int doStartTag() throws JspException {

        String locale = (String) pageContext.getSession().getAttribute(ControllerConstants.LOCALE);
        String begin="Begin";
        String end= "End";

        if (locale!=null&& locale.equals(ControllerConstants.LOCALE_UA)){
            begin= MessageManager.UA.getString(begin);
            end = MessageManager.UA.getString(end);
        }

        StringBuilder sb = new StringBuilder();
        sb.append("<div class btn-group btn group-xs>")
            .append("<ul id=\"pagination_").append(name).append("\" class = \"pagination pagination-sm\">")
            .append("<li class=\"disabled\"><input type=\"button\" onClick=\"click_page").append("(1").append(",'").append(name).append("')\" name=\"").append(name).append("pag\" class=\"btn btn-info btn-md\" value=\"").append(begin).append("\"/></li>");

        for (int i=1; i<=count_page;i++) {
            String act =i==current_page?"active":"";
            sb.append("<li class=\"").append(act).append("\"><input type=\"button\" onClick=\"click_page").append("(").append(i).append(",'").append(name).append("')\" name=\"").append(name).append("pag\" class=\"btn btn-info btn-md\" value=\"").append(i).append("\"/></li>");
        }
        sb.append("<li><input type=\"button\" onClick=\"click_page").append("(").append(count_page).append(",'").append(name).append("')\" name=\"").append(name).append("pag\" class=\"btn btn-info btn-md\" value=\"").append(end).append("\"/></li>");
        sb.append("<input type=\"hidden\" id =\"pat_comment_").append(name).append("\" name=\"current_page_").append(name).append("\" value=\"").append(current_page).append("\" />");
        sb.append("</div>");
        JspWriter out = pageContext.getOut();
        try {
            out.write(sb.toString());
        } catch (IOException e) {
            throw new JspException(e.getMessage());
        }
        return SKIP_BODY;
    }

    @Override
    public int doEndTag(){
        return EVAL_PAGE;
    }
}
