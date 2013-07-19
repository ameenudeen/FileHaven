<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>FileHaven - Home</title>
    <link href="resources/css/bootstrap.css" rel="stylesheet" type="text/css" />
    <link href="resources/css/bootstrap-responsive.css" rel="stylesheet" type="text/css" />
    <link href="resources/css/application.css" rel="stylesheet" type="text/css" />
    <link href="resources/css/sliderman.css" rel="stylesheet" type="text/css" />
    <script src="resources/js/sliderman.1.3.7.js"></script>
	<script src="resources/js/jquery-1.9.1.js"></script>
	<script src="resources/js/jquery-ui-1.9.1.js"></script>  
<style>
.subtitle{
font-weight:bold;
text-decoration:underline;
}
.content_space{
padding:60px;
			min-height:400px;
			background-color:#ECECE8;
			-webkit-border-radius: 6px;
			-moz-border-radius: 6px;
			border-radius: 6px;
			background-image: linear-gradient(bottom, rgb(242,239,242) 23%, rgb(240,240,240) 62%, rgb(237,237,237) 81%);
			background-image: -o-linear-gradient(bottom, rgb(242,239,242) 23%, rgb(240,240,240) 62%, rgb(237,237,237) 81%);
			background-image: -moz-linear-gradient(bottom, rgb(242,239,242) 23%, rgb(240,240,240) 62%, rgb(237,237,237) 81%);
			background-image: -webkit-linear-gradient(bottom, rgb(242,239,242) 23%, rgb(240,240,240) 62%, rgb(237,237,237) 81%);
			background-image: -ms-linear-gradient(bottom, rgb(242,239,242) 23%, rgb(240,240,240) 62%, rgb(237,237,237) 81%);
			background-image: -webkit-gradient(
				linear,
				left bottom,
				left top,
				color-stop(0.23, rgb(242,239,242)),
				color-stop(0.62, rgb(240,240,240)),
				color-stop(0.81, rgb(237,237,237))
			);
}
</style>
</head>
<body>
<%@ include file="header.jsp"%> 
      	<div class="span9">
			<div class="hero-unit">
			            <h1>Welcome to FileHaven!</h1>
			            <p><span style="font-weight:bold;text-decoration:underline">FileHaven</span> is a safe and secured file hosting web application which allow you and your company have a platform to store and share the things among your company</p>
			           <p><a href="#" class="btn">More about FileHaven</a></p> 
			            
			</div>
			<div class="row-fluid" >
				<div class="content_space">
					<div id="examples_outer">
						<h2>Announcement</h2>
			
						<div id="slider_container_1">
			
							<div id="SliderName">
								<img src="resources/img/sliderman/1.jpg" />
								<div class="SliderNameDescription"><Strong></Strong>asd</div>
								<img src="resources/img/sliderman/2.jpg" />
								<div class="SliderNameDescription"><Strong></Strong>asd</div>
								<img src="resources/img/sliderman/3.jpg" />
								<div class="SliderNameDescription"><Strong></Strong>asd</div>
								<img src="resources/img/sliderman/4.jpg" />
								<div class="SliderNameDescription"><Strong></Strong>asd</div>
							</div>
							<div id="SliderNameNavigation"></div>
							<script type="text/javascript">
		
							// we created new effect and called it 'demo01'. We use this name later.
							Sliderman.effect({name: 'demo01', cols: 10, rows: 5, delay: 10, fade: true, order: 'straight_stairs'});
		
							var demoSlider = Sliderman.slider({container: 'SliderName', width: 640, height: 300, effects: 'demo01',
							display: {
								pause: true, // slider pauses on mouseover
								autoplay: 3000, // 3 seconds slideshow
								always_show_loading: 200, // testing loading mode
								description: {background: '#ffffff', opacity: 0.5, height: 50, position: 'bottom'}, // image description box settings
								loading: {background: '#000000', opacity: 0.2, image: 'img/loading.gif'}, // loading box settings
								buttons: {opacity: 1, prev: {className: 'SliderNamePrev', label: ''}, next: {className: 'SliderNameNext', label: ''}}, // Next/Prev buttons settings
								navigation: {container: 'SliderNameNavigation', label: '&nbsp;'} // navigation (pages) settings
							}});
		
						</script>
					</div>
				</div>
			</div>
<%@ include file="footer.jsp"%>
</body>
</html>