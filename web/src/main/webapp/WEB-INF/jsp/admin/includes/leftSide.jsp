<%@ page import="ru.flystar.travelrk.tools.StringTool" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<security:authentication var="login" property="principal.username"/>
<div class="col-md-3 left_col">
    <div class="left_col scroll-view">
        <div class="navbar nav_title" style="border: 0;">
            <a href="index.html" class="site_title"><i class="fa fa-paw"></i> <span>TravelRK!</span></a>
        </div>

        <div class="clearfix"></div>

        <!-- menu profile quick info -->
        <div class="profile clearfix">
            <div class="profile_pic">
                <img src="../images/img.jpg" alt="..." class="img-circle profile_img">
            </div>
            <div class="profile_info">
                <span>Добропожаловать,</span>
                <h2>${userService.getUserByLogin(login).getFio()}</h2>
            </div>
            <div class="clearfix"></div>
        </div>
        <!-- /menu profile quick info -->

        <br/>

        <!-- sidebar menu -->
        <div id="sidebar-menu" class="main_menu_side hidden-print main_menu">
            <div class="menu_section">
                <ul class="nav side-menu">
                    <security:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_USER')">
                        <li><a><i class="fa fa-image"></i> Галереи <span class="fa fa-chevron-down"></span></a>
                            <ul class="nav child_menu">
                                <li class="current-page"><a href="/admin/youtubegallery">Ролики с Youtube</a></li>
                                <li class="current-page"><a href="/admin/panoscans">Развёртки панорам</a></li>
                                <li class="current-page"><a href="/admin/pano3D">3D панорамы</a></li>
                            </ul>
                        </li>
                    </security:authorize>
                    <li><a><i class="fa fa-map-signs"></i> Туры <span class="fa fa-chevron-down"></span></a>
                        <ul class="nav child_menu">
                            <security:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_USER')">
                                <li class="current-page"><a href="/admin/exclusivetour"><i class="fa fa-universal-access"></i> Эксклюзивные</a></li>
                            </security:authorize>
                            <security:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_USER','ROLE_MANAGER')">
                                <li class="current-page"><a href="/admin/rentatour"><i class="fa fa-adn"></i> Арендные</a></li>
                            </security:authorize>
                        </ul>
                    </li>
                    <security:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_USER','ROLE_MANAGER')">
                        <li><a><i class="fa fa-info"></i> Справочники <span class="fa fa-chevron-down"></span></a>
                            <ul class="nav child_menu">
                                <li class="current-page"><a href="/admin/customers">Заказчики</a></li>
                                <li class="current-page"><a href="/admin/regions">Регионы</a></li>
                            </ul>
                        </li>
                    </security:authorize>
                    <security:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_USER')">
                        <li><a><i class="fa fa-cogs"></i> Настройка <span class="fa fa-chevron-down"></span></a>
                            <ul class="nav child_menu">
                                <li class="current-page"><a href="/admin/krpanoconfigs">KrPano конфиги</a></li>
                            </ul>
                        </li>
                    </security:authorize>
                </ul>
                <span style="padding-left:14px;"><%= StringTool.getFreeSize() %></span>
            </div>


        </div>
        <!-- /sidebar menu -->

        <!-- /menu footer buttons -->
        <div class="sidebar-footer hidden-small">
            <a data-toggle="tooltip" data-placement="top" title="Settings">
                <span class="glyphicon glyphicon-cog" aria-hidden="true"></span>
            </a>
            <a data-toggle="tooltip" data-placement="top" title="FullScreen">
                <span class="glyphicon glyphicon-fullscreen" aria-hidden="true"></span>
            </a>
            <a data-toggle="tooltip" data-placement="top" title="Lock">
                <span class="glyphicon glyphicon-eye-close" aria-hidden="true"></span>
            </a>
            <a data-toggle="tooltip" data-placement="top" title="Logout" href="/admin/logout">
                <span class="glyphicon glyphicon-off" aria-hidden="true"></span>
            </a>
        </div>
        <!-- /menu footer buttons -->
    </div>
</div>