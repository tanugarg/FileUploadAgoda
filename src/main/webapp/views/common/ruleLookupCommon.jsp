<%@ include file="/tld_includes.jsp"%>

	<body>
				<div class="cod-outer">
					<div>${message}</div>
					<div>
						<br>
						<table class="serviceabilityRules">
							<tr>
								<td><span class="serchtext_bg searchBox"> SUPC </span>
								</td>
								<td><span class="serchtext_bg searchBox"> VENDOR
										CODE</span>
								</td>
								<td><span class="serchtext_bg searchBox"> CATEGORY
										URL </span></td>
								
								
								<td><span class="serchtext_bg searchBox"> SHIPPING METHOD</span>
								</td>
								
							</tr>

							<tr>
								<td><input type="text" id="supc"  name="supc" />
								</td>
								<td >
									<input type="text" id="vendorCode" style="width: 300px;" name="vendorCode" >
								</td>
								<td><select id="categoryUrl" name="categoryUrl"
									class="chzn-select-deselect  wSelect">
										<option value=""></option>
										<c:forEach
											items="${cache.getCacheByName('productCategoryInfoCache').getParentCategoryNameToProductCategoryDTO()}"
											var="categoryMap">
											<optgroup label="${categoryMap.key}">
												<c:forEach items="${categoryMap.value }" var="category">
													<option value="${category.categoryUrl}">${category.categoryName}
														- ${categoryMap.key }</option>
												</c:forEach>
											</optgroup>
										</c:forEach>
								</select>
								</td>
								
								
								<td>
								<select id="shippingMethod" name="shippingMethod"
									class="chzn-select-deselect wSelect"> 
									<option value=""></option>
									<option value="COD"> COD </option>
									<option value="STD">STD</option></select>
								</td>
								
							</tr>
							<tr>
								<td><span class="serchtext_bg searchBox"> FC Code</span>
								</td>
								<td><span class="serchtext_bg searchBox"> COURIER</span>
								<td><span class="serchtext_bg searchBox"> VENDOR TYPE</span></td>
							</tr>

							<tr>
								<td><select id="fcCode" name="fcCode" 
									class="chzn-select-deselect" style="width:195px">
										<option value=""></option>
									<c:forEach items="${cache.getCacheByName('allFcDetailsCache').getFcDetailsMap()}" var="fcDetailsMap">
										<option value='${fcDetailsMap.key}'>${fcDetailsMap.key} - ${fcDetailsMap.value.getType()}</option>
									</c:forEach>
								</select>
								</td>
								<td><select id="shippingProvider" name="shippingProvider"
									class="chzn-select-deselect wSelect">
										<option value=""></option>
										<c:forEach
											items="${cache.getCacheByName('shippingProvidersCache').shippingProviders}"
											var="shippingProvider">
											<option value="${shippingProvider.code}">${shippingProvider.name}</option>
										</c:forEach>
								</select>
								</td>
								<td> <select id="vendorType" name="vendorType"
									class="chzn-select-deselect wSelect">
									<option value=""></option>
									<c:forEach items="${vendorTypes}" var="vendorType"> 
										<option value="${vendorType}"> ${vendorType } </option>
									</c:forEach>
								</select></td>
							</tr>

							<tr>
								<td><span class="serchtext_bg searchBox"> RULE CODE</span>
								</td>
								<td colspan="2"><span class="serchtext_bg searchBox" style="width:650px"> RULE TYPE</span>
								</td>
							</tr>

							<tr>
								<td rowspan="2"><input type="text" id="ruleCode" name="ruleCode" />
								</td>
								
								<td colspan="2"><select id="ruleType" name="ruleType" multiple
									class="chzn-select-deselect" style="width:650px">
										<c:forEach
											items="${blockNames}"
											var="blockName">
											<option value="${blockName}">${blockName}</option>
										</c:forEach>
								</select>
								</td>
										
								<td rowspan="2">IS RULE ENABLED <input type="checkbox" value="enabled"
									id="enabled" title="Is Rule Enabled" checked />
									<input type="button" class="button"
									id="serviceabilityRuleLookup" style="float:right" value="Search">
								</td>
							</tr>
						</table>

						<div id="ajax-loader" align="center" style="display: none">
							<img src="${path.resources('img/ajax-loader.gif')}">
						</div>
						<br>
						<div class="serviceabilityRule">
							<div id="serviceabilityRuleGridDiv" style="width: 100%">
								<table id="serviceabilityRuleGrid">
								</table>
								<div id="serviceabilityRuleGrid_pager"></div>
							</div>
						</div>
					</div>
				</div>
	</body>
	
