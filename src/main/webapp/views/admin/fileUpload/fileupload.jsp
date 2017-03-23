<%@ include file="/tld_includes.jsp"%>
	<tiles:putAttribute name="title"
		value="File Upload" />
	<tiles:putAttribute name="head">
	<script type="text/javascript" src="${path.js('jquery/jquery-1.4.2.min.js')}"></script>
		<style>
.text_filter,.select_filter {
	width: 78px;
	font-size: 11px !important;
}

<html>
<head>
<title>File Upload Form</title>
</head>
<body>
<h3>File Upload:</h3>
Select a file to upload: <br />
<form action="uploadFile" method="post"
                        enctype="multipart/form-data">
<input type="file" name="file" size="50" />
<br />
<input type="submit" value="Upload File" />
</form>
</body>
</html>