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

    /* GLOBAL STYLES
    -------------------------------------------------- */
    /* Padding below the footer and lighter body text */

    body {
      padding-bottom: 40px;
      color: #5a5a5a;
    }



    /* CUSTOMIZE THE NAVBAR
    -------------------------------------------------- */

    /* Special class on .container surrounding .navbar, used for positioning it into place. */
    .navbar-wrapper {
      position: absolute;
      top: 0;
      left: 0;
      right: 0;
      z-index: 10;
      margin-top: 20px;
      margin-bottom: -90px; /* Negative margin to pull up carousel. 90px is roughly margins and height of navbar. */
    }
    .navbar-wrapper .navbar {

    }

    /* Remove border and change up box shadow for more contrast */
    .navbar .navbar-inner {
      border: 0;
      -webkit-box-shadow: 0 2px 10px rgba(0,0,0,.25);
         -moz-box-shadow: 0 2px 10px rgba(0,0,0,.25);
              box-shadow: 0 2px 10px rgba(0,0,0,.25);
    }

    /* Downsize the brand/project name a bit */
    .navbar .brand {
      padding: 14px 20px 16px; /* Increase vertical padding to match navbar links */
      font-size: 16px;
      font-weight: bold;
      text-shadow: 0 -1px 0 rgba(0,0,0,.5);
    }

    /* Navbar links: increase padding for taller navbar */
    .navbar .nav > li > a {
      padding: 15px 20px;
    }

    /* Offset the responsive button for proper vertical alignment */
    .navbar .btn-navbar {
      margin-top: 10px;
    }



    /* CUSTOMIZE THE CAROUSEL
    -------------------------------------------------- */

    /* Carousel base class */
    .carousel {
      margin-bottom: 60px;
    }

    .carousel .container {
      position: relative;
      z-index: 9;
    }

    .carousel-control {
      height: 80px;
      margin-top: 0;
      font-size: 120px;
      text-shadow: 0 1px 1px rgba(0,0,0,.4);
      background-color: transparent;
      border: 0;
      z-index: 10;
    }

    .carousel .item {
      height: 500px;
    }
    .carousel img {
      position: absolute;
      top: 0;
      left: 0;
      min-width: 100%;
      height: 500px;
    }

    .carousel-caption {
      background-color: transparent;
      position: static;
      max-width: 550px;
      padding: 0 20px;
      margin-top: 200px;
    }
    .carousel-caption h1,
    .carousel-caption .lead {
      margin: 0;
      line-height: 1.25;
      color: #fff;
      text-shadow: 0 1px 1px rgba(0,0,0,.4);
    }
    .carousel-caption .btn {
      margin-top: 10px;
    }



    /* MARKETING CONTENT
    -------------------------------------------------- */

    /* Center align the text within the three columns below the carousel */
    .marketing .span4 {
      text-align: center;
    }
    .marketing h2 {
      font-weight: normal;
    }
    .marketing .span4 p {
      margin-left: 10px;
      margin-right: 10px;
    }


    /* Featurettes
    ------------------------- */

    .featurette-divider {
      margin: 80px 0; /* Space out the Bootstrap <hr> more */
    }
    .featurette {
      padding-top: 120px; /* Vertically center images part 1: add padding above and below text. */
      overflow: hidden; /* Vertically center images part 2: clear their floats. */
    }
    .featurette-image {
      margin-top: -120px; /* Vertically center images part 3: negative margin up the image the same amount of the padding to center it. */
    }

    /* Give some space on the sides of the floated elements so text doesn't run right into it. */
    .featurette-image.pull-left {
      margin-right: 40px;
    }
    .featurette-image.pull-right {
      margin-left: 40px;
    }

    /* Thin out the marketing headings */
    .featurette-heading {
      font-size: 50px;
      font-weight: 300;
      line-height: 1;
      letter-spacing: -1px;
    }



    /* RESPONSIVE CSS
    -------------------------------------------------- */

    @media (max-width: 979px) {

      .container.navbar-wrapper {
        margin-bottom: 0;
        width: auto;
      }
      .navbar-inner {
        border-radius: 0;
        margin: -20px 0;
      }

      .carousel .item {
        height: 500px;
      }
      .carousel img {
        width: auto;
        height: 500px;
      }

      .featurette {
        height: auto;
        padding: 0;
      }
      .featurette-image.pull-left,
      .featurette-image.pull-right {
        display: block;
        float: none;
        max-width: 40%;
        margin: 0 auto 20px;
      }
    }


    @media (max-width: 767px) {

      .navbar-inner {
        margin: -20px;
      }

      .carousel {
        margin-left: -20px;
        margin-right: -20px;
      }
      .carousel .container {

      }
      .carousel .item {
        height: 300px;
      }
      .carousel img {
        height: 300px;
      }
      .carousel-caption {
        width: 65%;
        padding: 0 70px;
        margin-top: 100px;
      }
      .carousel-caption h1 {
        font-size: 30px;
      }
      .carousel-caption .lead,
      .carousel-caption .btn {
        font-size: 18px;
      }

      .marketing .span4 + .span4 {
        margin-top: 40px;
      }

      .featurette-heading {
        font-size: 30px;
      }
      .featurette .lead {
        font-size: 18px;
        line-height: 1.5;
      }

    }
    </style>
</head>
<body>
<% if(session.getAttribute("LoggedInUser")==null) {%>
	<%@ include file="LoginHeader.jsp"%> 
<% } else { %>
	<%@ include file="header.jsp"%> 
<%} %>
      	<div class="span9">
			<div class="hero-unit">
			            <h1>Welcome to FileHaven!</h1>

			</div>
			<div class="row-fluid" >
				<div class="content_space">
					<div id="examples_outer">
					<hr class="featurette-divider">
						<h2 class="featurette-heading"> A Little Introduction to <span class="muted">FileHaven</span></h2>
						<p class="lead"><span style="font-weight:bold;text-decoration:underline">FileHaven</span> 
						is a safe and secured file hosting web application which allow you 
						and your company have a platform to store and share the things among your company.
						</p>
						<p><a href="#" class="btn">More about FileHaven</a></p>
						
						<hr class="featurette-divider">
						 
						<h2 class="featurette-heading"> Need to start immediately? <span class="muted">Look no further</span></h2>
						<p class="lead">
						To get started, please contact us at our hotline: 000-0000-0000 <br />
						Alternatively, you may leave us an e-mail at: example@mail.com <br /><br />
						
						We will provide you with the CEO credentials immediately so that you can
						start work immediately without any delays!
						</p>
						
						<hr class="featurette-divider">
						
						<h2 class="featurette-heading">Our Pricing Rate</h2>
						<p class="lead">
						Currently, we are offering 3 types of packages.
						<br />
						<table>
						<tr> <td>Basic </td><td>- 100 GB Storage at $.99 per month.</td></tr>
						<tr> <td>Pro </td><td>- 5 TB Storage at $34.99 per month.</td></tr>
						<tr> <td>Industrial </td><td>- 15 TB Storage at $59.99 per month.</td></tr>
						</table>
						</p>
						
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
			</div>
			</div>
<%@ include file="footer.jsp"%>
</body>
</html>