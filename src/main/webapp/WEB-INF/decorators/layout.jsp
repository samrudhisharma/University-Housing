<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="X-UA-Compatible" content="chrome=1" />
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
    <title>University Housing</title>
    <link href="/<s:property value='appName'/>/css/bootstrap.min.css" rel="stylesheet">
    <link href="/<s:property value='appName'/>/css/bootstrap-responsive.min.css" rel="stylesheet">
    <link href="/<s:property value='appName'/>/css/housing.css" rel="stylesheet">
    <script type="text/javascript" src="/<s:property value='appName'/>/js/jquery-2.1.3.min.js"></script>
    <script type="text/javascript" src="/<s:property value='appName'/>/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/<s:property value='appName'/>/js/housing.js"></script>
    <decorator:head/>
</head>

<body>
<div id="allContainer">
    <!-- Header -->
    <div id="top-header" class="homePage">
            <div class="section-2">
                <div class="col1">
                    <a id="logo" rel="home" title="University Housing Home Page" href="#">
                        <img alt="University Housing Logo" src="http://housing.ncsu.edu/sites/all/themes/theme687/logo.png">
                    </a>
                </div>
            </div>
        </div>
    <div>
        <div class="upper">
            <span><a id="home" class="btn btn-primary navbar-btn" href="/<s:property value='appName'/>/login.action"> Home</a></span>
            <span><button type="button" id="backButton" class="btn btn-primary navbar-btn"><span class="glyphicon glyphicon-arrow-left" aria-hidden="true"></span>Back</button></span>
            <span><a id="logout" class="btn btn-primary navbar-btn logout" href="/<s:property value='appName'/>/logout.action"> Log out</a></span>
        </div>
    </div>

        <div id="columns">
            <decorator:body/>
        </div>

        <div id="footer">
            <footer id="footer" role="contentinfo">
                <div class="footer-wrapper clearfix">
                    <div class="region region-footer">
                        <div id="block-block-5" class="block block-block block-even">
                        </div>
                    </div>
                </div>
            </footer>
        </div>
</div>
</body>
</html>
