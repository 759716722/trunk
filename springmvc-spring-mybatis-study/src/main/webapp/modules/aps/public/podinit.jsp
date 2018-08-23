<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://mes/protaltag" prefix="p" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="pfn" uri="http://mes/portalFunction" %>
<%@ taglib prefix="webres" uri="http://acegisecurity.org/webres" %>
<%
    //set context path to pagecontext varibale
    String contextPath = request.getContextPath();
    request.setAttribute("contextPath", contextPath);

    String yuiLibs = request.getContextPath() + "/imes/base/ui/yui/";
    String portalLibs = request.getContextPath() + "/imes/base/ui/portalui_2.0.0/";
    String pluginsLibs = request.getContextPath() + "/imes/base/ui/plugins/";
    String uiLibs = request.getContextPath() + "/imes/base/";
    String libs = "yui:'" + yuiLibs + "',portalUi:'" + portalLibs + "',portalPlugins:'" + pluginsLibs + "',uiPlugins:'" + uiLibs + "'";

    request.setAttribute("uiLibs", libs);
    request.setAttribute("webDir", request.getRealPath("/"));
%>
<c:if test="${empty initScriptIsOut}">
    <c:set var="initScriptIsOut" scope="request" value="Y"/>
    <c:set var="menuId" scope="request"><%=request.getParameter("menuId")%>
    </c:set>
    <c:set var="activityId" scope="request"><%=request.getParameter("activityId")%>
    </c:set>
    <!--
    <link rel="stylesheet" href="<c:out value="${contextPath}"/>/imes/base/global/css/MES_global.css" type="text/css" />
    <link rel="stylesheet" type="text/css" href="<c:out value="${contextPath}"/>/imes/base/ui/yui/fonts/fonts.css"></link>
    <link rel="stylesheet" type="text/css" href="<c:out value="${contextPath}"/>/imes/base/ui/yui/assets/skins/sam/skin.css"></link>
    <link rel="stylesheet" type="text/css" href="<c:out value="${contextPath}"/>/imes/base/ui/yui/grids/grids-min.css"></link>
    <link rel="icon" href="<c:out value="${contextPath}"/>/imes/base/icon/imesicon.ico" type="image/x-icon" />
    <link rel="shortcut icon" href="<c:out value="${contextPath}"/>/imes/base/icon/imesicon.ico" type="image/x-icon" />
    <link rel="bookmark" href="<c:out value="${contextPath}"/>/imes/base/icon/imesicon.ico" type="image/x-icon" />
    <link href="<c:out
        value="${contextPath}"/>/imes/base/ui/portalui_2.0.0/asstes/skins/sam/skin.css" rel="stylesheet" type="text/css" />
    -->
    <link rel="stylesheet" href="<c:out value="${contextPath}"/>/framework/css/bootstrap.css?v1=1" type="text/css"/>
    <link rel="stylesheet" href="<c:out value="${contextPath}"/>/framework/css/animate.css" type="text/css"/>
    <link rel="stylesheet" href="<c:out value="${contextPath}"/>/framework/css/font-awesome.min.css" type="text/css"/>
    <link rel="stylesheet" href="<c:out value="${contextPath}"/>/framework/css/simple-line-icons.css" type="text/css"/>
    <link rel="stylesheet" href="<c:out value="${contextPath}"/>/framework/css/font.css" type="text/css"/>
    <link rel="stylesheet" href="<c:out value="${contextPath}"/>/framework/css/app.css" type="text/css"/>
    <link rel="stylesheet" href="<c:out value="${contextPath}"/>/framework/js/jquery/loadbar/loading-bar.css"
          type="text/css"/>
    <!--<p:jsLib>
    {<c:out escapeXml="false" value="${uiLibs}"/>,
    components:['PORTAL','json','event','dom','PORTAL.widget.container',
    'PORTAL.widget.message','PORTAL.widget.connection','animation',
    'PORTAL.plugins.My97DatePicker','UI.plugins.menu','PORTAL.widget.MesHelp','resize']}
</p:jsLib>-->
    <!-- jQuery -->
    <script src="<c:out value="${contextPath}"/>/framework/js/jquery/jquery.min.js"></script>
    <!-- Angular -->
    <script src="<c:out value="${contextPath}"/>/framework/js/angular/angular.min.js"></script>
    <script src="<c:out value="${contextPath}"/>/framework/js/angular/angular-cookies.min.js"></script>
    <script src="<c:out value="${contextPath}"/>/framework/js/angular/angular-animate.min.js"></script>
    <script src="<c:out value="${contextPath}"/>/framework/js/angular/angular-ui-router.min.js"></script>
    <script src="<c:out value="${contextPath}"/>/framework/js/angular/angular-translate.js"></script>
    <script src="<c:out value="${contextPath}"/>/framework/js/angular/ngStorage.min.js"></script>
    <script src="<c:out value="${contextPath}"/>/framework/js/angular/ui-load.js"></script>
    <script src="<c:out value="${contextPath}"/>/framework/js/angular/ui-jq.js"></script>
    <script src="<c:out value="${contextPath}"/>/framework/js/angular/ui-validate.js"></script>
    <script src="<c:out value="${contextPath}"/>/framework/js/angular/ui-bootstrap-tpls.min.js"></script>
    <script src="<c:out value="${contextPath}"/>/framework/js/jquery/loadbar/loading-bar.js"></script>
    <Script Language="JavaScript">
        /**====================================================================================
         *                                配置全局参数
         ====================================================================================*/
            //项目路径常量
        PORTAL.constant.CONTEXT_PATH = "<%=contextPath%>";
        //PORTAL UI类库
        PORTAL.constant.PORTALLIBS_PATH = "<%=portalLibs%>";
        //YUI类库
        PORTAL.constant.YUILIBS_PATH = "<%=yuiLibs%>";
        //PLUGINS
        PORTAL.constant.PLUGINS_PATH = "<%=pluginsLibs%>";

        PORTAL.constant.UPLOADCOMMAND_URL = PORTAL.constant.CONTEXT_PATH + "/core/system/utils/pageUtilsAction!upload.action";

        //页面初始化
        YAHOO.util.Event.onDOMReady(function () {
            PORTAL.initialize.Message.hide();
            PORTAL.constant.MesHelp = new PORTAL.widget.MesHelp("helpBtn");
            if (parent.document.getElementById('content_iframe') != null && parent.document.getElementById('content_iframe') != undefined) {
                parent.updateiFrameHeight(666);
                var height = document.documentElement.clientHeight;
                var bodyheight = document.body.scrollHeight;
                if (bodyheight >= height) {
                    height = bodyheight;
                }
                parent.updateiFrameHeight(height);
            }
            document.body.style.backgroundColor = "#C3DEEF";
            document.body.style.width = "99.9%";
        });
    </Script>
    <c:set var="HEADER_MESSAGE">
        <div class="comm_left_tx_vo_text"> &nbsp;&nbsp;&nbsp;&nbsp;您好！ <c:out value="${SESSION_USER.username}"/>，欢迎来到i-Factory数字化工厂平台，当前在[<c:out
                value="${CURRENT_SITE.name}"/>]站点 ！
        </div>
    </c:set>
    <Script Language="JavaScript">
        var activityId =${activityId};
        var helpIdValue =${pfn:getSysActivity(activityId).helpId};
    </Script>
</c:if>
