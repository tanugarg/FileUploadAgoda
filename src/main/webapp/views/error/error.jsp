<%@ include file="/tld_includes.jsp"%>
<tiles:insertTemplate template="/views/layout/base.jsp">

	<c:choose>
		<c:when test="${param['code'] == '404'}">
			<tiles:putAttribute name="title"
				value="SnapDeal.com - 404 &ndash; Page not found" />
		</c:when>
		<c:otherwise>
			<c:choose>
				<c:when test="${param['code'] == '500'}">
					<tiles:putAttribute name="title"
						value="SnapDeal.com - 500 &ndash; Internal Server Error" />
				</c:when>
				<c:when test="${param['code'] == '403'}">
					<tiles:putAttribute name="title"
						value="SnapDeal.com - 403 &ndash; Access Denied" />
				</c:when>
				<c:otherwise>
					<tiles:putAttribute name="title" value="SnapDeal.com - 401 &ndash;" />
				</c:otherwise>
			</c:choose>
		</c:otherwise>
	</c:choose>


	<tiles:putAttribute name="body">
		<div id="internal-content">
			<c:choose>
				<c:when test="${param['code'] == '404'}">
					<div class="error_pic">
						<img src="${path.resources('img/error/error404.jpg')}" width="320">
						<div class="clear"></div>
					</div>
					<div class="error-cont">
						<div class="error-text">
							The page you were looking for appears to have been moved, deleted
							or does not exists. <br> Please click the links below to go
							to relevant section of the website.
						</div>
						<div class="error-text-line">&nbsp;</div>
						<div class="clear"></div>
					</div>

				</c:when>
				<c:otherwise>
					<div align="center">
						<c:choose>
							<c:when test="${param['code'] == '500'}">
								<img src="${path.resources('img/error/error500.jpg')}">
							</c:when>
							<c:when test="${param['code'] == '403'}">
								<img src="${path.resources('img/error/error403.jpg')}">
							</c:when>
							<c:otherwise>
								<img src="${path.resources('img/error/error401.jpg')}">
							</c:otherwise>
						</c:choose>
					</div>
				</c:otherwise>
			</c:choose>
		</div>
	</tiles:putAttribute>
	<tiles:putAttribute name="deferredScript">
		<script type="text/javascript"
			src="${path.js('omniture/sd_scode.js')}"></script>
		<!-- ominture script -->
		<sd:omnitureScript pageName="errorPage" />
	</tiles:putAttribute>
</tiles:insertTemplate>